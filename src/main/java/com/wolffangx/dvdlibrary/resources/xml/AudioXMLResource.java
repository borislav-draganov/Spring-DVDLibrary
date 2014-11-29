package com.wolffangx.dvdlibrary.resources.xml;

import com.wolffangx.dvdlibrary.entities.Audio;
import com.wolffangx.dvdlibrary.resources.AudioRes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Exposed Audio XML Web Services
 *
 * @author borislav.draganov
 */

@RestController
@RequestMapping("xml/audios")
public class AudioXMLResource extends AudioRes {
    /**
     * Retrieve all elements from the database
     *
     * @return - A Response with a List of Audio
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity getAll() {
        return super.GetAllAudios();
    }

    //------------------------------------------------------------------------------------------------------------------
    /**
     * Retrieve a single element from the database by a given ID
     *
     * @param id - Path Parameter for the requested ID
     * @return - A Response with the Audio object or error
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity getById(@PathVariable("id") int id) {
        return super.getAudioById(id);
    }

    //------------------------------------------------------------------------------------------------------------------
    /**
     * Insert a new entry into the database
     *
     * @param audio - XML Serialized object to be inserted into the database
     * @return - Response object with the ID of the new entry or error message
     */
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_XML_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity insertNew(@RequestBody Audio audio) {
        return super.insertNewAudio(audio);
    }

    //------------------------------------------------------------------------------------------------------------------
    /**
     * Update an element in the database by a given ID
     *
     * @param id - Path Parameter for the target ID
     * @return - A Response with either a success message or an error message
     */
    @RequestMapping(value = "{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_XML_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity update(@PathVariable("id") int id, @RequestBody Audio updatedAudio) {
        return super.updateAudio(id, updatedAudio);
    }

    //------------------------------------------------------------------------------------------------------------------
    /**
     * Delete an element in the database by a given ID
     *
     * @param id - Path Parameter for the target ID
     * @return - A Response with either a success message or an error message
     */
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity deleteMovie(@PathVariable("id") int id) {
        return super.deleteAudioById(id);
    }
}