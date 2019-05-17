package tcrplayer;

import com.google.common.collect.Lists;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.ProjectLevelVcsManager;
import com.intellij.openapi.vcs.changes.Change;
import com.intellij.openapi.vcs.changes.ChangeList;
import com.intellij.openapi.vcs.changes.ChangeListManager;
import com.intellij.openapi.vcs.changes.ui.CommitHelper;
import com.intellij.util.FunctionUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author lyning
 */
public class GitHandler {
    private final Project project;

    public GitHandler(@NotNull Project project) {
        this.project = project;
    }

    public void doCommit() {
        ChangeList changeList = this.getChangeList();
        if (Objects.isNull(changeList)) {
            return;
        }
        String commitMessage = "test commit";
        CommitHelper commitHelper = new CommitHelper(this.project,
                changeList,
                this.getChangeFiles(changeList),
                "",
                commitMessage,
                new ArrayList<>(),
                true,
                true,
                FunctionUtil.nullConstant(),
                null
        );
        commitHelper.doCommit();
    }

    @Deprecated
    public void doPush() {
    }

    private List<Change> getChangeFiles(ChangeList changeList) {
        return changeList == null ? new ArrayList<>() : Lists.newArrayList(changeList.getChanges());
    }

    private ChangeList getChangeList() {
        if (!ProjectLevelVcsManager.getInstance(this.project).hasActiveVcss()) {
            return null;
        }
        return ChangeListManager.getInstance(this.project).getDefaultChangeList();
    }
}
