package Test;

import TaskManagers.InMemoryTaskManager;
import TaskManagers.TaskManager;
import TasksClass.Task;

public abstract class TaskManagerTest<T extends TaskManager> {

    public abstract void addTaskTest();
    public abstract void addEpicTest();
    public abstract void addSubtaskTest();

}
