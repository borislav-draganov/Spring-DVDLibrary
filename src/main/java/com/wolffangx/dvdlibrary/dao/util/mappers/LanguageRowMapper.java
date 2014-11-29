package com.wolffangx.dvdlibrary.dao.util.mappers;

import com.wolffangx.dvdlibrary.dao.util.SQLUtil;
import com.wolffangx.dvdlibrary.entities.Language;
import com.wolffangx.dvdlibrary.exceptions.MissingIdException;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A Row Mapper used to read the data received from the database
 *
 * @author borislav.draganov
 */

public class LanguageRowMapper implements RowMapper<Language> {
    @Override
    public Language mapRow(ResultSet resultSet, int i) throws SQLException {
        // Check for an error
        int code = resultSet.getInt(1);

        if (code == SQLUtil.SQL_ERROR) {
            throw new MissingIdException(resultSet.getString(2));
        }

        Language language = new Language();                     // Empty New Object
        language.setId(code);                                   // ID
        language.setName(resultSet.getString("language"));      // Name

        return language;
    }
}