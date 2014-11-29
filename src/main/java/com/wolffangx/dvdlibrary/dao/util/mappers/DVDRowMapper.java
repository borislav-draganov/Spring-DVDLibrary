package com.wolffangx.dvdlibrary.dao.util.mappers;

import com.wolffangx.dvdlibrary.dao.util.SQLUtil;
import com.wolffangx.dvdlibrary.entities.Audio;
import com.wolffangx.dvdlibrary.entities.DVD;
import com.wolffangx.dvdlibrary.entities.Language;
import com.wolffangx.dvdlibrary.entities.Movie;
import com.wolffangx.dvdlibrary.entities.util.LanguageAudioPair;
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

public class DVDRowMapper implements RowMapper<DVD> {
    @Override
    public DVD mapRow(ResultSet resultSet, int i) throws SQLException {
        // Check for an error
        int code = resultSet.getInt(1);

        if (code == SQLUtil.SQL_ERROR) {
            throw new MissingIdException(resultSet.getString(2));
        }

        // Split the language data
        String[] splitLanguageIDs = resultSet.getString("LanguageIDs").split(",");  // IDs
        String[] splitLanguagesNames = resultSet.getString("Languages").split(",");      // Names

        // Split the audio data
        String[] splitAudioIDs = resultSet.getString("AudioIDs").split(",");    // IDs
        String[] splitAudiosNames = resultSet.getString("Audios").split(",");        // Names

        // Container for the final LanguageAudioPairs
        List<LanguageAudioPair> lapList = new LinkedList<LanguageAudioPair>();

        // Construct the pairs and add them to the list
        for (int j = 0; j < splitLanguageIDs.length; j++) {
            Language language = new Language(splitLanguagesNames[j]);
            language.setId(Integer.parseInt(splitLanguageIDs[j]));

            Audio audio = new Audio(splitAudiosNames[j]);
            audio.setId(Integer.parseInt(splitAudioIDs[j]));

            LanguageAudioPair lap = new LanguageAudioPair(language, audio);

            lapList.add(lap);
        }

        // Parse the movie data, returning a valid Movie object
        Movie movie = new MovieRowMapper().mapRow(resultSet, i);

        // Create a DVD object with the data
        DVD dvd = new DVD();
        dvd.setId(resultSet.getInt("idDVD"));
        dvd.setMovie(movie);
        dvd.setIsbn(resultSet.getString("ISBN"));
        dvd.setEdition(resultSet.getString("edition"));
        dvd.setScreenFormat(resultSet.getString("screenFormat"));
        dvd.setRegion(resultSet.getString("region"));
        dvd.setLanguageAudioPairs(lapList);

        // Return the object
        return dvd;
    }
}