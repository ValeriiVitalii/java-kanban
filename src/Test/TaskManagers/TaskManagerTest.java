package TaskManagers;

import TasksClass.Epic;
import TasksClass.Subtask;
import TasksClass.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class TaskManagerTest<T extends TaskManager> {

    public T taskManager;
    public abstract T createManager();

    @Test
    public abstract void addTaskAndGetTaskByIdTest();
    @Test
    public abstract void addEpicAndGetEpicByIdTest();
    @Test
    public abstract void addSubtaskAndGetSubtaskIdTest();
    @BeforeEach
    void getManager() {
        taskManager = createManager();
    }

    @Test
    public void getAllTasksEpicsSubtasksTest() {
        Task taskTest = new Task(1,"ТаскТест №1", "ТаскПроверка 1",
                "2021-11-22T16:22:10", 44);
        Epic epicTest = new Epic(2,"ЭпикТест №1", "ЭпикПроверка 1",
                "2021-11-22T16:22:10", 44);
        Subtask subtaskTest = new Subtask(3,"СубтаскТест №1", "СубтаскПроверка 1", 2,
                "2021-11-22T16:22:10", 44);
        taskManager.add(taskTest);
        taskManager.add(epicTest);
        taskManager.add(subtaskTest);

        ArrayList<Subtask> arrayList = taskManager.getListSubtasksOfAnEpic(2);
        String name = arrayList.get(0).getName();

        assertEquals(1, taskManager.getListTask().size(), "Неверное количество задач.");
        assertEquals(1, taskManager.getListEpic().size(), "Неверное количество эпиков.");
        assertEquals(1, taskManager.getListSubtask().size(), "Неверное количество подзадач.");
        assertEquals("СубтаскТест №1", name, "Подзадачи не находит по эпику");
        assertEquals(taskTest, taskManager.getTask(1), "Задачи не совпадают!");
        assertEquals(epicTest, taskManager.getEpic(2), "Эпики не совпадают!");
        assertEquals(subtaskTest, taskManager.getSubtask(3), "Подзадачи не совпадают!");
    }

    @Test
    public void removeListTaskAndListEpicAndSubtaskTest() {
        taskManager.add(new Task(1,"ТаскТест №1", "ТаскПроверка 1",
                "2021-11-22T16:22:10", 44));
        taskManager.add(new Task(2,"ТаскТест №2", "ТаскПроверка 2",
                "2021-11-22T16:22:10", 44));
        taskManager.add(new Epic(3,"ЭпикТест №1", "ЭпикПроверка 1",
                "2021-11-22T16:22:10", 44));
        taskManager.add(new Epic(4,"ЭпикТест №2", "ЭпикПроверка 2",
                "2021-11-22T16:22:10", 44));
        taskManager.add(new Subtask(5,"СубтаскТест №1", "СубтаскПроверка 1", 3,
                "2021-11-22T16:22:10", 44));
        taskManager.add(new Subtask(6,"СубтаскТест №2", "СубтаскПроверка 2", 4,
                "2021-11-22T16:22:10", 44));
        taskManager.removeListTask();
        taskManager.removeListEpic();
        taskManager.removeListSubtask();
        assertTrue(taskManager.getListTask().isEmpty(), "Лист с Тасками не удален!");
        assertTrue(taskManager.getListEpic().isEmpty(), "Лист с Эпиками не удален!");
        assertTrue(taskManager.getListSubtask().isEmpty(), "Лист с Подзадачами не удален!");
    }

    @Test
    public void updateTaskAndEpicAndSubtaskTest() {
        Task task =  new Task(1,"ТаскТест №1", "ТаскПроверка 1",
                "2021-11-22T16:22:10", 44);
        Epic epic = new Epic(2,"ЭпикТест №1", "ЭпикПроверка 1",
                "2021-11-22T16:22:10", 44);
        Subtask subtask = new Subtask(3,"СубтаскТест №1", "СубтаскПроверка 1", 2,
                "2021-11-22T16:22:10", 44);
        taskManager.add(task);
        taskManager.add(epic);
        taskManager.add(subtask);
        Task taskUpdate =  new Task(1,"ТаскАпдейт №1", "ТаскАпдейт 1",
                "2021-11-22T16:22:10", 44);
        taskManager.udpate(taskUpdate);
        Epic epicUpdate = new Epic(2,"ЭпикАпдейт №1", "ЭпикАпдейт 1",
                "2021-11-22T16:22:10", 44);
        taskManager.udpate(epicUpdate);
        Subtask subtaskUpdate  = new Subtask(3,"СубтаскАпдейт №1", "СубтаскАпдейт 1", 2,
                "2021-11-22T16:22:10", 44);
        taskManager.udpate(subtaskUpdate);

        assertEquals("ТаскАпдейт №1", taskManager.getTask(1).getName(), "Имена Таксов разные!");
        assertEquals("ЭпикАпдейт №1", taskManager.getEpic(2).getName(), "Имена Эпиков разные!");
        assertEquals("СубтаскАпдейт №1",
                taskManager.getSubtask(3).getName(), "Имена Подзадач разные!");
    }

    @Test
    public void prioritizedTasksTest() {
        Task taskTest = new Task(1,"ТаскАпдейт №1", "ТаскАпдейт 1",
                "2021-11-20T10:10:10", 100);
        Epic epicTest = new Epic(2,"ЭпикТест №1", "ЭпикПроверка 1",
                "2021-11-21T11:10:10", 120);
        Subtask subtaskTest = new Subtask(3, "СубтаскТест №1", "СубтаскПроверка 1", 2,
                "2021-11-22T16:22:10", 44);
        Subtask subtaskTest2 = new Subtask(4, "СубтаскТест №2", "СубтаскПроверка 2", 2,
                "2021-11-23T10:10:10", 150);
        taskManager.add(taskTest);
        taskManager.add(epicTest);
        taskManager.add(subtaskTest);
        taskManager.add(subtaskTest2);
        ArrayList<Task> prioritizedTasksTest = new ArrayList<>();
        prioritizedTasksTest.add(taskTest);
        prioritizedTasksTest.add(epicTest);
        prioritizedTasksTest.add(subtaskTest);
        prioritizedTasksTest.add(subtaskTest2);
        Set<Task> setprioritizedTasksTest = taskManager.getPrioritizedTasks();

        assertEquals(4, setprioritizedTasksTest.size(), "Неправильный размер списка задач!");
        int i = 0;
        for (Task task : setprioritizedTasksTest) {
            assertEquals(task, prioritizedTasksTest.get(i++));
        }
    }

    public void endTimeTest() {
        Task taskTest = new Task(1,"ТаскАпдейт №1", "ТаскАпдейт 1",
                "2021-11-20T10:10:10", 100);
        Epic epicTest = new Epic(2,"ЭпикТест №1", "ЭпикПроверка 1",
                "2021-11-22T16:22:10", 44);
        Subtask subtaskTest = new Subtask(3, "СубтаскТест №1", "СубтаскПроверка 1", 2,
                "2021-11-22T10:22:10", 10);
        Subtask subtaskTest2 = new Subtask(3, "СубтаскТест №2", "СубтаскПроверка 2", 2,
                "2021-11-22T16:22:10", 10);
        int idTask = taskManager.add(taskTest);
        int idEpic = taskManager.add(epicTest);
        int idSubtask = taskManager.add(subtaskTest);
        int idSubtask2 = taskManager.add(subtaskTest2);

        assertEquals(taskManager.getEndTime(idTask),
                "2021-11-20T10:10:10", "Время конца Таска не совпадает!");
        assertEquals(taskManager.getEndTime(idEpic),
                "2021-11-22T10:22:10", "Время конца Сабтаска не совпадает!");
        assertEquals(taskManager.getEndTime(idSubtask),
                "2021-11-22T16:22:10", "Время конца Эпика не совпадает!");
    }


}
