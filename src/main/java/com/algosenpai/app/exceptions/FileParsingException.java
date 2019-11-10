package com.algosenpai.app.exceptions;

/**
 * Thrown when the file contains invalid or corrupted data.
 * The program can choose to show an error message, etc.
 */
public class FileParsingException extends SenpaiExceptions {

    public FileParsingException(String message) {
        super(message);
    }
}
