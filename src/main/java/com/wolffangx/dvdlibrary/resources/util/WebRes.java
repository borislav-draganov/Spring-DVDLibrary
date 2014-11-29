package com.wolffangx.dvdlibrary.resources.util;

import com.wolffangx.dvdlibrary.dao.delete.SQLDeleteOperations;
import com.wolffangx.dvdlibrary.dao.insert.SQLInsertOperations;
import com.wolffangx.dvdlibrary.dao.insert.SQLUpdateOperations;
import com.wolffangx.dvdlibrary.dao.report.SQLReportOperations;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author borislav.draganov
 */

public class WebRes {
    @Autowired
    protected SQLReportOperations sqlReportOperations;

    @Autowired
    protected SQLInsertOperations sqlInsertOperations;

    @Autowired
    protected SQLUpdateOperations sqlUpdateOperations;

    @Autowired
    protected SQLDeleteOperations sqlDeleteOperations;

    // Getters and Setters - required for @Autowired
    public SQLReportOperations getReports() {
        return sqlReportOperations;
    }

    public void setReports(SQLReportOperations sqlReportOperations) {
        this.sqlReportOperations = sqlReportOperations;
    }

    public SQLInsertOperations getSqlInsertOperations() {
        return sqlInsertOperations;
    }

    public void setSqlInsertOperations(SQLInsertOperations sqlInsertOperations) {
        this.sqlInsertOperations = sqlInsertOperations;
    }

    public SQLUpdateOperations getSqlUpdateOperations() {
        return sqlUpdateOperations;
    }

    public void setSqlUpdateOperations(SQLUpdateOperations sqlUpdateOperations) {
        this.sqlUpdateOperations = sqlUpdateOperations;
    }

    public SQLDeleteOperations getSqlDeleteOperations() {
        return sqlDeleteOperations;
    }

    public void setSqlDeleteOperations(SQLDeleteOperations sqlDeleteOperations) {
        this.sqlDeleteOperations = sqlDeleteOperations;
    }
}