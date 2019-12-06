import org.jetbrains.annotations.NotNull;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.AnAction;

public class UpdateTimeAction extends AnAction
{
    public void actionPerformed(@NotNull final AnActionEvent anActionEvent) {
        if (anActionEvent == null) {
            $$$reportNull$$$0(0);
        }
        final UpdateTimeDialog dialog = new UpdateTimeDialog();
        dialog.pack();
        dialog.setSize(1000, 800);
        dialog.setVisible(true);
    }

    private static /* synthetic */ void $$$reportNull$$$0(final int n) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "anActionEvent", "com/action/UpdateTimeAction", "actionPerformed"));
    }
}
