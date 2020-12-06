package ua.edu.ucu.helper.exceptions;

public class EmptyQueueException extends RuntimeException {
    public EmptyQueueException(String errorMessage) {
        super(errorMessage);
    }
}
