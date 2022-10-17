package TaskManagers;

import History.HistoryManager;
import History.InMemoryHistoryManager;

public interface Manager {

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }


}

