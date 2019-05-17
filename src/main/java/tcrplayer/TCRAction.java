package tcrplayer;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

/**
 * @author lyning
 */
public class TCRAction extends AnAction {
    public TCRAction() {
        super("Hello");
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project project = event.getProject();
        //获取当前操作的类文件
        PsiFile psiFile = event.getData(CommonDataKeys.PSI_FILE);
        //获取当前类文件的路径
        String classPath = psiFile.getVirtualFile().getPath();
        String title = "Hello World!";

        //显示对话框
        Messages.showMessageDialog(project, classPath, title, Messages.getInformationIcon());
    }
}
