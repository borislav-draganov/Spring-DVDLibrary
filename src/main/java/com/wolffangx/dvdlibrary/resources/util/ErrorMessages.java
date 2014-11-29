package com.wolffangx.dvdlibrary.resources.util;

/**
 * Simple class to contain different error message that can be sent to the user
 *
 * @author borislav.draganov
 */

public abstract class ErrorMessages {
    public static final String SQL_SERVER_ERROR = "A problem occurred with the SQL Server";

    public static final String UNEXPECTED_ERROR = "An unexpected error occurred on the server.";

    public static final String MISSING_FIELD = "Mandatory field missing";

    public static final String INVALID_FIELD = "Mandatory field contains invalid data";
}