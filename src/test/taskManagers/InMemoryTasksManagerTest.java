package taskManagers;

import tasksClass.Epic;
import tasksClass.Subtask;
import tasksClass.Task;
import tasksClass.TaskStatus;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class
InMemoryTasksManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @Override
    public InMemoryTaskManager createManager() {
        return new InMemoryTaskManager();
    }

    @Override
    @Test
    public void addTaskAndGetTaskByIdTest() {
        Task taskTest = new Task(1,"ТаскТест №1", "ТаскПроверка 1",
                "2022-10-20T10:10:10", 50);
        InMemoryTaskManager taskManager = createManager();
        int id = taskManager.add(taskTest);
        Task task = taskManager.getTask(id);

        assertNotNull(task, "Задача не найдена!");
        assertEquals(task, taskTest, "Задачи не совпадают!");

        List<Task> listTask = taskManager.getListTask();

        assertNotNull(listTask, "Список задач не найден!");
        assertEquals(1, listTask.size(), "Неверное количество задач.");

        assertEquals(task.getEndTime(), "20.10.2022, 11:00", "Время не совпадает!");
    }

    @Override
    @Test
    public void addEpicAndGetEpicByIdTest() {
        Epic epicTest = new Epic(1,"ЭпикТест №1", "ЭпикПроверка 1",
                "2021-11-22T16:22:10", 44);
        InMemoryTaskManager taskManager = createManager();
        int idEpic = taskManager.add(epicTest);
        Epic epic = taskManager.getEpic(idEpic);

        assertNotNull(epic, "Задача не найдена!");
        assertEquals(epic, epicTest, "Задачи не совпадают!");

        List<Epic> listEpic = taskManager.getListEpic();

        assertNotNull(listEpic, "Список задач не найден!");
        assertEquals(1, listEpic.size(), "Неверное количество задач.");

        Subtask subtaskTest = new Subtask(2, "СубтаскТест №1", "СубтаскПроверка 1", 1,
                "2021-11-22T16:22:10", 44);
        int idSubtask = taskManager.add(subtaskTest);
        taskManager.setStatusSubtask(2, TaskStatus.IN_PROGRESS);

        assertEquals(epic.getStatus(), TaskStatus.IN_PROGRESS, "Статус Эпика не верный!");

        taskManager.setStatusSubtask(2, TaskStatus.DONE);

        assertEquals(epic.getStatus(), TaskStatus.DONE, "Статус Эпика не верный!");
    }

    @Override
    @Test
    public void addSubtaskAndGetSubtaskIdTest() {
        Epic epicTest = new Epic(1,"ЭпикТест №1", "ЭпикПроверка 1",
                "2021-11-22T16:22:10", 44);
        InMemoryTaskManager taskManager = createManager();
        int idEpic = taskManager.add(epicTest);
        Subtask subtaskTest = new Subtask(2,"СубтаскТест №1", "СубтаскПроверка 1", 1,
                "2021-11-22T16:22:10", 44);
        int idSubtask = taskManager.add(subtaskTest);
        Subtask subtask = taskManager.getSubtask(idSubtask);

        assertNotNull(subtask, "Задача не найдена!");
        assertEquals(subtask, subtaskTest, "Задачи не совпадают!");

        List<Subtask> listSubtask = taskManager.getListSubtasksOfAnEpic(1);

        assertNotNull(listSubtask, "Список задач не найден!");
        assertEquals(1, listSubtask.size(), "Неверное количество задач.");
    }
}
