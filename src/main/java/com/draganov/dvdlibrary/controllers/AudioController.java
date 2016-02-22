package com.draganov.dvdlibrary.controllers;

import com.draganov.dvdlibrary.model.Audio;
import com.draganov.dvdlibrary.service.AudioService;
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
@RequestMapping(path = "audios", produces = MediaType.APPLICATION_JSON_VALUE)
public class AudioController {
    @Autowired
    AudioService audioService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Audio>> getAll() {
        return new ResponseEntity<>(audioService.getAll(), HttpStatus.OK);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Audio> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(audioService.getById(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Audio> create(@Valid @RequestBody Audio audio) {
        audio = audioService.create(audio);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.LOCATION, "audios/" + audio.getId());

        return new ResponseEntity<>(audio, headers, HttpStatus.OK);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Audio> update(@PathVariable("id") Long id, @Valid @RequestBody Audio audio) {
        audio.setId(id);    // Ensure proper ID

        return new ResponseEntity<>(audioService.update(audio), HttpStatus.OK);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable("id") Long id) {
        audioService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
