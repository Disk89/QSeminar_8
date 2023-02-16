package ru.sberbank.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sberbank.data.Release;
import ru.sberbank.data.Task;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReleaseServiceTest {
    ReleaseService releaseService;

    @BeforeEach
    void setUp() {
        Task expectedTask = new Task(1, "task");
        TaskService taskService = mock(TaskService.class);
        when(taskService.getTask(1)).thenReturn(expectedTask);
        when(taskService.getTask(eq(1))).thenReturn(expectedTask);
        releaseService = new ReleaseService(taskService);
        releaseService.create(60, "Релиз 60");
        releaseService.addTaskToRelease(60, 1);
    }

    @Test
    void create_failException() {
        String expectedMessage = "Название релиза не задано";

        Exception actualException = assertThrows(
                IllegalStateException.class,
                () -> {
                    releaseService.create(1, "");
                }
        );

        assertEquals(expectedMessage, actualException.getMessage());
    }

    @Test
    void createRelease_successful() {
        Release expected = new Release(1, "r60");
        Release actual = releaseService.create(1, "r60");

        assertEquals(expected, actual);
    }

    @Test
    void getTaskFromRelease_failReleaseException() {
        String expected = "Релиз с 1 не найден";
        Exception actual = assertThrows(
                IllegalStateException.class,
                () -> {
                    releaseService.getTaskFromRelease(1, 2);
                }
        );
        assertEquals(expected, actual.getMessage());
    }

    @Test
    void getTaskFromRelease_failTaskException() {
        String expected = "Задача(id=2) не найдена в релизе(id=60)";
        Exception actual = assertThrows(
                IllegalStateException.class,
                () -> {
                    releaseService.getTaskFromRelease(60, 2);
                }
        );
        assertEquals(expected, actual.getMessage());
    }

    @Test
    void getTaskFromRelease_successful() {
        Task expected = new Task(1, "task");
        Task actual = releaseService.getTaskFromRelease(60, 1);
        assertEquals(expected, actual);
    }
}