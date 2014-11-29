package com.wolffangx.dvdlibrary.resources.json;

import com.wolffangx.dvdlibrary.entities.Genre;
import com.wolffangx.dvdlibrary.resources.GenreRes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Exposed Genre JSON Web Services
 *
 * @author borislav.draganov
 */

@RestController
@RequestMapping("json/genres")
public class JSONResource extends GenreRes {
    /**
     * Retrieve all elements from the database
     *
     * @return - A ResponseEntity with a List of Genre objects
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAll() {
        return super.GetAllGenres();
    }

    //------------------------------------------------------------------------------------------------------------------
    /**
     * Retrieve a single element from the database by a given ID
     *
     * @param id - Path Parameter for the requested ID
     * @return - A ResponseEntity with the Genre object or error
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getById(@PathVariable("id") int id) {
        return super.getGenreById(id);
    }

    //------------------------------------------------------------------------------------------------------------------
    /**
     * Insert a new entry into the database
     *
     * @param genre - JSON Serialized object to be inserted into the database
     * @return - ResponseEntity object with the ID of the new entry or error message
     */
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity insertNew(@RequestBody Genre genre) {
        return super.insertNewGenre(genre);
    }

    //------------------------------------------------------------------------------------------------------------------
    /**
     * Update an element in the database by a given ID
     *
     * @param id - Path Parameter for the target ID
     * @return - A ResponseEntity with either a success message or an error message
     */
    @RequestMapping(value = "{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@PathVariable("id") int id, @RequestBody Genre updatedGenre) {
        return super.updateGenre(id, updatedGenre);
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
        return super.deleteGenreById(id);
    }
}