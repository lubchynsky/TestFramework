package framework.configuration;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.SneakyThrows;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URL;
import java.util.Arrays;

import static framework.configuration.BrowserType.CHROME;
import static framework.configuration.BrowserType.FIREFOX;

@Configuration
public class DriverConfiguration {

    private static final String SUPPORTED_BROWSER_TYPES = Arrays.toString(BrowserType.values());

    @Autowired
    private Logger log;

    @SneakyThrows
    @Bean
    public WebDriver driver() {
        BrowserType browserType = getBrowserType();
        switch (browserType) {
            case FIREFOX: {
                if (getGridUrl() == null) {
                    WebDriverManager.firefoxdriver().setup();
                    return new FirefoxDriver();
                }
                DesiredCapabilities desiredCapabilities = DesiredCapabilities.firefox();
                desiredCapabilities.setPlatform(Platform.LINUX);
                desiredCapabilities.setBrowserName(FIREFOX.name().toLowerCase());
                return new RemoteWebDriver(new URL(getGridUrl()), desiredCapabilities);
            }
            case EDGE: {
                WebDriverManager.edgedriver().setup();
                return new EdgeDriver();
            }
            case CHROME:
            default:
                if (getGridUrl() == null) {
                    WebDriverManager.chromedriver().setup();
                    return new ChromeDriver();
                }
                DesiredCapabilities desiredCapabilities = DesiredCapabilities.chrome();
                desiredCapabilities.setPlatform(Platform.LINUX);
                desiredCapabilities.setBrowserName(CHROME.name().toLowerCase());
                return new RemoteWebDriver(new URL(getGridUrl()), desiredCapabilities);
        }
    }

    private String getGridUrl() {
        return System.getProperty("gridUrl");
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
