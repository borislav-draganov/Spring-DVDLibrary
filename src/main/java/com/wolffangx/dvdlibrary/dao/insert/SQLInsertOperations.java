package com.wolffangx.dvdlibrary.dao.insert;

import com.wolffangx.dvdlibrary.dao.util.SQLOperations;
import com.wolffangx.dvdlibrary.dao.util.SQLUtil;
import com.wolffangx.dvdlibrary.dao.util.mappers.MsgRowMapper;
import com.wolffangx.dvdlibrary.entities.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * Handle SQL operations for inserting new data into the database
 *
 * @author borislav.draganov
 */

@Component
public class SQLInsertOperations extends SQLOperations {
    // The available statements and procedures

    // Movies and DVDs have n:m relationships so, they'll be handled with SQL Statements rather than Stored Procedures
    private static final String ADD_MOVIE_STATEMENT = "INSERT INTO Movie(title, length, releaseDate, IMDB, rating) VALUES(?, ?, ?, ?, ?)";

    private static final String ADD_DVD_STATEMENT = "INSERT INTO DVD(movieId, ISBN, edition, screenFormat, region) VALUES(?, ?, ?, ?, ?)";

    // These will be handled by Stored Procedures
    private static final String ADD_GENRE_PROCEDURE = "usp_add_genre";

    private static final String ADD_RATING_PROCEDURE = "usp_add_rating";

    private static final String ADD_LANGUAGE_PROCEDURE = "usp_add_language";

    private static final String ADD_AUDIO_PROCEDURE = "usp_add_audio";

    //----------------------------------------------------Movie---------------------------------------------------------
    /**
     * Insert a Movie object into the database
     *
     * @param movie - The data that will be inserted into the database (including ID)
     * @return - ID of the new Object in the database or Error message
     */
    @Transactional("insertNewMovie")
    public String insertNewMovie(final Movie movie) {
        // Make a Spring JDBC Template
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        // Prepare a holder for the key of the newly generated entry
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            // Make the Prepared Statement
            // Statement.RETURN_GENERATED_KEYS - makes the ID of the newly inserted entry retrievable by stmt.getGeneratedKeys()
            PreparedStatement stmt = connection.prepareStatement(ADD_MOVIE_STATEMENT, Statement.RETURN_GENERATED_KEYS);

            // Set the parameters
            stmt.setString(1, movie.getTitle());            // Title
            stmt.setInt(2, movie.getLength());              // Length
            stmt.setString(3, movie.getReleaseDate());      // Release Date
            stmt.setInt(4, movie.getImdbId());              // IMDB ID
            stmt.setInt(5, movie.getRating());              // Rating

            return stmt;
        }, keyHolder);

        // Get the key
        int key = keyHolder.getKey().intValue();

        // Populate the movie with its genres in the bridge table (n:m relation)
        SQLUtil.populateGenresForMovieId(dataSource, movie.getGenres(), key);

        // Return the ID of the new entry
        return Integer.toString(key);
    }
    //------------------------------------------------------DVD---------------------------------------------------------
    /**
     * Insert a DVD object into the database
     *
     * @param dvd - The data that will be inserted into the database (including ID)
     * @return - ID of the new Object in the database or Error message
     */
    @Transactional("insertNewDVD")
    public String insertNewDVD(final DVD dvd) {
        // Make a Spring JDBC Template
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        // Prepare a holder for the key of the newly generated entry
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
                // Make the Prepared Statement
                // Statement.RETURN_GENERATED_KEYS - makes the ID of the newly inserted entry retrievable by stmt.getGeneratedKeys()
                PreparedStatement stmt = connection.prepareStatement(ADD_DVD_STATEMENT, Statement.RETURN_GENERATED_KEYS);

                // Set the parameters
                stmt.setInt(1, dvd.getMovie().getId());     // Movie Id
                stmt.setString(2, dvd.getIsbn());           // ISBN
                stmt.setString(3, dvd.getEdition());        // Edition
                stmt.setString(4, dvd.getScreenFormat());   // Screen Format
                stmt.setString(5, dvd.getRegion());         // Region

                return stmt;
            }, keyHolder);

        // Get the key
        int key = keyHolder.getKey().intValue();

        // Populate the movie with its genres in the bridge table (n:m relation)
        SQLUtil.populateLanguageAudioForDVDId(dataSource, dvd.getLanguageAudioPairs(), key);

        // Return the ID of the new entry
        return Integer.toString(key);
    }

    //------------------------------------------------------Genre-------------------------------------------------------
    /**
     * Insert a Genre object into the database
     *
     * @param genre - The data that will be inserted into the database (including ID)
     * @return - ID of the new Object in the database or Error message
     */
    public String insertNewGenre(Genre genre) {
        // Make a Spring JDBC Simple Call
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(dataSource);
        simpleJdbcCall.withProcedureName(ADD_GENRE_PROCEDURE);
        simpleJdbcCall.returningResultSet("msg", new MsgRowMapper());

        // Set the IN parameters
        SqlParameterSource in = new MapSqlParameterSource().addValue("inGenre", genre.getName());

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

    //------------------------------------------------------Rating------------------------------------------------------
    /**
     * Insert a Rating object into the database
     *
     * @param rating - The data that will be inserted into the database (including ID)
     * @return - ID of the new Object in the database or Error message
     */
    public String insertNewRating(Rating rating) {
        // Make a Spring JDBC Simple Call
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(dataSource);
        simpleJdbcCall.withProcedureName(ADD_RATING_PROCEDURE);
        simpleJdbcCall.returningResultSet("msg", new MsgRowMapper());

        // Set the IN parameters
        SqlParameterSource in = new MapSqlParameterSource().addValue("inRating", rating.getName());

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

    //-------------------------------------------------Language---------------------------------------------------------
    /**
     * Insert a Language object into the database
     *
     * @param language - The data that will be inserted into the database (including ID)
     * @return - ID of the new Object in the database or Error message
     */
    public String insertNewLanguage(Language language) {
        // Make a Spring JDBC Simple Call
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(dataSource);
        simpleJdbcCall.withProcedureName(ADD_LANGUAGE_PROCEDURE);
        simpleJdbcCall.returningResultSet("msg", new MsgRowMapper());

        // Set the IN parameters
        SqlParameterSource in = new MapSqlParameterSource().addValue("inLanguage", language.getName());

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

    //------------------------------------------------------Audio-------------------------------------------------------
    /**
     * Insert an Audio object into the database
     *
     * @param audio - The data that will be inserted into the database (including ID)
     * @return - ID of the new Object in the database or Error message
     */
    public String insertNewAudio(Audio audio) {
        // Make a Spring JDBC Simple Call
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(dataSource);
        simpleJdbcCall.withProcedureName(ADD_AUDIO_PROCEDURE);
        simpleJdbcCall.returningResultSet("msg", new MsgRowMapper());

        // Set the IN parameters
        SqlParameterSource in = new MapSqlParameterSource().addValue("inAudio", audio.getName());

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