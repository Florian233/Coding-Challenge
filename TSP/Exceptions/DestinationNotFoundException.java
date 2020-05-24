package Exceptions;

/**
 * Exception, wenn eine Adresse nicht gefunden werden konnte
 */
public class DestinationNotFoundException extends RuntimeException {


    public DestinationNotFoundException(String message) {
        super(message);
    }

}
