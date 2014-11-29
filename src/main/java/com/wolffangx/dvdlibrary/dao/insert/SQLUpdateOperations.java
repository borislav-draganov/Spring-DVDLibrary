package com.wolffangx.dvdlibrary.dao.insert;

import com.wolffangx.dvdlibrary.dao.report.SQLReportOperations;
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
 * Handle all SQL operations for updating entries
 *
 * @author borislav.draganov
 */

@Component
public class SQLUpdateOperations extends SQLOperations {
    // The available procedures
    // Movies and DVDs have n:m relationships so, they'll be handled with SQL Statements rather than Stored Procedures
    private static final String UPDATE_MOVIE_PROCEDURE = "CALL usp_update_movie(?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_DVD_PROCEDURE = "CALL usp_update_dvd(?, ?, ?, ?, ?, ?)";

    // These will be handled by Stored Procedures
    private static final String UPDATE_GENRE_PROCEDURE = "usp_update_genre";
    private static final String UPDATE_RATING_PROCEDURE = "usp_update_rating";
    private static final String UPDATE_LANGUAGE_PROCEDURE = "usp_update_language";
    private static final String UPDATE_AUDIO_PROCEDURE = "usp_update_audio";

    //----------------------------------------------------Movie---------------------------------------------------------
    /**
     * Update a Movie object
     *
     * @param movie - The data that will be inserted into the database (including ID)
     * @return - Status message
     */
    @Transactional("updateMovie")
    public String updateMovie(final Movie movie) {
        // Get old movie in case of missing data fields
        SQLReportOperations sqlReportOperations = new SQLReportOperations();
        sqlReportOperations.setDataSource(dataSource);
        Movie oldMovie = sqlReportOperations.viewMovieById(movie.getId());

        // Fill in the missing data
        if (movie.getTitle() == null) {
            movie.setTitle(oldMovie.getTitle());
        }
        if (movie.getLength() == 0) {
            movie.setLength(oldMovie.getLength());
        }
        if (movie.getReleaseDate() == null) {
            movie.setReleaseDate(oldMovie.getReleaseDate());
        }
        if (movie.getImdbId() == 0) {
            movie.setImdbId(oldMovie.getImdbId());
        }
        if (movie.getRating() == 0) {
            movie.setRating(oldMovie.getRating());
        }
        if (movie.getGenres() == null) {
            movie.setGenres(oldMovie.getGenres());
        }

        // Make a Spring JDBC Template
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        // Prepare a holder for the key of the newly generated entry
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
                // Make the Prepared Statement
                // Statement.RETURN_GENERATED_KEYS - makes the ID of the newly inserted entry retrievable by stmt.getGeneratedKeys()
                PreparedStatement stmt = connection.prepareStatement(UPDATE_MOVIE_PROCEDURE, Statement.RETURN_GENERATED_KEYS);

                // Set the parameters
                stmt.setInt(1, movie.getId());                  // ID
                stmt.setString(2, movie.getTitle());            // Title
                stmt.setInt(3, movie.getLength());              // Length
                stmt.setString(4, movie.getReleaseDate());      // Release Date
                stmt.setInt(5, movie.getImdbId());              // IMDB ID
                stmt.setInt(6, movie.getRating());              // Rating

                return stmt;
            }, keyHolder);

        // Update genres
        // Delete all old genres of the movie in the bridge table (n:m relation)
        SQLUtil.deleteGenresForMovieId(dataSource, movie.getId());

        // Populate the movie with its genres in the bridge table (n:m relation)
        SQLUtil.populateGenresForMovieId(dataSource, movie.getGenres(), movie.getId());

        // Return the ID of the new entry
        return "Success";
    }

    //------------------------------------------------------DVD---------------------------------------------------------
    /**
     * Update a DVD object
     *
     * @param dvd - The data that will be inserted into the database (including ID)
     * @return - Status message
     */
    @Transactional("updateDVD")
    public String updateDVD(final DVD dvd) {
        // Get old DVD in case of missing data fields
        SQLReportOperations sqlReportOperations = new SQLReportOperations();
        sqlReportOperations.setDataSource(dataSource);
        DVD oldDVD = sqlReportOperations.viewDVDById(dvd.getId());

        // Fill in the missing data
        if (dvd.getMovie() == null) {
            dvd.setMovie(oldDVD.getMovie());
        } else {
            // Check if the ID was set, since that is all that matters for the DVD Update
            if (dvd.getMovie().getId() == 0) {
                dvd.setMovie(oldDVD.getMovie());
            }
        }
        if (dvd.getIsbn() == null) {
            dvd.setIsbn(oldDVD.getIsbn());
        }
        if (dvd.getEdition() == null) {
            dvd.setEdition(oldDVD.getEdition());
        }
        if (dvd.getScreenFormat() == null) {
            dvd.setScreenFormat(oldDVD.getScreenFormat());
        }
        if (dvd.getRegion() == null) {
            dvd.setRegion(oldDVD.getRegion());
        }
        if (dvd.getLanguageAudioPairs() == null) {
            dvd.setLanguageAudioPairs(oldDVD.getLanguageAudioPairs());
        }

        // Make a Spring JDBC Template
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        // Prepare a holder for the key of the newly generated entry
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
                // Make the Prepared Statement
                // Statement.RETURN_GENERATED_KEYS - makes the ID of the newly inserted entry retrievable by stmt.getGeneratedKeys()
                PreparedStatement stmt = connection.prepareStatement(UPDATE_DVD_PROCEDURE, Statement.RETURN_GENERATED_KEYS);

                // Set the parameters
                stmt.setInt(1, dvd.getId());                  // ID
                stmt.setInt(2, dvd.getMovie().getId());       // Movie ID
                stmt.setString(3, dvd.getIsbn());             // ISBN
                stmt.setString(4, dvd.getEdition());          // Edition
                stmt.setString(5, dvd.getScreenFormat());     // Screen Format
                stmt.setString(6, dvd.getRegion());           // Region

                return stmt;
            }, keyHolder);

        // Update Language Audio Pairs
        // Delete all old languages and audios of the DVD in the bridge table (n:m relation)
        SQLUtil.deleteLAPsForDVDid(dataSource, dvd.getId());

        // Populate the DVD with its new languages and audios in the bridge table (n:m relation)
        SQLUtil.populateLanguageAudioForDVDId(dataSource, dvd.getLanguageAudioPairs(), dvd.getId());

        // Return the ID of the new entry
        return "Success";
    }

    //------------------------------------------------------Genre-------------------------------------------------------
    /**
     * Update a Genre object
     *
     * @param genre - The data that will be inserted into the database (including ID)
     * @return - Status message
     */
    public String updateGenre(Genre genre) {
        // Make a Spring JDBC Simple Call
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(dataSource);
        simpleJdbcCall.withProcedureName(UPDATE_GENRE_PROCEDURE);
        simpleJdbcCall.returningResultSet("msg", new MsgRowMapper());

        // Set the IN parameters
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("inID", genre.getId())
                .addValue("inGenre", genre.getName());

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

    //-----------------------------------------------------Rating-------------------------------------------------------
    /**
     * Update a Rating object
     *
     * @param rating - The data that will be inserted into the database (including ID)
     * @return - Status message
     */
    public String updateRating(Rating rating) {
        // Make a Spring JDBC Simple Call
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(dataSource);
        simpleJdbcCall.withProcedureName(UPDATE_RATING_PROCEDURE);
        simpleJdbcCall.returningResultSet("msg", new MsgRowMapper());

        // Set the IN parameters
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("inID", rating.getId())
                .addValue("inRating", rating.getName());

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

    //--------------------------------------------------Language--------------------------------------------------------
    /**
     * Update a Language object
     *
     * @param language - The data that will be inserted into the database (including ID)
     * @return - Status message
     */
    public String updateLanguage(Language language) {
        // Make a Spring JDBC Simple Call
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(dataSource);
        simpleJdbcCall.withProcedureName(UPDATE_LANGUAGE_PROCEDURE);
        simpleJdbcCall.returningResultSet("msg", new MsgRowMapper());

        // Set the IN parameters
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("inID", language.getId())
                .addValue("inLanguage", language.getName());

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

    //--------------------------------------------------Audio-----------------------------------------------------------
    /**
     * Update an Audio object
     *
     * @param audio - The data that will be inserted into the database (including ID)
     * @return - Status message
     */
    public String updateAudio(Audio audio) {
        // Make a Spring JDBC Simple Call
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(dataSource);
        simpleJdbcCall.withProcedureName(UPDATE_AUDIO_PROCEDURE);
        simpleJdbcCall.returningResultSet("msg", new MsgRowMapper());

        // Set the IN parameters
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("inID", audio.getId())
                .addValue("inAudio", audio.getName());

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