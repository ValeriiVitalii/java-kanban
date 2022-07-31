public class Task {
   private Integer id;
   private String name;
   private String description;
   private String status;

   public Task(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        status = "Задача только создана, но к её выполнению ещё не приступили.";
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}




