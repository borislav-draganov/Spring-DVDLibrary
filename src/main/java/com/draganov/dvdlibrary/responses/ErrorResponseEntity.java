package com.draganov.dvdlibrary.responses;

import org.springframework.http.HttpStatus;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * A standard response format that's going to be send by the server on failed operations
 *
 * @author borislav.draganov
 */

// @XmlRootElement - makes the class serializable in XML and JSON. Also sets the name of the XML root element as "response"
@XmlRootElement(name = "response")
public class ErrorResponseEntity extends AbstractResponseEntity {
    private String error;

    public ErrorResponseEntity() {
        success = false;
    }

    public ErrorResponseEntity(HttpStatus httpStatus, String error) {
        success = false;
        this.statusCode = httpStatus.value();
        this.statusMessage = httpStatus.getReasonPhrase();

        this.error = error;
    }

    // Getters and Setters
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
