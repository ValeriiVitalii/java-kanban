
import java.util.Scanner;
import java.util.ArrayList;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Manager manager = new Manager();


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


        manager.setStatusSubtask(3, "Над задачей ведётся работа.");  //Сменили статус подзадачи
        System.out.println(arrayListEpic.get(1).toString());     //Статус Эпика поменялся
        System.out.println("\n");

        for (Subtask sub : arrayListSubtask) {
            System.out.println(sub.toString());    //Статус подзадачи поменялся
        } System.out.println("\n");

        manager.deleteSpecificEpic(1);
        System.out.println(manager.getSpecificEpic(1)); //Удалили Эпик и проверили
        manager.deleteSpecificSubtask(1);
        System.out.println(manager.getSpecificSubtask(1)); //Удалили Подзадачу и проверили

    }

}
