package sg.edu.nus.iss.miniproject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sg.edu.nus.iss.miniproject.models.Riddle;

@Service
public class AppService {

    @Value("${api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final String API_URL = "https://api.api-ninjas.com/v1/riddles?limit=3";

    @Autowired
    public AppService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Riddle[] getRiddles() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("api-key", apiKey); // Use the actual header name required by your API
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Riddle[]> response = restTemplate.exchange(API_URL, HttpMethod.GET, entity, Riddle[].class);
        return response.getBody();
    }
}
