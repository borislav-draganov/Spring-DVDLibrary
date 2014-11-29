package com.wolffangx.dvdlibrary.resources.json;

import com.wolffangx.dvdlibrary.entities.DVD;
import com.wolffangx.dvdlibrary.resources.DVDRes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Exposed DVD JSON Web Services
 *
 * @author borislav.draganov
 */

@RestController
@RequestMapping("json/dvds")
public class DVDJSONResource extends DVDRes {
    /**
     * Retrieve all elements from the database
     *
     * @return - A ResponseEntity with a List of DVD objects
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAll() {
        return super.GetAllDVDs();
    }

    //------------------------------------------------------------------------------------------------------------------
    /**
     * Retrieve a single element from the database by a given ID
     *
     * @param id - Path Parameter for the requested ID
     * @return - A ResponseEntity with the DVD object or error
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getById(@PathVariable("id") int id) {
        return super.getDVDById(id);
    }

    //------------------------------------------------------------------------------------------------------------------
    /**
     * Retrieve all elements from the database with the given movie rating
     *
     * @param rating - The Movie Rating that will be used for filtering
     * @param sort - Available sorting - title and genre
     * @param offset - Start point of the paging
     * @param limit - Maximum number of entries
     * @return - A ResponseEntity with a List of DVD objects
     */
    @RequestMapping(value = "rating/{rating}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getDVDByMovieRating(@PathVariable("rating") int rating,
                                        @RequestParam(value = "sort", required = false) String sort,
                                        @RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
                                        @RequestParam(value = "limit", required = false, defaultValue = "0") int limit) {
        return super.getDVDByMovieRating(rating, sort, offset, limit);
    }

    //------------------------------------------------------------------------------------------------------------------
    /**
     * Delete all DVDs with a movie with a given rating
     *
     * @param rating - The Movie Rating by which the DVDs should be deleted
     * @return - A ResponseEntity with either a success message or an error message
     */
    @RequestMapping(value = "rating/{rating}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteDVDByMovieRating(@PathVariable("rating") int rating) {
        return super.deleteDVDByMovieRating(rating);
    }

    //------------------------------------------------------------------------------------------------------------------
    /**
     * Retrieve all DVDs with the given Language and Audio
     *
     * @param language - The Language that will be used for filtering
     * @param audio - The Audio that will be used for filtering
     * @param sort - Available sorting - title and genre
     * @param offset - Start point of the paging
     * @param limit - Maximum number of entries
     * @return - A ResponseEntity with a List of DVD objects
     */
    @RequestMapping(value = "language/{language}/audio/{audio}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getDVDByLAP(@PathVariable("language") String language, @PathVariable("audio") String audio,
                                      @RequestParam(value = "sort", required = false) String sort,
                                      @RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
                                      @RequestParam(value = "limit", required = false, defaultValue = "0") int limit) {
        return super.getDVDByLAP(language, audio, sort, offset, limit);
    }

    //------------------------------------------------------------------------------------------------------------------
    /**
     * Retrieve all DVDs with the given Language and Audio
     * @param language - The Language that will be used for filtering
     * @param audio - The Audio that will be used for filtering
     * @return - A ResponseEntity with either a success message or an error message
     */
    @RequestMapping(value = "language/{language}/audio/{audio}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteDVDByLAP(@PathVariable("language") String language, @PathVariable("audio") String audio) {
        return super.deleteDVDByLAP(language, audio);
    }

    //------------------------------------------------------------------------------------------------------------------
    /**
     * Insert a new entry into the database
     *
     * @param dvd - JSON Serialized object to be inserted into the database
     * @return - ResponseEntity object with the ID of the new entry or error message
     */
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity insertNew(@RequestBody DVD dvd) {
        return super.insertNewDVD(dvd);
    }

    //------------------------------------------------------------------------------------------------------------------
    /**
     * Update an element in the database by a given ID
     *
     * @param id - Path Parameter for the target ID
     * @return - A ResponseEntity with either a success message or an error message
     */
    @RequestMapping(value = "{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@PathVariable("id") int id, @RequestBody DVD updatedDvd) {
        return super.updateDVD(id, updatedDvd);
    }

    //------------------------------------------------------------------------------------------------------------------
    /**
     * Delete an element in the database by a given ID
     *
     * @param id - Path Parameter for the target ID
     * @return - A ResponseEntity with either a success message or an error message
     */
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteDVD(@PathVariable("id") int id) {
        return super.deleteDVDById(id);
    }
}