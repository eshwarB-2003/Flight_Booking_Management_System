package com.flightbooking.BookingService.service;

import com.flightbooking.BookingService.client.SeatInventoryClient;
import com.flightbooking.BookingService.dto.BookingResponse;
import com.flightbooking.BookingService.dto.CreateBookingRequest;
import com.flightbooking.BookingService.entity.Booking;
import com.flightbooking.BookingService.entity.SeatReservation;
import com.flightbooking.BookingService.factory.BookingFactory;
import com.flightbooking.BookingService.repository.BookingRepository;
import com.flightbooking.BookingService.repository.SeatReservationRepository;
import com.flightbooking.BookingService.state.RequestedState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final SeatReservationRepository seatReservationRepository;
    private final SeatInventoryClient seatInventoryClient;
    private final BookingAncillaryRepository bookingAncillaryRepository;
    private final BookingEventPublisher eventPublisher;

    public BookingResponse createBooking(CreateBookingRequest request) {

        // create booking
        Booking booking = BookingFactory.create(
                request.getUserId(),
                request.getFlightId(),
                request.getTotalAmount()
        );

        booking = bookingRepository.save(booking);

        // lock seat in inventory service
        seatInventoryClient.lockSeat(
                request.getScheduleId(),
                request.getSeatNumber()
        );

        // create seat reservation
        SeatReservation seat = new SeatReservation();
        seat.setBookingId(booking.getBookingId());
        seat.setSeatId(1L); // temp (seat service owns real seat id)
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

        Booking booking = bookingRepository
                .findById(bookingId)
                .orElseThrow();

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

        eventPublisher.publish(
                new BookingConfirmedEvent(
                        booking.getBookingId(),
                        booking.getUserId(),
                        booking.getFlightId()
                )
        );
    }

    public void cancelBooking(Long bookingId) {

        Booking booking = bookingRepository
                .findById(bookingId)
                .orElseThrow();

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
    }

    public BookingResponse getBooking(Long bookingId){

        Booking booking = bookingRepository
                .findById(bookingId)
                .orElseThrow();

        return BookingResponse.builder()
                .bookingId(booking.getBookingId())
                .bookingReference(booking.getBookingReference())
                .bookingStatus(booking.getBookingStatus())
                .totalAmount(booking.getTotalAmount())
                .build();
    }

    public List<BookingResponse> getMyBookings(Long userId){

        return bookingRepository
                .findByUserId(userId)
                .stream()
                .map(booking -> BookingResponse.builder()
                        .bookingId(booking.getBookingId())
                        .bookingReference(booking.getBookingReference())
                        .bookingStatus(booking.getBookingStatus())
                        .totalAmount(booking.getTotalAmount())
                        .build()
                ).toList();
    }

    public String addAncillary(
            Long bookingId,
            AncillaryRequest request
    ){

        BookingAncillary ancillary = new BookingAncillary();

        ancillary.setBookingId(bookingId);
        ancillary.setItemId(request.getItemId());
        ancillary.setQuantity(request.getQuantity());

        bookingAncillaryRepository.save(ancillary);

        return "Ancillary added";
    }

    public List<BookingAncillary> getAncillaries(Long bookingId){

        return bookingAncillaryRepository.findByBookingId(bookingId);
    }

    public String removeAncillary(Long id){

        bookingAncillaryRepository.deleteById(id);

        return "Ancillary removed";
    }
}