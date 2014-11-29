package com.wolffangx.dvdlibrary.resources;

import com.wolffangx.dvdlibrary.entities.Rating;
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
 * The actual implementation of the Rating Web Resources
 *
 * @author borislav.draganov
 */

public class RatingRes extends WebRes {
    /**
     * Get all Rating in the database
     *
     * @return - ResponseEntity with a list of all Ratings in the database
     */
    public ResponseEntity GetAllRatings() {
        // Try to read all Ratings from the database
        try {
            List<Rating> sqlResponse = sqlReportOperations.viewAllRatings();

            // Return with HTTP Status Code 200
            HttpStatus status = HttpStatus.OK;

            // Wrap the result in a standard response format
            SuccessResponseEntity<Rating> responseEntity = new SuccessResponseEntity<Rating>(status, sqlResponse);

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
     * Get a Rating by its ID
     *
     * @param id - The requested ID
     * @return - ResponseEntity with a valid Rating object from the data in the database
     */
    public ResponseEntity getRatingById(int id) {
        // Try to read from the database
        try {
            Rating sqlResponse = sqlReportOperations.viewRatingById(id);

            // Return with HTTP Status Code 200
            HttpStatus status = HttpStatus.OK;

            // Wrap the result in a standard response format
            SuccessResponseEntity<Rating> responseEntity = new SuccessResponseEntity<Rating>(status, sqlResponse);

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
     * Insert a new Rating
     *
     * @param rating - The object to be inserted into the database
     * @return - ResponseEntity with the status of the operation
     */
    public ResponseEntity insertNewRating(Rating rating) {
        // Validation Check
        if(rating.getName() == null) {
            return ErrorBuilder.missingFieldError(ErrorBuilder.nameField);
        }
        
        // Try to insert into the database
        try {
            String sqlResponse = sqlInsertOperations.insertNewRating(rating);

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
     * Delete a Rating by it ID
     *
     * @param id - The ID for deletion
     * @return - ResponseEntity with the status of the operation
     */
    public ResponseEntity deleteRatingById(int id) {
        // Try to delete from the database
        try {
            String sqlResponse = sqlDeleteOperations.deleteRatingById(id);

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
     * Update a Rating
     *
     * @param id - The ID for the update
     * @param updatedRating - The new data to be inserted into the database
     * @return - ResponseEntity with the status of the operation
     */
    public ResponseEntity updateRating(int id, Rating updatedRating) {
        // Validation Check
        if(updatedRating.getName() == null) {
            return ErrorBuilder.missingFieldError(ErrorBuilder.nameField);
        }
        
        // Set the ID
        updatedRating.setId(id);

        // Try to update the database
        try {
            String sqlResponse = sqlUpdateOperations.updateRating(updatedRating);

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