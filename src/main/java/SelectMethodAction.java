import org.jetbrains.annotations.NotNull;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.AnAction;

public class SelectMethodAction extends AnAction
{
    public void actionPerformed(@NotNull final AnActionEvent anActionEvent) {
        SelectMethodDialog dialog = new SelectMethodDialog();

        dialog.pack();
        dialog.setSize(1000, 800);
        dialog.setVisible(true);
    }
}
