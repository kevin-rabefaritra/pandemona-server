package studio.startapps.pandemona.admin.feed.internal;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(long id) {
        super("Post not found with id %s".formatted(id));
    }
}
