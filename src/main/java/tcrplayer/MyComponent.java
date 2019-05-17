package tcrplayer;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;

/**
 * @author lyning
 */
public class MyComponent implements ProjectComponent {
    private final Project project;

    public MyComponent(Project project) {
        this.project = project;
    }

    @Override
    public void projectOpened() {
        ProjectManager.getInstance();
    }
}
