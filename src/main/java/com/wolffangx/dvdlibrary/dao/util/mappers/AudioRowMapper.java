package com.wolffangx.dvdlibrary.dao.util.mappers;

import com.wolffangx.dvdlibrary.dao.util.SQLUtil;
import com.wolffangx.dvdlibrary.entities.Audio;
import com.wolffangx.dvdlibrary.exceptions.MissingIdException;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A Row Mapper used to read the data received from the database
 *
 * @author borislav.draganov
 */

public class AudioRowMapper implements RowMapper<Audio> {
    @Override
    public Audio mapRow(ResultSet resultSet, int i) throws SQLException {
        // Check for an error
        int code = resultSet.getInt(1);

        if (code == SQLUtil.SQL_ERROR) {
            throw new MissingIdException(resultSet.getString(2));
        }

        Audio audio = new Audio();                      // Empty New Object
        audio.setId(code);                              // ID
        audio.setName(resultSet.getString("audio"));    // Name

        return audio;
    }
}