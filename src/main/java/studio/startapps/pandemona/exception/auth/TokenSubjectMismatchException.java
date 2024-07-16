package studio.startapps.pandemona.exception.auth;

/**
 * Exception used when a token subject does not match with a claimed username
 */
public class TokenSubjectMismatchException extends RuntimeException {

    public TokenSubjectMismatchException(String tokenSubject, String claimedSubject) {
        super(String.format("Token subject and claimed subject are not equal: expecting %s, claimed %s", tokenSubject, claimedSubject));
    }
}
