package com.wolffangx.dvdlibrary.dao.delete;

import com.wolffangx.dvdlibrary.dao.util.SQLOperations;
import com.wolffangx.dvdlibrary.dao.util.mappers.MsgRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Handle SQL operation for deleting entries
 *
 * Note: Since the database constraints are set to CASCADE - if you delete an entry, all other entries that point to it will be deleted as well
 * e.g.: Deleting a Rating will delete all movies with it and then will delete all DVDs of those Movies.
 *
 * @author borislav.draganov
 */

@Component
public class SQLDeleteOperations extends SQLOperations {
    // Available procedures
    private static final String DELETE_MOVIE_BY_ID = "usp_delete_movie_by_id";

    private static final String DELETE_DVD_BY_ID = "usp_delete_dvd_by_id";
    private static final String DELETE_DVD_BY_MOVIE_RATING = "usp_delete_dvd_by_movie_rating";
    private static final String DELETE_DVD_BY_LAP = "usp_delete_dvd_by_language_audio";

    private static final String DELETE_GENRE_BY_ID = "usp_delete_genre_by_id";

    private static final String DELETE_RATING_BY_ID = "usp_delete_rating_by_id";

    private static final String DELETE_LANGUAGE_BY_ID = "usp_delete_language_by_id";

    private static final String DELETE_AUDIO_BY_ID = "usp_delete_audio_by_id";

    //-------------------------------------------------Movie------------------------------------------------------------
    /**
     * Delete a Movie by ID
     *
     * @param id - The ID for deletion
     * @return - Status message
     */
    public String deleteMovieById(int id) {
        // Make a Spring JDBC Simple Call
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(dataSource);
        simpleJdbcCall.withProcedureName(DELETE_MOVIE_BY_ID);
        simpleJdbcCall.returningResultSet("msg", new MsgRowMapper());

        // Set the IN parameters
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("inID", id);

        // Execute Stored Procedure
        Map<String, Object> out = simpleJdbcCall.execute(in);

        // Get e reference to the actual object - a String because of the MsgRowMapper
        Object response = out.get("msg");

        // Return
        if (response instanceof List) {
            return ((List) response).get(0).toString();
        }
        // Invalid Response from the Database Server received
        else {
            throw new RuntimeException();
        }
    }

    //---------------------------------------------------DVD------------------------------------------------------------
    /**
     * Delete a DVD by ID
     *
     * @param id - The ID for deletion
     * @return - Status message
     */
    public String deleteDVDById(int id) {
        // Make a Spring JDBC Simple Call
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(dataSource);
        simpleJdbcCall.withProcedureName(DELETE_DVD_BY_ID);
        simpleJdbcCall.returningResultSet("msg", new MsgRowMapper());

        // Set the IN parameters
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("inID", id);

        // Execute Stored Procedure
        Map<String, Object> out = simpleJdbcCall.execute(in);

        // Get e reference to the actual object - a String because of the MsgRowMapper
        Object response = out.get("msg");

        // Return
        if (response instanceof List) {
            return ((List) response).get(0).toString();
        }
        // Invalid Response from the Database Server received
        else {
            throw new RuntimeException();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Delete DVDs by their movie rating
     *
     * @param rating - The Movie's rating by which to delete
     * @return - Status message
     */
    public String deleteDVDByMovieRating(int rating) {
        // Make a Spring JDBC Simple Call
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(dataSource);
        simpleJdbcCall.withProcedureName(DELETE_DVD_BY_MOVIE_RATING);
        simpleJdbcCall.returningResultSet("msg", new MsgRowMapper());

        // Set the IN parameters
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("inRating", rating);

        // Execute Stored Procedure
        Map<String, Object> out = simpleJdbcCall.execute(in);

        // Get e reference to the actual object - a String because of the MsgRowMapper
        Object response = out.get("msg");

        // Return
        if (response instanceof List) {
            return ((List) response).get(0).toString();
        }
        // Invalid Response from the Database Server received
        else {
            throw new RuntimeException();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    /**
     * Delete DVDs by their Language and Audio Pair
     *
     * @param language - The Language by which to delete
     * @param audio - The Audio by which to delete
     * @return - Status message
     */
    public String deleteDVDByLAP(String language, String audio) {
        // Make a Spring JDBC Simple Call
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(dataSource);
        simpleJdbcCall.withProcedureName(DELETE_DVD_BY_LAP);
        simpleJdbcCall.returningResultSet("msg", new MsgRowMapper());

        // Set the IN parameters
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("inLanguage", language)
                .addValue("inAudio", audio);

        // Execute Stored Procedure
        Map<String, Object> out = simpleJdbcCall.execute(in);

        // Get e reference to the actual object - a String because of the MsgRowMapper
        Object response = out.get("msg");

        // Return
        if (response instanceof List) {
            return ((List) response).get(0).toString();
        }
        // Invalid Response from the Database Server received
        else {
            throw new RuntimeException();
        }
    }

    //---------------------------------------------Genre----------------------------------------------------------------
    /**
     * Delete a Genre by ID
     *
     * @param id - The ID for deletion
     * @return - Status message
     */
    public String deleteGenreById(int id) {
        // Make a Spring JDBC Simple Call
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(dataSource);
        simpleJdbcCall.withProcedureName(DELETE_GENRE_BY_ID);
        simpleJdbcCall.returningResultSet("msg", new MsgRowMapper());

        // Set the IN parameters
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("inID", id);

        // Execute Stored Procedure
        Map<String, Object> out = simpleJdbcCall.execute(in);

        // Get e reference to the actual object - a String because of the MsgRowMapper
        Object response = out.get("msg");

        // Return
        if (response instanceof List) {
            return ((List) response).get(0).toString();
        }
        // Invalid Response from the Database Server received
        else {
            throw new RuntimeException();
        }
    }

    //---------------------------------------------Rating---------------------------------------------------------------
    /**
     * Delete a Rating by ID
     *
     * @param id - The ID for deletion
     * @return - Status message
     */
    public String deleteRatingById(int id) {
        // Make a Spring JDBC Simple Call
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(dataSource);
        simpleJdbcCall.withProcedureName(DELETE_RATING_BY_ID);
        simpleJdbcCall.returningResultSet("msg", new MsgRowMapper());

        // Set the IN parameters
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("inID", id);

        // Execute Stored Procedure
        Map<String, Object> out = simpleJdbcCall.execute(in);

        // Get e reference to the actual object - a String because of the MsgRowMapper
        Object response = out.get("msg");

        // Return
        if (response instanceof List) {
            return ((List) response).get(0).toString();
        }
        // Invalid Response from the Database Server received
        else {
            throw new RuntimeException();
        }
    }

    //---------------------------------------------Language-------------------------------------------------------------
    /**
     * Delete a Language by ID
     *
     * @param id - The ID for deletion
     * @return - Status message
     */
    public String deleteLanguageById(int id) {
        // Make a Spring JDBC Simple Call
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(dataSource);
        simpleJdbcCall.withProcedureName(DELETE_LANGUAGE_BY_ID);
        simpleJdbcCall.returningResultSet("msg", new MsgRowMapper());

        // Set the IN parameters
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("inID", id);

        // Execute Stored Procedure
        Map<String, Object> out = simpleJdbcCall.execute(in);

        // Get e reference to the actual object - a String because of the MsgRowMapper
        Object response = out.get("msg");

        // Return
        if (response instanceof List) {
            return ((List) response).get(0).toString();
        }
        // Invalid Response from the Database Server received
        else {
            throw new RuntimeException();
        }
    }

    //--------------------------------------------Audio-----------------------------------------------------------------
    /**
     * Delete an Audio by ID
     *
     * @param id - The ID for deletion
     * @return - Status message
     */
    public String deleteAudioById(int id) {
        // Make a Spring JDBC Simple Call
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(dataSource);
        simpleJdbcCall.withProcedureName(DELETE_AUDIO_BY_ID);
        simpleJdbcCall.returningResultSet("msg", new MsgRowMapper());

        // Set the IN parameters
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("inID", id);

        // Execute Stored Procedure
        Map<String, Object> out = simpleJdbcCall.execute(in);

        // Get e reference to the actual object - a String because of the MsgRowMapper
        Object response = out.get("msg");

        // Return
        if (response instanceof List) {
            return ((List) response).get(0).toString();
        }
        // Invalid Response from the Database Server received
        else {
            throw new RuntimeException();
        }
    }
}