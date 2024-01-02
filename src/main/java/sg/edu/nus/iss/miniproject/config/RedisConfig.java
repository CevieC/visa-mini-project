package sg.edu.nus.iss.miniproject.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
public class RedisConfig {

    // Railway: REDIS_HOST
    @Value("${spring.redis.host}")
    private String redisHost;

    // Railway: REDIS_PORT
    @Value("${spring.redis.port}")
    private Integer redisPort;

    // Railway: REDIS_USERNAME
    @Value("${spring.redis.username}")
    private String redisUsername;

    // Railway: REDIS_PASSWORD
    @Value("${spring.redis.password}")
    private String redisPassword;

    // Railway: REDIS_USERDATABSE
    @Value("${spring.redis.database}")
    private Integer userDatabase;


    private JedisConnectionFactory createJedisConnectionFactory(int database) {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHost, redisPort);
        if (redisUsername != null && !redisUsername.isEmpty()) {
            config.setUsername(redisUsername);
        }
        if (redisPassword != null && !redisPassword.isEmpty()) {
            config.setPassword(redisPassword);
        }
        config.setDatabase(database);

        JedisClientConfiguration jedisClientConfiguration = JedisClientConfiguration.builder().build();
        return new JedisConnectionFactory(config, jedisClientConfiguration);
    }

    private RedisTemplate<String, String> createRedisTemplate(JedisConnectionFactory connectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        
        template.setKeySerializer(stringRedisSerializer);
        template.setValueSerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        template.setHashValueSerializer(stringRedisSerializer);
        return template;
    }

    @Bean
    public JedisConnectionFactory jedisUserConnFactory() {
        return createJedisConnectionFactory(userDatabase);
    }

    // Default RedisTemplate (could be one of the existing connection factories)
    @Bean
    public RedisTemplate<String, String> redisTemplate() {
        return createRedisTemplate(jedisUserConnFactory());
    }

    @Bean("userRedisTemplate")
    public RedisTemplate<String, String> userRedisTemplate() {
        return createRedisTemplate(jedisUserConnFactory());
    }
}
