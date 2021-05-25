package com.bms.services;

import com.bms.adaptors.BookingAdapter;
import com.bms.adaptors.PaymentAdapter;
import com.bms.dto.BookTicketInputDto;
import com.bms.dto.BookingDto;
import com.bms.dto.BookingEntities;
import com.bms.dto.PaymentDto;
import com.bms.enums.BookingStatus;
import com.bms.enums.PaymentStatus;
import com.bms.enums.UserType;
import com.bms.model.*;
import com.bms.repositories.*;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import springfox.documentation.spring.web.PropertySourcedMapping;

import java.awt.print.Book;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
@PropertySource("classpath:application.properties")
public class BookingService {
    final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ShowRepository showRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SeatsRepository seatsRepository;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private BookingExpiryRepository bookingExpiryRepository;
    @Value("${payment.gateway.default:STRIPE}")
    private String paymentGateway;
    @Value("${payment.timeout:10}")
    private int paymentTimeoutInterval;

    public PaymentDto bookTicket(BookTicketInputDto bookTicketInput) throws Exception {
        BookingEntities entitiesToSave = validateAndGetEntitiesToSave(bookTicketInput);
        PaymentDto paymentDto = syncBookTicket(bookTicketInput, entitiesToSave);
        //startPayment(paymentDto); -- register payment here with gateway and complete the payment from UI.
        return paymentDto;
    }

    // ## CASE : 1 : immediate responce from payment
    // ## description : UI will handle the payment part (entering card details, otp etc)
    //                  and after that based on payment status it will call updateBooking

    // note : payment status field will be already set as per outcome.

    public BookingDto updateBooking(PaymentDto paymentDto) {
        BookingDto result = null;
        // verify the payment status with gateway once
        if (paymentDto.getStatus().equals(PaymentStatus.SUCCESSFUL)) {
            result = updateBookingOnPaymentSuccess(paymentDto);
        } else if (paymentDto.getStatus().equals(PaymentStatus.FAILED)) {
            result = rollbackBookingOnPaymentFailure(paymentDto);
        }
        return result;
    }

    @Transactional
    public BookingDto updateBookingOnPaymentSuccess(PaymentDto paymentDto) {
        BookingEntity bookingEntity = BookingAdapter.toEntity(paymentDto.getBooking());
        bookingEntity.setStatus(BookingStatus.SUCCESSFUL);
        bookingEntity = bookingRepository.save(bookingEntity);
        Iterable<BookingExpiryEntity> bookingExpiryEntities = bookingExpiryRepository.findBookingExpiryEntitiesByBooking(bookingEntity.getId());
        bookingExpiryRepository.deleteAll(bookingExpiryEntities);
        return BookingAdapter.toDto(bookingEntity);
    }

    @Transactional
    public BookingDto rollbackBookingOnPaymentFailure(PaymentDto paymentDto) {
        BookingEntity bookingEntity = BookingAdapter.toEntity(paymentDto.getBooking());
        bookingEntity.setStatus(BookingStatus.FAILED);
        bookingEntity = bookingRepository.save(bookingEntity);

        Iterable<SeatsEntity> seatsEntities = seatsRepository.findSeatsByBooking(paymentDto.getBooking().getId());
        seatsEntities.forEach(seatsEntity -> {
            seatsEntity.setBooked(false);
            seatsEntity.setBooking(null);
        });
        seatsRepository.saveAll(seatsEntities);
        Iterable<BookingExpiryEntity> bookingExpiryEntities = bookingExpiryRepository.findBookingExpiryEntitiesByBooking(bookingEntity.getId());
        bookingExpiryRepository.deleteAll(bookingExpiryEntities);
        return BookingAdapter.toDto(bookingEntity);
    }

    private BookingEntities validateAndGetEntitiesToSave(BookTicketInputDto bookTicketInput) throws Exception {
        BookingEntities result = new BookingEntities();
        Optional<ShowEntity> optionalShowEntity = showRepository.findById(bookTicketInput.getShowId());
        if (!optionalShowEntity.isPresent()) {
            throw new Exception("Show id is invalid. id : " + bookTicketInput.getShowId());
        }
        UserEntity userEntity = userRepository.findUserByUsername(bookTicketInput.getUsername());
        if (userEntity == null) {
            throw new Exception("User not found. username : " + bookTicketInput.getUsername());
        }
        if (!userEntity.getType().equals(UserType.CUSTOMER) && !userEntity.getType().equals(UserType.BOXOFFICE)) {
            throw new Exception("User does not have privileges. username : " + bookTicketInput.getUsername());
        }
        if (bookTicketInput.getSeatIds().size() <= 0) {
            throw new Exception("Please select atleast one seat.");
        }

        Iterable<SeatsEntity> seatsEntities = seatsRepository.findAllById(bookTicketInput.getSeatIds());

        List<SeatsEntity> seatsEntityList = new ArrayList<>();
        double amount = 0.0;
        for (SeatsEntity seatsEntity : seatsEntities) {
            seatsEntity.setBooked(true);
            amount += seatsEntity.getRate();
            seatsEntityList.add(seatsEntity);
        }

        BookingEntity bookingEntity = BookingEntity.builder()
                .bookingtime(new Date())
                .allottedSeats(StringUtils.join(seatsEntities, ","))
                .amount(amount)
                .show(optionalShowEntity.get())
                .status(BookingStatus.IN_PROGRESS)
                .user(userEntity)
                .build();

        result.setBookingEntity(bookingEntity);
        result.setSeatsEntityList(seatsEntityList);
        return result;
    }

    @Transactional
    public synchronized PaymentDto syncBookTicket(BookTicketInputDto bookTicketInput,
                                                  BookingEntities entitiesToSave)
            throws Exception {

        Iterable<SeatsEntity> seatsEntities = seatsRepository.findAllById(bookTicketInput.getSeatIds());
        List<SeatsEntity> seatsEntityList = new ArrayList<>();
        seatsEntities.forEach(seat -> {
            SeatsEntity tempEntity = seat;
            if (seat.isBooked()) {
                throw new RuntimeException("Selected seat is already booked.");
            }
        });

        BookingEntity dbData = bookingRepository.save(entitiesToSave.getBookingEntity());
        entitiesToSave.getSeatsEntityList().forEach(seatsEntity -> seatsEntity.setBooking(dbData));
        seatsRepository.saveAll(entitiesToSave.getSeatsEntityList());
        PaymentDto paymentDto = paymentService.createPayment(PaymentDto.builder()
                .amount(dbData.getAmount())
                .gateway(paymentGateway)
                .status(PaymentStatus.NOT_STARTED)
                .booking(BookingAdapter.toDto(dbData))
                .build());

        long curTimeInMs = new Date().getTime();
        Date bookingExpiryTime = new Date(curTimeInMs + (paymentTimeoutInterval * ONE_MINUTE_IN_MILLIS));
        BookingExpiryEntity bookingExpiryEntity = BookingExpiryEntity.builder()
                .expirytime(bookingExpiryTime)
                .booking(dbData)
                .build();

        return paymentDto;
    }

    public BookingDto getBooking(Long bookingId) throws Exception {
        Optional<BookingEntity> dbData = bookingRepository.findById(bookingId);
        if (!dbData.isPresent()) {
            throw new Exception("Unable to get the booking. Id : " + bookingId);
        }
        return BookingAdapter.toDto(dbData.get());
    }

    // ## CASE : 2 : abandoned payment / gateway timeout cases for payment
    // ## description : This cron job will get executed every minute.
    //                  We will get all the expired payment, will check the status
    //                  of those payment and accordingly either mark booking as
    //                  successful or rollback the booking.

    @Scheduled(fixedDelay = 1000)
    public void checkAbandonedBookingStatus() {
        Iterable<BookingExpiryEntity> bookingExpiryEntities = bookingExpiryRepository.findExpiredBookings();
        bookingExpiryEntities.forEach(bookingExpiryEntity -> checkAndUpdate(bookingExpiryEntity));
    }

    @Transactional
    public void returnBooking(PaymentDto paymentDto) {
        try {
            paymentDto.setStatus(PaymentStatus.FAILED);
            paymentDto = paymentService.updatePayment(paymentDto);
            //register a return request with gateway
            //requestRollBackPayment(paymentDto);
            rollbackBookingOnPaymentFailure(paymentDto);
        } catch (Exception e) {
            System.out.println("Failed to return the payment for booking id: " +
                    paymentDto.getId() + ". will try again in some time.");
        }
    }

    public void checkAndUpdate(BookingExpiryEntity bookingExpiryEntity) {
        PaymentDto paymentDto = PaymentAdapter.toDto(paymentService.getPaymentEntityFromBookingId(bookingExpiryEntity.getId()));
        if (paymentDto.getBooking().equals(BookingStatus.SUCCESSFUL)) {
            updateBookingOnPaymentSuccess(paymentDto);
        } else if (paymentDto.getBooking().equals(BookingStatus.FAILED)) {
            rollbackBookingOnPaymentFailure(paymentDto);
        } else {
            returnBooking(paymentDto);
        }
    }
}