package exceptions;

public class ConferencePlatformException extends RuntimeException {

    public ConferencePlatformException() {
        super();
    }

    public ConferencePlatformException(String message) {
        super(message);
    }
}
