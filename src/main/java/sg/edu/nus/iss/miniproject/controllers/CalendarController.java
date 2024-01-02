package sg.edu.nus.iss.miniproject.controllers;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

import jakarta.servlet.http.HttpServletRequest;
import sg.edu.nus.iss.miniproject.Service.CalendarService;
import sg.edu.nus.iss.miniproject.models.CalendarEvent;
import sg.edu.nus.iss.miniproject.models.Riddle;
import sg.edu.nus.iss.miniproject.models.User;

import java.util.Date;

@Controller
public class CalendarController {

    @Autowired
    private CalendarService calendarService;

    @GetMapping("/calendar")
    public String home(String username, Model model, HttpServletRequest request) {
        User user = CalendarService.getMockUser(request);

        model.addAttribute("username", username); 
        model.addAttribute("events", calendarService.getAllTasks(user).toString());
        return "calendar"; 
    }

    @GetMapping("/addtask")
    public String addTask(Model model, @RequestParam String date) {
        model.addAttribute("date", date);
        return "addtask";
    }

    @PostMapping("/saveTask")
    public String saveTask(
            @RequestParam String eventDate,
            @RequestParam String eventDescription,
            @RequestParam String title,
            @RequestParam String startTime,
            @RequestParam String endTime,
            User user, HttpServletRequest request) throws ParseException {
        CalendarEvent task = new CalendarEvent(
                UUID.randomUUID().toString(),
                new SimpleDateFormat("yyyy-MM-dd").parse(eventDate),
                eventDescription,
                title,
                convertToSqlTime(startTime),
                convertToSqlTime(endTime));
        user = CalendarService.getMockUser(request);
        String result = calendarService.saveTask(task, user);

        return result;
    }

    private Time convertToSqlTime(String time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date date = sdf.parse(time);
        return new Time(date.getTime());
    }

    @GetMapping("/updatetask")
    public String updateTask(@RequestParam String id, Model model, HttpServletRequest request) {

        CalendarEvent event = calendarService.getTaskById(id, CalendarService.getMockUser(request));

        model.addAttribute("task", event);

        return "updatetask";
    }

    @PostMapping("/updatetask")
    public String updateTask(
            @RequestParam String id,
            @RequestParam String title,
            @RequestParam String eventDescription,
            @RequestParam String eventDate,
            @RequestParam String startTime,
            @RequestParam String endTime, HttpServletRequest request

    ) {

        User user = CalendarService.getMockUser(request);

        try {
            CalendarEvent event = new CalendarEvent(
                    id,
                    new SimpleDateFormat("yyyy-MM-dd").parse(eventDate),
                    eventDescription,
                    title,
                    convertToSqlTime(startTime),
                    convertToSqlTime(endTime));

            String result = calendarService.saveTask(event, user);
        } catch (ParseException e) {

            e.printStackTrace();
        }

        return "redirect:/calendar";
    }

    @DeleteMapping("/deletetask/{taskId}")
    public String deleteTask(@PathVariable String taskId, HttpServletRequest request) {

        User user = CalendarService.getMockUser(request);
        String result = calendarService.deleteTaskById(taskId, user);

        return "redirect:/calendar";
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model, HttpServletRequest request) {
        User user = CalendarService.getMockUser(request);
        model.addAttribute("events", calendarService.getAllTasks(user).toString());

        String externalApiUrl = "https://api.api-ninjas.com/v1/riddles";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", "DDXHKudFF+1EJIXsRhvXlg==pDQWFWFX3aEPlcbi");
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        JsonNode apiResponse = restTemplate.exchange(externalApiUrl, HttpMethod.GET, entity, JsonNode.class).getBody();

        if (null == apiResponse) {
            return "dashboard";
        }
        Riddle riddle = new Riddle(apiResponse.get(0).get("title").asText(),
                apiResponse.get(0).get("question").asText(), apiResponse.get(0).get("answer").asText());

        Riddle riddle2 = CalendarRestController.getRiddle();
        model.addAttribute("riddle", riddle);

        return "dashboard";
    }
}
