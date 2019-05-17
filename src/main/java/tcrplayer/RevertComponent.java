package tcrplayer;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.ProjectLevelVcsManager;
import com.intellij.openapi.vcs.changes.Change;
import com.intellij.openapi.vcs.changes.ChangeList;
import com.intellij.openapi.vcs.changes.ChangeListManager;
import com.intellij.openapi.vcs.changes.ui.RollbackWorker;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @author lyning
 */
public class RevertComponent implements ProjectComponent {
    private final Project project;
    private boolean enable;

    public RevertComponent(@NotNull Project project) {
        this.project = project;
    }

    public boolean enable() {
        return this.enable;
    }

    public ChangeList getChangeList() {
        if (!ProjectLevelVcsManager.getInstance(this.project).hasActiveVcss()) {
            return null;
        }
        return ChangeListManager.getInstance(this.project).getDefaultChangeList();
    }

    @Override
    public void projectOpened() {
        Collection<Change> changeList = this.getChangeList().getChanges();
        String operationName = "$pluginId tcrplayer";
        new RollbackWorker(this.project, operationName, false)
                .doRollback(changeList, true, null, operationName);

    }
}
