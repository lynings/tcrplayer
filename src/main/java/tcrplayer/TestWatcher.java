package tcrplayer;

import com.intellij.execution.testframework.TestsUIUtil;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.notification.NotificationsAdapter;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * @author lyning
 */
public class TestWatcher {
    ITestStatusListener testStatusListener;

    public TestWatcher(@NotNull Project project) {
        this.testStatusListener = new TestStatusListener(project);
    }

    public void subscribe(Listener listener) {
        this.testStatusListener.subscribe(listener);
    }

    interface ITestStatusListener {
        /**
         * subscribe test statue
         *
         * @param listener when test status changed then callback listener
         */
        void subscribe(Listener listener);
    }

    interface Listener {
        /**
         * test failure
         */
        default void failure() {
        }

        /**
         * test success
         */
        default void success() {
        }
    }

    class TestStatusListener implements ITestStatusListener {
        private final Project project;

        public TestStatusListener(Project project) {
            this.project = project;
        }

        @Override
        public void subscribe(Listener listener) {
            this.project.getMessageBus().connect().subscribe(Notifications.TOPIC, new NotificationsAdapter() {
                @Override
                public void notify(@NotNull Notification notification) {
                    if (this.isTestGroup(notification)) {
                        this.toNotify(notification);
                    }
                }

                private boolean isFailure(@NotNull Notification notification) {
                    return notification.getType() == NotificationType.ERROR;
                }

                private boolean isTestGroup(@NotNull Notification notification) {
                    return notification.getGroupId().equals(TestsUIUtil.NOTIFICATION_GROUP.getDisplayId());
                }

                private void toNotify(@NotNull Notification notification) {
                    if (this.isFailure(notification)) {
                        listener.failure();
                    } else {
                        listener.success();
                    }
                }
            });
        }
    }
}
