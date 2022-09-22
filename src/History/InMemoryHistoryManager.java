package History;
import TasksClass.Task;
import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryHistoryManager implements HistoryManager {
   static ArrayList<Task> history = new ArrayList<>();
   static HashMap<Integer, Node<Task>> taskNode = new HashMap<>();
   static CustomLinkedList<Task> customLinkedList = new CustomLinkedList<>();

    @Override
    public void addTask(Task task) {
        int id = task.getId();
        if (taskNode.containsKey(id)) {
            Node<Task> node = taskNode.get(id);
            node.getPrev().setNext(node.getNext());
            node.getNext().setPrev(node.getPrev());
            removeNode(node);
            taskNode.put(id, customLinkedList.linkLast(task));
        }
        if (taskNode.isEmpty()) {
            Node<Task> nn = customLinkedList.linkFirst(task);
            taskNode.put(id, nn);
        }
        else {
            Node<Task> nn = customLinkedList.linkLast(task);
            taskNode.put(id, nn);
        }
    }
    @Override
    public ArrayList<Task> getHistory() {
        history.clear();
        Node<Task> tail = customLinkedList.getTail();
        for (Node<Task> node : taskNode.values()) {
            history.add(node.getItem());
        }
        return history;
    }

    @Override
    public void remove(int id) {
        taskNode.remove(id);
        }

    @Override
    public void removeNode(Node<Task> node) {
        taskNode.remove(node);
    }

}
