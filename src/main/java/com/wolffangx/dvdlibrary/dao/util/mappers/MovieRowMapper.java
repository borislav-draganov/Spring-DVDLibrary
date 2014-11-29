package com.wolffangx.dvdlibrary.dao.util.mappers;

import com.wolffangx.dvdlibrary.dao.util.SQLUtil;
import com.wolffangx.dvdlibrary.entities.Genre;
import com.wolffangx.dvdlibrary.entities.Movie;
import com.wolffangx.dvdlibrary.exceptions.MissingIdException;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * A Row Mapper used to read the data received from the database
 *
 * @author borislav.draganov
 */

public class MovieRowMapper implements RowMapper<Movie> {
    @Override
    public Movie mapRow(ResultSet resultSet, int i) throws SQLException {
        // Check for an error
        int code = resultSet.getInt(1);

        if (code == SQLUtil.SQL_ERROR) {
            throw new MissingIdException(resultSet.getString(2));
        }

        // Split the concatenated data
        String[] splitGenreIDs = resultSet.getString("GenreIDs").split(",");    // IDs
        String[] splitGenreNames = resultSet.getString("Genres").split(",");    // Names

        // Container for the all finished Genre objects
        List<Genre> genres = new LinkedList<Genre>();

        //
        for (int j = 0; j < splitGenreIDs.length; j++) {
            Genre genre = new Genre(splitGenreNames[j]);        // Name
            genre.setId(Integer.parseInt(splitGenreIDs[j]));    // ID

            // Add it to the list
            genres.add(genre);
        }

        // Set the data to a new Movie object
        Movie movie = new Movie();
        movie.setId(resultSet.getInt("idMovie"));
        movie.setTitle(resultSet.getString("title"));
        movie.setLength(resultSet.getInt("length"));
        movie.setReleaseDate(resultSet.getString("releaseDate"));
        movie.setImdbId(resultSet.getInt("IMDB"));
        movie.setRating(resultSet.getInt("rating"));
        movie.setGenres(genres);

        // Return the object
        return movie;
    }
}