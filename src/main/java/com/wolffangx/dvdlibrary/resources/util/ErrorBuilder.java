package com.wolffangx.dvdlibrary.resources.util;

import com.wolffangx.dvdlibrary.responses.ErrorResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


/**
 * A Validator that will build adequate error responses
 *
 * @author borislav.draganov
 */

public abstract class ErrorBuilder {
    public static final String nameField = "Name";
    public static final String movieIdField = "Movie ID";
    public static final String isbnIdField = "ISBN";
    public static final String LAPField = "Language and Audio Pair";
    public static final String titleField = "Title";
    public static final String genresField = "Genres";
    public static final String ratingField = "Rating";

    //------------------------------------------------------------------------------------------------------------------
    /**
     * Construct an ErrorResponseEntity when a mandatory field is missing
     *
     * @param nameField - The name of the missing field
     * @return - A ErrorResponseEntity wrapped in a Response object
     */
    public static ResponseEntity missingFieldError(String nameField) {
        // Return with HTTP Status Code 400
        HttpStatus status = HttpStatus.BAD_REQUEST;

        // Wrap the result in a standard response format
        ErrorResponseEntity errorResponseEntity = new ErrorResponseEntity(status, ErrorMessages.MISSING_FIELD + " - " + nameField);

        // Return the error code and message
        return new ResponseEntity<ErrorResponseEntity>(errorResponseEntity, status);
    }

    //------------------------------------------------------------------------------------------------------------------
    /**
     * Construct an ErrorResponseEntity when a mandatory field contains invalid data
     *
     * @param nameField - The name of the missing field
     * @return - A ErrorResponseEntity wrapped in a Response object
     */
    public static ResponseEntity invalidFieldError(String nameField) {
        // Return with HTTP Status Code 400
        HttpStatus status = HttpStatus.BAD_REQUEST;

        // Wrap the result in a standard response format
        ErrorResponseEntity errorResponseEntity = new ErrorResponseEntity(status, ErrorMessages.INVALID_FIELD + " - " + nameField);

        // Return the error code and message
        return new ResponseEntity<ErrorResponseEntity>(errorResponseEntity, status);
    }
}