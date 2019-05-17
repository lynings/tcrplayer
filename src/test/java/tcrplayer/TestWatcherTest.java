package tcrplayer;

import com.intellij.openapi.project.Project;
import org.junit.Before;
import org.junit.Test;

import java.util.function.Consumer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author lyning
 */
public class TestWatcherTest {

    private TestWatcher testWatcher;

    @Before
    public void setUp() throws Exception {
        this.testWatcher = new TestWatcher(mock(Project.class));
    }

    @Test
    public void should_callback_when_test_failure() {
        // given
        Consumer<String> testFailureConsumer = mock(Consumer.class);
        this.testWatcher.testStatusListener = (listener) -> listener.failure();
        // when
        this.testWatcher.subscribe(new TestWatcher.Listener() {
            @Override
            public void failure() {
                testFailureConsumer.accept("failure");
            }
        });
        // then
        verify(testFailureConsumer).accept("failure");
    }

    @Test
    public void should_callback_when_test_success() {
        // given
        Consumer<String> testSuccessConsumer = mock(Consumer.class);
        this.testWatcher.testStatusListener = (listener) -> listener.success();
        // when
        this.testWatcher.subscribe(new TestWatcher.Listener() {
            @Override
            public void success() {
                testSuccessConsumer.accept("success");
            }
        });
        // then
        verify(testSuccessConsumer).accept("success");
    }
}
