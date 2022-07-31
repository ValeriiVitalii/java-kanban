import java.util.ArrayList;
import java.util.HashMap;
public class Manager {
    private int idTask = 1;
    private int idEpic = 1;
    private int idSubtask = 1;

    private HashMap<Integer, Task> task = new HashMap<>();
    private HashMap<Integer, Epic> epic = new HashMap<>();
    private HashMap<Integer, Subtask> subtask = new HashMap<>();


    public void add(Task task) {
        task.setId(idTask);
        this.task.put(idTask++, task);
    }

    public void add(Epic epic) {
        epic.setId(idEpic);
        this.epic.put(idEpic++, epic);
    }

    public void add(Subtask subtask) {
        subtask.setId(idSubtask);
        subtask.setIdEpic(idEpic - 1);
        this.subtask.put(idSubtask++, subtask);
        updateStatusEpic(idEpic - 1);
    }

    public ArrayList getArrayTask() {
      ArrayList<Task> arrayTask = new ArrayList<>();

      for (Task t : task.values()) {
          arrayTask.add(t);
      }
      return arrayTask;
    }

    public ArrayList getArrayEpic() {
        ArrayList<Epic> arrayEpic = new ArrayList<>();

        for (Epic e : epic.values()) {
            arrayEpic.add(e);
        }
        return arrayEpic;
    }

    public ArrayList getArraySubtask() {
        ArrayList<Subtask> arraySubtask = new ArrayList<>();

        for (Subtask sub : subtask.values()) {
            arraySubtask.add(sub);
        }
        return arraySubtask;
    }

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

    public void deletArrayTask() {
        task.clear();
    }

    public void deleteArrayEpic() {
        epic.clear();
    }

    public void deleteArraySubtask() {
        subtask.clear();
    }

    public Task getSpecificTask(Integer idTask) {
        return task.get(idTask);
    }

    public Epic getSpecificEpic(Integer idEpic) {
        return epic.get(idEpic);
    }

    public Subtask getSpecificSubtask(Integer idSubtask) {
        return subtask.get(idSubtask);
    }

    public void deleteSpecificTask(Integer idTask) {
        task.remove(idTask);
    }

    public void deleteSpecificEpic(Integer idEpic) {
        epic.remove(idEpic);
    }

    public void deleteSpecificSubtask(Integer idSubtask) {
        subtask.remove(idSubtask);
    }

    public void udpate(Task task) {
        this.task.put(task.getId(), task);
    }

    public void udpate(Epic epic) {
        this.epic.put(epic.getId(), epic);
    }

    public void udpate(Subtask subtask) {
        this.subtask.put(subtask.getId(), subtask);
    }

    public void setStatusTask(int idTask, String status) {
        task.get(idTask).setStatus(status);
    }

    public void setStatusEpic(int idEpic, String status) {
        epic.get(idEpic).setStatus(status);
    }

    public void setStatusSubtask(int idSubtask, String status) {
        subtask.get(idSubtask).setStatus(status);
        updateStatusEpic(subtask.get(idSubtask).getIdEpic());
    }

    public void updateStatusEpic(int idEpic) {
        ArrayList<Subtask> subtasks = getArraySubtasksOfAnEpic(idEpic);
        int doneSubtask = 0;

        for (Subtask subtask : subtasks) {
            if (subtask.getStatus().equals("Над задачей ведётся работа.")) {
                epic.get(idEpic).setStatus("Над задачей ведётся работа.");
            } if (subtask.getStatus().equals("Задача выполнена.")) {
                doneSubtask++;
            } if (doneSubtask == subtasks.size()) {
                epic.get(idEpic).setStatus("Задача выполнена.");
            }
        }
    }
}

