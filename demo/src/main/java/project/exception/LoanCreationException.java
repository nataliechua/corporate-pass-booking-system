package project.exception;

public class LoanCreationException extends RuntimeException {
    private String message;

    public LoanCreationException(String message) {
        super(message);
    }
}
