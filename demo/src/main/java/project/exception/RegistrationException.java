package project.exception;

public class RegistrationException extends RuntimeException {
    private String message;

    public RegistrationException(String message) {
        super(message);
    }
}
