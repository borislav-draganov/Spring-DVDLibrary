package com.wolffangx.dvdlibrary.resources.json;

import com.wolffangx.dvdlibrary.entities.Language;
import com.wolffangx.dvdlibrary.resources.LanguageRes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Exposed Language JSON Web Services
 *
 * @author borislav.draganov
 */

@RestController
@RequestMapping("json/languages")
public class LanguageJSONResource extends LanguageRes {
    /**
     * Retrieve all elements from the database
     *
     * @return - A ResponseEntity with a List of Language objects
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAll() {
        return super.GetAllLanguages();
    }

    //------------------------------------------------------------------------------------------------------------------
    /**
     * Retrieve a single element from the database by a given ID
     *
     * @param id - Path Parameter for the requested ID
     * @return - A ResponseEntity with the Language object or error
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getById(@PathVariable("id") int id) {
        return super.getLanguageById(id);
    }

    //------------------------------------------------------------------------------------------------------------------
    /**
     * Insert a new entry into the database
     *
     * @param language - JSON Serialized object to be inserted into the database
     * @return - ResponseEntity object with the ID of the new entry or error message
     */
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity insertNew(@RequestBody Language language) {
        return super.insertNewLanguage(language);
    }

    //------------------------------------------------------------------------------------------------------------------
    /**
     * Update an element in the database by a given ID
     *
     * @param id - Path Parameter for the target ID
     * @return - A ResponseEntity with either a success message or an error message
     */
    @RequestMapping(value = "{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@PathVariable("id") int id, @RequestBody Language updatedLanguage) {
        return super.updateLanguage(id, updatedLanguage);
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
        return super.deleteLanguageById(id);
    }
}