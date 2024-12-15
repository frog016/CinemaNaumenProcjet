package org.example.Backend.Controllers;

import org.example.Backend.Database.Model.Movie;
import org.example.Backend.Services.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/edit_movies")
public class EditMovieWebController {
    private final MovieService movieService;

    public EditMovieWebController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public String listMovies(Model model) {
        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        return "edit_movie_list";
    }

    @GetMapping("/add")
    public String showAddMovieForm(Model model) {
        model.addAttribute("movie", new Movie());
        return "edit_movie_form";
    }

    @PostMapping("/add")
    public String addMovie(@ModelAttribute Movie movie) {
        movieService.addMovie(movie);
        return "redirect:/edit_movies";
    }

    @GetMapping("/edit/{id}")
    public String showEditMovieForm(@PathVariable Long id, Model model) {
        Movie movie = movieService.getMovieById(id);
        model.addAttribute("movie", movie);
        return "edit_movie_form";
    }

    @PostMapping("/edit/{id}")
    public String updateMovie(@PathVariable Long id, @ModelAttribute Movie movie) {
        movie.setId(id);
        movieService.updateMovie(movie);
        return "redirect:/edit_movies";
    }

    @GetMapping("/delete/{id}")
    public String deleteMovie(@PathVariable Long id) {
        movieService.removeMovie(id);
        return "redirect:/edit_movies";
    }
}
