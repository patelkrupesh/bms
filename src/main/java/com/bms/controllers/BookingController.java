package com.bms.controllers;

import com.bms.dto.BookTicketInputDto;
import com.bms.dto.BookingDto;
import com.bms.dto.PaymentDto;
import com.bms.services.BookingService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/bookings")
@Api(value = "Booking Management")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping("bookTicket")
    public ResponseEntity<PaymentDto> bookTicket(@RequestBody BookTicketInputDto bookTicketInputDto) {
        PaymentDto savedDto = null;
        try{
            savedDto = bookingService.bookTicket(bookTicketInputDto);
        }catch (Exception e) {
            System.out.println("Got exception while adding theatre : " + e.getMessage());
            return ResponseEntity.badRequest().body(savedDto);
        }
        return ResponseEntity.ok(savedDto);
    }

    @PostMapping("updateAfterPayment")
    public ResponseEntity<BookingDto> updateBooking(@RequestBody PaymentDto paymentDto) {
        BookingDto savedDto = null;
        try{
            savedDto = bookingService.updateBooking(paymentDto);
        }catch (Exception e) {
            System.out.println("Got exception while adding theatre : " + e.getMessage());
            return ResponseEntity.badRequest().body(savedDto);
        }
        return ResponseEntity.ok(savedDto);
    }

    @GetMapping("bookingData")
    public ResponseEntity<BookingDto> getBookingData(@RequestParam(name = "bookingId", required = true) Long bookingId) {
        BookingDto result = null;
        try{
            result = bookingService.getBooking(bookingId);
        }catch (Exception e) {
            System.out.println("Got exception while fetching theatres : " + e.getMessage());
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }
}