package tcrplayer;

import com.intellij.openapi.project.Project;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * @author lyning
 */
public class RevertComponentTest {
    private RevertComponent revertComponent;

    @Before
    public void setUp() throws Exception {
        revertComponent = new RevertComponent(mock(Project.class));
    }

    @Test
    public void should_revert_when_test_failure() {
        // given

        // when
        revertComponent.projectOpened();
        // then
        assertThat(revertComponent.enable()).isTrue();
    }
}
