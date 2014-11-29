package com.wolffangx.dvdlibrary.resources;

import com.wolffangx.dvdlibrary.entities.DVD;
import com.wolffangx.dvdlibrary.exceptions.MissingIdException;
import com.wolffangx.dvdlibrary.resources.util.ErrorBuilder;
import com.wolffangx.dvdlibrary.resources.util.ErrorMessages;
import com.wolffangx.dvdlibrary.resources.util.WebRes;
import com.wolffangx.dvdlibrary.responses.ErrorResponseEntity;
import com.wolffangx.dvdlibrary.responses.SuccessResponseEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * The actual implementation of the DVD Web Resources
 *
 * @author borislav.draganov
 */

public class DVDRes extends WebRes {
    /**
     * Get all DVDs in the database
     *
     * @return - Response with a list of all DVDs in the database
     */
    public ResponseEntity GetAllDVDs() {
        // Try to read all DVDs from the database
        try {
            List<DVD> sqlResponse = sqlReportOperations.viewAllDVDs();

            // Return with HTTP Status Code 200
            HttpStatus status = HttpStatus.OK;

            // Wrap the result in a standard response format
            SuccessResponseEntity<DVD> responseEntity = new SuccessResponseEntity<DVD>(status, sqlResponse);

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
     * Get a DVD by its ID
     *
     * @param id - The requested ID
     * @return - Response with a valid DVD object from the data in the database
     */
    public ResponseEntity getDVDById(int id) {
        // Try to read from the database
        try {
            DVD sqlResponse = sqlReportOperations.viewDVDById(id);

            // Return with HTTP Status Code 200
            HttpStatus status = HttpStatus.OK;

            // Wrap the result in a standard response format
            SuccessResponseEntity<DVD> responseEntity = new SuccessResponseEntity<DVD>(status, sqlResponse);

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
     * Get a list of DVDs by their movie ratings - allows sorting and paging
     *
     * @param rating - The Movie Rating that will be used for filtering
     * @param sort   - Available sorting - title and genre
     * @param offset - Start point of the paging
     * @param limit  - Maximum number of entries
     * @return - Response with a list of all DVDs in the database that meet the criteria
     */
    public ResponseEntity getDVDByMovieRating(int rating, final String sort, int offset, int limit) {
        // Try to read from the database
        try {
            List<DVD> sqlResponse = sqlReportOperations.viewDVDByMovieRating(rating);

            // Sort
            sqlResponse = sortBy(sqlResponse, sort);

            // Paging
            sqlResponse = paging(sqlResponse, offset, limit);

            // Return with HTTP Status Code 200
            HttpStatus status = HttpStatus.OK;

            // Wrap the result in a standard response format
            SuccessResponseEntity<DVD> responseEntity = new SuccessResponseEntity<DVD>(status, sqlResponse);

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
     * Delete a DVDs by their movie rating
     *
     * @param rating - The Movie Rating by which the DVDs should be deleted
     * @return - Response with the status of the operation
     */
    public ResponseEntity deleteDVDByMovieRating(int rating) {
        // Try to delete from the database
        try {
            String sqlResponse = sqlDeleteOperations.deleteDVDByMovieRating(rating);

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
     * Get DVDs by their Language and Audio Pair
     *
     * @param language - The Language that will be used for filtering
     * @param audio    - The Audio that will be used for filtering
     * @param sort     - Available sorting - title and genre
     * @param offset   - Start point of the paging
     * @param limit    - Maximum number of entries
     * @return - Response with a list of all DVDs in the database that meet the criteria
     */
    public ResponseEntity getDVDByLAP(String language, String audio, final String sort, int offset, int limit) {
        // Try to read from the database
        try {
            List<DVD> sqlResponse = sqlReportOperations.viewDVDByLAP(language, audio);

            // Sort
            sqlResponse = sortBy(sqlResponse, sort);

            // Paging
            sqlResponse = paging(sqlResponse, offset, limit);

            // Return with HTTP Status Code 200
            HttpStatus status = HttpStatus.OK;

            // Wrap the result in a standard response format
            SuccessResponseEntity<DVD> responseEntity = new SuccessResponseEntity<DVD>(status, sqlResponse);

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
     * Delete DVDs by their Language and Audio Pairs
     *
     * @param language - The Language by which DVDs should be deleted
     * @param audio    - The Audio by which DVDs should be deleted
     * @return - Response with the status of the operation
     */
    public ResponseEntity deleteDVDByLAP(String language, String audio) {
        // Try to delete from the database
        try {
            String sqlResponse = sqlDeleteOperations.deleteDVDByLAP(language, audio);

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
     * Insert a new DVD
     *
     * @param newDvd - The object to be inserted into the database
     * @return - Response with the status of the operation
     */
    public ResponseEntity insertNewDVD(DVD newDvd) {
        // Validation Check
        if (newDvd.getMovie() == null) {
            return ErrorBuilder.missingFieldError(ErrorBuilder.movieIdField);
        } else if (newDvd.getMovie().getId() <= 0) {
            return ErrorBuilder.invalidFieldError(ErrorBuilder.movieIdField);
        } else if (newDvd.getIsbn() == null) {
            return ErrorBuilder.missingFieldError(ErrorBuilder.isbnIdField);
        } else if (newDvd.getLanguageAudioPairs() == null) {
            return ErrorBuilder.missingFieldError(ErrorBuilder.LAPField);
        } else if (newDvd.getLanguageAudioPairs().isEmpty()) {
            return ErrorBuilder.invalidFieldError(ErrorBuilder.LAPField);
        }

        // Try to insert into the database
        try {
            String sqlResponse = sqlInsertOperations.insertNewDVD(newDvd);

            // Return with HTTP Status Code 200
            HttpStatus status = HttpStatus.OK;

            // Wrap the result in a standard response format
            SuccessResponseEntity<String> responseEntity = new SuccessResponseEntity<String>(status, sqlResponse);

            // Return the response entity
            return new ResponseEntity<SuccessResponseEntity>(responseEntity, status);
        }
        // No matching language/audio found in the database
        catch (EmptyResultDataAccessException ex) {
            // Return with HTTP Status Code 400
            HttpStatus status = HttpStatus.BAD_REQUEST;

            // Wrap the result in a standard response format
            ErrorResponseEntity errorResponseEntity = new ErrorResponseEntity(status, "No matching language/audio found in the database");

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
     * Delete a DVD by it ID
     *
     * @param id - The ID for deletion
     * @return - Response with the status of the operation
     */
    public ResponseEntity deleteDVDById(int id) {
        // Try to delete from the database
        try {
            String sqlResponse = sqlDeleteOperations.deleteDVDById(id);

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
     * Update a Dvd
     *
     * @param id         - The ID for the update
     * @param updatedDvd - The new data to be inserted into the database
     * @return - Response with the status of the operation
     */
    public ResponseEntity updateDVD(int id, DVD updatedDvd) {
        // Set the ID
        updatedDvd.setId(id);

        // Try to update the database entry
        try {
            String sqlResponse = sqlUpdateOperations.updateDVD(updatedDvd);

            // Return with HTTP Status Code 200
            HttpStatus status = HttpStatus.OK;

            // Wrap the result in a standard response format
            SuccessResponseEntity<String> responseEntity = new SuccessResponseEntity<String>(status, sqlResponse);

            // Return the response entity
            return new ResponseEntity<SuccessResponseEntity>(responseEntity, status);
        }
        // No matching language/audio found in the database
        catch (EmptyResultDataAccessException ex) {
            // Return with HTTP Status Code 400
            HttpStatus status = HttpStatus.BAD_REQUEST;

            // Wrap the result in a standard response format
            ErrorResponseEntity errorResponseEntity = new ErrorResponseEntity(status, "No matching language/audio found in the database");

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
     * Sort a given List of DVDs by different fields
     *
     * @param list - The List of DVDs to sort
     * @param sort - The name of the field by which to sort
     * @return - The sorted list
     */
    public static List<DVD> sortBy(List<DVD> list, final String sort) {
        if (sort != null) {
            // Make a Comparator
            final Comparator<DVD> comp = new Comparator<DVD>() {
                @Override
                public int compare(DVD o1, DVD o2) {
                    // Movie Title
                    if (sort.equals("title")) {
                        return o1.getMovie().getTitle().compareTo(o2.getMovie().getTitle());
                    }
                    // Movie Genres
                    else if (sort.equals("genre")) {
                        return o1.getMovie().getGenres().toString().compareTo(o2.getMovie().getGenres().toString());
                    }
                    // DVD ISBN
                    else if (sort.equals("isbn")) {
                        return o1.getIsbn().compareTo(o2.getIsbn());
                    }
                    // Invalid sorting request
                    else {
                        return 0;
                    }
                }
            };

            // Sort the List using the Comparator
            Collections.sort(list, comp);
        }

        // Return the list
        return list;
    }

    //------------------------------------------------------------------------------------------------------------------

    /**
     * Page a given List of DVDs
     *
     * @param list   - The List of DVDs to be paged
     * @param offset - Start point of the paging
     * @param limit  - Maximum number of entries
     * @return - The paged portion of the given list
     */
    public static List<DVD> paging(List<DVD> list, int offset, int limit) {
        // Get the size of the list
        int listSize = list.size();

        if (limit <= 0) {
            limit = listSize;
        }                           // In case of an invalid or missing offset, return all data
        if (offset > listSize || offset < 0) {
            offset = listSize;
        }     // If the given offset is beyond the size of the list or is invalid, put it at the end of the list - no entries will be returned

        // Determine the endpoint of the pagination
        int to = Math.min(listSize, offset + limit);

        // Take the requested part of the list
        list = list.subList(offset, to);

        // Return the list
        return list;
    }
}