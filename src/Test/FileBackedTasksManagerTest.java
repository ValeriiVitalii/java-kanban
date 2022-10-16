import SaveCsv.FileBackedTasksManager;

public class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {

    @Override
    public FileBackedTasksManager createManager() {
        return new FileBackedTasksManager("C:\\Users\\79650\\Desktop\\java важное\\TaskManager.csv");
    }

    @Override
    public void addTaskAndGetTaskByIdTest() {

    }

    @Override
    public void addEpicAndGetEpicByIdTest() {
    }

    @Override
    public void addSubtaskAndGetSubtaskIdTest() {
    }


}
