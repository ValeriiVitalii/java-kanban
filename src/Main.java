/*
import History.HistoryManager;
import SaveCsv.FileBackedTasksManager;
import TaskManagers.Manager;
import TaskManagers.TaskManager;
import TasksClass.Epic;
import TasksClass.Subtask;
import TasksClass.Task;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
public class Main {
    public static void main(String[] args) {
        // "C:\\Users\\79650\\Desktop\\java важное\\TaskManager.csv"
        TaskManager manager = Manager.getDefault();
        HistoryManager history = Manager.getDefaultHistory();

        Epic epic = new Epic(22, "Организовать свадьбу", "Сделать много задач");
        manager.add(epic);
        Subtask subtask3 = new Subtask(2222, "Сделать визу", "Пойти куда-то", 1);
        Subtask subtask = new Subtask(2222, "Заказать торт", "Позвонить", 1);
        Subtask subtask2 = new Subtask(22, "Купить платье", "Позвонить",1 );
        manager.add(subtask);
        manager.add(subtask2);
        manager.add(subtask3);
        Epic epic2 = new Epic(22, "Уехать заграницу", "Сделать документы");
        manager.add(epic2);

        manager.getEpic(5);
        manager.getEpic(1);
        manager.getSubtask(2);
        ArrayList<Task> taskManager = history.getHistory();
        for (Task task : taskManager) {
            System.out.println(task);
        }
        System.out.println("........................");
        manager.getSubtask(3);
        manager.getSubtask(4);
        taskManager = history.getHistory();
        for (Task task : taskManager) {
            System.out.println(task);
        }
        System.out.println("........................");
        manager.getSubtask(3);
        manager.getEpic(1);
        taskManager = history.getHistory();
        for (Task task : taskManager) {
            System.out.println(task);
        }
        System.out.println("........................");

        history.remove(5);         // удалили "Уехать заграницу"

        taskManager = history.getHistory();
        for (Task task : taskManager) {
            System.out.println(task);
        }
        System.out.println("........................");

        history.remove(1);
        history.remove(2);
        history.remove(3);
        history.remove(4);          // удалили все задачи

        taskManager = history.getHistory();
        for (Task task : taskManager) {
            System.out.println(task);
        }
        System.out.println("........................");

    }


        }*/






