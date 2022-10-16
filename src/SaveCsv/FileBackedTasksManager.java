package SaveCsv;

import TaskManagers.InMemoryTaskManager;
import History.HistoryManager;
import TaskManagers.Manager;
import TasksClass.Epic;
import TasksClass.Subtask;
import TasksClass.Task;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {

   public static void main(String[] args) throws IOException {
        FileBackedTasksManager manager = new FileBackedTasksManager(
                ("C:\\Users\\79650\\Desktop\\java важное\\TaskManager.csv"));
        Task task = new Task(1, "Сходить в туалет", "Жестко покакать");
        Task task2 = new Task(2, "Valera2", "Vitalii2");
        Epic epic1 = new Epic(3, "Epic3", "Epic3");
        Subtask subtask1 = new Subtask(4, "Eldar4", "Eldar4", 3);
        manager.add(task);
        manager.add(task2);
        manager.add(epic1);
        manager.add(subtask1);

        manager.getTaskOrEpicOrSubtask(1);
        manager.getTaskOrEpicOrSubtask(2);
        manager.getTaskOrEpicOrSubtask(3);
        manager.getTaskOrEpicOrSubtask(4);

        FileBackedTasksManager manager1 = loadFromFile();
        System.out.println(manager1.task.get(1).getName());
        System.out.println(manager1.task.get(2).getName());
        System.out.println(manager1.epic.get(3).getName());
        System.out.println(manager1.subtask.get(4).getName());

        manager1.save();

    }

    public static final HashMap<Integer, Task> task = new HashMap<>();
    private static final HashMap<Integer, Epic> epic = new HashMap<>();
    private static final HashMap<Integer, Subtask> subtask = new HashMap<>();
    static HistoryManager historyManager = Manager.getDefaultHistory();
    static Path path;

    public FileBackedTasksManager(String path) {
    this.path = Paths.get(path);
    }

    public void save() throws IOException {
        try (Writer fileWriter = new FileWriter(path.toString()))  {
            for (Task task : task.values()) {
                fileWriter.write(toString(task));
            }
            for (Epic epic : epic.values()) {
                fileWriter.write(toString(epic));
            }
            for (Subtask subtask : subtask.values()) {
                fileWriter.write(toString(subtask));
            }
            String idHistory = historyToString(historyManager);
            fileWriter.write("\n" + "\n" + idHistory);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }


    public static String toString(Task task) {
        return task.getId() + "," + task.getType() + ","  + task.getName() + ","
                   + task.getStatus() + "," + task.getDescription() + "," + task.getIdEpic() + "," + "\n";
    }

    public static String historyToString(HistoryManager manager)  {
        String id = "";
        ArrayList<Task> tasks = manager.getHistory();
        for (Task task : tasks) {
            id = id + String.valueOf(task.getId()) + ",";
        }
        return id;
    }

    public static Task fromString(String value)  {
        String[] taskString = value.split(",");
        if (taskString[1].contains("Task")) {
        return new Task(Integer.valueOf(taskString[0]), taskString[2], taskString[4]);
        }
        if (taskString[1].contains("Epic")) {
            return new Epic(Integer.valueOf(taskString[0]), taskString[2], taskString[4]);
        }
        else {
            return new Subtask(Integer.valueOf(taskString[0]), taskString[2],
                    taskString[4],Integer.valueOf(taskString[5]));
        }
    }

    public static List<Integer> historyFromString(String value) {
        List<Integer> idHistory = new ArrayList<>();
        String[] idString = value.split(",");
        for (String s : idString) {
            idHistory.add(Integer.valueOf(s));
        }
            return idHistory;
    }

    public static FileBackedTasksManager loadFromFile() throws IOException  {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(path.toString());
        try {
            String[] lines = Files.readString(Path.of(path.toString()), StandardCharsets.UTF_8).split(System.lineSeparator());
            for (int i = 0; i < lines.length; i++) {
                String line = lines[i];
                    if (line.contains("Task") || line.contains("Epic") || line.contains("Subtask")) {
                        fileBackedTasksManager.add(fromString(line));
                    }
                if (i != 0 && line.length() > 0 && lines[i - 1].contains("")) {
                    List<Integer> idHistory = historyFromString(line);
                    for (Integer id : idHistory) {
                        fileBackedTasksManager.getTaskOrEpicOrSubtask(id);
                    }
                }
            }
        }       catch (IOException e) {
            throw new ManagerSaveException("Ошибка данных");
        }
        return fileBackedTasksManager;
    }

    @Override
    public int add(Task task) {
        super.add(task);
        try {
            this.task.put(task.getId(), task);
            save();
            return task.getId();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int add(Epic epic) {
        super.add(epic);
        try {
            this.epic.put(epic.getId(), epic);
            save();
            return epic.getId();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int add(Subtask subtask) {
        super.add(subtask);
        try {
            this.subtask.put(subtask.getId(), subtask);
            save();
            return subtask.getId();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void getTaskOrEpicOrSubtask(Integer id) {
        try {
           if(task.containsKey(id)) {
               super.getTask(id);
               save();
           } if(epic.containsKey(id)) {
                super.getEpic(id);
                save();
            } if(subtask.containsKey(id)) {
                super.getSubtask(id);
                save();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

