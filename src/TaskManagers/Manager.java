package TaskManagers;

import History.HistoryManager;
import History.InMemoryHistoryManager;
import TaskManagers.InMemoryTaskManager;
import TaskManagers.TaskManager;

public interface Manager {

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }


}

