package framework.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerConfiguration {
    private static final Logger log = LoggerFactory.getLogger(LoggerConfiguration.class);

    @Bean
    public Logger logger(){
        return log;
    }
}
