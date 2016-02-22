package com.draganov.dvdlibrary.controllers;

import com.draganov.dvdlibrary.model.Genre;
import com.draganov.dvdlibrary.service.GenreService;
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
@RequestMapping(path = "genres", produces = MediaType.APPLICATION_JSON_VALUE)
public class GenreController {
    @Autowired
    GenreService genreService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Genre>> getAll() {
        return new ResponseEntity<>(genreService.getAll(), HttpStatus.OK);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Genre> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(genreService.getById(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Genre> create(@Valid @RequestBody Genre genre) {
        genre = genreService.create(genre);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.LOCATION, "genres/" + genre.getId());

        return new ResponseEntity<>(genre, headers, HttpStatus.OK);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Genre> update(@PathVariable("id") Long id, @Valid @RequestBody Genre genre) {
        genre.setId(id);    // Ensure proper ID

        return new ResponseEntity<>(genreService.update(genre), HttpStatus.OK);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable("id") Long id) {
        genreService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
