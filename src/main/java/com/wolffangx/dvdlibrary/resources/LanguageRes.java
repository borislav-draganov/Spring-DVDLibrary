package com.wolffangx.dvdlibrary.resources;

import com.wolffangx.dvdlibrary.entities.Language;
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
 * The actual implementation of the Language Web Resources
 *
 * @author borislav.draganov
 */

public class LanguageRes extends WebRes {
    /**
     * Get all Languages in the database
     *
     * @return - ResponseEntity with a list of all Languages in the database
     */
    public ResponseEntity GetAllLanguages() {
        // Try to read all Languages from the database
        try {
            List<Language> sqlResponse = sqlReportOperations.viewAllLanguages();

            // Return with HTTP Status Code 200
            HttpStatus status = HttpStatus.OK;

            // Wrap the result in a standard response format
            SuccessResponseEntity<Language> responseEntity = new SuccessResponseEntity<Language>(status, sqlResponse);

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
     * Get a Language by its ID
     *
     * @param id - The requested ID
     * @return - ResponseEntity with a valid Language object from the data in the database
     */
    public ResponseEntity getLanguageById(int id) {
        // Try to read from the database
        try {
            Language sqlResponse = sqlReportOperations.viewLanguageById(id);

            // Return with HTTP Status Code 200
            HttpStatus status = HttpStatus.OK;

            // Wrap the result in a standard response format
            SuccessResponseEntity<Language> responseEntity = new SuccessResponseEntity<Language>(status, sqlResponse);

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
     * Insert a new language
     *
     * @param language - The object to be inserted into the database
     * @return - ResponseEntity with the status of the operation
     */
    public ResponseEntity insertNewLanguage(Language language) {
        // Validation Check
        if(language.getName() == null) {
            return ErrorBuilder.missingFieldError(ErrorBuilder.nameField);
        }

        // Try to insert into the database
        try {
            String sqlResponse = sqlInsertOperations.insertNewLanguage(language);

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
     * Delete a Language by it ID
     *
     * @param id - The ID for deletion
     * @return - ResponseEntity with the status of the operation
     */
    public ResponseEntity deleteLanguageById(int id) {
        // Try to delete from the database
        try {
            String sqlResponse = sqlDeleteOperations.deleteLanguageById(id);

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
     * Update a Language
     *
     * @param id - The ID for the update
     * @param updatedLanguage - The new data to be inserted into the database
     * @return - ResponseEntity with the status of the operation
     */
    public ResponseEntity updateLanguage(int id, Language updatedLanguage) {
        // Validation Check
        if(updatedLanguage.getName() == null) {
            return ErrorBuilder.missingFieldError(ErrorBuilder.nameField);
        }

        // Set the ID
        updatedLanguage.setId(id);

        // Try to update the database
        try {
            String sqlResponse = sqlUpdateOperations.updateLanguage(updatedLanguage);

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