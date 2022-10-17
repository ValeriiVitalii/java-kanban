package SaveCsv;

import TaskManagers.TaskManagerTest;
import TasksClass.Epic;
import TasksClass.Subtask;
import TasksClass.Task;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static SaveCsv.FileBackedTasksManager.loadFromFile;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {

    @Override
    public FileBackedTasksManager createManager() {
        return new FileBackedTasksManager();
    }

    @Override
    @Test
    public void addTaskAndGetTaskByIdTest() {
        Task taskTest = new Task(1,"ТаскТест №1", "ТаскПроверка 1",
                "2021-11-22T16:22:10", 44);
        FileBackedTasksManager taskManager = createManager();
        int idTask = taskManager.add(taskTest);
        Task task = taskManager.getTask(idTask);

        assertNotNull(task, "Задача не найдена!");
        assertEquals(task, taskTest, "Задачи не совпадают!");

        List<Task> listTask = taskManager.getListTask();

        assertNotNull(listTask, "Список задач не найден!");
        assertEquals(1, listTask.size(), "Неверное количество задач.");
    }

    @Override
    @Test
    public void addEpicAndGetEpicByIdTest() {
    }

    @Override
    @Test
    public void addSubtaskAndGetSubtaskIdTest() {
    }

    @Test
    public void loadFromFileTest() throws IOException {
        try {
            FileBackedTasksManager taskManager = createManager();
            Task taskTest = new Task(1,"ТаскТест №1", "ТаскПроверка 1",
                    "2021-11-22T16:22:10", 44);
            Epic epicTest = new Epic(2,"ЭпикТест №1", "ЭпикПроверка 1",
                    "2021-11-22T16:22:10", 44);
            Subtask subtaskTest = new Subtask(3, "СубтаскТест №1", "СубтаскПроверка 1", 2,
                    "2021-11-22T16:22:10", 44);
            int idTask = taskManager.add(taskTest);
            int idEpic = taskManager.add(epicTest);
            int idSubtask = taskManager.add(subtaskTest);
            taskManager.getTaskOrEpicOrSubtask(idTask);
            taskManager.getTaskOrEpicOrSubtask(idEpic);
            taskManager.getTaskOrEpicOrSubtask(idSubtask);
            FileBackedTasksManager tasksManagerLoad = loadFromFile();
            Task task = tasksManagerLoad.getTask(idTask);
            Epic epic = tasksManagerLoad.getEpic(idEpic);
            Subtask subtask = tasksManagerLoad.getSubtask(idSubtask);

            assertEquals(task.getName(), "ТаскТест №1", "Имена Тасков не совпадают!");
            assertEquals(epic.getName(), "ЭпикТест №1", "Имена Эпиков не совпадают!");
            assertEquals(subtask.getName(), "СубтаскТест №1", "Имена Субтасков не совпадают!");
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка данных");
        }
    }
}
