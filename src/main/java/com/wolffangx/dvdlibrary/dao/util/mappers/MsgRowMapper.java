package com.wolffangx.dvdlibrary.dao.util.mappers;

import com.wolffangx.dvdlibrary.exceptions.MissingIdException;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A Row Mapper used to read the data received from the database
 *
 * @author borislav.draganov
 */

public class MsgRowMapper implements RowMapper<String> {
    @Override
    public String mapRow(ResultSet resultSet, int i) throws SQLException {
        // If the code is a negative then there was an error with the query
        if (resultSet.getInt(1) < 0) {
            throw new MissingIdException(resultSet.getString(2));
        }

        // Otherwise just return the successful message
        return resultSet.getString(2);
    }
}