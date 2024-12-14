package org.example.Backend.Controllers;

import org.example.Backend.Database.Model.Movie;
import org.example.Backend.Services.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MovieListWebController {
    private final MovieService movieService;

    public MovieListWebController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movies")
    public String showMovies(@RequestParam(name = "search", required = false) String search,
                             @RequestParam(name = "genre", required = false) String genre,
                             Model model) {
        List<Movie> movies;

        if (search != null && !search.isEmpty()) {
            movies = movieService.searchMovies(search);
        } else if (genre != null && !genre.isEmpty()) {
            movies = movieService.filterByGenre(genre);
        } else {
            movies = movieService.getAllMovies();
        }

        model.addAttribute("movies", movies);
        model.addAttribute("movie_form", new Movie());
        model.addAttribute("search", search);
        model.addAttribute("genre", genre);

        return "movie_list";
    }

    @GetMapping("/movies/{id}")
    public String viewMovie(@PathVariable Long id, Model model) {
        Movie movie = movieService.getMovieById(id);
        if (movie == null) {
            return "error";
        }

        model.addAttribute("movie", movie);
        return "movie_session";
    }

    @GetMapping("/movies/add")
    public String showAddMovie(Model model) {
        model.addAttribute("movie_form", new Movie());
        return "movie_list";
    }

    @PostMapping("/movies/add")
    public String addMovie(@ModelAttribute("movie_form") Movie movie) {
        movieService.addMovie(movie);
        return "redirect:/movies";
    }
}
