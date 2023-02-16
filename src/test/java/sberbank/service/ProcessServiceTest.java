package sberbank.service;

import org.junit.jupiter.api.*;
import ru.sberbank.data.Task;
import ru.sberbank.service.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProcessServiceTest {

    private ProcessService processService;
    private final int EXPECTED_TASK_ID = 0;
    private final String EXPECTED_TASK_SUMMARY = "Тестовая задача";

    @BeforeEach
    void setUp() {
        Task expectedTask = new Task(EXPECTED_TASK_ID, EXPECTED_TASK_SUMMARY);

        TaskService taskService = mock(TaskService.class);
        when(taskService.createTask(EXPECTED_TASK_ID, EXPECTED_TASK_SUMMARY)).thenReturn(expectedTask);

        DeveloperService developerService = mock(DeveloperService.class);

        TesterService testerService = mock(TesterService.class);

        ReleaseService releaseService = mock(ReleaseService.class);

        this.processService = new ProcessService(taskService, developerService, testerService, releaseService);
    }

    @Test
    void createTask_successful(){
        Assertions.assertEquals(0, processService.createTask(EXPECTED_TASK_SUMMARY));
    }

    @Test
    void pushStatusTask_successful(){

    }

}