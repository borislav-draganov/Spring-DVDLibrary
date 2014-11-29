package com.wolffangx.dvdlibrary.dao.util;

import com.wolffangx.dvdlibrary.dao.util.mappers.IdRowMapper;
import com.wolffangx.dvdlibrary.entities.Genre;
import com.wolffangx.dvdlibrary.entities.util.LanguageAudioPair;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.LinkedList;
import java.util.List;

/**
 * Handle basic Utility-related SQL operations like populating n:m relations and parsing received data
 *
 * @author borislav.draganov
 */

public abstract class SQLUtil {
    // If the database returns -1, it means an error occurred - probably a missing ID
    public static final int SQL_ERROR = -1;

    public static final String VIEW_CALL ="SELECT * FROM ";

    // The available statements
    private static final String GET_GENRE_ID_STATEMENT = "SELECT idGenre FROM Genre WHERE Genre = ?";

    private static final String ADD_GENRE_TO_MOVIE_STATEMENT = "INSERT INTO Movie_has_Genre(Movie_idMovie, Genre_idGenre) VALUES(?, ?)";

    private static final String DELETE_GENRE_FOR_MOVIE_ID_STATEMENT = "DELETE FROM Movie_has_Genre WHERE Movie_idMovie = ?";

    private static final String GET_LANGUAGE_ID_STATEMENT = "SELECT idLanguage FROM Language WHERE Language = ?";

    private static final String GET_AUDIO_ID_STATEMENT = "SELECT idAudio FROM Audio WHERE Audio = ?";

    private static final String ADD_LANG_AUDIO_TO_DVD_STATEMENT = "INSERT INTO dvd_has_language_audio(DVD_idDVD, Language_idLanguage, Audio_idAudio) VALUES(?, ?, ?)";

    private static final String DELETE_LAPS_FOR_DV_DID_STATEMENT = "DELETE FROM DVD_has_Language_Audio WHERE DVD_idDVD = ?";

    /**
     * Insert all needed genres to a movie
     * Modifies the bridge table (n:m relation)
     *
     * @param dataSource - The data source for the database
     * @param genres - A list of Genre objects that need to be added to the movie ID
     * @param id - The ID of the movie
     */
    public static void populateGenresForMovieId(DataSource dataSource, List<Genre> genres, int id) {
        // Make a Spring JDBC Template
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        // Set the parameter list
        List<Object[]> parameters = new LinkedList<Object[]>();

        for (Genre g : genres) {
            int returnedId = jdbcTemplate.queryForObject(GET_GENRE_ID_STATEMENT, new Object[]{g.getName()}, new IdRowMapper());

            // Add the IDs to the list of parameters for the batch update
            parameters.add(new Object[]{
                    id,         // ID of the Movie
                    returnedId  // ID of the Genre
            });
        }

        // Execute batch update
        jdbcTemplate.batchUpdate(ADD_GENRE_TO_MOVIE_STATEMENT, parameters);
    }
    //------------------------------------------------------------------------------------------------------------------
    /**
     * Delete all genres of a movie
     * Modifies the bridge table (n:m relation)
     *
     * @param dataSource - The data source for the database
     * @param id - The ID of the movie
     */
    public static void deleteGenresForMovieId(DataSource dataSource, int id) {
        // Make a Spring JDBC Template
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        // Set the parameter list
        List<Object[]> parameters = new LinkedList<Object[]>();

        // Add the IDs to the list of parameters for the batch update
        parameters.add(new Object[]{
                id,             // ID of the Movie
        } );

        // Execute batch update
        jdbcTemplate.batchUpdate(DELETE_GENRE_FOR_MOVIE_ID_STATEMENT, parameters);
    }

    //------------------------------------------------------------------------------------------------------------------
    /**
     * Add all Languages and Audio pairs to a DVD
     * Modifies the bridge table (n:m relation)
     *
     * @param dataSource - The data source for the database
     * @param languageAudioPairs - A list of the Language & Audio Pairs to be inserted
     * @param id - The ID of the DVD
     */
    public static void populateLanguageAudioForDVDId(DataSource dataSource, List<LanguageAudioPair> languageAudioPairs, int id) {
        // Make a Spring JDBC Template
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        // Set the parameter list
        List<Object[]> parameters = new LinkedList<Object[]>();

        for (LanguageAudioPair lap : languageAudioPairs) {
            // Get the ID of the current language
            int languageId = jdbcTemplate.queryForObject(GET_LANGUAGE_ID_STATEMENT, new Object[] { lap.getLanguage().getName() }, new IdRowMapper());

            // Get the ID of the current language
            int audioId = jdbcTemplate.queryForObject(GET_AUDIO_ID_STATEMENT, new Object[] { lap.getAudio().getName() }, new IdRowMapper());

            // Add the IDs to the list of parameters for the batch update
            parameters.add(new Object[]{
                    id,             // ID of the DVD
                    languageId,     // ID of the Language
                    audioId         // ID of the Audio
            } );
        }

        // Execute batch update
        jdbcTemplate.batchUpdate(ADD_LANG_AUDIO_TO_DVD_STATEMENT, parameters);
    }

    //------------------------------------------------------------------------------------------------------------------
    /**
     * Delete all Languages and Audio pairs of a DVD
     * Modifies the bridge table (n:m relation)
     *
     * @param dataSource - The data source for the database
     * @param id - The ID of the DVD
     */
    public static void deleteLAPsForDVDid(DataSource dataSource, int id) {
        // Make a Spring JDBC Template
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        // Set the parameter list
        List<Object[]> parameters = new LinkedList<Object[]>();

        // Add the IDs to the list of parameters for the batch update
        parameters.add(new Object[]{
                id,             // ID of the DVD
        } );

        // Execute batch update
        jdbcTemplate.batchUpdate(DELETE_LAPS_FOR_DV_DID_STATEMENT, parameters);
    }
}