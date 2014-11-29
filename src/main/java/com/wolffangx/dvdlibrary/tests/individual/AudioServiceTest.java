package com.wolffangx.dvdlibrary.tests.individual;

import com.jayway.restassured.http.ContentType;
import com.wolffangx.dvdlibrary.entities.Audio;
import com.wolffangx.dvdlibrary.tests.util.TestUtil;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static com.jayway.restassured.RestAssured.given;

/**
 * The Audio ReST Web Service Unit Test
 * Uses the REST-Assured framework - https://code.google.com/p/rest-assured/
 *
 * given() - defines what will be send to the server
 * when() - defines the operation - GET, POST, PUT, DELETE, etc.
 * then() - defines what is the expected response from the server
 *
 * @author borislav.draganov
 */

public class AudioServiceTest {
    private static String resType = "audios/";

    private static String validationFieldName = "name";

    //-----------------------------------------------------------------------------------------------------------------
    // Implementation Methods
    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Insert a new entry into the database, retrieve the data for validation and then delete the data
     *
     * @param type - JSON or XML
     * @param name - Name of the object to be inserted
     * @param expectedCode - The expected Status Code of the operation
     * @return - The ID of the newly generated entry
     */
    public static int insertNewEntry(ContentType type, String name, int expectedCode) {
        // URI to the Resources
        String path = TestUtil.getResourcePath(type, resType);

        // Path to the Information
        String dataPath = TestUtil.getDataFieldPath(type);

        // The new valid entry
        Audio validAudio = new Audio(name);

        // Insert new Entry
        if (name != null) {
            String newId = // Get the ID of the newly added entry
                    given().
                            contentType(type).                              // Set the Content Type
                            body(validAudio).                               // Supply the data
                    when().
                            post(path).                                     // POST the data
                    then().
                            statusCode(expectedCode).                       // Check the Status Code
                            contentType(type).                              // Check the Content Type of the response
                    extract().
                            path(dataPath);                                 // Retrieve the ID of the newly inserted entry

            return Integer.parseInt(newId);                                 // Return the ID as an integer
        } else {
                    given().
                            contentType(type).                              // Set the Content Type
                            body(validAudio).                               // Supply the data
                    when().
                            post(path).                                     // POST the data
                    then().
                            statusCode(expectedCode).                       // Check the Status Code
                            contentType(type);                              // Check the Content Type of the response

            return 0;
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Insert a valid Audio object
     *
     * @param type - JSON or XML
     */
    public static void insertValidObject(ContentType type) {
        // Insert new entry
        int newId = insertNewEntry(type, TestUtil.SAMPLE_NAME, HttpStatus.OK.value());

        // Verify correct insert by reading the data
        TestUtil.getEntryByIdCheckField(type, resType, newId, validationFieldName, TestUtil.SAMPLE_NAME);

        // Delete the entry as it is no longer needed
        TestUtil.deleteEntryById(type, resType, newId, HttpStatus.OK.value());
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Insert a valid Audio object
     *
     * @param type - JSON or XML
     */
    public static void validUpdate(ContentType type) {
        // Insert a new entry
        int newId = insertNewEntry(type, TestUtil.SAMPLE_NAME, HttpStatus.OK.value());

        // Verify the newly inserted data
        TestUtil.getEntryById(type, resType, newId, HttpStatus.OK.value());

        // Update the entry
        String newName = "UPDATED";
        updateEntryById(type, newId, newName, HttpStatus.OK.value());

        // Verify the change
        TestUtil.getEntryByIdCheckField(type, resType, newId, validationFieldName, newName);

        // Delete the entry
        TestUtil.deleteEntryById(type, resType, newId, HttpStatus.OK.value());

        // Verify correct deletion
        TestUtil.getEntryById(type, resType, newId, HttpStatus.BAD_REQUEST.value());
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Try to insert an invalid Audio object
     *
     * @param type - JSON or XML
     */
    public static void invalidUpdate(ContentType type) {
        // Insert a new entry
        int newId = insertNewEntry(type, TestUtil.SAMPLE_NAME, HttpStatus.OK.value());

        // Verify the newly inserted data
        TestUtil.getEntryById(type, resType, newId, HttpStatus.OK.value());

        // Try to update the entry
        updateEntryById(type, newId, null, HttpStatus.BAD_REQUEST.value());

        // Verify the change didn't happen
        TestUtil.getEntryByIdCheckField(type, resType, newId, validationFieldName, TestUtil.SAMPLE_NAME);

        // Delete the entry
        TestUtil.deleteEntryById(type, resType, newId, HttpStatus.OK.value());

        // Verify correct deletion
        TestUtil.getEntryById(type, resType, newId, HttpStatus.BAD_REQUEST.value());
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Update a given entry with a new name. Expect a given return code
     *
     * @param type - JSON or XML
     * @param id - ID of the Entry to be updated
     * @param newName - The Name that should be given to the entry
     * @param expectedCode - The return code that's expected from the operation - 200 or 400
     */
    public static void updateEntryById(ContentType type, int id, String newName, int expectedCode) {
        // Get the Resource Path
        String path = TestUtil.getResourcePath(type, resType);

        // The new valid entry
        Audio validAudio = new Audio(newName);

        // Update the Entry
        given().
                contentType(type).                      // Set the Content Type
                body(validAudio).                       // Supply the data
        when().
                post(path + id).                        // Update the entry on the given ID
        then().
                statusCode(expectedCode).               // Check the Expected Status Code
                contentType(type);                      // Check if the response format is correct
    }

    //-----------------------------------------------------------------------------------------------------------------
    // INSERT Tests
    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Insert Valid Object in JSON Format
     */
    @Test
    public void testInsertValidObjectJSON() {
        insertValidObject(ContentType.JSON);
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Insert Valid Object in XML Format
     */
    @Test
    public void testInsertValidObjectXML() {
        insertValidObject(ContentType.XML);
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Insert Invalid Object in JSON Format
     */
    @Test
    public void testInsertInvalidObjectJSON() {
        insertNewEntry(ContentType.JSON, null, HttpStatus.BAD_REQUEST.value());
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Insert Invalid Object in XML Format
     */
    @Test
    public void testInsertInvalidObjectXML() {
        insertNewEntry(ContentType.XML, null, HttpStatus.BAD_REQUEST.value());
    }

    //-----------------------------------------------------------------------------------------------------------------
    // GET Tests
    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Get All Entries in JSON Format
     */
    @Test
    public void testGetAllJSON() {
        TestUtil.getAllEntries(ContentType.JSON, resType, HttpStatus.OK.value());
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Get All Entries in XML Format
     */
    @Test
    public void testGetAllXML() {
        TestUtil.getAllEntries(ContentType.XML, resType, HttpStatus.OK.value());
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Get Invalid ID in JSON Format
     */
    @Test
    public void testGetInvalidIdJSON() {
        TestUtil.getEntryById(ContentType.JSON, resType, TestUtil.INVALID_ID, HttpStatus.BAD_REQUEST.value());
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Get Invalid ID in XML Format
     */
    @Test
    public void testGetInvalidIdXML() {
        TestUtil.getEntryById(ContentType.XML, resType, TestUtil.INVALID_ID, HttpStatus.BAD_REQUEST.value());
    }

    //-----------------------------------------------------------------------------------------------------------------
    // UPDATE Tests
    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Insert a new entry in the database, update it(change it) and then delete it. JSON Format
     */
    @Test
    public void testValidUpdateJSON() {
        validUpdate(ContentType.JSON);
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Insert a new entry in the database, update it(change it) and then delete it. XML Format
     */
    @Test
    public void testValidUpdateXML() {
        validUpdate(ContentType.XML);
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Try to Update with invalid data in JSON Format
     */
    @Test
    public void testInvalidUpdateJSON() {
        invalidUpdate(ContentType.JSON);
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Try to Update with invalid data in XML Format
     */
    @Test
    public void testInvalidUpdateXML() {
        invalidUpdate(ContentType.XML);
    }

    //-----------------------------------------------------------------------------------------------------------------
    // DELETE Tests
    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Try to delete an invalid ID in JSON Format
     */
    @Test
    public void testInvalidDeleteJSON() {
        TestUtil.deleteEntryById(ContentType.JSON, resType, TestUtil.INVALID_ID, HttpStatus.BAD_REQUEST.value());
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Try to delete an invalid ID in XML Format
     */
    @Test
    public void testInvalidDeleteXML() {
        TestUtil.deleteEntryById(ContentType.XML, resType, TestUtil.INVALID_ID, HttpStatus.BAD_REQUEST.value());
    }
}