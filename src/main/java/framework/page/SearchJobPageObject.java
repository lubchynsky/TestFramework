package framework.page;

import framework.util.DriverSynchronization;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchJobPageObject {

    @Autowired
    private WebDriver driver;

    @Autowired
    private DriverSynchronization driverSynchronization;

    public List<WebElement> signOut() {
        return driver.findElements(By.xpath("//span[text()='Sign Out']"));
    }

    public WebElement searchField() {
        return driverSynchronization.getElementWhenPresent(By.xpath("//input[@id='keywordLocation']"));
    }

    public WebElement searchButton() {
        return driverSynchronization.getElementWhenClickable(By.xpath("//button[@title='Search']"));
    }

    public WebElement filterByCountry() {
        return driverSynchronization.getElementWhenPresent(By.xpath("//input[@id='location']"));
    }

    /**
     * Used in workaround
     */
    public WebElement countryPopUp(String country) {
        String popUpCountryXpath = String.format("//ul[@id='location_list']/li[normalize-space()='%s']", country);
        return driverSynchronization.getElementWhenPresent(By.xpath(popUpCountryXpath));
    }

    public List<WebElement> searchFiltersForName(String filterName) {
        String filterXpath = String.format("//span[normalize-space()='%s']", filterName);
        return driver.findElements(By.xpath(filterXpath));
    }

    public List<WebElement> jobSearchResults() {
        return driver.findElements(By.xpath("//div[contains(@class, 'list-group-item')]"));
    }

    public void waitUntilSearchListSpinnerDisappears() {
        driverSynchronization.waitUntilElementDisappear(By.xpath("//ui-loading-spinner"));
    }

    public WebElement foundSearchResults() {
        return driverSynchronization.getElementWhenPresent(By.xpath("//span[contains(text(),'jobs based on your search criteria')]"));
    }
}
