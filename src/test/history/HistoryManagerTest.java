package history;

import taskManagers.InMemoryTaskManager;
import tasksClass.Epic;
import tasksClass.Subtask;
import tasksClass.Task;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HistoryManagerTest {

    @Test
    public void addAndGetHistoryAndRemoveTest() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Task taskTest = new Task(1,"ТаскТест №1", "ТаскПроверка 1",
                "2021-11-22T16:22:10", 44);
        Task taskTest2= new Task(2,"ТаскТест №2", "ТаскПроверка 2",
                "2021-11-23T16:22:10", 44);
        Task taskTest3 = new Task(3,"ТаскТест №3", "ТаскПроверка 3",
                "2021-11-24T16:22:10", 44);
        Task taskTest4 = new Task(4,"ТаскТест №4", "ТаскПроверка 4",
                "2021-11-25T16:22:10", 44);
        Epic epicTest = new Epic(5,"ЭпикТест №1", "ЭпикПроверка 1",
                "2021-11-26T16:22:10", 44);
        Subtask subtaskTest = new Subtask(6,"СубтаскТест №1", "СубтаскПроверка 1", 5,
                "2021-11-27T16:22:10", 44);
        taskManager.add(taskTest);
        taskManager.add(taskTest2);
        taskManager.add(taskTest3);
        taskManager.add(taskTest4);
        taskManager.add(epicTest);
        taskManager.add(subtaskTest);
        taskManager.getTask(1);
        taskManager.getTask(2);
        taskManager.getTask(3);
        taskManager.getTask(4);
        taskManager.getEpic(5);
        taskManager.getSubtask(6);

        ArrayList<Task> listTask = historyManager.getHistory();
        assertEquals(listTask.get(0).getName(), "ТаскТест №1", "Имена Тасков не совпадают!");
        assertEquals(listTask.get(4).getName(), "ЭпикТест №1", "Имена Эпиков не совпадают!");
        assertEquals(listTask.get(5).getName(), "СубтаскТест №1", "Имена Субтасков не совпадают!");
        assertEquals(listTask.get(0).getStartTimeString(),
                "2021-11-22T16:22:10", "СтартТайм Таска не совпадает!");
        assertEquals(listTask.get(4).getStartTimeString(),
                "2021-11-26T16:22:10", "СтартТайм Эпика не совпадает!");
        assertEquals(listTask.get(5).getStartTimeString(),
                "2021-11-27T16:22:10", "СтартТайм Субтаска не совпадает!");

        taskManager.getTask(1);
        taskManager.getEpic(5);
        taskManager.getSubtask(6);
        listTask = historyManager.getHistory();

        assertEquals(listTask.size(), 6, "Задачи ошибочно добавлена в историю повторно!");

        historyManager.remove(1);
        listTask = historyManager.getHistory();

        assertEquals(listTask.size(), 5, "Задачи не удалена!");

        historyManager.remove(3);
        listTask = historyManager.getHistory();

        assertEquals(listTask.size(), 4, "Задачи не удалена!");

        historyManager.remove(6);
        listTask = historyManager.getHistory();

        assertEquals(listTask.size(), 3, "Задачи не удалена!");
    }
}
