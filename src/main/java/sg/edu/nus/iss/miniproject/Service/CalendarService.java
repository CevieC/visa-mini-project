package sg.edu.nus.iss.miniproject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import sg.edu.nus.iss.miniproject.repository.CalendarEventRepo;
import sg.edu.nus.iss.miniproject.models.Calendar;
import sg.edu.nus.iss.miniproject.models.CalendarEvent;
import sg.edu.nus.iss.miniproject.models.User;

@Service
public class CalendarService {

    @Autowired
    private CalendarEventRepo calendarEventRepo;

    public static User getMockUser(HttpServletRequest request) {

        // get from session

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        return user;
    }

    public String saveTask(CalendarEvent task, User user) {
        return calendarEventRepo.saveTaskRecord(task, user);
        // return null;
    }

    public JSONArray getAllTasks(User user) {
        Calendar calendar = calendarEventRepo.getAllTasks(user);

        JSONArray jsonArray = new JSONArray();
        for (CalendarEvent event : calendar.getEvents()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", event.getId());
            jsonObject.put("title", event.getTitle());
            jsonObject.put("date", event.getEventDate());
            jsonObject.put("start", event.getStartTime());
            jsonObject.put("end", event.getEndTime());
            jsonObject.put("description", event.getEventDescription());
            jsonArray.put(jsonObject);
        }

        return jsonArray;

    }

    public CalendarEvent getTaskById(String id, User user) {
        return calendarEventRepo.getTaskById(id, user);
    }

    public String deleteTaskById(String id, User user) {
        return calendarEventRepo.deleteTaskById(id, user);
    }

}
