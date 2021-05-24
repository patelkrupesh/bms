package com.bms.services;

import com.bms.adaptors.BookingAdapter;
import com.bms.dto.BookTicketInputDto;
import com.bms.dto.BookingDto;
import com.bms.dto.BookingEntities;
import com.bms.enums.UserType;
import com.bms.model.*;
import com.bms.repositories.*;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.*;

@Service
public class BookingService {

//        @Autowired
        private BookingRepository bookingRepository;
//        @Autowired
        private ShowRepository showRepository;
//        @Autowired
        private UserRepository userRepository;
//        @Autowired
        private SeatsRepository seatsRepository;


        public BookingDto bookTicket(BookTicketInputDto bookTicketInput) throws Exception{
            BookingEntities entitiesToSave = validateAndGetEntitiesToSave(bookTicketInput);
            BookingDto bookingDto = syncBookTicket(bookTicketInput, entitiesToSave);
            return bookingDto;
        }

    private BookingEntities validateAndGetEntitiesToSave(BookTicketInputDto bookTicketInput) throws Exception{
            BookingEntities result = new BookingEntities();
            Optional<ShowEntity> optionalShowEntity = showRepository.findById(bookTicketInput.getShowId());
            if(!optionalShowEntity.isPresent()){
                throw new Exception("Show id is invalid. id : "+ bookTicketInput.getShowId());
            }
            UserEntity userEntity = userRepository.findUserByUsername(bookTicketInput.getUsername());
            if(userEntity == null){
                throw new Exception("User not found. username : "+ bookTicketInput.getUsername());
            }
            if(!userEntity.getType().equals(UserType.CUSTOMER) && !userEntity.getType().equals(UserType.BOXOFFICE)){
                throw new Exception("User does not have privileges. username : "+ bookTicketInput.getUsername());
            }
            if(bookTicketInput.getSeatIds().size()<=0){
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
                    .user(userEntity)
                    .build();

            result.setBookingEntity(bookingEntity);
            result.setSeatsEntityList(seatsEntityList);
            return result;
        }

        @Transactional
        public synchronized BookingDto syncBookTicket(BookTicketInputDto bookTicketInput,
                                                      BookingEntities entitiesToSave)
                throws Exception{

            Iterable<SeatsEntity> seatsEntities = seatsRepository.findAllById(bookTicketInput.getSeatIds());
            List<SeatsEntity> seatsEntityList = new ArrayList<>();
            seatsEntities.forEach(seat -> {
                SeatsEntity tempEntity = seat;
                if(seat.isBooked()){
                    throw new RuntimeException("Selected seat is already booked.");
                }
            });

            BookingEntity dbData = bookingRepository.save(entitiesToSave.getBookingEntity());
            entitiesToSave.getSeatsEntityList().forEach(seatsEntity -> seatsEntity.setBooking(dbData));
            seatsRepository.saveAll(entitiesToSave.getSeatsEntityList());
            return BookingAdapter.toDto(dbData);
        }

        public BookingDto getBooking(Long bookingId) throws Exception{
            Optional<BookingEntity> dbData = bookingRepository.findById(bookingId);
            if(!dbData.isPresent()){
                throw new Exception("Unable to get the booking. Id : "+ bookingId);
            }
            return BookingAdapter.toDto(dbData.get());
        }
}