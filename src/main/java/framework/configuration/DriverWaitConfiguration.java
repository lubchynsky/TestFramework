package framework.configuration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DriverWaitConfiguration {
    @Autowired
    private WebDriver driver;

    @Bean
    public WebDriverWait driverWait(){
        return new WebDriverWait(driver, 10);
    }
}
