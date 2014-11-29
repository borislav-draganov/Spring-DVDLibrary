package com.wolffangx.dvdlibrary.exceptions;

/**
 * A custom exception - thrown when an entry is not found in the database
 *
 * @author borislav.draganov
 */

public class MissingIdException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public MissingIdException(String message) {
        super(message);
    }
}