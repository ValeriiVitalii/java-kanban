public class Subtask extends Task{
    private int idEpic = 1;

    public Subtask(Integer id, String name, String description) {
        super(id, name, description);
    }

    public int getIdEpic() {
        return idEpic;
    }

    public void setIdEpic(int idEpic) {
        this.idEpic = idEpic;
    }
}





