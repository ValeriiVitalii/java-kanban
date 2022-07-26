package taskManagers;

import tasksClass.Epic;
import tasksClass.Subtask;
import tasksClass.Task;
import tasksClass.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public interface TaskManager {

    int add(Task task);

    int add(Epic epic);

    int add(Subtask subtask);
    HashMap getMapTask();

    HashMap getMapEpic();

    HashMap getMapSubtask();

    ArrayList getListSubtasksOfAnEpic(int idEpic);

    void removeListTask();

    void removeListEpic();

    void removeListSubtask();

    Task getTask(Integer idTask);
    Epic getEpic(Integer idEpic);

    Subtask getSubtask(Integer idSubtask);

    void removeSpecificTask(Integer idTask);

    void removeSpecificEpic(Integer idEpic);

    void removeSpecificSubtask(Integer idSubtask);
    void udpate(Task task);

    void udpate(Epic epic);

    void udpate(Subtask subtask);
    void setStatusTask(int idTask, TaskStatus status);

    void setStatusEpic(int idEpic, TaskStatus status);

    void setStatusSubtask(int idSubtask, TaskStatus status);

    void updateStatusEpic(int idEpic);

    Set<Task> getPrioritizedTasks();

    void intersectionCheck();

    String getEndTime(int id);
}

