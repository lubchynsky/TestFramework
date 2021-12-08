package framework.page;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SearchJobPageHandler {

    @Autowired
    private SearchJobPageObject searchJobPageObject;
    @Autowired
    private Logger log;

    public void assureUserLoggedOut() {
        List<WebElement> signOut = searchJobPageObject.signOut();
        if (!signOut.isEmpty()) {
            log.info("Logging out user...");
            signOut.get(0).click();
        }
    }

    public void searchJobByKeyword(String keyword) {
        searchJobPageObject.searchField().sendKeys(keyword);
        searchJobPageObject.searchButton().click();
        searchJobPageObject.waitUntilSearchListSpinnerDisappears();
    }

    public void searchJobByKeywordAndCountry(String keyword, String country) {
        handleBugForCountryFilter(country);
        searchJobByKeyword(keyword);
    }

    /**
     * Looks like there is a BUG when adding country filter :)
     * When I set country to 'Poland' and click search -> it will not add country filter even if I do it manually
     * <p>
     * There are two workarounds while setting field (popup window do not show when set to 'Poland'):
     * - Add space before country ' Poland';
     * - Remove last letter from country name 'Polan';
     * Then click on popup window with text ' Poland'
     */
    public void handleBugForCountryFilter(String country) {
        searchJobPageObject.filterByCountry().sendKeys(" " + country);
        searchJobPageObject.countryPopUp(country).click();
    }
}
