package com.wolffangx.dvdlibrary.test;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;

/**
 * @author Borislav
 * @since 20/02/2016
 */
@RestController
public class TestController {
    @RequestMapping(value = "test", method = RequestMethod.GET)
    public ResponseEntity<String> test() {
        System.out.println("haahahah");
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
