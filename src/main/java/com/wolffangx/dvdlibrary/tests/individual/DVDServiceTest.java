package com.wolffangx.dvdlibrary.tests.individual;

import com.jayway.restassured.http.ContentType;
import com.wolffangx.dvdlibrary.entities.Audio;
import com.wolffangx.dvdlibrary.entities.DVD;
import com.wolffangx.dvdlibrary.entities.Language;
import com.wolffangx.dvdlibrary.entities.Movie;
import com.wolffangx.dvdlibrary.entities.util.LanguageAudioPair;
import com.wolffangx.dvdlibrary.tests.util.TestUtil;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.util.LinkedList;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;

/**
 * The DVD ReST Web Service Unit Test
 * Uses the REST-Assured framework - https://code.google.com/p/rest-assured/
 *
 * given() - defines what will be send to the server
 * when() - defines the operation - GET, POST, PUT, DELETE, etc.
 * then() - defines what is the expected response from the server
 *
 * @author borislav.draganov
 */

public class DVDServiceTest {
    private static String resType = "dvds/";

    private static String validationFieldName = "isbn";

    //-----------------------------------------------------------------------------------------------------------------
    // Implementation Methods
    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Insert a new entry into the database and return it's ID.
     * This method will insert a new Movie, Genre, Rating, Audio and Language.
     *
     * @param type - JSON or XML
     * @param isbn - ISBN of the object to be inserted
     * @param expectedCode - The expected Status Code of the operation
     * @return - The ID of the newly generated entry
     */
    public static int insertNewEntry(ContentType type, String isbn, int expectedCode) {
        // URI to the Resources
        String path = TestUtil.getResourcePath(type, resType);

        // Path to the Information
        String dataPath = TestUtil.getDataFieldPath(type);

        // The new valid entry
        DVD validDVD = TestUtil.generateValidDVD(isbn);

        // Insert new Entry
        if (isbn != null) {
            String newId = // Get the ID of the newly added entry
                    given().
                            contentType(type).                              // Set the Content Type
                            body(validDVD).                                 // Supply the data
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
                            body(validDVD).                                 // Supply the data
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
     * Insert a new entry into the database and return it's ID.
     * This method will not insert anything else into the database, but will assume that a Audio and Language with the sample name already exist,
     * as well as a temporary Movie
     *
     * @param type - JSON or XML
     * @param isbn - ISBN of the object to be inserted
     * @return - The ID of the newly generated entry
     */
    public static int insertNewEntryWithExistingDependencies(ContentType type, String isbn) {
        // URI to the Resources
        String path = TestUtil.getResourcePath(type, resType);

        // Path to the Information
        String dataPath = TestUtil.getDataFieldPath(type);

        // The new valid entry
        // Only the ID is required
        Movie movie = new Movie();
        movie.setId(TestUtil.getTempMovieId());

        // Add to e list as a LanguageAudioPair
        List<LanguageAudioPair> laps = new LinkedList<LanguageAudioPair>();
        laps.add(new LanguageAudioPair(new Language(TestUtil.SAMPLE_NAME), new Audio(TestUtil.SAMPLE_NAME)));

        // Return the DVD Object
        DVD validDVD =  new DVD(movie, isbn, "edition", "16:9", "BG", laps);

        // Insert new Entry
            String newId = // Get the ID of the newly added entry
                    given().
                            contentType(type).                              // Set the Content Type
                            body(validDVD).                                 // Supply the data
                    when().
                            post(path).                                     // POST the data
                    then().
                            statusCode(HttpStatus.OK.value()).              // Check the Status Code
                            contentType(type).                              // Check the Content Type of the response
                    extract().
                            path(dataPath);                                 // Retrieve the ID of the newly inserted entry

            return Integer.parseInt(newId);                                 // Return the ID as an integer
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Insert a valid DVD object
     *
     * @param type - JSON or XML
     */
    public static void insertValidObject(ContentType type) {
        try {
            // Insert new entry
            int newId = insertNewEntry(type, TestUtil.SAMPLE_NAME, HttpStatus.OK.value());

            // Verify correct insert by reading the data
            TestUtil.getEntryByIdCheckField(type, resType, newId, validationFieldName, TestUtil.SAMPLE_NAME);

            // Delete the entry as it is no longer needed
            TestUtil.deleteEntryById(type, resType, newId, HttpStatus.OK.value());
        } finally {
            // Clean Up (Remove the temporary entries)
            TestUtil.cleanUpDVDTemps();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Insert a valid DVD object
     *
     * @param type - JSON or XML
     */
    public static void validUpdate(ContentType type) {
        try {
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
        } finally {
            // Clean Up (Remove the temporary entries)
            TestUtil.cleanUpDVDTemps();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Try to insert an invalid DVD object
     *
     * @param type - JSON or XML
     */
    public static void invalidUpdate(ContentType type) {
        try {
            // Insert a new entry
            int newId = insertNewEntry(type, TestUtil.SAMPLE_NAME, HttpStatus.OK.value());

            // Verify the newly inserted data
            TestUtil.getEntryById(type, resType, newId, HttpStatus.OK.value());

            // Try to update the entry
            updateEntryById(type, newId, null, HttpStatus.OK.value());

            // Verify the change didn't happen
            TestUtil.getEntryByIdCheckField(type, resType, newId, validationFieldName, TestUtil.SAMPLE_NAME);

            // Delete the entry
            TestUtil.deleteEntryById(type, resType, newId, HttpStatus.OK.value());

            // Verify correct deletion
            TestUtil.getEntryById(type, resType, newId, HttpStatus.BAD_REQUEST.value());
        } finally {
            // Clean Up (Remove the temporary entries)
            TestUtil.cleanUpDVDTemps();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Create a new DVD entry, retrieve it by Rating and then delete the same way
     *
     * @param type - JSON or XML
     */
    public static void getDvdsByMovieRating(ContentType type) {
        try {
            // Insert a new entry
            int newId = insertNewEntry(type, TestUtil.SAMPLE_NAME, HttpStatus.OK.value());

            // Verify the entry by its movie's rating
            TestUtil.getDvdsByMovieRating(type, TestUtil.getTempRatingId(), HttpStatus.OK.value());

            // Delete the entry
            TestUtil.deleteDvdsByMovieRating(type, TestUtil.getTempRatingId(), HttpStatus.OK.value());

            // Verify correct deletion
            TestUtil.getEntryById(type, resType, newId, HttpStatus.BAD_REQUEST.value());
        } finally {
            // Clean Up (Remove the temporary entries)
            TestUtil.cleanUpDVDTemps();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Create a new Rating entry, a Movie with it and 3 DVDs
     * Get the inserted data by movie rating sorted by ISBN
     *
     * @param type - JSON or XML
     */
    public static void getDvdsByMovieRatingWithSortingIsbn(ContentType type) {
        try {
            // Insert a new entry
            int newId1 = insertNewEntry(type, TestUtil.SAMPLE_NAME + 1, HttpStatus.OK.value());

            // Insert 2 more Movies with the same Rating
            int newId2 = insertNewEntryWithExistingDependencies(type, TestUtil.SAMPLE_NAME + 2);
            int newId3 = insertNewEntryWithExistingDependencies(type, TestUtil.SAMPLE_NAME + 3);

            // Verify the entry by its movie's rating
            TestUtil.getDvdsByMovieRatingWithSortByIsbn(type, TestUtil.getTempRatingId(), HttpStatus.OK.value());

            // Delete the entry
            TestUtil.deleteDvdsByMovieRating(type, TestUtil.getTempRatingId(), HttpStatus.OK.value());

            // Verify correct deletion
            TestUtil.getEntryById(type, resType, newId1, HttpStatus.BAD_REQUEST.value());
            TestUtil.getEntryById(type, resType, newId2, HttpStatus.BAD_REQUEST.value());
            TestUtil.getEntryById(type, resType, newId3, HttpStatus.BAD_REQUEST.value());
        } finally {
            // Clean Up (Remove the temporary entries)
            TestUtil.cleanUpDVDTemps();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Create a new Rating entry, a Movie with it and 3 DVDs
     * Get the inserted data by movie rating with pagination: second entry
     *
     * @param type - JSON or XML
     */
    public static void getDvdsByMovieRatingWithPaging(ContentType type) {
        try {
            // Insert a new entry
            int newId1 = insertNewEntry(type, TestUtil.SAMPLE_NAME + 1, HttpStatus.OK.value());

            // Insert 2 more Movies with the same Rating
            int newId2 = insertNewEntryWithExistingDependencies(type, TestUtil.SAMPLE_NAME + 2);
            int newId3 = insertNewEntryWithExistingDependencies(type, TestUtil.SAMPLE_NAME + 3);

            // Verify the entry by its movie's rating
            TestUtil.getDvdsByMovieRatingWithPaging(type, TestUtil.getTempRatingId(), HttpStatus.OK.value());

            // Delete the entry
            TestUtil.deleteDvdsByMovieRating(type, TestUtil.getTempRatingId(), HttpStatus.OK.value());

            // Verify correct deletion
            TestUtil.getEntryById(type, resType, newId1, HttpStatus.BAD_REQUEST.value());
            TestUtil.getEntryById(type, resType, newId2, HttpStatus.BAD_REQUEST.value());
            TestUtil.getEntryById(type, resType, newId3, HttpStatus.BAD_REQUEST.value());
        } finally {
            // Clean Up (Remove the temporary entries)
            TestUtil.cleanUpDVDTemps();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Create a new DVD entry, retrieve it by Language and Audio and then delete the same way
     *
     * @param type - JSON or XML
     */
    public static void getDvdsByLanguageAudio(ContentType type) {
        try {
            // Insert a new entry
            int newId = insertNewEntry(type, TestUtil.SAMPLE_NAME, HttpStatus.OK.value());

            // Verify the entry by its Language and Audio
            TestUtil.getDvdsByLanguageAudio(type, TestUtil.SAMPLE_NAME, TestUtil.SAMPLE_NAME, HttpStatus.OK.value());

            // Delete the entry
            TestUtil.deleteDvdsByLanguageAudio(type, TestUtil.SAMPLE_NAME, TestUtil.SAMPLE_NAME, HttpStatus.OK.value());

            // Verify correct deletion
            TestUtil.getEntryById(type, resType, newId, HttpStatus.BAD_REQUEST.value());
        } finally {
            // Clean Up (Remove the temporary entries)
            TestUtil.cleanUpDVDTemps();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Create a new Rating entry, a Movie with it and 3 DVDs
     * Get the inserted data by movie rating sorted by ISBN
     *
     * @param type - JSON or XML
     */
    public static void getDvdsByLanguageAudioWithSortingIsbn(ContentType type) {
        try {
            // Insert a new entry
            int newId1 = insertNewEntry(type, TestUtil.SAMPLE_NAME + 1, HttpStatus.OK.value());

            // Insert 2 more Movies with the same Language and Audio
            int newId2 = insertNewEntryWithExistingDependencies(type, TestUtil.SAMPLE_NAME + 2);
            int newId3 = insertNewEntryWithExistingDependencies(type, TestUtil.SAMPLE_NAME + 3);

            // Verify the entry by its Language and Audio
            TestUtil.getDvdsByLanguageAudioWithSortByIsbn(type, TestUtil.SAMPLE_NAME, TestUtil.SAMPLE_NAME, HttpStatus.OK.value());

            // Delete the entry
            TestUtil.deleteDvdsByLanguageAudio(type, TestUtil.SAMPLE_NAME, TestUtil.SAMPLE_NAME, HttpStatus.OK.value());

            // Verify correct deletion
            TestUtil.getEntryById(type, resType, newId1, HttpStatus.BAD_REQUEST.value());
            TestUtil.getEntryById(type, resType, newId2, HttpStatus.BAD_REQUEST.value());
            TestUtil.getEntryById(type, resType, newId3, HttpStatus.BAD_REQUEST.value());
        } finally {
            // Clean Up (Remove the temporary entries)
            TestUtil.cleanUpDVDTemps();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Create a new Rating entry, a Movie with it and 3 DVDs
     * Get the inserted data by movie rating with pagination: second entry
     *
     * @param type - JSON or XML
     */
    public static void getDvdsByLanguageAudioWithPaging(ContentType type) {
        try {
            // Insert a new entry
            int newId1 = insertNewEntry(type, TestUtil.SAMPLE_NAME + 1, HttpStatus.OK.value());

            // Insert 2 more Movies with the same Language and Audio
            int newId2 = insertNewEntryWithExistingDependencies(type, TestUtil.SAMPLE_NAME + 2);
            int newId3 = insertNewEntryWithExistingDependencies(type, TestUtil.SAMPLE_NAME + 3);

            // Verify the entry by its Language and Audio
            TestUtil.getDvdsByLanguageAudioWithPaging(type, TestUtil.SAMPLE_NAME, TestUtil.SAMPLE_NAME, HttpStatus.OK.value());

            // Delete the entry
            TestUtil.deleteDvdsByLanguageAudio(type, TestUtil.SAMPLE_NAME, TestUtil.SAMPLE_NAME, HttpStatus.OK.value());

            // Verify correct deletion
            TestUtil.getEntryById(type, resType, newId1, HttpStatus.BAD_REQUEST.value());
            TestUtil.getEntryById(type, resType, newId2, HttpStatus.BAD_REQUEST.value());
            TestUtil.getEntryById(type, resType, newId3, HttpStatus.BAD_REQUEST.value());
        } finally {
            // Clean Up (Remove the temporary entries)
            TestUtil.cleanUpDVDTemps();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Update a given entry with a new name. Expect a given return code
     *
     * @param type - JSON or XML
     * @param id - ID of the Entry to be updated
     * @param isbn - The ISBN that should be given to the entry
     * @param expectedCode - The return code that's expected from the operation - 200 or 400
     */
    public static void updateEntryById(ContentType type, int id, String isbn, int expectedCode) {
        // Get the Resource Path
        String path = TestUtil.getResourcePath(type, resType);

        // The new valid entry
        DVD movie = new DVD();
        movie.setIsbn(isbn);

        // Update the Entry
        given().
                contentType(type).                      // Set the Content Type
                body(movie).                            // Supply the data
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
        try {
            insertNewEntry(ContentType.JSON, null, HttpStatus.BAD_REQUEST.value());
        } finally {
            // Clean Up (Remove the temporary entries)
            TestUtil.cleanUpDVDTemps();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Insert Invalid Object in XML Format
     */
    @Test
    public void testInsertInvalidObjectXML() {
        try {
            insertNewEntry(ContentType.XML, null, HttpStatus.BAD_REQUEST.value());
        } finally {
            // Clean Up (Remove the temporary entries)
            TestUtil.cleanUpDVDTemps();
        }
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
    /**
     * Create a new DVD entry, retrieve it by its movie Rating and then delete the same way in JSON Format
     */
    @Test
    public void testGetDvdsByMovieRatingJSON() {
        getDvdsByMovieRating(ContentType.JSON);
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Create a new DVD entry, retrieve it by its movie Rating and then delete the same way in XML Format
     */
    @Test
    public void testGetDvdsByMovieRatingXML() {
        getDvdsByMovieRating(ContentType.XML);
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Create a new DVD entry, retrieve it by its movie Rating and then delete the same way in JSON Format
     * The retrieved list must be sorted by ISBN
     */
    @Test
    public void testGetDvdsByMovieRatingWithSortingIsbnJSON() {
        getDvdsByMovieRatingWithSortingIsbn(ContentType.JSON);
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Create a new DVD entry, retrieve it by its movie Rating and then delete the same way in XML Format
     * The retrieved list must be sorted by ISBN
     */
    @Test
    public void testGetDvdsByMovieRatingWithSortingIsbnXML() {
        getDvdsByMovieRatingWithSortingIsbn(ContentType.XML);
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Create a new DVD entry, retrieve it by its movie Rating and then delete the same way in JSON Format
     * Page result: second entry
     */
    @Test
    public void testGetDvdsByMovieRatingWithPagingJSON() {
        getDvdsByMovieRatingWithPaging(ContentType.JSON);
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Create a new DVD entry, retrieve it by its movie Rating and then delete the same way in XML Format
     * Page result: second entry
     */
    @Test
    public void testGetDvdsByMovieRatingWithPagingXML() {
        getDvdsByMovieRatingWithPaging(ContentType.XML);
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Create a new DVD entry, retrieve it by Language and Audio and then delete the same way in JSON Format
     */
    @Test
    public void testGetDvdsByLanguageAudioJSON() {
        getDvdsByLanguageAudio(ContentType.JSON);
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Create a new DVD entry, retrieve it by Language and Audio and then delete the same way in XML Format
     */
    @Test
    public void testGetDvdsByLanguageAudioXML() {
        getDvdsByLanguageAudio(ContentType.XML);
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Create 3 new DVD entries, retrieve them by Language and Audio and then delete the same way in JSON Format
     * Request sort by ISBN.
     */
    @Test
    public void testGetDvdsByLanguageAudioWithSortingIsbnJSON() {
        getDvdsByLanguageAudioWithSortingIsbn(ContentType.JSON);
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Create 3 new DVD entries, retrieve them by Language and Audio and then delete the same way in XML Format
     * Request sort by ISBN.
     */
    @Test
    public void testGetDvdsByLanguageAudioWithSortingIsbnXML() {
        getDvdsByLanguageAudioWithSortingIsbn(ContentType.XML);
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Create 3 new DVD entries, retrieve them by Language and Audio and then delete the same way in JSON Format
     * Page result: second entry
     */
    @Test
    public void testGetDvdsByLanguageAudioWithPagingJSON() {
        getDvdsByLanguageAudioWithPaging(ContentType.JSON);
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Create 3 new DVD entries, retrieve them by Language and Audio and then delete the same way in XML Format
     * Page result: second entry
     */
    @Test
    public void testGetDvdsByLanguageAudioWithPagingXML() {
        getDvdsByLanguageAudioWithPaging(ContentType.XML);
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

    // Getters
    public static String getResType() {
        return resType;
    }
}