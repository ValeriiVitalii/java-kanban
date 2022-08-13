import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {
    private int idTask = 1;
    private int idEpic = 1;
    private int idSubtask = 1;

    private final HashMap<Integer, Task> task = new HashMap<>();
    private final HashMap<Integer, Epic> epic = new HashMap<>();
    private final HashMap<Integer, Subtask> subtask = new HashMap<>();

    HistoryManager historyManager = Manager.getDefaultHistory();

    @Override
    public void add(Task task) {
        task.setId(idTask);
        this.task.put(idTask++, task);
    }

    @Override
    public void add(Epic epic) {
        epic.setId(idEpic);
        this.epic.put(idEpic++, epic);
    }

    @Override
    public void add(Subtask subtask) {
        subtask.setId(idSubtask);
        subtask.setIdEpic(idEpic - 1);
        this.subtask.put(idSubtask++, subtask);
        updateStatusEpic(idEpic - 1);
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
    public ArrayList getArraySubtasksOfAnEpic(int idEpic) {
        ArrayList<Subtask> arraySubtask = new ArrayList<>();

        for (Subtask sub : subtask.values()) {
            if (sub.getIdEpic() == idEpic) {
                sub.toString();
                arraySubtask.add(sub);
            }
        }
        return arraySubtask;
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
    public Task getTask(Integer idTask) {
        historyManager.add(task.get(idTask));
        return task.get(idTask);
    }

    @Override
    public Epic getEpic(Integer idEpic) {
        historyManager.add(epic.get(idEpic));
        return epic.get(idEpic);
    }

    @Override
    public Subtask getSubtask(Integer idSubtask) {
        historyManager.add(subtask.get(idSubtask));
        return subtask.get(idSubtask);
    }

    @Override
    public void deleteSpecificTask(Integer idTask) {
        task.remove(idTask);
    }

    @Override
    public void deleteSpecificEpic(Integer idEpic) {
        epic.remove(idEpic);
    }

    @Override
    public void deleteSpecificSubtask(Integer idSubtask) {
        subtask.remove(idSubtask);
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
    public void setStatusTask(int idTask, TaskStatus status) {
        task.get(idTask).setStatus(status);
    }

    @Override
    public void setStatusEpic(int idEpic, TaskStatus status) {
        epic.get(idEpic).setStatus(status);
    }

    @Override
    public void setStatusSubtask(int idSubtask, TaskStatus status) {
        subtask.get(idSubtask).setStatus(status);
        updateStatusEpic(subtask.get(idSubtask).getIdEpic());
    }

    @Override
    public void updateStatusEpic(int idEpic) {
        ArrayList<Subtask> subtasks = getArraySubtasksOfAnEpic(idEpic);
        int doneSubtask = 0;

        for (Subtask subtask : subtasks) {
            if (subtask.getStatus().equals(TaskStatus.IN_PROGRESS)) {
                epic.get(idEpic).setStatus(TaskStatus.IN_PROGRESS);
            } if (subtask.getStatus().equals(TaskStatus.DONE)) {
                doneSubtask++;
            } if (doneSubtask == subtasks.size()) {
                epic.get(idEpic).setStatus(TaskStatus.DONE);
            }
        }
    }
}
