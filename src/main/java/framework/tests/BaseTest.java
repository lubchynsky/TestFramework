package framework.tests;

import lombok.NoArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public abstract class BaseTest {
    @Autowired
    protected WebDriver driver;

    public void before() {
        driver.manage().window().maximize();
        driver.get("https://akamaicareers.inflightcloud.com/");
    }

    public abstract void execute();

    public void after() {
    }
}
