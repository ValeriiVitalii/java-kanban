package TasksClass;

public class Subtask extends Task {
    private int idEpic = 1;
    private String type;

    public Subtask(Integer id, String name, String description, int idEpic) {
        super(id, name, description);
        this.idEpic = idEpic;
        setType("Subtask");
    }

    @Override
    public int getIdEpic() {
        return idEpic;
    }
}





