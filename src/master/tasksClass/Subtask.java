package tasksClass;

import java.util.Objects;

public class Subtask extends Task {
    public Subtask(Integer id, String name, String description, int idEpic, String startTime, long duration) {
        super(id, name, description, startTime, duration);
        this.idEpic = idEpic;
        setType("Subtask");
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", type=" + type +
                ", title='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", duration=" + duration +
                ", startTime=" + startTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subtask subtask = (Subtask) o;
        return idEpic == subtask.idEpic;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, name, description, status, duration, startTime);
    }
}




