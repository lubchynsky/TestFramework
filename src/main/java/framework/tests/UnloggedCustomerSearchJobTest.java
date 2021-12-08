package framework.tests;

import framework.page.SearchJobPageHandler;
import framework.page.SearchJobPageChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnloggedCustomerSearchJobTest extends BaseTest {

    private static final String KEYWORD = "Senior Software Development Engineer in Test";
    private static final String COUNTRY = "Poland";

    @Autowired
    private SearchJobPageHandler searchJobPageHandler;
    @Autowired
    private SearchJobPageChecker searchJobPageChecker;

    @Override
    public void execute() {
        searchJobPageHandler.assureUserLoggedOut();
        searchJobPageHandler.searchJobByKeywordAndCountry(KEYWORD, COUNTRY);
        searchJobPageChecker.checkFilterPresent(KEYWORD);
        searchJobPageChecker.checkFilterPresent(COUNTRY);
        searchJobPageChecker.checkThereAreAnySearchResults();
    }
}
