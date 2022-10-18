package tasksClass;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
   private Integer id;
   private String name;
   private String description;
   private TaskStatus status;
   private String type;

   private String startTime;
   private long duration;

   private int idEpic = 0;

   public Task(Integer id, String name, String description, String startTime, long duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        status = TaskStatus.NEW;
        setType("Task");

        this.startTime = startTime;
        this.duration = duration;

   }



    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", description=" + description +
                '}';
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIdEpic() {
        return idEpic;
    }

    public void setIdEpic(int idEpic) {
        this.idEpic = idEpic;
    }

    public LocalDateTime getStartTimeLocal() {
        return LocalDateTime.parse(startTime);
    }

    public String getStartTimeString() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getEndTime() {
       LocalDateTime endTime = getStartTimeLocal().plusMinutes(duration);
       DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
       return endTime.format(format);
    }

    public LocalDateTime getEndLocalDateTime() {
        LocalDateTime endTime = getStartTimeLocal().plusMinutes(duration);
        return endTime;
    }

    public int getSecond() {
        Instant a = Instant.parse(startTime);
        return (int) a.toEpochMilli();
    }
}




