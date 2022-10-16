package TaskManagers;

import TasksClass.Epic;
import TasksClass.Subtask;
import TasksClass.Task;
import TasksClass.TaskStatus;

import java.util.ArrayList;
public interface TaskManager {

    int add(Task task);

    int add(Epic epic);

    int add(Subtask subtask);
    ArrayList getListTask();

    ArrayList getListEpic();

    ArrayList getListSubtask();

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
}

