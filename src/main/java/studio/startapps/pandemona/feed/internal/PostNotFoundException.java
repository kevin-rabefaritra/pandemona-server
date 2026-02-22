package studio.startapps.pandemona.feed.internal;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(long id) {
        super("Post not found with id %s".formatted(id));
    }
}
