package com.draganov.dvdlibrary.exceptions;

/**
 * A custom exception - thrown when an entry is not found in the database
 *
 * @author borislav.draganov
 */

public class DvdLibraryException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DvdLibraryException(String message) {
        super(message);
    }
}