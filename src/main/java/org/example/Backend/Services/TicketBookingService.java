package org.example.Backend.Services;

import org.example.Backend.Database.Model.Seat;
import org.example.Backend.Database.Model.SeatStatus;
import org.example.Backend.Database.Model.Session;
import org.example.Backend.Database.Model.Ticket;
import org.example.Backend.Database.Repository.SessionRepository;
import org.example.Backend.Database.Repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TicketBookingService {
    private final TicketRepository ticketRepository;
    private final SessionRepository sessionRepository;

    private final Map<Long, HashSet<Integer>> selectedSessionSeatNumbers;

    public TicketBookingService(TicketRepository ticketRepository, SessionRepository sessionRepository) {
        this.ticketRepository = ticketRepository;
        this.sessionRepository = sessionRepository;
        this.selectedSessionSeatNumbers = new HashMap<>();
    }

    public Boolean isSeatReserved(Long sessionId, int seatNumber) {
        List<Ticket> reservedSeats = ticketRepository.findAllBySession_Id(sessionId);
        return reservedSeats.stream().anyMatch(ticket -> ticket.getSeatNumber() == seatNumber);
    }

    public void toggleSeatSelection(Long sessionId, int seatNumber) {
        if (!selectedSessionSeatNumbers.containsKey(sessionId)) {
            selectedSessionSeatNumbers.put(sessionId, new HashSet<>());
        }

        HashSet<Integer> selectedSeatNumbers = selectedSessionSeatNumbers.get(sessionId);
        if (selectedSeatNumbers.contains(seatNumber)) {
            selectedSeatNumbers.remove(seatNumber);
        } else {
            selectedSeatNumbers.add(seatNumber);
        }
    }

    public void reserveSeat(Long sessionId, int seatNumber, int price) {
        Ticket ticket = new Ticket();

        Session session = sessionRepository.findById(sessionId).orElse(null);
        ticket.setSession(session);

        ticket.setSeatNumber(seatNumber);
        ticket.setPrice(price);

        ticketRepository.save(ticket);

        if (selectedSessionSeatNumbers.containsKey(sessionId)) {
            selectedSessionSeatNumbers.get(sessionId).remove(seatNumber);
        }
    }

    public List<Seat> getAllSeats(Long sessionId) {
        Session session = sessionRepository.findById(sessionId).orElse(null);
        if (session == null) {
            return new ArrayList<>();
        }

        List<Seat> seats = new ArrayList<>();
        for (int seatNumber = 1; seatNumber < session.getAvailableSeats(); seatNumber++) {
            SeatStatus seatStatus = getSeatStatus(sessionId, seatNumber);

            Seat seat = new Seat();
            seat.setNumber(seatNumber);
            seat.setStatus(seatStatus);

            seats.add(seat);
        }

        return seats;
    }

    public List<Integer> getSelectedSeats(Long sessionId) {
        return selectedSessionSeatNumbers.containsKey(sessionId) ?
                new ArrayList<>(selectedSessionSeatNumbers.get(sessionId)) :
                new ArrayList<>();
    }

    private SeatStatus getSeatStatus(Long sessionId, int seatNumber) {
        Ticket ticket = ticketRepository.findTicketBySession_IdAndSeatNumber(sessionId, seatNumber);

        return ticket == null ? selectedSessionSeatNumbers.containsKey(sessionId) &&
                selectedSessionSeatNumbers.get(sessionId).contains(seatNumber) ?
                SeatStatus.Selected :
                SeatStatus.None :
                SeatStatus.Reserved;
    }
}