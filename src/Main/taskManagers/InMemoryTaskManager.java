package taskManagers;

import history.HistoryManager;
import tasksClass.Epic;
import tasksClass.Subtask;
import tasksClass.Task;
import tasksClass.TaskStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

public class InMemoryTaskManager implements TaskManager {
    private int id = 1;
    private HashMap<Integer, Task> task = new HashMap<>();
    private HashMap<Integer, Epic> epic = new HashMap<>();
    private HashMap<Integer, Subtask> subtask = new HashMap<>();

    protected Set<Task> prioritizedTasks = new TreeSet<>((o1, o2) -> {
        if (o1.getStartTimeLocal() == null && o2.getStartTimeLocal() == null) return o1.getId() - o2.getId();
        if (o1.getStartTimeLocal() == null) return 1;
        if (o2.getStartTimeLocal() == null) return -1;
        if (o1.getStartTimeLocal().isAfter(o2.getStartTimeLocal())) return 1;
        if (o1.getStartTimeLocal().isBefore(o2.getStartTimeLocal())) return -1;
        if (o1.getStartTimeLocal().isEqual(o2.getStartTimeLocal())) return o1.getId() - o2.getId();
        return 0;
    });

    HistoryManager historyManager = Manager.getDefaultHistory();

    @Override
    public int add(Task task) {
        task.setId(id);
        this.task.put(id, task);
        prioritizedTasks.add(task);
        return id++;
    }

    @Override
    public int add(Epic epic) {
        epic.setId(id);
        this.epic.put(id, epic);
        prioritizedTasks.add(epic);
        return id++;
    }

    @Override
    public int add(Subtask subtask) {
        subtask.setId(id);
        epic.get(subtask.getIdEpic()).setSubtask(subtask);
        this.subtask.put(id, subtask);
        updateStatusEpic(subtask.getIdEpic());
        prioritizedTasks.add(subtask);
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
        prioritizedTasks.remove(task.get(id));
        task.remove(id);
    }

    @Override
    public void removeSpecificEpic(Integer idEpic) {
        prioritizedTasks.remove(epic.get(idEpic));
        epic.remove(idEpic);
    }

    @Override
    public void removeSpecificSubtask(Integer idSubtask) {
        prioritizedTasks.remove(subtask.get(idSubtask));
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
    public String getEndTime(int id) {
        if (task.containsKey(id)) {
           return task.get(id).getEndTime();
        }
        if (epic.containsKey(id)) {
            return epic.get(id).getEndTime();
        }
        if (subtask.containsKey(id)) {
            return subtask.get(id).getEndTime();
        } else {
            return "Задача не найдена!";
        }
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

    @Override
    public Set<Task> getPrioritizedTasks() {
        intersectionCheck();
        return prioritizedTasks;
    }

    @Override
    public void intersectionCheck() {
        LocalDateTime checkTime = null;
        boolean flagCheckTimeIsEmpty = true;
        for (Task task : prioritizedTasks) {
            if (flagCheckTimeIsEmpty) {
                checkTime = task.getEndLocalDateTime();
                flagCheckTimeIsEmpty = false;
            } else if (task.getStartTimeLocal() != null) {
                if (task.getStartTimeLocal().isBefore(checkTime)) {
                    System.out.println("Найдено пересечение времени задач, проверьте корректность данных");
                    return;
                }
                if (task.getStartTimeLocal().isAfter(checkTime) || task.getStartTimeLocal().isEqual(checkTime)) {
                    checkTime = task.getEndLocalDateTime();
                }
            }
        }
    }
}
