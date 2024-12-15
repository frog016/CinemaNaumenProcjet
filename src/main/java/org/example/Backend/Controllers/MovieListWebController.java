package org.example.Backend.Controllers;

import org.example.Backend.Database.Model.Movie;
import org.example.Backend.Services.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/movies")
public class MovieListWebController {
    private final MovieService movieService;

    public MovieListWebController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
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
        model.addAttribute("search", search);
        model.addAttribute("genre", genre);

        return "movie_list";
    }

    @PostMapping
    public String searchMovies(@RequestParam(name = "search", required = false) String search,
                               RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("search", search);
        return "redirect:/movies";
    }

    @GetMapping("/{id}")
    public String viewMovie(@PathVariable Long id, Model model) {
        Movie movie = movieService.getMovieById(id);
        if (movie == null) {
            return "error";
        }

        model.addAttribute("movie", movie);
        return "redirect:/movie_info/" + id;
    }
}
