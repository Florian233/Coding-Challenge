package Exceptions;

/**
 * Exception für den Fall, dass kein Startpunkt angegeben wurde
 */
public class NoStartPointSecifiedException extends RuntimeException {


    public NoStartPointSecifiedException(String message) {
        super(message);
    }
}
