package TasksClass;

import java.util.ArrayList;
public class Epic extends Task {
    private int idEpic = 1;
    private String type;
    private final ArrayList<Subtask> subTask = new ArrayList<>();
    public Epic(Integer id, String name, String description) {
        super(id, name, description);
        setType("Epic");
    }
    public int getIdEpic() {
        return idEpic;
    }
    public void setIdEpic(int idEpic) {
        this.idEpic = idEpic;
    }

    public ArrayList<Subtask> getSubtask() {
        return subTask;
    }

    public void setSubtask(Subtask subtask) {
        subTask.add(subtask);
    }
}
