package com.wolffangx.dvdlibrary.dao.report;

import com.wolffangx.dvdlibrary.dao.util.SQLUtil;
import com.wolffangx.dvdlibrary.dao.util.SQLOperations;
import com.wolffangx.dvdlibrary.dao.util.mappers.*;
import com.wolffangx.dvdlibrary.entities.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Handle all SQL operation for viewing data
 *
 * @author borislav.draganov
 */

@Component
public class SQLReportOperations extends SQLOperations {
    // The available views and procedures
    private static final String VIEW_ALL_MOVIES = "view_all_movies";
    private static final String VIEW_MOVIE_BY_ID = "usp_view_movie_by_id";

    private static final String VIEW_ALL_DVDS = "view_all_dvds";
    private static final String VIEW_DVD_BY_ID = "usp_view_dvd_by_id";
    private static final String VIEW_DVD_BY_MOVIE_RATING = "usp_view_dvd_by_movie_rating";
    private static final String VIEW_DVD_BY_LAP = "usp_view_dvd_by_language_audio";

    private static final String VIEW_ALL_GENRES = "view_all_genres";
    private static final String VIEW_GENRE_BY_ID = "usp_view_genre_by_id";

    private static final String VIEW_ALL_RATINGS = "view_all_ratings";
    private static final String VIEW_RATING_BY_ID = "usp_view_rating_by_id";

    private static final String VIEW_ALL_LANGUAGES = "view_all_languages";
    private static final String VIEW_LANGUAGE_BY_ID = "usp_view_language_by_id";

    private static final String VIEW_ALL_AUDIOS = "view_all_audios";
    private static final String VIEW_AUDIO_BY_ID = "usp_view_audio_by_id";

    //-------------------------------------------------Movie------------------------------------------------------------
    /**
     * Get a list of all Movies in the database
     *
     * @return - List of parsed/valid Movie objects from all entries in the database
     */
    public List<Movie> viewAllMovies() {
        // Make a Spring JDBC Template
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        // Query For List and Return
        return jdbcTemplate.query(SQLUtil.VIEW_CALL + VIEW_ALL_MOVIES, new MovieRowMapper());
    }
    //------------------------------------------------------------------------------------------------------------------
    /**
     * Return a specific Movie by its ID
     *
     * @param id - The ID of the movie that's requested
     * @return - A parsed/valid Movie object
     */
    public Movie viewMovieById(int id) {
        // Make a Spring JDBC Simple Call
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(dataSource);
        simpleJdbcCall.withProcedureName(VIEW_MOVIE_BY_ID);
        simpleJdbcCall.returningResultSet("movie", new MovieRowMapper());

        // Set the IN parameters
        SqlParameterSource in = new MapSqlParameterSource().addValue("inID", id);

        // Execute Stored Procedure
        Map<String, Object> out = simpleJdbcCall.execute(in);

        // Return
        return ((List<Movie>) out.get("movie")).get(0);
    }

    //--------------------------------------------------DVD-------------------------------------------------------------
    /**
     * Get a list of all DVDs in the database
     *
     * @return - List of parsed/valid DVD objects from all entries in the database
     */
    public List<DVD> viewAllDVDs(){
        // Make a Spring JDBC Template
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        // Query For List and Return
        return jdbcTemplate.query(SQLUtil.VIEW_CALL + VIEW_ALL_DVDS, new DVDRowMapper());
    }
    //------------------------------------------------------------------------------------------------------------------
    /**
     * Return a specific DVD by its ID
     *
     * @param id - The ID of the DVD that's requested
     * @return - A parsed/valid DVD object
     */
    public DVD viewDVDById(int id) {
        // Make a Spring JDBC Simple Call
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(dataSource);
        simpleJdbcCall.withProcedureName(VIEW_DVD_BY_ID);
        simpleJdbcCall.returningResultSet("dvd", new DVDRowMapper());

        // Set the IN parameters
        SqlParameterSource in = new MapSqlParameterSource().addValue("inID", id);

        // Execute Stored Procedure
        Map<String, Object> out = simpleJdbcCall.execute(in);

        // Return
        return ((List<DVD>) out.get("dvd")).get(0);
    }
    //------------------------------------------------------------------------------------------------------------------
    /**
     * Return a List of DVDs by their movie ratings
     *
     * @param rating - The movie rating that's being requested
     * @return - List of parsed/valid DVD objects from all entries in the database with the given rating
     */
    public List<DVD> viewDVDByMovieRating(int rating) {
        // Make a Spring JDBC Simple Call
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(dataSource);
        simpleJdbcCall.withProcedureName(VIEW_DVD_BY_MOVIE_RATING);
        simpleJdbcCall.returningResultSet("dvd", new DVDRowMapper());

        // Set the IN parameters
        SqlParameterSource in = new MapSqlParameterSource().addValue("inRating", rating);

        // Execute Stored Procedure
        Map<String, Object> out = simpleJdbcCall.execute(in);

        // Return
        return (List<DVD>) out.get("dvd");
    }
    //------------------------------------------------------------------------------------------------------------------
    /**
     * Return a list of all DVDs that have language X and audio Y
     *
     * @param language - The language that's being requested
     * @param audio - The audio that's being requested
     * @return - List of parsed/valid DVD objects from all entries in the database with the given language and audio
     */
    public List<DVD> viewDVDByLAP(String language, String audio) {
        // Make a Spring JDBC Simple Call
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(dataSource);
        simpleJdbcCall.withProcedureName(VIEW_DVD_BY_LAP);
        simpleJdbcCall.returningResultSet("dvd", new DVDRowMapper());

        // Set the IN parameters
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("inLanguage", language)
                .addValue("inAudio", audio);

        // Execute Stored Procedure
        Map<String, Object> out = simpleJdbcCall.execute(in);

        // Return
        return (List<DVD>) out.get("dvd");
    }

    //------------------------------------------------Genre-------------------------------------------------------------
    /**
     * Return all Genres in the database
     *
     * @return - List of parsed/valid Genre objects from all entries in the database
     */
    public List<Genre> viewAllGenres() {
        // Make a Spring JDBC Template
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        // Query For List and Return
        return jdbcTemplate.query(SQLUtil.VIEW_CALL + VIEW_ALL_GENRES, new GenreRowMapper());
    }
    //------------------------------------------------------------------------------------------------------------------
    /**
     * Return a specific Genre by its ID
     *
     * @param id - The ID of the genre that's requested
     * @return - A parsed/valid Genre object
     */
    public Genre viewGenreById(int id) {
        // Make a Spring JDBC Simple Call
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(dataSource);
        simpleJdbcCall.withProcedureName(VIEW_GENRE_BY_ID);
        simpleJdbcCall.returningResultSet("genre", new GenreRowMapper());

        // Set the IN parameters
        SqlParameterSource in = new MapSqlParameterSource().addValue("inID", id);

        // Execute Stored Procedure
        Map<String, Object> out = simpleJdbcCall.execute(in);

        // Return
        return ((List<Genre>) out.get("genre")).get(0);
    }

    //----------------------------------------------------Rating--------------------------------------------------------
    /**
     * Return all Ratings in the database
     *
     * @return - List of parsed/valid Rating objects from all entries in the database
     */
    public List<Rating> viewAllRatings() {
        // Make a Spring JDBC Template
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        // Query For List and Return
        return jdbcTemplate.query(SQLUtil.VIEW_CALL + VIEW_ALL_RATINGS, new RatingRowMapper());
    }
    //------------------------------------------------------------------------------------------------------------------
    /**
     * Return a specific Rating by its ID
     *
     * @param id - The ID of the rating that's requested
     * @return - A parsed/valid Rating object
     */
    public Rating viewRatingById(int id) {
        // Make a Spring JDBC Simple Call
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(dataSource);
        simpleJdbcCall.withProcedureName(VIEW_RATING_BY_ID);
        simpleJdbcCall.returningResultSet("rating", new RatingRowMapper());

        // Set the IN parameters
        SqlParameterSource in = new MapSqlParameterSource().addValue("inID", id);

        // Execute Stored Procedure
        Map<String, Object> out = simpleJdbcCall.execute(in);

        // Return
        return ((List<Rating>) out.get("rating")).get(0);
    }

    //------------------------------------------------------Language----------------------------------------------------
    /**
     * Return all Languages in the database
     *
     * @return - List of parsed/valid Language objects from all entries in the database
     */
    public List<Language> viewAllLanguages() {
        // Make a Spring JDBC Template
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        // Query For List and Return
        return jdbcTemplate.query(SQLUtil.VIEW_CALL + VIEW_ALL_LANGUAGES, new LanguageRowMapper());
    }
    //------------------------------------------------------------------------------------------------------------------
    /**
     * Return a specific Language by its ID
     *
     * @param id - The ID of the language that's requested
     * @return - A parsed/valid Language object
     */
    public Language viewLanguageById(int id) {
        // Make a Spring JDBC Simple Call
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(dataSource);
        simpleJdbcCall.withProcedureName(VIEW_LANGUAGE_BY_ID);
        simpleJdbcCall.returningResultSet("language", new LanguageRowMapper());

        // Set the IN parameters
        SqlParameterSource in = new MapSqlParameterSource().addValue("inID", id);

        // Execute Stored Procedure
        Map<String, Object> out = simpleJdbcCall.execute(in);

        // Return
        return ((List<Language>) out.get("language")).get(0);
    }

    //---------------------------------------------------Audio----------------------------------------------------------
    /**
     * Return all Audios in the database
     *
     * @return - List of parsed/valid Audio objects from all entries in the database
     */
    public List<Audio> viewAllAudios() {
        // Make a Spring JDBC Template
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        // Query For List and Return
        return jdbcTemplate.query(SQLUtil.VIEW_CALL + VIEW_ALL_AUDIOS, new AudioRowMapper());
    }
    //--------------------------------------------------------------------------------------------------------
    /**
     * Return a specific Audio by its ID
     *
     * @param id - The ID of the audio that's requested
     * @return - A parsed/valid Audio object
     */
    public Audio viewAudioById(int id) {
        // Make a Spring JDBC Simple Call
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(dataSource);
        simpleJdbcCall.withProcedureName(VIEW_AUDIO_BY_ID);
        simpleJdbcCall.returningResultSet("audio", new AudioRowMapper());

        // Set the IN parameters
        SqlParameterSource in = new MapSqlParameterSource().addValue("inID", id);

        // Execute Stored Procedure
        Map<String, Object> out = simpleJdbcCall.execute(in);

        // Return
        return ((List<Audio>) out.get("audio")).get(0);
    }
}