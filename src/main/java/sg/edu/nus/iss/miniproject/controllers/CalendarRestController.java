package sg.edu.nus.iss.miniproject.controllers;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

import sg.edu.nus.iss.miniproject.config.ApiConfig;
import sg.edu.nus.iss.miniproject.models.Riddle;


@RestController
public class CalendarRestController {

    @GetMapping("/riddle")
    public static Riddle getRiddle() {


        String externalApiUrl = "https://api.api-ninjas.com/v1/riddles";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", ApiConfig.getApiKey());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        JsonNode apiResponse = restTemplate.exchange(externalApiUrl, HttpMethod.GET, entity, JsonNode.class).getBody();

        if (null == apiResponse) {
            return null;
        }
        Riddle riddle = new Riddle(apiResponse.get(0).get("title").asText(),
                apiResponse.get(0).get("question").asText(), apiResponse.get(0).get("answer").asText());

        return riddle;
    }

}
