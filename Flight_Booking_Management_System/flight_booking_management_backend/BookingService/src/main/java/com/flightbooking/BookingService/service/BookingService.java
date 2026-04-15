package com.flightbooking.BookingService.service;

import com.flightbooking.BookingService.client.SeatInventoryClient;
import com.flightbooking.BookingService.dto.AncillaryRequest;
import com.flightbooking.BookingService.dto.BookingEventDto;
import com.flightbooking.BookingService.dto.BookingResponse;
import com.flightbooking.BookingService.dto.CreateBookingRequest;
import com.flightbooking.BookingService.entity.Booking;
import com.flightbooking.BookingService.entity.BookingAncillary;
import com.flightbooking.BookingService.entity.SeatReservation;
import com.flightbooking.BookingService.event.BookingEventPublisher;
import com.flightbooking.BookingService.factory.BookingFactory;
import com.flightbooking.BookingService.repository.BookingAncillaryRepository;
import com.flightbooking.BookingService.repository.BookingRepository;
import com.flightbooking.BookingService.repository.SeatReservationRepository;
import com.flightbooking.BookingService.state.RequestedState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    private static final Logger log = LoggerFactory.getLogger(BookingService.class);

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private SeatReservationRepository seatReservationRepository;
    @Autowired
    private SeatInventoryClient seatInventoryClient;
    @Autowired
    private BookingAncillaryRepository bookingAncillaryRepository;
    @Autowired
    private BookingEventPublisher eventPublisher;

    public BookingResponse createBooking(CreateBookingRequest request) {
    	Booking booking = BookingFactory.create(
    	        request.getUserId(),
    	        request.getFlightId(),
    	        request.getTotalAmount(),
    	        request.getPassengerName(),  
    	        request.getPassengerEmail(),
    	        request.getPassengerPhone()
    	);
        booking = bookingRepository.save(booking);

        seatInventoryClient.lockSeat(
                request.getScheduleId(),
                request.getSeatNumber()
        );

        SeatReservation seat = new SeatReservation();
        seat.setBookingId(booking.getBookingId());
        seat.setSeatId(1L);
        seat.setReservationStatus("LOCKED");
        seat.setLockedAt(LocalDateTime.now());
        seatReservationRepository.save(seat);

        return BookingResponse.builder()
                .bookingId(booking.getBookingId())
                .bookingReference(booking.getBookingReference())
                .bookingStatus(booking.getBookingStatus())
                .totalAmount(booking.getTotalAmount())
                .build();
    }

    public void confirmBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();

        new RequestedState().confirm(booking);
        bookingRepository.save(booking);

        SeatReservation seat = seatReservationRepository
                .findAll()
                .stream()
                .filter(s -> s.getBookingId().equals(bookingId))
                .findFirst()
                .orElseThrow();

        seat.setReservationStatus("CONFIRMED");
        seat.setConfirmedAt(LocalDateTime.now());
        seatReservationRepository.save(seat);

        // Publish to Kafka — Notification Service will consume this
        BookingEventDto event = new BookingEventDto();
        event.setBookingId(booking.getBookingId());
        event.setUserId(booking.getUserId());
        event.setFlightId(booking.getFlightId());
        event.setBookingStatus("CONFIRMED");
        event.setBookingReference(booking.getBookingReference());
        event.setTotalAmount(booking.getTotalAmount());

        // Note: passenger details like email/name are not stored in Booking entity
        // They should be fetched from User Service or passed in the request
        // For now we set what we have — extend CreateBookingRequest to include these
        event.setPassengerEmail(booking.getPassengerEmail());
        event.setPassengerName(booking.getPassengerName());
        event.setPassengerPhone(booking.getPassengerPhone());

        eventPublisher.publishConfirmed(event);
        log.info("Booking confirmed and event published for bookingId={}", bookingId);
    }

    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();

        new RequestedState().cancel(booking);
        bookingRepository.save(booking);

        SeatReservation seat = seatReservationRepository
                .findAll()
                .stream()
                .filter(s -> s.getBookingId().equals(bookingId))
                .findFirst()
                .orElseThrow();

        seat.setReservationStatus("RELEASED");
        seat.setReleasedAt(LocalDateTime.now());
        seatReservationRepository.save(seat);

        // Publish cancellation event to Kafka
        BookingEventDto event = new BookingEventDto();
        event.setBookingId(booking.getBookingId());
        event.setUserId(booking.getUserId());
        event.setFlightId(booking.getFlightId());
        event.setBookingStatus("CANCELLED");
        event.setBookingReference(booking.getBookingReference());
        event.setTotalAmount(booking.getTotalAmount());
        event.setPassengerEmail(booking.getPassengerEmail());
        event.setPassengerName(booking.getPassengerName());
        event.setPassengerPhone(booking.getPassengerPhone());

        eventPublisher.publishCancelled(event);
        log.info("Booking cancelled and event published for bookingId={}", bookingId);
    }

    public BookingResponse getBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        return BookingResponse.builder()
                .bookingId(booking.getBookingId())
                .bookingReference(booking.getBookingReference())
                .bookingStatus(booking.getBookingStatus())
                .totalAmount(booking.getTotalAmount())
                .build();
    }

    public List<BookingResponse> getMyBookings(Long userId) {
        return bookingRepository.findByUserId(userId)
                .stream()
                .map(booking -> BookingResponse.builder()
                        .bookingId(booking.getBookingId())
                        .bookingReference(booking.getBookingReference())
                        .bookingStatus(booking.getBookingStatus())
                        .totalAmount(booking.getTotalAmount())
                        .build())
                .toList();
    }

    public String addAncillary(Long bookingId, AncillaryRequest request) {
        BookingAncillary ancillary = new BookingAncillary();
        ancillary.setBookingId(bookingId);
        ancillary.setItemId(request.getItemId());
        ancillary.setQuantity(request.getQuantity());
        bookingAncillaryRepository.save(ancillary);
        return "Ancillary added";
    }

    public List<BookingAncillary> getAncillaries(Long bookingId) {
        return bookingAncillaryRepository.findByBookingId(bookingId);
    }

    public String removeAncillary(Long id) {
        bookingAncillaryRepository.deleteById(id);
        return "Ancillary removed";
    }
}