package taskManagers;

import com.google.gson.Gson;
import history.HistoryManager;
import history.InMemoryHistoryManager;
import api.HttpTaskManager;
import saveCsv.FileBackedTasksManager;

public interface Manager {

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static FileBackedTasksManager getDefaultHistoryFileBackedTasks() {
        return new FileBackedTasksManager();
    }

    public static HttpTaskManager getDefaultHttpTaskManager(String path, boolean load) {
        return new HttpTaskManager(path, load);
    }

    public static Gson getGson() {
        Gson gson = new Gson();
        return gson;
    }
}

