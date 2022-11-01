package project.exception;

public class ObjectNotFoundException extends RuntimeException {
    private String message;

    public ObjectNotFoundException(String message) {
        super(message);
    }
}
