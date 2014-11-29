package com.wolffangx.dvdlibrary.resources.json;

import com.wolffangx.dvdlibrary.entities.Rating;
import com.wolffangx.dvdlibrary.resources.RatingRes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Exposed Rating JSON Web Services
 *
 * @author borislav.draganov
 */

@RestController
@RequestMapping("json/ratings")
public class RatingJSONResource extends RatingRes {
    /**
     * Retrieve all elements from the database
     *
     * @return - A ResponseEntity with a List of Rating objects
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAll() {
        return super.GetAllRatings();
    }

    //------------------------------------------------------------------------------------------------------------------
    /**
     * Retrieve a single element from the database by a given ID
     *
     * @param id - Path Parameter for the requested ID
     * @return - A ResponseEntity with the Rating object or error
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getById(@PathVariable("id") int id) {
        return super.getRatingById(id);
    }

    //------------------------------------------------------------------------------------------------------------------
    /**
     * Insert a new entry into the database
     *
     * @param rating - JSON Serialized object to be inserted into the database
     * @return - ResponseEntity object with the ID of the new entry or error message
     */
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity insertNew(@RequestBody Rating rating) {
        return super.insertNewRating(rating);
    }

    //------------------------------------------------------------------------------------------------------------------
    /**
     * Update an element in the database by a given ID
     *
     * @param id - Path Parameter for the target ID
     * @return - A ResponseEntity with either a success message or an error message
     */
    @RequestMapping(value = "{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@PathVariable("id") int id, @RequestBody Rating updatedRating) {
        return super.updateRating(id, updatedRating);
    }

    //------------------------------------------------------------------------------------------------------------------
    /**
     * Delete an element in the database by a given ID
     *
     * @param id - Path Parameter for the target ID
     * @return - A ResponseEntity with either a success message or an error message
     */
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteMovie(@PathVariable("id") int id) {
        return super.deleteRatingById(id);
    }
}