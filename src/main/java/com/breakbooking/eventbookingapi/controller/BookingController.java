package com.breakbooking.eventbookingapi.controller;

import com.breakbooking.eventbookingapi.VO.BookingVO;
import com.breakbooking.eventbookingapi.VO.ResponseTemplateVO;
import com.breakbooking.eventbookingapi.model.Booking;
import com.breakbooking.eventbookingapi.service.BookingService;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@CrossOrigin(origins={"http://localhost:4200", "http://localhost",
        "http://192.168.99.101", "http://ec2-3-104-154-174.ap-southeast-2.compute.amazonaws.com",
        "http://break-booking.online", "https://break-booking.online",
        "http://www.break-booking.online", "https://www.break-booking.online"})
@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }


    /* FIND ALL EVENT BOOKINGS */


    @GetMapping("/list")
    @ApiOperation(value = "Finds all Event Bookings for all events, including history bookings")
    public ResponseEntity<List<Booking>> findAllEventBookings(){

        List<Booking> bookings = bookingService.findAllBookings();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }


    /* FIND ALL EVENT BOOKINGS OF AN EVENT */


    @GetMapping("/event/{eid}")
    @ApiOperation(value = "Finds all Event Bookings of an Event")
    public List<Booking> findBookingsOfEvent(@PathVariable("eid") String eid ){
        return bookingService.findBookingsOfEvent(eid);
    }


    /* FIND ALL BOOKINGS OF SPECIFIC USER */


    @GetMapping("/user/{id}")
    @ApiOperation(value = "Finds all Bookings for specific user ")
    public ResponseEntity<ResponseTemplateVO> findBookingsWithUser(@PathVariable("id") String id ){
        return new ResponseEntity<ResponseTemplateVO>(bookingService.findBookingsWithUser(id),HttpStatus.OK);
    }


    /* FIND AN EVENT BOOKING DETAIL BY ID */


    @GetMapping("/find/{id}")
    @ApiOperation(value = "Finds an Event booking detail by id ")
    public Booking findBookingById(@PathVariable("id") String id ){
        return bookingService.findBookingById(id);
    }



    /* CREATE A NEW BOOKING */


    @PostMapping("/new")
    @ApiOperation(value = "Creates a new Booking")
    public ResponseEntity<BookingVO> addBooking(@Valid @RequestBody Booking newBooking,
                                                BindingResult result)  {

        if(result.hasErrors()) {
            System.out.println(result);
            throw  new IllegalStateException("Bad request");
        }
        return new ResponseEntity<BookingVO>(bookingService.addBooking(newBooking),HttpStatus.CREATED);

    }


    /* DELETE AN EVENT BOOKING */


    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Deletes an Event Booking by Id")
    public ResponseEntity<?> deleteBooking(@PathVariable ("id") String id) {

        return new ResponseEntity<>(bookingService.deleteBooking(id),HttpStatus.OK);

    }


    /* DELETE All BOOKINGS */


    @DeleteMapping("/deleteDb")
    @ApiOperation(value = "Deletes all event bookings")
    public ResponseEntity<?> deleteAll() {

        return new ResponseEntity<>(bookingService.deleteAllBookings(),HttpStatus.OK);

    }

}
