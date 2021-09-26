package com.breakbooking.eventbookingapi.VO;

import com.breakbooking.eventbookingapi.model.Booking;
import com.breakbooking.eventbookingapi.model.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingVO {

    private Boolean success;
    private Integer code;
    private String message;
    private Booking booking;
    private Event event;
    private BigDecimal paidAmount;
    private LocalDateTime bookedAt;

}
