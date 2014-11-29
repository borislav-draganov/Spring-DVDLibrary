package com.wolffangx.dvdlibrary.dao.util;

import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

/**
 * Define the Data Source with its getters and setters
 * For use as a parent for the actual SQL operation classes
 *
 * @author borislav.draganov
 */

public abstract class SQLOperations {
    @Autowired
    protected DataSource dataSource;

    // Getters and Setters
    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}