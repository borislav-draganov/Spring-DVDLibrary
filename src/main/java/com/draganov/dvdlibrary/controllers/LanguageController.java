package com.draganov.dvdlibrary.controllers;

import com.draganov.dvdlibrary.model.Language;
import com.draganov.dvdlibrary.service.LanguageService;
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
@RequestMapping(path = "languages", produces = MediaType.APPLICATION_JSON_VALUE)
public class LanguageController {
    @Autowired
    LanguageService languageService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Language>> getAll() {
        return new ResponseEntity<>(languageService.getAll(), HttpStatus.OK);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Language> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(languageService.getById(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Language> create(@Valid @RequestBody Language language) {
        language = languageService.create(language);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.LOCATION, "languages/" + language.getId());

        return new ResponseEntity<>(language, headers, HttpStatus.OK);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Language> update(@PathVariable("id") Long id, @Valid @RequestBody Language language) {
        language.setId(id);    // Ensure proper ID

        return new ResponseEntity<>(languageService.update(language), HttpStatus.OK);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable("id") Long id) {
        languageService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
