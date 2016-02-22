package com.draganov.dvdlibrary.controllers;

import com.draganov.dvdlibrary.model.Movie;
import com.draganov.dvdlibrary.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Borislav
 * @since 20/02/2016
 */
@RestController
@RequestMapping(path = "movies", produces = MediaType.APPLICATION_JSON_VALUE)
public class MovieController {
    @Autowired
    MovieService movieService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Movie>> getAll() {
        return new ResponseEntity<>(movieService.getAll(), HttpStatus.OK);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Movie> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(movieService.getById(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Movie> create(@Valid @RequestBody Movie movie) {
        movie = movieService.create(movie);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.LOCATION, "movies/" + movie.getId());

        return new ResponseEntity<>(movie, headers, HttpStatus.OK);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Movie> update(@PathVariable("id") Long id, @Valid @RequestBody Movie movie) {
        movie.setId(id);    // Ensure proper ID

        return new ResponseEntity<>(movieService.update(movie), HttpStatus.OK);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable("id") Long id) {
        movieService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
