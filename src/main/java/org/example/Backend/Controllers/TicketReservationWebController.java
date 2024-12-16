package org.example.Backend.Controllers;

import org.example.Backend.Database.Model.Seat;
import org.example.Backend.Services.TicketBookingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ticket_selection/{sessionId}")
public class TicketReservationWebController {
    private final TicketBookingService seatService;

    public TicketReservationWebController(TicketBookingService seatService) {
        this.seatService = seatService;
    }

    @GetMapping("/seats")
    public String showSeats(@PathVariable Long sessionId, Model model) {
        List<Seat> seats = seatService.getAllSeats(sessionId);
        model.addAttribute("seats", seats);
        model.addAttribute("reservation_session", sessionId);

        return "ticket_reservation";
    }

    @PostMapping("/select/{seatNumber}")
    public String selectSeat(@PathVariable Long sessionId, @PathVariable int seatNumber) {
        seatService.toggleSeatSelection(sessionId, seatNumber);
        return String.format("redirect:/ticket_selection/%d/seats", sessionId);
    }

    @PostMapping("/reserve")
    public String reserveSeats(@PathVariable Long sessionId) {
        List<Integer> seatIds = seatService.getSelectedSeats(sessionId);

        for (int seatNumber : seatIds) {
            if (!seatService.isSeatReserved(sessionId, seatNumber)) {
                seatService.reserveSeat(sessionId, seatNumber, 0);
            }
        }
        return String.format("redirect:/ticket_selection/%d/seats", sessionId);
    }
}
