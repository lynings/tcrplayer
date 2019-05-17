package tcrplayer;

import java.util.HashSet;
import java.util.Set;

/**
 * @author lyning
 */
public class TCRConfiguration {
    public boolean enable = true;
    public boolean commit = true;
    public boolean push;
    public Set<PathMatcher> excludePathMatchers = new HashSet<>();
}
