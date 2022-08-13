import java.util.ArrayList;
import java.util.List;
public class InMemoryHistoryManager implements HistoryManager {
   static List<Task> historyTask = new ArrayList<>();

    @Override
    public List<Task> getHistory() {
        return historyTask;
    }

    @Override
    public void add(Task task) {
        if (historyTask.size() == 10) {
                historyTask.remove(0);
                historyTask.add(task);
      } else {
            historyTask.add(task);
        }
    }
}
