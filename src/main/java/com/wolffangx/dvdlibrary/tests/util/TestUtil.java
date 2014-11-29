package com.wolffangx.dvdlibrary.tests.util;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.path.xml.XmlPath;
import com.wolffangx.dvdlibrary.entities.*;
import com.wolffangx.dvdlibrary.entities.util.LanguageAudioPair;
import com.wolffangx.dvdlibrary.tests.individual.*;
import org.springframework.http.HttpStatus;

import java.util.LinkedList;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.fail;

/**
 * An utility class to provide data that the Unit Tests use in common (e.g. URI, JSON Data Path, etc.)
 *
 * @author borislav.draganov
 */

public abstract class TestUtil {
    public static String RESOURCES = "http://localhost:8080/DVD.Library.Spring.Maven/%s/";

    public static String JSON_DATA_PATH = "data[0].";
    public static String XML_DATA_PATH = "response.data.entry[0].";

    public static final int INVALID_ID = -1;
    public static final String SAMPLE_NAME = "TEST INPUT";

    private static int tempRatingId;
    private static int tempGenreId;

    private static int tempMovieId;
    private static int tempLanguageId;
    private static int tempAudioId;

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Return the Complete URI to the resources based on the requested type (JSON or XML)
     *
     * @param type - JSON or XML
     * @return - Complete URI to the resources based on the requested type (JSON or XML)
     */
    public static String getResourcePath(ContentType type, String resType) {
        String JSON = "json";
        String XML = "xml";

        if (type == ContentType.JSON) {
            return String.format(RESOURCES, JSON) + resType;
        } else {
            return String.format(RESOURCES, XML) + resType;
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Return the data field path needed to read the data from the response (JSON or XML)
     *
     * @param type - JSON or XML
     * @return - Data field path needed to read the data from the response (JSON or XML)
     */
    public static String getDataFieldPath(ContentType type) {
        if (type == ContentType.JSON) {
            return JSON_DATA_PATH;
        } else {
            return XML_DATA_PATH;
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Generate and insert into the database a dummy genre and rating entry
     *
     * @return - A valid Movie object with a dummy genre and rating
     */
    public static Movie generateValidMovie(String name) {
        ContentType type = ContentType.JSON;
        int expectedCode = HttpStatus.OK.value();

        // Make a list out of a dummy genre
        tempGenreId = GenreServiceTest.insertNewEntry(type, SAMPLE_NAME, expectedCode);
        List<Genre> genres = new LinkedList<Genre>();
        genres.add(new Genre(SAMPLE_NAME));

        //Make a dummy rating entry
        tempRatingId = RatingServiceTest.insertNewEntry(type, SAMPLE_NAME, expectedCode);

        return new Movie(name, 120, "2014-01-01", 13, tempRatingId, genres);
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Delete the dummy genre and rating from the database
     */
    public static void cleanUpMovieTemps() {
        // Set the expected Status Code form the operations
        int expectedCode = HttpStatus.OK.value();

        // Remove the temporary genre
        deleteEntryById(ContentType.JSON, "genres/", tempGenreId, expectedCode);

        // Remove the temporary rating
        deleteEntryById(ContentType.JSON, "ratings/", tempRatingId, expectedCode);
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Generate and insert into the database a dummy movie, audio and language entry
     *
     * @return - A valid DVD object
     */
    public static DVD generateValidDVD(String isbn) {
        ContentType type = ContentType.JSON;       // Set the Type
        int expectedCode = HttpStatus.OK.value();  // Set the expected code

        // Make the dummy Movie
        tempMovieId = MovieServiceTest.insertNewEntry(type, SAMPLE_NAME, expectedCode);

        // Only the ID is required
        Movie movie = new Movie();
        movie.setId(tempMovieId);

        // Make the dummy Language and Audio
        tempAudioId = AudioServiceTest.insertNewEntry(type, SAMPLE_NAME, expectedCode);
        tempLanguageId = LanguageServiceTest.insertNewEntry(type, SAMPLE_NAME, expectedCode);

        // Add to e list as a LanguageAudioPair
        List<LanguageAudioPair> laps = new LinkedList<LanguageAudioPair>();
        laps.add(new LanguageAudioPair(new Language(SAMPLE_NAME), new Audio(SAMPLE_NAME)));

        // Return the DVD Object
        return new DVD(movie, isbn, "edition", "16:9", "BG", laps);
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Delete the dummy movie, audio and language from the database
     */
    public static void cleanUpDVDTemps() {
        // Set the type
        ContentType type = ContentType.JSON;

        // Set the expected Status Code form the operations
        int expectedCode = HttpStatus.OK.value();

        // Delete the Movie and related entries
        deleteEntryById(type, "movies/", tempMovieId, expectedCode);
        cleanUpMovieTemps();

        // Delete the temporary Audio entry
        deleteEntryById(type, "audios/", tempAudioId, expectedCode);

        // Delete the temporary Language entry
        deleteEntryById(type, "languages/", tempLanguageId, expectedCode);
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Get an entry by its ID
     *
     * @param type - JSON or XML
     * @param resType - The name of the resources
     * @param expectedCode - The Status code that's expected
     */
    public static void getAllEntries(ContentType type, String resType, int expectedCode) {
        // Get the Resource Path
        String path = getResourcePath(type, resType);

        // For JSON Format
        if (type == ContentType.JSON) {
            when().
                    get(path).                                              // GET the data
                    then().
                    statusCode(expectedCode).                               // Check the Status Code
                    contentType(type);                                      // Check the Content Type of the response
        }
        // For XML Format
        else {
            when().
                    get(path).                                              // GET the data
                    then().
                    statusCode(expectedCode).                               // Check the Status Code
                    contentType(type);                                      // Check the Content Type of the response
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Get an entry by its ID
     *
     * @param type - JSON or XML
     * @param resType - The name of the resources
     * @param id - The requested ID
     * @param expectedCode - The Status code that's expected
     */
    public static void getEntryById(ContentType type, String resType, int id, int expectedCode) {
        // Get the Resource Path
        String path = getResourcePath(type, resType);

        // For JSON Format
        if (type == ContentType.JSON) {
            when().
                    get(path + id).                                         // GET the data
            then().
                    statusCode(expectedCode).                               // Check the Status Code
                    contentType(type);                                      // Check the Content Type of the response
        }
        // For XML Format
        else {
            when().
                    get(path + id).                                         // GET the data
            then().
                    statusCode(expectedCode).                               // Check the Status Code
                    contentType(type);                                      // Check the Content Type of the response
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Get an entry from the database by ID and check a specified field
     *
     * @param type - JSON or XML
     * @param resType - The name of the resources
     * @param id - The ID for selection
     * @param fieldName - name of the field to extract and compare
     * @param expectedValue - The expected value of the filed
     */
    public static void getEntryByIdCheckField(ContentType type, String resType, int id, String fieldName, String expectedValue) {
        // Get the Resource Path
        String path = getResourcePath(type, resType);

        // The the Data Field path
        String dataPath = getDataFieldPath(type);

        // For JSON Format
        if (type == ContentType.JSON) {
            when().
                    get(path + id).                                         // GET the data
            then().
                    statusCode(HttpStatus.OK.value()).                      // Check the Status Code
                    contentType(type).                                      // Check the Content Type of the response
                    body(dataPath + "id", equalTo(id)).                     // Check the ID of the received object
                    body(dataPath + fieldName, equalTo(expectedValue));     // Check the name of the received object
        }
        // For XML Format
        else {
            when().
                    get(path + id).                                         // GET the data
            then().
                    statusCode(HttpStatus.OK.value()).                      // Check the Status Code
                    contentType(type).                                      // Check the Content Type of the response
                    body(dataPath + "id", equalTo(Integer.toString(id))).   // Check the ID of the received object
                    body(dataPath + fieldName, equalTo(expectedValue));     // Check the name of the received object
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Get all DVDs with the given movie rating
     *
     * @param type - JSON or XML
     * @param rating - The requested Rating
     * @param expectedCode - The Status code that's expected
     */
    public static void getDvdsByMovieRating(ContentType type, int rating, int expectedCode) {
        // Get the Resource Path
        String path = getResourcePath(type, DVDServiceTest.getResType());

        // The the Data Field path
        String dataPath = getDataFieldPath(type);

        // For JSON Format
        if (type == ContentType.JSON) {
            when().
                    get(path + "rating/" + rating).                         // GET the data
            then().
                    statusCode(expectedCode).                               // Check the Status Code
                    contentType(type).                                      // Check the Content Type of the response
                    body(dataPath + "movie.rating", equalTo(rating));       // Check the Rating of the received object
        }
        // For XML Format
        else {
            when().
                    get(path + "rating/" + rating).                                         // GET the data
            then().
                    statusCode(expectedCode).                                               // Check the Status Code
                    contentType(type).                                                      // Check the Content Type of the response
                    body(dataPath + "movie.rating", equalTo(Integer.toString(rating)));     // Check the Rating of the received object
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Get all DVDs with the given movie rating sorted by ISBN
     *
     * @param type - JSON or XML
     * @param rating - The requested Rating
     * @param expectedCode - The Status code that's expected
     */
    public static void getDvdsByMovieRatingWithSortByIsbn(ContentType type, int rating, int expectedCode) {
        // Get the Resource Path
        String path = getResourcePath(type, DVDServiceTest.getResType());

        // The the Data Field path
        String dataPath = getDataFieldPath(type);

        // Holder list for all the ISBNs
        List<String> allIsbn;

        // For JSON Format
        if (type == ContentType.JSON) {
            JsonPath json =
                    given().
                            //pathParam(rating)
                            queryParam("sort", "isbn").                             // Set the sorting by ISBN
                    when().
                            get(path + "rating/" + rating).                         // GET the data
                    then().
                            statusCode(expectedCode).                               // Check the Status Code
                            contentType(type).                                      // Check the Content Type of the response
                            body(dataPath + "movie.rating", equalTo(rating)).       // Check the Rating of the received object
                    extract().
                            jsonPath();                                             // Extract JSON Path

            // Convert the JSON Path to a List<String>
            allIsbn = json.get("data.isbn");
        }
        // For XML Format
        else {
            XmlPath xml =
                    given().
                            //pathParam(rating)
                            queryParam("sort", "isbn").                                             // Set the sorting by ISBN
                    when().
                            get(path + "rating/" + rating).                                         // GET the data
                    then().
                            statusCode(expectedCode).                                               // Check the Status Code
                            contentType(type).                                                      // Check the Content Type of the response
                            body(dataPath + "movie.rating", equalTo(Integer.toString(rating))).     // Check the Rating of the received object
                    extract().
                            xmlPath();                                                              // Extract XML Path

            // Convert the XML Path to a List<String>
            allIsbn = xml.getList("response.data.entry.isbn");
        }

        // Check if the list is sorted
        if(!isSorted(allIsbn)) {
            fail("List was not sorted");
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Get all DVDs with the given movie rating
     * Assumes at least 3 entries would be returned without any pagination
     * Paged result: second entry
	 
     * @param type - JSON or XML
     * @param rating - The requested Rating
     * @param expectedCode - The Status code that's expected
     */
    public static void getDvdsByMovieRatingWithPaging(ContentType type, int rating, int expectedCode) {
        // Get the Resource Path
        String path = getResourcePath(type, DVDServiceTest.getResType());

        // The the Data Field path
        String dataPath = getDataFieldPath(type);

        // Holder list for all the ISBNs
        List<String> allIsbn;

        // For JSON Format
        if (type == ContentType.JSON) {
            JsonPath json =
                    given().
                            queryParam("offset", "1").                              // From Second Element
                            queryParam("limit", "1").                               // Two element after the start
                    when().
                            get(path + "rating/" + rating).                         // GET the data
                    then().
                            statusCode(expectedCode).                               // Check the Status Code
                            contentType(type).                                      // Check the Content Type of the response
                            body(dataPath + "movie.rating", equalTo(rating)).       // Check the Rating of the received object
                    extract().
                            jsonPath();                                             // Extract JSON Path

            // Convert the JSON Path to a List<String>
            allIsbn = json.get("data.isbn");
        }
        // For XML Format
        else {
            XmlPath xml =
                    given().
                            queryParam("offset", "1").                                              // From Second Element
                            queryParam("limit", "1").                                               // Two element after the start
                    when().
                            get(path + "rating/" + rating).                                         // GET the data
                    then().
                            statusCode(expectedCode).                                               // Check the Status Code
                            contentType(type).                                                      // Check the Content Type of the response
                            body(dataPath + "movie.rating", equalTo(Integer.toString(rating))).     // Check the Rating of the received object
                    extract().
                            xmlPath();                                                              // Extract XML Path

            // Convert the XML Path to a List<String>
            allIsbn = xml.getList("response.data.entry.isbn");
        }

        // Check if the list is of size 1
        if(allIsbn.size() != 1) {
            fail("Incorrect paging");
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Get all DVDs with the given language and audio
     *
     * @param type - JSON or XML
     * @param language - Requested Language
     * @param audio - Requested Audio
     * @param expectedCode - The Status code that's expected
     */
    public static void getDvdsByLanguageAudio(ContentType type, String language, String audio, int expectedCode) {
        // Get the Resource Path
        String path = getResourcePath(type, DVDServiceTest.getResType());

        // The the Data Field path
        String dataPath = getDataFieldPath(type);

        // For JSON Format
        if (type == ContentType.JSON) {
            when().
                    get(path + "language/" + language + "/audio/" + audio).                     // GET the data
            then().
                    statusCode(expectedCode).                                                   // Check the Status Code
                    contentType(type).                                                          // Check the Content Type of the response
                    body(dataPath + "languageAudioPairs[0].language.name", equalTo(language)).  // Check the Language of the received object
                    body(dataPath + "languageAudioPairs[0].audio.name", equalTo(audio));        // Check the Audio of the received object
        }
        // For XML Format
        else {
            when().
                    get(path + "language/" + language + "/audio/" + audio).                                     // GET the data
            then().
                    statusCode(expectedCode).                                                                   // Check the Status Code
                    contentType(type).                                                                          // Check the Content Type of the response
                    body(dataPath + "languageAudioPairs.languageAudioPair.language.name", equalTo(language)).   // Check the Language of the received object
                    body(dataPath + "languageAudioPairs.languageAudioPair.audio.name", equalTo(audio));         // Check the Audio of the received object
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Get all DVDs with the given language and audio, sorted by ISBN
     *
     * @param type - JSON or XML
     * @param language - Requested Language
     * @param audio - Requested Audio
     * @param expectedCode - The Status code that's expected
     */
    public static void getDvdsByLanguageAudioWithSortByIsbn(ContentType type, String language, String audio, int expectedCode) {
        // Get the Resource Path
        String path = getResourcePath(type, DVDServiceTest.getResType());

        // The the Data Field path
        String dataPath = getDataFieldPath(type);

        // Holder list for all the ISBNs
        List<String> allIsbn;

        // For JSON Format
        if (type == ContentType.JSON) {
            JsonPath json =
                    when().
                            get(path + "language/" + language + "/audio/" + audio).                     // GET the data
                    then().
                            statusCode(expectedCode).                                                   // Check the Status Code
                            contentType(type).                                                          // Check the Content Type of the response
                            body(dataPath + "languageAudioPairs[0].language.name", equalTo(language)).  // Check the Language of the received object
                            body(dataPath + "languageAudioPairs[0].audio.name", equalTo(audio)).        // Check the Audio of the received object
                    extract().
                            jsonPath();                                                                 // Extract JSON Path

            // Convert the XML Path to a List<String>
            allIsbn = json.getList("data.isbn");
        }
        // For XML Format
        else {
            XmlPath xml =
                    when().
                            get(path + "language/" + language + "/audio/" + audio).                                     // GET the data
                    then().
                            statusCode(expectedCode).                                                                   // Check the Status Code
                            contentType(type).                                                                          // Check the Content Type of the response
                            body(dataPath + "languageAudioPairs.languageAudioPair.language.name", equalTo(language)).   // Check the Language of the received object
                            body(dataPath + "languageAudioPairs.languageAudioPair.audio.name", equalTo(audio)).         // Check the Audio of the received object
                    extract().
                            xmlPath();                                                                                  // Extract XML Path

            // Convert the XML Path to a List<String>
            allIsbn = xml.getList("response.data.entry.isbn");
        }

        // Check if the list is sorted
        if(!isSorted(allIsbn)) {
            fail("List was not sorted");
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Get all DVDs with the given language and audio.
     * Assumes at least 3 entries would be returned without any pagination
     * Paged result: second entry
     *
     * @param type - JSON or XML
     * @param language - Requested Language
     * @param audio - Requested Audio
     * @param expectedCode - The Status code that's expected
     */
    public static void getDvdsByLanguageAudioWithPaging(ContentType type, String language, String audio, int expectedCode) {
        // Get the Resource Path
        String path = getResourcePath(type, DVDServiceTest.getResType());

        // The the Data Field path
        String dataPath = getDataFieldPath(type);

        // Holder list for all the ISBNs
        List<String> allIsbn;

        // For JSON Format
        if (type == ContentType.JSON) {
            JsonPath json =
                    given().
                            queryParam("offset", "1").                                                  // From Second Element
                            queryParam("limit", "1").                                                   // Two element after the start
                    when().
                            get(path + "language/" + language + "/audio/" + audio).                     // GET the data
                    then().
                            statusCode(expectedCode).                                                   // Check the Status Code
                            contentType(type).                                                          // Check the Content Type of the response
                            body(dataPath + "languageAudioPairs[0].language.name", equalTo(language)).  // Check the Language of the received object
                            body(dataPath + "languageAudioPairs[0].audio.name", equalTo(audio)).        // Check the Audio of the received object
                    extract().
                            jsonPath();                                                                 // Extract JSON Path

            // Convert the XML Path to a List<String>
            allIsbn = json.getList("data.isbn");
        }
        // For XML Format
        else {
            XmlPath xml =
                    given().
                            queryParam("offset", "1").                                                                  // From Second Element
                            queryParam("limit", "1").                                                                   // Two element after the start
                    when().
                            get(path + "language/" + language + "/audio/" + audio).                                     // GET the data
                    then().
                            statusCode(expectedCode).                                                                   // Check the Status Code
                            contentType(type).                                                                          // Check the Content Type of the response
                            body(dataPath + "languageAudioPairs.languageAudioPair.language.name", equalTo(language)).   // Check the Language of the received object
                            body(dataPath + "languageAudioPairs.languageAudioPair.audio.name", equalTo(audio)).         // Check the Audio of the received object
                    extract().
                            xmlPath();                                                                                  // Extract XML Path

            // Convert the XML Path to a List<String>
            allIsbn = xml.getList("response.data.entry.isbn");
        }

        // Check if the list is of size 1
        if(allIsbn.size() != 1) {
            fail("Incorrect paging");
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Delete an entry by its ID - JSON or XML
     *
     * @param type - JSON or XML
     * @param id - The ID for deletion
     * @param expectedCode - The Status code that's expected
     */
    public static void deleteEntryById(ContentType type, String resType, int id, int expectedCode) {
        // Get the Resource Path
        String path = getResourcePath(type, resType);

        // Remove the entry
        when().
                delete(path + id).                                      // GET the data
        then().
                statusCode(expectedCode).                               // Check the Status Code
                contentType(type);                                      // Check the Content Type of the response
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Delete all DVDs with the given movie rating
     *
     * @param type - JSON or XML
     * @param rating - The requested Rating
     * @param expectedCode - The Status code that's expected
     */
    public static void deleteDvdsByMovieRating(ContentType type, int rating, int expectedCode) {
        // Get the Resource Path
        String path = getResourcePath(type, DVDServiceTest.getResType());

        when().
                delete(path + "rating/" + rating).                      // DELETE the data
        then().
                statusCode(expectedCode).                               // Check the Status Code
                contentType(type);                                      // Check the Content Type of the response
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Delete all DVDs with the given language and audio
     *
     * @param type - JSON or XML
     * @param language - Requested Language
     * @param audio - Requested Audio
     * @param expectedCode - The Status code that's expected
     */
    public static void deleteDvdsByLanguageAudio(ContentType type, String language, String audio, int expectedCode) {
        // Get the Resource Path
        String path = getResourcePath(type, DVDServiceTest.getResType());

        when().
                delete(path + "language/" + language + "/audio/" + audio).                  // DELETE the data
        then().
                statusCode(expectedCode).                                                   // Check the Status Code
                contentType(type);                                                          // Check the Content Type of the response

    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Check a given list of String if it's sorted or not
     *
     * @param list - List of Strings to be checked if it's sorted
     * @return - true if sorted; false otherwise
     */
    public static boolean isSorted(List<String> list) {
        boolean sorted = true;
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i-1).compareTo(list.get(i)) > 0) {
                sorted = false;
            }
        }

        return sorted;
    }

    //-----------------------------------------------------------------------------------------------------------------
	// Getters
    public static int getTempRatingId() {
        return tempRatingId;
    }

    public static int getTempMovieId() {
        return tempMovieId;
    }
}