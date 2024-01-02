package sg.edu.nus.iss.miniproject.repository;

import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;
import sg.edu.nus.iss.miniproject.models.User;

@Repository
public class UserRepo {

	@Autowired
	@Resource(name = "userRedisTemplate")
	RedisTemplate<String, String> redisStringTemplate;

	public String saveUserRecord(User user) {

		// JsonObject JsonUserObj = Json.createObjectBuilder()
		// .add("userId", user.getUserId())
		// .add("firstName", user.getFirstName())
		// .add("lastName", user.getLastName())
		// .add("dob", user.getDob().toString())
		// .add("email", user.getEmail())
		// .add("password", user.getPassword())
		// .build();
		// String JsonUserString = JsonUserObj.toString();

		// HashOperations<String, String, String> opsHash =
		// redisStringTemplate.opsForHash();
		// opsHash.put(user.getEmail(), "userId", user.getUserId());
		// opsHash.put(user.getEmail(), "firstName", user.getFirstName());
		// opsHash.put(user.getEmail(), "lastName", user.getLastName());
		// opsHash.put(user.getEmail(), "email", user.getEmail());
		// opsHash.put(user.getEmail(), "dob", user.getDob().toString());
		// opsHash.put(user.getEmail(), "password", user.getPassword());

		HashOperations<String, String, String> opsHash = redisStringTemplate.opsForHash();

		if (user.getUserId() == null || user.getUserId().equals("")) {
			user.setUserId(UUID.randomUUID().toString());
		}
		String userString = "user";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("email", user.getEmail());
		jsonObject.put("firstName", user.getFirstName());
		jsonObject.put("lastName", user.getLastName());
		jsonObject.put("dob", user.getDob().toString());
		jsonObject.put("password", user.getPassword());

		opsHash.put(userString, user.getUserId(), jsonObject.toString());

		return "success";

	}

	public User login(String email, String password) {
		HashOperations<String, String, String> opsHash = redisStringTemplate.opsForHash();
		if (opsHash == null) {
			System.out.println("opsHash is null");
			return null;
		}

		for (Object entry : opsHash.entries("user").entrySet()) {
			String key = (String) ((java.util.Map.Entry) entry).getKey();
			String value = (String) ((java.util.Map.Entry) entry).getValue();

			JSONObject jsonObject = new JSONObject(value);
			System.out.println(jsonObject);

			User user = new User();
			System.out.println("here");
			System.out.println(jsonObject.getString("email"));
			System.out.println(jsonObject.getString("password"));

			System.out.println(email);
			System.out.println(password);
			if (!jsonObject.getString("email").equals(email)) {
				continue;
			}

			if (!jsonObject.getString("password").equals(password)) {
				continue;
			}

			user.setUserId(key);
			user.setFirstName(jsonObject.getString("firstName"));
			user.setLastName(jsonObject.getString("lastName"));
			user.setEmail(jsonObject.getString("email"));
			user.setPassword(jsonObject.getString("password"));
			System.out.println(user);
			return user;
		}

		return null;
	}
}
