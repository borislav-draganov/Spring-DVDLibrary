package com.wolffangx.dvdlibrary.dao.util.mappers;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A Row Mapper used to read the data received from the database
 * Used only for the utility methods when populating Movie and DVD n:m relations
 *
 * @author borislav.draganov
 */

public class IdRowMapper implements RowMapper<Integer> {
    @Override
    public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
        // Return the ID
        return resultSet.getInt(1);
    }
}