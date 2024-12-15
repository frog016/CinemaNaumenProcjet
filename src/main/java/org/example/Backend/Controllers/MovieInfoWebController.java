package org.example.Backend.Controllers;

import org.example.Backend.Database.Model.Movie;
import org.example.Backend.Database.Model.Session;
import org.example.Backend.Services.MovieService;
import org.example.Backend.Services.MovieSessionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/movie_info")
public class MovieInfoWebController {
    private final MovieSessionService sessionService;
    private final MovieService movieService;

    public MovieInfoWebController(MovieSessionService sessionService, MovieService movieService) {
        this.sessionService = sessionService;
        this.movieService = movieService;
    }

    @GetMapping("/{movieId}")
    public String showMovieSessions(@PathVariable Long movieId, Model model) {
        List<Session> sessions = sessionService.getSessionsForMovie(movieId);
        model.addAttribute("sessions", sessions);

        Movie movie = movieService.getMovieById(movieId);
        model.addAttribute("movie", movie);

        return "movie_session";
    }

    @GetMapping("/session/{sessionId}")
    public String getBookingPage(@PathVariable Long sessionId, Model model) {
        Session session = sessionService.getSessionById(sessionId);
        model.addAttribute("session", session);

        Movie movie = session.getMovie();
        model.addAttribute("movie", movie);

        return "ticket_reservation";
    }
}