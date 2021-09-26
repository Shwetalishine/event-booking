package com.breakbooking.eventbookingapi.service;

import com.breakbooking.eventbookingapi.VO.BookingVO;
import com.breakbooking.eventbookingapi.VO.ResponseTemplateVO;
import com.breakbooking.eventbookingapi.VO.ResponseVO;
import com.breakbooking.eventbookingapi.exception.ResourceNotFoundException;
import com.breakbooking.eventbookingapi.model.Booking;
import com.breakbooking.eventbookingapi.model.Event;
import com.breakbooking.eventbookingapi.repository.BookingRepository;
import com.breakbooking.eventbookingapi.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.sound.sampled.FloatControl;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private BookingRepository bookingRepository;
    private EventRepository eventRepository;
    private EmailSenderService emailSenderService;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, EventRepository eventRepository, EmailSenderService emailSenderService) {
        this.bookingRepository = bookingRepository;
        this.eventRepository = eventRepository;
        this.emailSenderService = emailSenderService;
    }

    @Override
    public List<Booking> findAllBookings() {
        return  bookingRepository.findAll();
    }

    @Override
    public Booking findBookingById(String id) {

            return bookingRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Booking by id "+id+" not found"));
    }

    @Override
    public ResponseTemplateVO findBookingsWithUser(String id) {

        ResponseTemplateVO vo=new ResponseTemplateVO();
        List<Booking> bookingsWithUser=bookingRepository.findByUserId(id);
        List<Event> events=eventRepository.findEventsByBookingId(id);
      //  vo.setBooking(booking);
        vo.setBooking(bookingsWithUser);
        vo.setCode(200);
        vo.setMessage("Bookings of user with "+id);
        vo.setSuccess(Boolean.TRUE);
        vo.setTimestamp(LocalDateTime.now());
        return vo;


    }


    @Override
    public List<Booking> findBookingsOfEvent(String eid) {

        if(!eventRepository.existsById(eid)) {
            throw new ResourceNotFoundException("Event by id "+eid+" was not found");
        }
        return bookingRepository.findByEventEid(eid);
    }


    @Override
    public BookingVO addBooking(Booking newBooking) {

        Event event=eventRepository.findById(newBooking.getEventEid()).orElseThrow(()->new ResourceNotFoundException("Event by id "+ newBooking.getEventEid() +" was not found"));

        bookingRepository.save(newBooking);
        event.setBooking(newBooking);
        eventRepository.save(event);
        if(newBooking.getId()!=null){
            emailSenderService.sendBookingConfirmationEmail(newBooking.getBookerEmail(),
                    "Thanks "+newBooking.getBookerName()+"! Your booking in Break booking is confirmed!",
                    "Break booking");
        }
        BookingVO vo=new BookingVO();
        vo.setBooking(newBooking);
        vo.setBookedAt(LocalDateTime.now());
        vo.setMessage("You successfully created your booking!");
        vo.setSuccess(Boolean.TRUE);
        vo.setEvent(event);
        vo.setPaidAmount(event.getPrice());
        vo.setCode(201);
        return vo;
    }

    @Override
    public Map<String, Boolean> deleteBooking(String id) {

        Booking booking = bookingRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Booking by id "+ id +" was not found"));

        Event event=eventRepository.findByEid(booking.getEventEid());

        event.cancelBooking(booking);

        System.out.println("Your booking has been successfully cancelled!");
        bookingRepository.deleteById(id);
        eventRepository.save(event);

        Map<String, Boolean> response =new HashMap<>();
        response.put("Booking deleted with success!", Boolean.TRUE);
        return response;

    }


    /* DELETE ALL BOOKINGS  */


    @Override
    public Map<String, Boolean> deleteAllBookings() {

        Map<String, Boolean> response =new HashMap<>();
        response.put("All Bookings deleted with success!", Boolean.TRUE);
        bookingRepository.deleteAll();

        return response;
    }
}
