import TaskManagers.InMemoryTaskManager;
import TasksClass.Epic;
import TasksClass.Subtask;
import TasksClass.TaskStatus;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.testng.annotations.Test;
import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    static InMemoryTaskManager taskManager = new InMemoryTaskManager();
    private static Epic epicTest = new Epic(1,"ЭпикТест №1", "ЭпикПроверка 1");
    private static Subtask subtaskTest = new Subtask(1, "СубТест1", "СубПроверка1", 1);
    private static Subtask subtaskTest2 = new Subtask(2, "СубюТест2", "СубПроверка2", 1);
    private static Subtask subtaskTest3 = new Subtask(3, "СубТест3", "СубПроверка3", 1);
    private static Subtask subtaskTest4 = new Subtask(4, "СубТест4", "СубПроверка4", 1);


    @Test
    public void addEpicEmptyOfSubtask() {
        final Epic epic = taskManager.add(epicTest);

        assertNotNull(epic, "Задача не найдена!");
        assertEquals(epic.getIdEpic(), 1, "ID не совпадают!");
        assertEquals(epic.getName(), "ЭпикТест №1", "Имена не совпадают!");
        assertEquals(epic.getDescription(), "ЭпикПроверка 1", "Описания не совпадают!");
        Assertions.assertEquals(epic.getStatus(), TaskStatus.NEW, "Статус эпика не верный!");
    }

    @Test
    public void addSubtaskWithTheNewStatus() {
        final Epic epic = taskManager.add(epicTest);
        final Subtask subtask = taskManager.add(subtaskTest);
        final Subtask subtask2 = taskManager.add(subtaskTest2);

        assertNotNull(subtask, "Подзадача №1 не найдена!");
        assertNotNull(subtask2, "Подзадача №2 не найдена!");
        assertEquals(epic.getIdEpic(), subtask.getIdEpic(), "ID эпика и подзадачи №1 не совпадают!");
        assertEquals(epic.getIdEpic(), subtask2.getIdEpic(), "ID эпика и подзадачи №2 не совпадают!");
        assertEquals(subtask.getStatus(), TaskStatus.NEW, "Статус подзадачи №1 не верный!");
        assertEquals(subtask2.getStatus(), TaskStatus.NEW, "Статус подзадачи №2 не верный!");
    }

    @BeforeAll
    static void addSubtaskWithTheDoneStatus() {
        final Epic epic = taskManager.add(epicTest);
        final Subtask subtask3 = taskManager.add(subtaskTest3);
        final Subtask subtask4 = taskManager.add(subtaskTest4);
        taskManager.setStatusSubtask(3, TaskStatus.DONE);
        taskManager.setStatusSubtask(4, TaskStatus.DONE);

        assertEquals(epic.getIdEpic(), subtask3.getIdEpic(), "ID эпика и подзадачи №3 не совпадают!");
        assertEquals(epic.getIdEpic(), subtask4.getIdEpic(), "ID эпика и подзадачи №4 не совпадают!");
        assertEquals(subtask3.getStatus(), TaskStatus.DONE, "Статус подзадачи №3 не верный!");
        assertEquals(subtask4.getStatus(), TaskStatus.DONE, "Статус подзадачи №4 не верный!");
    }

    @Test
    public void addSubtaskWithTheNewAndDoneStatus() {
        final Subtask subtask = taskManager.add(subtaskTest);
        final Subtask subtask2 = taskManager.add(subtaskTest2);
        final Subtask subtask3 = taskManager.add(subtaskTest3);
        final Subtask subtask4 = taskManager.add(subtaskTest4);

        assertEquals(subtask.getStatus(), TaskStatus.NEW, "Статус подзадачи №1 не верный!");
        assertEquals(subtask2.getStatus(), TaskStatus.NEW, "Статус подзадачи №2 не верный!");
        assertEquals(subtask3.getStatus(), TaskStatus.DONE, "Статус подзадачи №3 не верный!");
        assertEquals(subtask4.getStatus(), TaskStatus.DONE, "Статус подзадачи №4 не верный!");
    }

    @AfterAll
    static void addSubtaskWithTheInProgressStatus() {
        final Epic epic = taskManager.add(epicTest);
        final Subtask subtask = taskManager.add(subtaskTest);
        final Subtask subtask2 = taskManager.add(subtaskTest2);
        final Subtask subtask3 = taskManager.add(subtaskTest3);
        final Subtask subtask4 = taskManager.add(subtaskTest4);
        taskManager.setStatusSubtask(1, TaskStatus.IN_PROGRESS);
        taskManager.setStatusSubtask(2, TaskStatus.IN_PROGRESS);
        taskManager.setStatusSubtask(3, TaskStatus.IN_PROGRESS);
        taskManager.setStatusSubtask(4, TaskStatus.IN_PROGRESS);

        assertEquals(subtask.getStatus(), TaskStatus.IN_PROGRESS, "Статус подзадачи №1 не верный!");
        assertEquals(subtask2.getStatus(), TaskStatus.IN_PROGRESS, "Статус подзадачи №2 не верный!");
        assertEquals(subtask3.getStatus(), TaskStatus.IN_PROGRESS, "Статус подзадачи №3 не верный!");
        assertEquals(subtask4.getStatus(), TaskStatus.IN_PROGRESS, "Статус подзадачи №4 не верный!");
    }

}