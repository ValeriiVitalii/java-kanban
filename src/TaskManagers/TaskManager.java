package TaskManagers;
import TasksClass.Task;
import TasksClass.Epic;
import TasksClass.Subtask;
import java.util.ArrayList;
import TasksClass.TaskStatus;
public interface TaskManager {

    void add(Task task);

    void add(Epic epic);

    void add(Subtask subtask);
    ArrayList getArrayTask();

    ArrayList getArrayEpic();

    ArrayList getArraySubtask();

    ArrayList getArraySubtasksOfAnEpic(int idEpic);

    void deleteArrayTask();

    void deleteArrayEpic();

    void deleteArraySubtask();

    Task getTask(Integer idTask);
    Epic getEpic(Integer idEpic);

    Subtask getSubtask(Integer idSubtask);

    void deleteSpecificTask(Integer idTask);

    void deleteSpecificEpic(Integer idEpic);

    void deleteSpecificSubtask(Integer idSubtask);
    void udpate(Task task);

    void udpate(Epic epic);

    void udpate(Subtask subtask);
    void setStatusTask(int idTask, TaskStatus status);

    void setStatusEpic(int idEpic, TaskStatus status);

    void setStatusSubtask(int idSubtask, TaskStatus status);

    void updateStatusEpic(int idEpic);
}

