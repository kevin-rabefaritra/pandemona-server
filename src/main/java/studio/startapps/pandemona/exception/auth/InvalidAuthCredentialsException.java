package studio.startapps.pandemona.exception.auth;

public class InvalidAuthCredentialsException extends RuntimeException {
    /**
     * Constructs an instance of this class.
     *
     * @param username the principal username
     */
    public InvalidAuthCredentialsException(String username) {
        super(String.format("%s authentication attempt failed", username));
    }
}
