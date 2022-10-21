package saveCsv;

import history.HistoryManager;
import taskManagers.InMemoryTaskManager;
import taskManagers.Manager;
import tasksClass.Epic;
import tasksClass.Subtask;
import tasksClass.Task;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {

   public static void main(String[] args) throws IOException {
        FileBackedTasksManager manager = new FileBackedTasksManager();
        Task task = new Task(1, "Умыться", "Почистить зубы",
                "2021-11-22T16:22:10", 44);
        Task task2 = new Task(2, "Valera2", "Vitalii2",
                "2021-11-22T16:22:10", 44);
        Epic epic1 = new Epic(3, "Epic3", "Epic3",
                "2021-11-22T16:22:10", 44);
        Subtask subtask1 = new Subtask(4, "Eldar4", "Eldar4", 3,
                "2021-11-22T16:22:10", 44);
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

    protected HashMap<Integer, Task> task = new HashMap<>();
    protected HashMap<Integer, Epic> epic = new HashMap<>();
    protected HashMap<Integer, Subtask> subtask = new HashMap<>();
    protected HashMap<Integer, Task> allTasks = new HashMap<>();
    static HistoryManager historyManager = Manager.getDefaultHistory();

    private static final String PATH = "C:\\Users\\79650\\Desktop\\java важное\\TaskManager.csv";

    public void save() throws IOException {
        try (Writer fileWriter = new FileWriter(PATH))  {
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
        return task.getId() + "," + task.getType() + "," + task.getName() + ","
                   + task.getStatus() + "," + task.getDescription() + "," + task.getIdEpic() + "," +
                task.getStartTimeString() + "," + task.getDuration() + "," + "\n";
    }

    public static String historyToString(HistoryManager manager)  {
        String id = "";
        ArrayList<Task> tasks = manager.getHistory();
        for (Task task : tasks) {
            id = id + String.valueOf(task.getId()) + ",";
        }
        return id;
    }

    public static String historyToString()  {
        String id = "";
        ArrayList<Task> tasks = historyManager.getHistory();
        for (Task task : tasks) {
            id = id + String.valueOf(task.getId()) + ",";
        }
        return id;
    }

    public static String[] fromString(String value)  {
        String[] taskString = value.split(",");
        return taskString;
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
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();
        try {
            String[] lines = Files.readString(Path.of(PATH)).split("\n");
            for (int i = 0; i < lines.length; i++) {
                String line = lines[i];

                if (line.contains("Task") || line.contains("Epic") || line.contains("Subtask")) {
                    String[] lineTask = fromString(line);
                    if (lineTask[1].contains("Task")) {
                        fileBackedTasksManager.add(new Task(Integer.valueOf(lineTask[0]), lineTask[2], lineTask[4],
                                lineTask[6], Long.valueOf(lineTask[7])));
                    }
                    if (lineTask[1].contains("Epic")) {
                        fileBackedTasksManager.add(new Epic(Integer.valueOf(lineTask[0]), lineTask[2], lineTask[4],
                                lineTask[6], Long.valueOf(lineTask[7])));
                    }
                    if (lineTask[1].contains("Subtask")) {
                        fileBackedTasksManager.add(new Subtask(Integer.valueOf(lineTask[0]), lineTask[2],
                                lineTask[4], Integer.valueOf(lineTask[5]),
                                lineTask[6], Long.valueOf(lineTask[7])));
                    }
                }
                    if (i != 0 && line.length() > 0 && lines[i - 1].contains("") &&
                            !(line.contains("Task")) && !(line.contains("Epic")) && !(line.contains("Subtask"))) {
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
            allTasks.put(task.getId(), task);
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
            allTasks.put(epic.getId(), epic);
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
            allTasks.put(subtask.getId(), subtask);
            save();
            return subtask.getId();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeSpecificSubtask(Integer idSubtask) {
        try {
            super.removeSpecificSubtask(idSubtask);
            subtasks.remove(idSubtask);
            save();
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

    public HashMap<Integer, Task> getAllTasks() {
        return allTasks;
    }
    public ArrayList<Task> getHistory() {
        return historyManager.getHistory();
    }

}

