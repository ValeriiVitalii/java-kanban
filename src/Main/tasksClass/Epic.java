package tasksClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
public class Epic extends Task {
    private int idEpic = 1;
    private String type;
    private final ArrayList<Subtask> subTask = new ArrayList<>();
    public Epic(Integer id, String name, String description, String startTime, long duration) {
        super(id, name, description, startTime, duration);
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

    @Override
    public String getEndTime() {
        LocalDateTime earlySubtask = LocalDateTime.parse("2100-10-20T10:10:10");
        LocalDateTime lastSubtask = LocalDateTime.parse("1999-10-20T10:10:10");
        long minutes = 0;
        if (subTask.isEmpty()) {
            return "Добавьте подзадачи, чтобы узнать время выполнения эпика!";
        } else {
            for (Subtask s : subTask) {
                minutes += s.getDuration();
                if (s.getStartTimeLocal().isBefore(earlySubtask)) {
                    earlySubtask = s.getStartTimeLocal();
                }
                if (s.getStartTimeLocal().isAfter(lastSubtask)) {
                    lastSubtask = s.getStartTimeLocal();
                }
            }
            setStartTime(earlySubtask.toString());
            setDuration(minutes);

            LocalDateTime endTime = lastSubtask.plusMinutes(minutes);
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
            return endTime.format(format);
        }
    }
}
