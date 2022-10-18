package taskManagers;

import history.HistoryManager;
import history.InMemoryHistoryManager;

public interface Manager {

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }


}

