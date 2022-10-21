package taskManagers;

import history.HistoryManager;
import history.InMemoryHistoryManager;
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

}

