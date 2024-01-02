package sg.edu.nus.iss.miniproject.models;

import java.time.LocalDateTime;

public class Task {
    
    private String userId;
    private String taskId;
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String desc;

    public String getUserId() {return userId;}
    public void setUserId(String userId) {this.userId = userId;}

    public String getTaskId() {return taskId;}
    public void setTaskId(String taskId) {this.taskId = taskId;}

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public LocalDateTime getStartTime() {return startTime;}
    public void setStartTime(LocalDateTime startTime) {this.startTime = startTime;}

    public LocalDateTime getEndTime() {return endTime;}
    public void setEndTime(LocalDateTime endTime) {this.endTime = endTime;}
    
    public String getDesc() {return desc;}
    public void setDesc(String desc) {this.desc = desc;}
}
