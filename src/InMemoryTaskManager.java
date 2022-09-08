import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private int id = 1;
    private final HashMap<Integer, Task> task = new HashMap<>();
    private final HashMap<Integer, Epic> epic = new HashMap<>();
    private final HashMap<Integer, Subtask> subtask = new HashMap<>();

    HistoryManager historyManager = Manager.getDefaultHistory();

    @Override
    public void add(Task task) {
        task.setId(id);
        this.task.put(id++, task);
    }

    @Override
    public void add(Epic epic) {
        epic.setId(id);
        this.epic.put(id++, epic);
    }

    @Override
    public void add(Subtask subtask) {
        subtask.setId(id);
        epic.get(subtask.getIdEpic()).setSubtask(subtask);
        this.subtask.put(id++, subtask);
        updateStatusEpic(subtask.getIdEpic());

        }


    @Override
    public ArrayList getArrayTask() {
        ArrayList<Task> arrayTask = new ArrayList<>();

        for (Task t : task.values()) {
            arrayTask.add(t);
        }
        return arrayTask;
    }

    @Override
    public ArrayList getArrayEpic() {
        ArrayList<Epic> arrayEpic = new ArrayList<>();

        for (Epic e : epic.values()) {
            arrayEpic.add(e);
        }
        return arrayEpic;
    }

    @Override
    public ArrayList getArraySubtask() {
        ArrayList<Subtask> arraySubtask = new ArrayList<>();

        for (Subtask sub : subtask.values()) {
            arraySubtask.add(sub);
        }
        return arraySubtask;
    }

    @Override
    public ArrayList<Subtask> getArraySubtasksOfAnEpic(int idEpic) {
        return epic.get(idEpic).getSubtask();
    }

    @Override
    public void deleteArrayTask() {
        task.clear();
    }

    @Override
    public void deleteArrayEpic() {
        epic.clear();
    }

    @Override
    public void deleteArraySubtask() {
        subtask.clear();
    }

    @Override
    public Task getTask(Integer id) {
        historyManager.add(task.get(id));
        return task.get(id);
    }

    @Override
    public Epic getEpic(Integer id) {
        historyManager.add(epic.get(id));
        return epic.get(id);
    }

    @Override
    public Subtask getSubtask(Integer id) {
        historyManager.add(subtask.get(id));
        return subtask.get(id);
    }

    @Override
    public void deleteSpecificTask(Integer id) {
        task.remove(id);
    }

    @Override
    public void deleteSpecificEpic(Integer idEpic) {
        epic.remove(idEpic);
    }

    @Override
    public void deleteSpecificSubtask(Integer idSubtask) {
        subtask.remove(id);
    }

    @Override
    public void udpate(Task task) {
        this.task.put(task.getId(), task);
    }

    @Override
    public void udpate(Epic epic) {
        this.epic.put(epic.getId(), epic);
    }

    @Override
    public void udpate(Subtask subtask) {
        this.subtask.put(subtask.getId(), subtask);
    }

    @Override
    public void setStatusTask(int id, TaskStatus status) {
        task.get(id).setStatus(status);
    }

    @Override
    public void setStatusEpic(int id, TaskStatus status) {
        epic.get(id).setStatus(status);
    }

    @Override
    public void setStatusSubtask(int id, TaskStatus status) {
        subtask.get(id).setStatus(status);
        updateStatusEpic(subtask.get(id).getIdEpic());
    }

    @Override
    public void updateStatusEpic(int idEpic) {
        ArrayList<Subtask> subtasks = getArraySubtasksOfAnEpic(idEpic);
        int doneSubtask = 0;

        for (Subtask subtask : subtasks) {
            if (subtask.getStatus().equals(TaskStatus.IN_PROGRESS)) {
                for (Epic ep : epic.values()) {
                    if (ep.getIdEpic() == subtask.getIdEpic()) {
                       ep.setStatus(TaskStatus.IN_PROGRESS);
                    }
                }
            } if (subtask.getStatus().equals(TaskStatus.DONE)) {
                doneSubtask++;
            } if (doneSubtask == subtasks.size()) {
                for (Epic ep : epic.values()) {
                    if (ep.getIdEpic() == subtask.getIdEpic()) {
                        ep.setStatus(TaskStatus.DONE);
                    }
                }
            }
        }
    }
}
