package api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import history.HistoryManager;
import saveCsv.FileBackedTasksManager;
import taskManagers.Manager;
import tasksClass.Epic;
import tasksClass.Subtask;
import tasksClass.Task;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class HttpTaskManager extends FileBackedTasksManager {

    KVTaskClient kvTaskClient;
    Gson gson;
    FileBackedTasksManager fileTasksManager = new FileBackedTasksManager();

    HistoryManager historyManager = Manager.getDefaultHistory();

    public HttpTaskManager(String path, boolean load) {
        gson = Manager.getGson();
        kvTaskClient = new KVTaskClient(path);
        if (load) {
            getToken();
            loadTasks();
        }
    }

    public void getToken() {
        kvTaskClient.register();
    }

    @Override
    public void save() {
        if (kvTaskClient == null) {
            System.out.println("Требуется регистрация");
            return;
        }
        kvTaskClient.put("/tasks", gson.toJson(getMapTask().values()));
        kvTaskClient.put("/epics", gson.toJson(getMapEpic().values()));
        kvTaskClient.put("/subtasks", gson.toJson(getMapSubtask().values()));
        kvTaskClient.put("/history", gson.toJson(historyToString()));
    }

    private void loadTasks() {
        String json = kvTaskClient.load("/tasks");
        if (!json.equals("Ошибка получения запроса")) {
            Type type = new TypeToken<ArrayList<Task>>(){}.getType();
            ArrayList<Task> tasksList = gson.fromJson(json, type);
            for (Task task : tasksList) {
                add(task);
            }
        }

        json = kvTaskClient.load("/epics");
        if (!json.equals("Ошибка получения запроса")) {
            Type type = new TypeToken<ArrayList<Epic>>(){}.getType();
            ArrayList<Epic> epicsList = gson.fromJson(json, type);
            for (Epic epic : epicsList) {
                add(epic);
            }
        }


        json = kvTaskClient.load("/subtasks");
        if (!json.equals("Ошибка получения запроса")) {
            Type type = new TypeToken<ArrayList<Subtask>>(){}.getType();
            ArrayList<Subtask> subtasksList = gson.fromJson(json, type);
            for (Subtask subtask : subtasksList) {
                add(subtask);
            }
        }

        if (!json.equals("Ошибка получения запроса")) {
            json = kvTaskClient.load("/history");
            String historyLine = json.substring(1, json.length() - 1);
            if (!historyLine.equals("")) {
                String[] historyLineContents = historyLine.split(",");
                for (String s : historyLineContents) {
                    if (task.get(Integer.parseInt(s)) != null) {
                        historyManager.addTask(task.get(Integer.parseInt(s)));
                    } if (epic.get(Integer.parseInt(s)) != null) {
                        historyManager.addTask(epic.get(Integer.parseInt(s)));
                    } if (subtask.get(Integer.parseInt(s)) != null) {
                        historyManager.addTask(subtask.get(Integer.parseInt(s)));
                    }


                }
            }
        }
    }

    @Override
    public int add(Task task) {
        task.setId(task.getId());
        prioritizedTasks.add(task);
        tasks.put(task.getId(), task);
        return task.getId();
    }
    @Override
    public int add(Epic epic) {
        epic.setId(epic.getId());
        prioritizedTasks.add(epic);
        epics.put(epic.getId(), epic);
        return epic.getId();
    }
    @Override
    public int add(Subtask subtask) {
        subtask.setId(subtask.getId());
        prioritizedTasks.add(subtask);
        subtasks.put(subtask.getId(), subtask);
        return subtask.getId();
    }
}
