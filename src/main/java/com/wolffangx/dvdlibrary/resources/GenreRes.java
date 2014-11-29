package com.wolffangx.dvdlibrary.resources;

import com.wolffangx.dvdlibrary.entities.Genre;
import com.wolffangx.dvdlibrary.exceptions.MissingIdException;
import com.wolffangx.dvdlibrary.resources.util.ErrorBuilder;
import com.wolffangx.dvdlibrary.resources.util.ErrorMessages;
import com.wolffangx.dvdlibrary.resources.util.WebRes;
import com.wolffangx.dvdlibrary.responses.ErrorResponseEntity;
import com.wolffangx.dvdlibrary.responses.SuccessResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * The actual implementation of the Genre Web Resources
 *
 * @author borislav.draganov
 */

public class GenreRes extends WebRes {
    /**
     * Get all Genres in the database
     *
     * @return - ResponseEntity with a list of all Genres in the database
     */
    public ResponseEntity GetAllGenres() {
        // Try to read all Genres from the database
        try {
            List<Genre> sqlResponse = sqlReportOperations.viewAllGenres();

            // Return with HTTP Status Code 200
            HttpStatus status = HttpStatus.OK;

            // Wrap the result in a standard response format
            SuccessResponseEntity<Genre> responseEntity = new SuccessResponseEntity<Genre>(status, sqlResponse);

            // Return the response entity
            return new ResponseEntity<SuccessResponseEntity>(responseEntity, status);
        }
        // In case of anything else - an unexpected error
        catch (Exception ex) {
            // Log the Stack trace
            ex.printStackTrace();

            // Return with HTTP Status Code 500
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

            // An internal error occurred - Unexpected Error
            ErrorResponseEntity errorResponseEntity = new ErrorResponseEntity(status, ErrorMessages.UNEXPECTED_ERROR);

            // Return the error code and message
            return new ResponseEntity<ErrorResponseEntity>(errorResponseEntity, status);
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    /**
     * Get a Genre by its ID
     *
     * @param id - The requested ID
     * @return - ResponseEntity with a valid Genre object from the data in the database
     */
    public ResponseEntity getGenreById(int id) {
        // Try to read from the database
        try {
            Genre sqlResponse = sqlReportOperations.viewGenreById(id);

            // Return with HTTP Status Code 200
            HttpStatus status = HttpStatus.OK;

            // Wrap the result in a standard response format
            SuccessResponseEntity<Genre> responseEntity = new SuccessResponseEntity<Genre>(status, sqlResponse);

            // Return the response entity
            return new ResponseEntity<SuccessResponseEntity>(responseEntity, status);
        }
        // A Missing ID was requested
        catch (MissingIdException ex) {
            // Return with HTTP Status Code 400
            HttpStatus status = HttpStatus.BAD_REQUEST;

            // Wrap the result in a standard response format
            ErrorResponseEntity errorResponseEntity = new ErrorResponseEntity(status, ex.getMessage());

            // Return the error code and message
            return new ResponseEntity<ErrorResponseEntity>(errorResponseEntity, status);
        }
        // In case of anything else - an unexpected error
        catch (Exception ex) {
            // Log the Stack trace
            ex.printStackTrace();

            // Return with HTTP Status Code 500
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

            // An internal error occurred - Unexpected Error
            ErrorResponseEntity errorResponseEntity = new ErrorResponseEntity(status, ErrorMessages.UNEXPECTED_ERROR);

            // Return the error code and message
            return new ResponseEntity<ErrorResponseEntity>(errorResponseEntity, status);
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    /**
     * Insert a new Genre
     *
     * @param genre - The object to be inserted into the database
     * @return - ResponseEntity with the status of the operation
     */
    public ResponseEntity insertNewGenre(Genre genre) {
        // Validation Check
        if(genre.getName() == null) {
            return ErrorBuilder.missingFieldError(ErrorBuilder.nameField);
        }
        
        // Try to insert into the database
        try {
            String sqlResponse = sqlInsertOperations.insertNewGenre(genre);

            // Return with HTTP Status Code 200
            HttpStatus status = HttpStatus.OK;

            // Wrap the result in a standard response format
            SuccessResponseEntity<String> responseEntity = new SuccessResponseEntity<String>(status, sqlResponse);

            // Return the response entity
            return new ResponseEntity<SuccessResponseEntity>(responseEntity, status);
        }
        // In case of anything else - an unexpected error
        catch (Exception ex) {
            // Log the Stack trace
            ex.printStackTrace();

            // Return with HTTP Status Code 500
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

            // An internal error occurred - Unexpected Error
            ErrorResponseEntity errorResponseEntity = new ErrorResponseEntity(status, ErrorMessages.UNEXPECTED_ERROR);

            // Return the error code and message
            return new ResponseEntity<ErrorResponseEntity>(errorResponseEntity, status);
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    /**
     * Delete a Genre by it ID
     *
     * @param id - The ID for deletion
     * @return - ResponseEntity with the status of the operation
     */
    public ResponseEntity deleteGenreById(int id) {
        // Try to delete from the database
        try {
            String sqlResponse = sqlDeleteOperations.deleteGenreById(id);

            // Return with HTTP Status Code 200
            HttpStatus status = HttpStatus.OK;

            // Wrap the result in a standard response format
            SuccessResponseEntity<String> responseEntity = new SuccessResponseEntity<String>(status, sqlResponse);

            // Return the response entity
            return new ResponseEntity<SuccessResponseEntity>(responseEntity, status);
        }
        // A Missing ID was requested
        catch (MissingIdException ex) {
            // Return with HTTP Status Code 400
            HttpStatus status = HttpStatus.BAD_REQUEST;

            // Wrap the result in a standard response format
            ErrorResponseEntity errorResponseEntity = new ErrorResponseEntity(status, ex.getMessage());

            // Return the error code and message
            return new ResponseEntity<ErrorResponseEntity>(errorResponseEntity, status);
        }
        // In case of anything else - an unexpected error
        catch (Exception ex) {
            // Log the Stack trace
            ex.printStackTrace();

            // Return with HTTP Status Code 500
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

            // An internal error occurred - Unexpected Error
            ErrorResponseEntity errorResponseEntity = new ErrorResponseEntity(status, ErrorMessages.UNEXPECTED_ERROR);

            // Return the error code and message
            return new ResponseEntity<ErrorResponseEntity>(errorResponseEntity, status);
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    /**
     * Update a Genre
     *
     * @param id - The ID for the update
     * @param updatedGenre - The new data to be inserted into the database
     * @return - ResponseEntity with the status of the operation
     */
    public ResponseEntity updateGenre(int id, Genre updatedGenre) {
        // Validation Check
        if(updatedGenre.getName() == null) {
            return ErrorBuilder.missingFieldError(ErrorBuilder.nameField);
        }

        // Set the ID
        updatedGenre.setId(id);

        // Try to update the database
        try {
            String sqlResponse = sqlUpdateOperations.updateGenre(updatedGenre);

            // Return with HTTP Status Code 200
            HttpStatus status = HttpStatus.OK;

            // Wrap the result in a standard response format
            SuccessResponseEntity<String> responseEntity = new SuccessResponseEntity<String>(status, sqlResponse);

            // Return the response entity
            return new ResponseEntity<SuccessResponseEntity>(responseEntity, status);
        }
        // A Missing ID was requested
        catch (MissingIdException ex) {
            // Return with HTTP Status Code 400
            HttpStatus status = HttpStatus.BAD_REQUEST;

            // Wrap the result in a standard response format
            ErrorResponseEntity errorResponseEntity = new ErrorResponseEntity(status, ex.getMessage());

            // Return the error code and message
            return new ResponseEntity<ErrorResponseEntity>(errorResponseEntity, status);
        }
        // In case of anything else - an unexpected error
        catch (Exception ex) {
            // Log the Stack trace
            ex.printStackTrace();

            // Return with HTTP Status Code 500
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

            // An internal error occurred - Unexpected Error
            ErrorResponseEntity errorResponseEntity = new ErrorResponseEntity(status, ErrorMessages.UNEXPECTED_ERROR);

            // Return the error code and message
            return new ResponseEntity<ErrorResponseEntity>(errorResponseEntity, status);
        }
    }
}