package tcrplayer;

/**
 * @author lyning
 */
public class PathMatcher {
    private final String pattern;

    public PathMatcher(String pattern) {
        this.pattern = pattern;
    }

    public boolean match(String path) {
        return path.matches(this.pattern);
    }
}
