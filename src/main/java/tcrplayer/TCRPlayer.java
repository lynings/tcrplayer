package tcrplayer;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * @author lyning
 */
public class TCRPlayer implements ProjectComponent {

    private final GitHandler gitHandler;
    private final RevertHandler revertHandler;
    private final TestWatcher testWatcher;
    private final TCRConfiguration configuration = this.defaultConfiguration();

    public TCRPlayer(@NotNull Project project) {
        this.testWatcher = new TestWatcher(project);
        this.revertHandler = new RevertHandler(project, this.configuration.excludePathMatchers);
        this.gitHandler = new GitHandler(project);
    }

    @Override
    public void projectOpened() {
        if (!this.configuration.enable) {
            return;
        }
        this.testWatcher.subscribe(new TestWatcher.Listener() {

            @Override
            public void failure() {
                TCRPlayer.this.revertHandler.doRevert();
            }

            @Override
            public void success() {
                if (TCRPlayer.this.configuration.commit) {
                    TCRPlayer.this.gitHandler.doCommit();
                }
                if (TCRPlayer.this.configuration.push) {
                    TCRPlayer.this.gitHandler.doPush();
                }
            }
        });
    }

    @NotNull
    private TCRConfiguration defaultConfiguration() {
        return new TCRConfiguration();
    }
}
