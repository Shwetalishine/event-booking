package com.breakbooking.eventbookingapi.service;

import com.breakbooking.eventbookingapi.VO.BookingVO;
import com.breakbooking.eventbookingapi.VO.ResponseTemplateVO;

import com.breakbooking.eventbookingapi.model.Booking;

import java.util.List;
import java.util.Map;


public interface BookingService {

    List<Booking> findAllBookings();

    Booking findBookingById(String id);

    ResponseTemplateVO findBookingsWithUser(String id);

    List<Booking> findBookingsOfEvent(String eid);

    BookingVO addBooking(Booking newBooking) ;

    Map<String,Boolean> deleteBooking(String id);

    Map<String,Boolean> deleteAllBookings();


}
