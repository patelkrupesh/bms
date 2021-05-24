package com.bms.controllers;

import com.bms.dto.BookingDto;
import com.bms.dto.UserDto;
import com.bms.services.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
@Api(value = "User Management")
public class UserController{
    @Autowired
    private UserService userService;

    @PostMapping("signUp")
    public ResponseEntity<Boolean> userSignUp(@RequestBody UserDto userDto) {
        boolean result = false;
        UserDto savedDto = null;
        try{
            savedDto = userService.userSignUp(userDto);
        }catch (Exception e) {
            System.out.println("Got exception while adding user : " + e.getMessage());
            return ResponseEntity.badRequest().body(false);
        }
        if(savedDto != null){
            result = true;
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("editUser")
    public ResponseEntity<UserDto> editSignUp(@RequestBody UserDto userDto) {
        boolean result = false;
        UserDto savedDto = null;
        try{
            savedDto = userService.editSignUp(userDto);
        }catch (Exception e) {
            System.out.println("Got exception while editing user : " + e.getMessage());
            return ResponseEntity.badRequest().body(savedDto);
        }
        return ResponseEntity.ok(savedDto);
    }

    @GetMapping("login")
    public ResponseEntity<UserDto> login(@RequestParam(name = "username", required = true) String username,
                                         @RequestParam(name = "password", required = true) String password) {
        UserDto result = null;
        try{
            result = userService.login(username, password);
        }catch (Exception e) {
            System.out.println("Got exception while logging in user : " + e.getMessage());
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("allBookings")
    public ResponseEntity<List<BookingDto>> getAllBookingsData(@RequestParam(name = "username", required = true) String username) {
        // ideally we will not pass the username ,
        // but we will pass the user authority token
        // and from token we will extract the username

        List<BookingDto> result = null;
        try{
            result = userService.getAllBookingData(username);
        }catch (Exception e) {
            System.out.println("Got exception while fetching booking data for user : "
                    + username + ". Exception :" + e.getMessage());
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }
}