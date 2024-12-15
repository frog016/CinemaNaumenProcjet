package org.example.Backend.Controllers;

import org.example.Backend.Database.Model.Movie;
import org.example.Backend.Database.Model.Session;
import org.example.Backend.Services.MovieService;
import org.example.Backend.Services.MovieSessionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/edit_sessions/{movieId}")
public class EditMovieSessionWebController {
    private final MovieSessionService sessionService;
    private final MovieService movieService;

    public EditMovieSessionWebController(MovieSessionService sessionService, MovieService movieService) {
        this.sessionService = sessionService;
        this.movieService = movieService;
    }

    @GetMapping
    public String listMovieSessions(@PathVariable Long movieId, Model model) {
        List<Session> sessions = sessionService.getSessionsForMovie(movieId);
        model.addAttribute("sessions", sessions);
        model.addAttribute("movie", movieService.getMovieById(movieId));

        return "edit_movie_sessions_list";
    }

    @GetMapping("/add")
    public String showAddSessionForm(@PathVariable Long movieId, Model model) {
        model.addAttribute("edited_session", new Session());
        model.addAttribute("edited_movieId", movieId);

        return "edit_movie_sessions_form";
    }

    @PostMapping("/add")
    public String addSession(@ModelAttribute Session session, @PathVariable Long movieId) {
        Movie movie = movieService.getMovieById(movieId);
        session.setMovie(movie);

        sessionService.addSession(session);
        return "redirect:/edit_sessions/" + session.getMovie().getId();
    }

    @GetMapping("/edit/{id}")
    public String showUpdateSessionForm(@PathVariable Long movieId, @PathVariable Long id, Model model) {
        Session session = sessionService.getSessionById(id);
        model.addAttribute("edited_session", session);
        model.addAttribute("edited_movieId", movieId);

        return "edit_movie_sessions_form";
    }

    @PostMapping("/edit/{id}")
    public String updateSession(@PathVariable Long movieId, @PathVariable Long id, @ModelAttribute Session session) {
        Movie movie = movieService.getMovieById(movieId);
        session.setMovie(movie);
        session.setId(id);

        sessionService.updateSession(session);
        return "redirect:/edit_sessions/" + session.getMovie().getId();
    }

    @GetMapping("/delete/{id}")
    public String deleteSession(@PathVariable Long movieId, @PathVariable Long id) {
        Session session = sessionService.getSessionById(id);
        sessionService.removeSession(id);
        return "redirect:/edit_sessions/" + session.getMovie().getId();
    }
}
