
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskManager manager = Manager.getDefault();


        Epic epic = new Epic(22, "Организовать свадьбу", "Сделать много задач");
        manager.add(epic);

        Subtask subtask = new Subtask(2222, "Заказать торт", "Позвонить");
        Subtask subtask2 = new Subtask(22, "Купить платье", "Позвонить");
        manager.add(subtask);
        manager.add(subtask2);

        Epic epic2 = new Epic(22, "Уехать за границу", "Сделать документы");
        manager.add(epic2);

        Subtask subtask3 = new Subtask(2222, "Сделать визу", "Пойти куда-то");
        manager.add(subtask3);
        // Создали эпики и подзадачи и добавили их в менеджер


        ArrayList<Epic> arrayListEpic = manager.getArrayEpic(); //Распечатываем Эпики
        for (Epic ep : arrayListEpic) {
            System.out.println(ep.toString());
        }  System.out.println("\n");


        ArrayList<Subtask> arrayListSubtask = manager.getArraySubtask(); //Распечатываем Подзадачи
        for (Subtask sub : arrayListSubtask) {
            System.out.println(sub.toString());
        }  System.out.println("\n");


        manager.setStatusSubtask(3, TaskStatus.IN_PROGRESS);  //Сменили статус подзадачи
        System.out.println(arrayListEpic.get(1).toString());     //Статус Эпика поменялся
        System.out.println("\n");

        for (Subtask sub : arrayListSubtask) {
            System.out.println(sub.toString());    //Статус подзадачи поменялся
        } System.out.println("\n");

        HistoryManager historyManager = Manager.getDefaultHistory();

        manager.getSubtask(2);   //"Купить платье" - самая первая задача
        manager.getEpic(1);
        manager.getEpic(2);
        manager.getEpic(2);
        manager.getEpic(1);
        manager.getSubtask(1);
        manager.getSubtask(1);
        manager.getSubtask(3);
        manager.getSubtask(3);
        manager.getEpic(1);

        List<Task> historyTask = historyManager.getHistory();
        for (Task task : historyTask) {
                System.out.println(task);
            }
        System.out.println("\n");

        manager.getEpic(2);
        historyTask = historyManager.getHistory();
        for (Task task : historyTask) {
            System.out.println(task);
        }                               //Купить платье больше нету
    }
}
