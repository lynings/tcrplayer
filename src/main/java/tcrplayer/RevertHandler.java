package tcrplayer;

import com.google.common.collect.Sets;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.ProjectLevelVcsManager;
import com.intellij.openapi.vcs.changes.Change;
import com.intellij.openapi.vcs.changes.ChangeListManager;
import com.intellij.openapi.vcs.changes.ui.RollbackWorker;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * @author lyning
 */
public class RevertHandler {
    private final Set<PathMatcher> excludePathMatchers;
    private final Project project;

    public RevertHandler(@NotNull Project project, Set<PathMatcher> excludePathMatchers) {
        this.project = project;
        this.excludePathMatchers = excludePathMatchers;
        this.addDefaultPathMatchers();
    }

    public void doRevert() {
        String operationName = "$pluginId revert";
        Collection<Change> changeFiles = this.getChangeFiles();
        new RollbackWorker(this.project, operationName, false)
                .doRollback(changeFiles, true, null, operationName);
    }

    private void addDefaultPathMatchers() {
        this.excludePathMatchers.addAll(
                Sets.newHashSet(
                        new PathMatcher(".*/.idea/.*")
                )
        );
    }

    @NotNull
    private Collection<Change> getChangeFiles() {
        return this.getChangeList()
                .stream()
                .filter(change -> !this.isMatches(change))
                .collect(toSet());
    }

    private Collection<Change> getChangeList() {
        if (!ProjectLevelVcsManager.getInstance(this.project).hasActiveVcss()) {
            return Collections.emptyList();
        }
        return ChangeListManager.getInstance(this.project).getDefaultChangeList().getChanges();
    }

    @NotNull
    private String getPath(Change change) {
        return change.getVirtualFile().getPath();
    }

    private boolean isMatches(Change change) {
        return this.excludePathMatchers
                .stream()
                .anyMatch(pathMatcher -> pathMatcher.match(this.getPath(change)));
    }
}
