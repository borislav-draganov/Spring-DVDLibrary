package com.draganov.dvdlibrary.controllers;

import com.draganov.dvdlibrary.model.DVD;
import com.draganov.dvdlibrary.service.DvdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author Borislav
 * @since 20/02/2016
 */
@RestController
@RequestMapping(path = "dvds", produces = MediaType.APPLICATION_JSON_VALUE)
public class DvdController {
    @Autowired
    DvdService dvdService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<DVD>> getAll() {
        return new ResponseEntity<>(dvdService.getAll(), HttpStatus.OK);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<DVD> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(dvdService.getById(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<DVD> create(@Valid @RequestBody DVD dvd) {
        dvd = dvdService.create(dvd);

        // process parallel
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.LOCATION, "dvds/" + dvd.getId());


        return new ResponseEntity<>(dvd, headers, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST, path = "/bulk")
    public ResponseEntity<List<DVD>> createBulk(@Valid @RequestBody List<DVD> dvds) throws InterruptedException {
        List<DVD> result = Collections.synchronizedList(new ArrayList<>());

        CountDownLatch latch = new CountDownLatch(dvds.size());

        dvds.forEach((dvd) -> {
            new Thread(() -> {
                result.add(dvdService.create(dvd));
                latch.countDown();
            });
        });

        latch.await();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<DVD> update(@PathVariable("id") Long id, @Valid @RequestBody DVD dvd) {
        dvd.setId(id);    // Ensure proper ID

        return new ResponseEntity<>(dvdService.update(dvd), HttpStatus.OK);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable("id") Long id) {
        dvdService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
