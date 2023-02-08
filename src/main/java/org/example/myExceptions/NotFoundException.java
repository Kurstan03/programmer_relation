package org.example.myExceptions;

/**
 * @author kurstan
 * @created at 08.02.2023 15:24
 */
public class NotFoundException extends RuntimeException{
    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }
}
