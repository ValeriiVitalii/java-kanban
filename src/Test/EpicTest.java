import TasksClass.Epic;
import TasksClass.Subtask;
import TasksClass.TaskStatus;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EpicTest {
    private static Epic epicTest = new Epic(1,"ЭпикТест №1", "ЭпикПроверка 1");
    private static Subtask subtaskTest = new Subtask(1, "СубТест1", "СубПроверка1", 1);
    private static Subtask subtaskTest2 = new Subtask(2, "СубюТест2", "СубПроверка2", 1);
    private static Subtask subtaskTest3 = new Subtask(3, "СубТест3", "СубПроверка3", 1);
    private static Subtask subtaskTest4 = new Subtask(4, "СубТест4", "СубПроверка4", 1);


    @Test
    public void addEpicEmptyOfSubtask() {
        assertNotNull(epicTest, "Задача не найдена!");
        assertEquals(epicTest.getIdEpic(), 1, "ID не совпадают!");
        assertEquals(epicTest.getName(), "ЭпикТест №1", "Имена не совпадают!");
        assertEquals(epicTest.getDescription(), "ЭпикПроверка 1", "Описания не совпадают!");
        Assertions.assertEquals(epicTest.getStatus(), TaskStatus.NEW, "Статус эпика не верный!");
    }

    @Test
    public void addSubtaskWithTheNewStatus() {
        assertNotNull(subtaskTest, "Подзадача №1 не найдена!");
        assertNotNull(subtaskTest2, "Подзадача №2 не найдена!");
        assertEquals(epicTest.getIdEpic(), subtaskTest.getIdEpic(), "ID эпика и подзадачи №1 не совпадают!");
        assertEquals(epicTest.getIdEpic(), subtaskTest2.getIdEpic(), "ID эпика и подзадачи №2 не совпадают!");
        assertEquals(subtaskTest.getStatus(), TaskStatus.NEW, "Статус подзадачи №1 не верный!");
        assertEquals(subtaskTest2.getStatus(), TaskStatus.NEW, "Статус подзадачи №2 не верный!");
    }

    @BeforeAll
    static void addSubtaskWithTheDoneStatus() {
        subtaskTest3.setStatus(TaskStatus.DONE);
        subtaskTest4.setStatus(TaskStatus.DONE);

        assertEquals(epicTest.getIdEpic(), subtaskTest3.getIdEpic(), "ID эпика и подзадачи №3 не совпадают!");
        assertEquals(epicTest.getIdEpic(), subtaskTest4.getIdEpic(), "ID эпика и подзадачи №4 не совпадают!");
        assertEquals(subtaskTest3.getStatus(), TaskStatus.DONE, "Статус подзадачи №3 не верный!");
        assertEquals(subtaskTest4.getStatus(), TaskStatus.DONE, "Статус подзадачи №4 не верный!");
    }

    @Test
    public void addSubtaskWithTheNewAndDoneStatus() {
        assertEquals(subtaskTest.getStatus(), TaskStatus.NEW, "Статус подзадачи №1 не верный!");
        assertEquals(subtaskTest2.getStatus(), TaskStatus.NEW, "Статус подзадачи №2 не верный!");
        assertEquals(subtaskTest3.getStatus(), TaskStatus.DONE, "Статус подзадачи №3 не верный!");
        assertEquals(subtaskTest4.getStatus(), TaskStatus.DONE, "Статус подзадачи №4 не верный!");
    }

    @AfterAll
    static void addSubtaskWithTheInProgressStatus() {
        subtaskTest.setStatus(TaskStatus.IN_PROGRESS);
        subtaskTest2.setStatus(TaskStatus.IN_PROGRESS);
        subtaskTest3.setStatus(TaskStatus.IN_PROGRESS);
        subtaskTest4.setStatus(TaskStatus.IN_PROGRESS);

        assertEquals(subtaskTest.getStatus(), TaskStatus.IN_PROGRESS, "Статус подзадачи №1 не верный!");
        assertEquals(subtaskTest2.getStatus(), TaskStatus.IN_PROGRESS, "Статус подзадачи №2 не верный!");
        assertEquals(subtaskTest3.getStatus(), TaskStatus.IN_PROGRESS, "Статус подзадачи №3 не верный!");
        assertEquals(subtaskTest4.getStatus(), TaskStatus.IN_PROGRESS, "Статус подзадачи №4 не верный!");
    }

}