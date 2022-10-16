import TaskManagers.InMemoryTaskManager;
import TasksClass.Epic;
import TasksClass.Subtask;
import TasksClass.Task;
import TasksClass.TaskStatus;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InMemoryTasksManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @Override
    public InMemoryTaskManager createManager() {
        return new InMemoryTaskManager();
    }

    @Override
    @Test
    public void addTaskAndGetTaskByIdTest() {
        Task taskTest = new Task(1,"ТаскТест №1", "ТаскПроверка 1");
        InMemoryTaskManager taskManager = createManager();
        int id = taskManager.add(taskTest);
        Task task = taskManager.getTask(id);

        assertNotNull(task, "Задача не найдена!");
        assertEquals(task, taskTest, "Задачи не совпадают!");

        List<Task> listTask = taskManager.getListTask();

        assertNotNull(listTask, "Список задач не найден!");
        assertEquals(1, listTask.size(), "Неверное количество задач.");
    }

    @Override
    @Test
    public void addEpicAndGetEpicByIdTest() {
        Epic epicTest = new Epic(1,"ЭпикТест №1", "ЭпикПроверка 1");
        InMemoryTaskManager taskManager = createManager();
        int idEpic = taskManager.add(epicTest);
        Epic epic = taskManager.getEpic(idEpic);

        assertNotNull(epic, "Задача не найдена!");
        assertEquals(epic, epicTest, "Задачи не совпадают!");

        List<Epic> listEpic = taskManager.getListEpic();

        assertNotNull(listEpic, "Список задач не найден!");
        assertEquals(2, listEpic.size(), "Неверное количество задач.");

        Subtask subtaskTest = new Subtask(1, "СубтаскТест №1", "СубтаскПроверка 1", 1);
        int idSubtask = taskManager.add(subtaskTest);
      //  taskManager.setStatusSubtask(1, TaskStatus.DONE);
        subtaskTest.setStatus(TaskStatus.IN_PROGRESS);

        assertEquals(epic.getStatus(), TaskStatus.IN_PROGRESS, "Статус Эпика не верный!");
    }

    @Override
    @Test
    public void addSubtaskAndGetSubtaskIdTest() {
        Epic epicTest = new Epic(1,"ЭпикТест №1", "ЭпикПроверка 1");
        InMemoryTaskManager taskManager = createManager();
        int idEpic = taskManager.add(epicTest);
        Subtask subtaskTest = new Subtask(1,"СубтаскТест №1", "СубтаскПроверка 1", 1);
        int idSubtask = taskManager.add(subtaskTest);
        Subtask subtask = taskManager.getSubtask(idSubtask);

        assertNotNull(subtask, "Задача не найдена!");
        assertEquals(subtask, subtaskTest, "Задачи не совпадают!");

        List<Subtask> listSubtask = taskManager.getListSubtasksOfAnEpic(1);

        assertNotNull(listSubtask, "Список задач не найден!");
        assertEquals(1, listSubtask.size(), "Неверное количество задач.");
    }

}
