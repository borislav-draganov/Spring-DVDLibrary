package com.draganov.dvdlibrary.test;

import com.draganov.dvdlibrary.model.Audio;
import com.draganov.dvdlibrary.service.AudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Borislav
 * @since 20/02/2016
 */
@RestController
@RequestMapping(path = "audios", produces = MediaType.APPLICATION_JSON_VALUE)
public class TestController {
    @Autowired
    AudioService audioService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Audio>> test() {
        return new ResponseEntity<>(audioService.getAll(), HttpStatus.OK);
    }
}
