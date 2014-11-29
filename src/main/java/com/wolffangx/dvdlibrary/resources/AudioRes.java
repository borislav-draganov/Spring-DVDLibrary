package com.wolffangx.dvdlibrary.resources;

import com.wolffangx.dvdlibrary.entities.Audio;
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
 * The actual implementation of the Audio Web Resources
 *
 * @author borislav.draganov
 */

public class AudioRes extends WebRes {
    /**
     * Get all Audios in the database
     *
     * @return - Response with a list of all Audios in the database
     */
    public ResponseEntity GetAllAudios() {
        // Try to read all Audios from the database
        try {
            List<Audio> sqlResponse = sqlReportOperations.viewAllAudios();

            // Return with HTTP Status Code 200
            HttpStatus status = HttpStatus.OK;

            // Wrap the result in a standard response format
            SuccessResponseEntity<Audio> responseEntity = new SuccessResponseEntity<Audio>(status, sqlResponse);

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
     * Get an Audio by its ID
     *
     * @param id - The requested ID
     * @return - Response with a valid Audio object from the data in the database
     */
    public ResponseEntity getAudioById(int id) {
        // Try to read from the database
        try {
            Audio sqlResponse = sqlReportOperations.viewAudioById(id);

            // Return with HTTP Status Code 200
            HttpStatus status = HttpStatus.OK;

            // Wrap the result in a standard response format
            SuccessResponseEntity<Audio> responseEntity = new SuccessResponseEntity<Audio>(status, sqlResponse);

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
     * Insert a new Audio
     *
     * @param audio - The object to be inserted into the database
     * @return - Response with the status of the operation
     */
    public ResponseEntity insertNewAudio(Audio audio) {
        // Validation Check
        if(audio.getName() == null) {
            return ErrorBuilder.missingFieldError(ErrorBuilder.nameField);
        }

        // Try to insert into the database
        try {
            String sqlResponse = sqlInsertOperations.insertNewAudio(audio);

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
     * Delete an Audio by it ID
     *
     * @param id - The ID for deletion
     * @return - Response with the status of the operation
     */
    public ResponseEntity deleteAudioById(int id) {
        // Try to delete from the database
        try {
            String sqlResponse = sqlDeleteOperations.deleteAudioById(id);

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
     * Update an Audio
     *
     * @param id - The ID for the update
     * @param updatedAudio - The new data to be inserted into the database
     * @return - Response with the status of the operation
     */
    public ResponseEntity updateAudio(int id, Audio updatedAudio) {
        // Validation Check
        if(updatedAudio.getName() == null) {
            return ErrorBuilder.missingFieldError(ErrorBuilder.nameField);
        }

        // Set the ID
        updatedAudio.setId(id);

        // Try to update the database
        try {
            String sqlResponse = sqlUpdateOperations.updateAudio(updatedAudio);

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