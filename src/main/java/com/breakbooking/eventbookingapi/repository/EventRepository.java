package com.breakbooking.eventbookingapi.repository;

import java.time.LocalDate;
import java.util.List;

import com.breakbooking.eventbookingapi.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.breakbooking.eventbookingapi.model.Event;

public interface EventRepository extends MongoRepository<Event, String>{
	
	@Query(value="{ 'startTime' : { $gte: ?0, $lte: ?1 } }", fields = "{'title':1,'startTime':1,'endTime':1,'_id':0}")
	List<Event> findByTitleBetween(LocalDate start, LocalDate end);

	Event findByEid(String eid);

	//Event findEventbookingById(String bookingId);

	//Event findByEventbookingId(String bookingId);

	List<Event> findEventsByBookingId(String id);


}
