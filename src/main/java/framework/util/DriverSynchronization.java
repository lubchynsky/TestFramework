package framework.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverSynchronization {

    @Autowired
    private WebDriverWait wait;

    public WebElement getElementWhenPresent(By by) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public WebElement getElementWhenClickable(By by) {
        return wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    public void waitUntilElementDisappear(By by) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }
}
