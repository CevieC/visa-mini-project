package sg.edu.nus.iss.miniproject.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.miniproject.models.Calendar;
import sg.edu.nus.iss.miniproject.models.CalendarEvent;
import sg.edu.nus.iss.miniproject.models.User;

@Repository
public class CalendarEventRepo {

	@Autowired
	@Qualifier("userRedisTemplate")
	RedisTemplate<String, String> redisStringTemplate;

	public String saveTaskRecord(CalendarEvent task, User user) {

		HashOperations<String, String, String> opsHash = redisStringTemplate.opsForHash();

		if (task.getId() == null || task.getId().equals("")) {
			task.setId(UUID.randomUUID().toString());
		}
		String taskString = "task";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("email", user.getEmail());
		jsonObject.put("title", task.getTitle());
		jsonObject.put("date", task.getEventDate());
		jsonObject.put("startTime", task.getStartTime());
		jsonObject.put("endTime", task.getEndTime());
		jsonObject.put("description", task.getEventDescription());

		opsHash.put(taskString, task.getId(), jsonObject.toString());

		return "success";
	}

	public Calendar getAllTasks(User user) throws JSONException {
		HashOperations<String, String, String> opsHash = redisStringTemplate.opsForHash();
		Calendar calendar = new Calendar();

		String email = user.getEmail();

		for (Object entry : opsHash.entries("task").entrySet()) {
			String key = (String) ((java.util.Map.Entry) entry).getKey();
			String value = (String) ((java.util.Map.Entry) entry).getValue();

			JSONObject jsonObject = new JSONObject(value);
			System.out.println(jsonObject);

			CalendarEvent task = new CalendarEvent();

			if (!jsonObject.getString("email").equals(email)) {
				continue;
			}

			SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
			try {

				java.util.Date utilDate = sdf.parse(jsonObject.getString("date"));
				System.out.println(utilDate);
				java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

				task.setId(key);
				task.setTitle(jsonObject.getString("title"));
				task.setEventDate(sqlDate);
				task.setStartTime(java.sql.Time.valueOf(jsonObject.getString("startTime")));
				task.setEndTime(java.sql.Time.valueOf(jsonObject.getString("endTime")));
				task.setEventDescription(jsonObject.getString("description"));

				calendar.addEvent(task);
			} catch (ParseException e) {
				System.out.println(e);
			}

		}

		// mock this part first with 3 records

		// Calendar calendar = new Calendar();

		// CalendarEvent task1 = new CalendarEvent();
		// task1.setId("1");
		// task1.setTitle("Task 1");
		// task1.setEventDate(java.sql.Date.valueOf("2023-12-01"));
		// task1.setStartTime(java.sql.Time.valueOf("10:00:00"));
		// task1.setEndTime(java.sql.Time.valueOf("11:00:00"));
		// task1.setEventDescription("Task 1 Description");
		// calendar.addEvent(task1);

		// CalendarEvent task2 = new CalendarEvent();
		// task2.setId("2");
		// task2.setTitle("Task 2");
		// task2.setEventDate(java.sql.Date.valueOf("2023-12-02"));
		// task2.setStartTime(java.sql.Time.valueOf("10:00:00"));
		// task2.setEndTime(java.sql.Time.valueOf("11:00:00"));
		// task2.setEventDescription("Task 2 Description");
		// calendar.addEvent(task2);

		// CalendarEvent task3 = new CalendarEvent();
		// task3.setId("3");
		// task3.setTitle("Task 3");
		// task3.setEventDate(java.sql.Date.valueOf("2023-12-06"));
		// task3.setStartTime(java.sql.Time.valueOf("10:00:00"));
		// task3.setEndTime(java.sql.Time.valueOf("11:00:00"));
		// task3.setEventDescription("Task 3 Description");
		// calendar.addEvent(task3);

		return calendar;

	}

	public CalendarEvent getTaskById(String id, User user) {
		HashOperations<String, String, String> opsHash = redisStringTemplate.opsForHash();
		CalendarEvent task = new CalendarEvent();

		String email = user.getEmail();

		for (Object entry : opsHash.entries("task").entrySet()) {
			String key = (String) ((java.util.Map.Entry) entry).getKey();
			String value = (String) ((java.util.Map.Entry) entry).getValue();

			JSONObject jsonObject = new JSONObject(value);
			System.out.println(jsonObject);

			// if (!jsonObject.getString("email").equals(email)) {
			// continue;
			// }

			if (key.equals(id)) {
				SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
				try {

					java.util.Date utilDate = sdf.parse(jsonObject.getString("date"));
					System.out.println(utilDate);
					java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

					task.setId(key);
					task.setTitle(jsonObject.getString("title"));
					task.setEventDate(sqlDate);
					task.setStartTime(java.sql.Time.valueOf(jsonObject.getString("startTime")));
					task.setEndTime(java.sql.Time.valueOf(jsonObject.getString("endTime")));
					task.setEventDescription(jsonObject.getString("description"));

				} catch (ParseException e) {
					System.out.println(e);
				}
			}

		}

		return task;
	}

	public String deleteTaskById(String id, User user) {
		HashOperations<String, String, String> opsHash = redisStringTemplate.opsForHash();
		CalendarEvent task = new CalendarEvent();

		String email = user.getEmail();

		for (Object entry : opsHash.entries("task").entrySet()) {
			String key = (String) ((java.util.Map.Entry) entry).getKey();
			String value = (String) ((java.util.Map.Entry) entry).getValue();

			JSONObject jsonObject = new JSONObject(value);
			System.out.println(jsonObject);

			// if (!jsonObject.getString("email").equals(email)) {
			// continue;
			// }

			if (key.equals(id)) {
				opsHash.delete("task", key);
			}

		}

		return "success";
	}

}
