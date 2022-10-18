package tasksClass;

public class Subtask extends Task {
    private int idEpic = 1;
    private String type;
    public Subtask(Integer id, String name, String description, int idEpic, String startTime, long duration) {
        super(id, name, description, startTime, duration);
        this.idEpic = idEpic;
        setType("Subtask");
    }
    @Override
    public int getIdEpic() {
        return idEpic;
    }
}





