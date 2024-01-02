package sg.edu.nus.iss.miniproject.models;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import org.springframework.data.redis.core.RedisHash;

@RedisHash("CalendarEvent")
public class CalendarEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private Date eventDate;
    private String eventDescription;
    private String title;
    private Time startTime;
    private Time endTime;

    public CalendarEvent() {
        id = "";
        eventDate = new Date();
        eventDescription = "";
        title = "";
        startTime = new Time(0);
        endTime = new Time(0);
    }

    public CalendarEvent(String id, Date eventDate, String eventDescription, String title,
            Time startTime,
            Time endTime) {
        this.id = id;
        this.eventDate = eventDate;
        this.eventDescription = eventDescription;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getEventDate() {
        return this.eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventDescription() {
        return this.eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public Date getEventDate(Date eventDate) {
        return this.eventDate;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Time getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }
}
