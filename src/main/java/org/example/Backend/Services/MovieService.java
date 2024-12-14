package org.example.Backend.Services;

import org.example.Backend.Database.Model.Movie;
import org.example.Backend.Database.Repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        List<Movie> list = new ArrayList<>();
        movieRepository.findAll().forEach(list::add);

        return list;
    }

    public List<Movie> searchMovies(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Movie> filterByGenre(String genre) {
        return movieRepository.findByGenre(genre);
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id).orElse(null);
    }

    public void addMovie(Movie movie) {
        movieRepository.save(movie);
    }
}

