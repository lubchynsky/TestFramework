package framework.configuration;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

import static framework.configuration.BrowserType.CHROME;

@Configuration
public class DriverConfiguration {

    private static final String SUPPORTED_BROWSER_TYPES = Arrays.toString(BrowserType.values());

    @Autowired
    private Logger log;

    @Bean
    public WebDriver driver() {
        BrowserType browserType = getBrowserType();
        switch (browserType) {
            case FIREFOX: {
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver();
            }
            case EDGE: {
                WebDriverManager.edgedriver().setup();
                return new EdgeDriver();
            }
            case CHROME:
            default:
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();
        }
    }

    private BrowserType getBrowserType() {
        String browser = System.getProperty("browser");
        if (browser == null) {
            log.info("No browser type selected. Chrome will be used as default browser!");
            log.info("To select browser type, please pass property -Dbrowser=... with any of {}", SUPPORTED_BROWSER_TYPES);
            return CHROME;
        }
        browser = browser.toUpperCase();
        if (BrowserType.contains(browser)) {
            return BrowserType.valueOf(browser);
        } else {
            log.info("Unsupported browser type '{}', please choose any of {}", browser, SUPPORTED_BROWSER_TYPES);
            log.info("Chrome will be used as default browser!");
            return CHROME;
        }
    }
}
