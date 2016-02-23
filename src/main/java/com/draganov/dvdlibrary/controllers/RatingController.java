package com.draganov.dvdlibrary.controllers;

import com.draganov.dvdlibrary.model.Rating;
import com.draganov.dvdlibrary.service.RatingService;
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
@RequestMapping(path = "ratings", produces = MediaType.APPLICATION_JSON_VALUE)
public class RatingController {
    @Autowired
    RatingService ratingService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Rating>> getAll() {
        return new ResponseEntity<>(ratingService.getAll(), HttpStatus.OK);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Rating> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(ratingService.getById(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Rating> create(@Valid @RequestBody Rating rating) {
        rating = ratingService.create(rating);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.LOCATION, "rating/" + rating.getId());

        return new ResponseEntity<>(rating, headers, HttpStatus.OK);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Rating> update(@PathVariable("id") Long id, @Valid @RequestBody Rating rating) {
        rating.setId(id);    // Ensure proper ID

        return new ResponseEntity<>(ratingService.update(rating), HttpStatus.OK);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable("id") Long id) {
        ratingService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
