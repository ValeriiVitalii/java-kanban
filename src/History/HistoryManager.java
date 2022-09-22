package History;
import TasksClass.Task;

import java.util.ArrayList;
import java.util.List;
public interface HistoryManager {
    ArrayList getHistory();
    void addTask(Task task);
    void remove(int id);
    void removeNode(Node<Task> node);
}
