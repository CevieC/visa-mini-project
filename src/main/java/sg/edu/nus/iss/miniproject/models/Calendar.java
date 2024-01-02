package sg.edu.nus.iss.miniproject.models;

import java.util.ArrayList;

public class Calendar {
    private ArrayList<CalendarEvent> events = new ArrayList<CalendarEvent>();

    public Calendar() {
    }

    public Calendar(ArrayList<CalendarEvent> events) {
        this.events = events;
    }

    public ArrayList<CalendarEvent> getEvents() {
        return this.events;
    }

    public void setEvents(ArrayList<CalendarEvent> events) {
        this.events = events;
    }

    public void addEvent(CalendarEvent event) {
        this.events.add(event);
    }

}
