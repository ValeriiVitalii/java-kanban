
import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
public class Main {
    public static void main(String[] args) {
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
}
