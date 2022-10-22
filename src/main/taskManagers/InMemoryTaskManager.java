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
    protected HashMap<Integer, Task> tasks = new HashMap<>();
    protected HashMap<Integer, Epic> epics = new HashMap<>();
    protected HashMap<Integer, Subtask> subtasks = new HashMap<>();

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
        this.tasks.put(id, task);
        prioritizedTasks.add(task);
        return id++;
    }

    @Override
    public int add(Epic epic) {
        epic.setId(id);
        this.epics.put(id, epic);
        prioritizedTasks.add(epic);
        return id++;
    }

    @Override
    public int add(Subtask subtask) {
        subtask.setId(id);
        epics.get(subtask.getIdEpic()).setSubtask(subtask);
        this.subtasks.put(id, subtask);
        updateStatusEpic(subtask.getIdEpic());
        prioritizedTasks.add(subtask);
        return id++;
        }


    @Override
    public HashMap<Integer, Task> getMapTask() {
        return tasks;
    }

    @Override
    public HashMap<Integer, Epic>  getMapEpic() {
        return epics;
    }

    @Override
    public HashMap<Integer, Subtask>  getMapSubtask() {
        return subtasks;
    }

    @Override
    public ArrayList<Subtask> getListSubtasksOfAnEpic(int idEpic) {
        return epics.get(idEpic).getSubtask();
    }

    @Override
    public void removeListTask() {
        if (prioritizedTasks.size() > 0) {
            prioritizedTasks.removeIf(task -> task.getType().equals("Task"));
        }
        tasks.clear();
    }

    @Override
    public void removeListEpic() {
        if (prioritizedTasks.size() > 0) {
            prioritizedTasks.removeIf(task -> task.getType().equals("Epic"));
        }
        epics.clear();
    }

    @Override
    public void removeListSubtask() {
        if (prioritizedTasks.size() > 0) {
            prioritizedTasks.removeIf(task -> task.getType().equals("Subtask"));
        }
        subtasks.clear();
    }

    @Override
    public Task getTask(Integer id) {
        if (tasks.get(id) != null) {
            historyManager.addTask(tasks.get(id));
        }
        return tasks.get(id);
    }

    @Override
    public Epic getEpic(Integer id) {
        if (epics.get(id) != null) {
            historyManager.addTask(epics.get(id));
        }
        return epics.get(id);
    }

    @Override
    public Subtask getSubtask(Integer id) {
        if (subtasks.get(id) != null) {
            historyManager.addTask(subtasks.get(id));
        }
        return subtasks.get(id);
    }

    @Override
    public void removeSpecificTask(Integer id) {
        prioritizedTasks.remove(tasks.get(id));
        tasks.remove(id);
    }

    @Override
    public void removeSpecificEpic(Integer idEpic) {
        prioritizedTasks.remove(epics.get(idEpic));
        epics.remove(idEpic);
    }
    @Override
    public void removeSpecificSubtask(Integer idSubtask) {
        prioritizedTasks.remove(subtasks.get(idSubtask));
        subtasks.remove(id);
    }

    @Override
    public void udpate(Task task) {
        this.tasks.put(task.getId(), task);
    }

    @Override
    public void udpate(Epic epic) {
        this.epics.put(epic.getId(), epic);
    }

    @Override
    public void udpate(Subtask subtask) {
        epics.get(subtask.getIdEpic()).setSubtask(subtask);
        this.subtasks.put(subtask.getId(), subtask);
        updateStatusEpic(subtask.getIdEpic());
    }

    @Override
    public void setStatusTask(int id, TaskStatus status) {
        tasks.get(id).setStatus(status);
    }

    @Override
    public void setStatusEpic(int id, TaskStatus status) {
        epics.get(id).setStatus(status);
    }

    @Override
    public void setStatusSubtask(int id, TaskStatus status) {
        subtasks.get(id).setStatus(status);
        updateStatusEpic(subtasks.get(id).getIdEpic());
    }

    @Override
    public String getEndTime(int id) {
        if (tasks.containsKey(id)) {
           return tasks.get(id).getEndTime();
        }
        if (epics.containsKey(id)) {
            return epics.get(id).getEndTime();
        }
        if (subtasks.containsKey(id)) {
            return subtasks.get(id).getEndTime();
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
                for (Epic ep : epics.values()) {
                    if (ep.getIdEpic() == subtask.getIdEpic()) {
                       ep.setStatus(TaskStatus.IN_PROGRESS);
                    }
                }
            } if (subtask.getStatus().equals(TaskStatus.DONE)) {
                doneSubtask++;
            } if (doneSubtask == subtasks.size()) {
                for (Epic ep : epics.values()) {
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
