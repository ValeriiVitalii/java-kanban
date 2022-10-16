package TaskManagers;

import History.HistoryManager;
import TasksClass.Epic;
import TasksClass.Subtask;
import TasksClass.Task;
import TasksClass.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {
    private int id = 1;
    private static final HashMap<Integer, Task> task = new HashMap<>();
    private static final HashMap<Integer, Epic> epic = new HashMap<>();
    private static final HashMap<Integer, Subtask> subtask = new HashMap<>();

    HistoryManager historyManager = Manager.getDefaultHistory();

    @Override
    public int add(Task task) {
        task.setId(id);
        this.task.put(id, task);
        return id++;
    }

    @Override
    public int add(Epic epic) {
        epic.setId(id);
        this.epic.put(id, epic);
        return id++;
    }

    @Override
    public int add(Subtask subtask) {
        subtask.setId(id);
        epic.get(subtask.getIdEpic()).setSubtask(subtask);
        this.subtask.put(id, subtask);
        updateStatusEpic(subtask.getIdEpic());
        return id++;
        }


    @Override
    public ArrayList<Task> getListTask() {
        ArrayList<Task> arrayTask = new ArrayList<>();

        for (Task t : task.values()) {
            arrayTask.add(t);
        }
        return arrayTask;
    }

    @Override
    public ArrayList<Epic> getListEpic() {
        ArrayList<Epic> arrayEpic = new ArrayList<>();

        for (Epic e : epic.values()) {
            arrayEpic.add(e);
        }
        return arrayEpic;
    }

    @Override
    public ArrayList<Subtask> getListSubtask() {
        ArrayList<Subtask> arraySubtask = new ArrayList<>();

        for (Subtask sub : subtask.values()) {
            arraySubtask.add(sub);
        }
        return arraySubtask;
    }

    @Override
    public ArrayList<Subtask> getListSubtasksOfAnEpic(int idEpic) {
        return epic.get(idEpic).getSubtask();
    }

    @Override
    public void removeListTask() {
        task.clear();
    }

    @Override
    public void removeListEpic() {
        epic.clear();
    }

    @Override
    public void removeListSubtask() {
        subtask.clear();
    }

    @Override
    public Task getTask(Integer id) {
        historyManager.addTask(task.get(id));
        return task.get(id);
    }

    @Override
    public Epic getEpic(Integer id) {
        historyManager.addTask(epic.get(id));
        return epic.get(id);
    }

    @Override
    public Subtask getSubtask(Integer id) {
        historyManager.addTask(subtask.get(id));
        return subtask.get(id);
    }

    @Override
    public void removeSpecificTask(Integer id) {
        task.remove(id);
    }

    @Override
    public void removeSpecificEpic(Integer idEpic) {
        epic.remove(idEpic);
    }

    @Override
    public void removeSpecificSubtask(Integer idSubtask) {
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
        epic.get(subtask.getIdEpic()).setSubtask(subtask);
        this.subtask.put(subtask.getId(), subtask);
        updateStatusEpic(subtask.getIdEpic());
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
        ArrayList<Subtask> subtasks = getListSubtasksOfAnEpic(idEpic);
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
