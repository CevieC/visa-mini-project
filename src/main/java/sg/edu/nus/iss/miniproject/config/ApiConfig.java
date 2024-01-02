package sg.edu.nus.iss.miniproject.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class ApiConfig {

    @Value("${api.key}")
    private String apiKey;

    private static String staticApiKey;

    @PostConstruct
    public void init() {
        ApiConfig.staticApiKey = apiKey;
    }

    public static String getApiKey() {
        return staticApiKey;
    }
}
