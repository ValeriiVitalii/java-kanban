import TaskManagers.TaskManager;
import TasksClass.Epic;
import TasksClass.Subtask;
import TasksClass.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class TaskManagerTest<T extends TaskManager> {

    public T taskManager;
    public abstract T createManager();

    public abstract void addTaskAndGetTaskByIdTest();
    public abstract void addEpicAndGetEpicByIdTest();
    public abstract void addSubtaskAndGetSubtaskIdTest();
    @BeforeEach
    void getManager() {
        taskManager = createManager();
    }

    @Test
    public void getAllTasksEpicsSubtasksTest() {
        Task taskTest = new Task(1,"ТаскТест №1", "ТаскПроверка 1");
        Epic epicTest = new Epic(1,"ЭпикТест №1", "ЭпикПроверка 1");
        Subtask subtaskTest = new Subtask(1,"СубтаскТест №1", "СубтаскПроверка 1", 1);
        taskManager.add(taskTest);
        taskManager.add(epicTest);
        taskManager.add(subtaskTest);

        assertEquals(1, taskManager.getListTask().size(), "Неверное количество задач.");
        assertEquals(1, taskManager.getListEpic().size(), "Неверное количество эпиков.");
        assertEquals(1, taskManager.getListSubtask().size(), "Неверное количество подзадач.");
        assertEquals(subtaskTest,
                taskManager.getListSubtasksOfAnEpic(1).size(), "Подзадачи не находит по эпику");
        assertEquals(taskTest, taskManager.getTask(1), "Задачи не совпадают!");
        assertEquals(epicTest, taskManager.getEpic(1), "Эпики не совпадают!");
        assertEquals(subtaskTest, taskManager.getSubtask(1), "Подзадачи не совпадают!");
    }

    @Test
    public void removeListTaskAndListEpicAndSubtaskTest() {
        taskManager.add(new Task(1,"ТаскТест №1", "ТаскПроверка 1"));
        taskManager.add(new Task(2,"ТаскТест №2", "ТаскПроверка 2"));
        taskManager.add(new Epic(1,"ЭпикТест №1", "ЭпикПроверка 1"));
        taskManager.add(new Epic(2,"ЭпикТест №2", "ЭпикПроверка 2"));
        taskManager.add(new Subtask(1,"СубтаскТест №1", "СубтаскПроверка 1", 1));
        taskManager.add(new Subtask(2,"СубтаскТест №2", "СубтаскПроверка 2", 2));
        taskManager.removeListTask();
        taskManager.removeListEpic();
        taskManager.removeListSubtask();
        assertTrue(taskManager.getListTask().isEmpty(), "Лист с Тасками не удален!");
        assertTrue(taskManager.getListEpic().isEmpty(), "Лист с Эпиками не удален!");
        assertTrue(taskManager.getListSubtask().isEmpty(), "Лист с Подзадачами не удален!");
    }

    @Test
    public void updateTaskAndEpicAndSubtask() {
        Task task =  new Task(1,"ТаскТест №1", "ТаскПроверка 1");
        Epic epic = new Epic(1,"ЭпикТест №1", "ЭпикПроверка 1");
        Subtask subtask = new Subtask(1,"СубтаскТест №1", "СубтаскПроверка 1", 1);
        taskManager.add(task);
        taskManager.add(epic);
        taskManager.add(subtask);
        Task taskUpdate =  new Task(1,"ТаскАпдейт №1", "ТаскАпдейт 1");
        taskManager.udpate(taskUpdate);
        Epic epicUpdate = new Epic(1,"ЭпикАпдейт №1", "ЭпикАпдейт 1");
        taskManager.udpate(epicUpdate);
        Subtask subtaskUpdate  = new Subtask(1,"СубтаскАпдейт №1", "СубтаскАпдейт 1", 1);
        taskManager.udpate(subtaskUpdate);

        assertEquals("ТаскАпдейт №1", taskManager.getTask(1).getName(), "Имена Таксов разные!");
        assertEquals("ЭпикАпдейт №1", taskManager.getEpic(1).getName(), "Имена Эпиков разные!");
        assertEquals("СубтаскАпдейт №1",
                taskManager.getSubtask(1).getName(), "Имена Подзадач разные!");

    }

}
