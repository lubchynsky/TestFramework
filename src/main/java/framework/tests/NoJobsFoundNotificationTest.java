package framework.tests;

import framework.page.SearchJobPageHandler;
import framework.page.SearchJobPageChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoJobsFoundNotificationTest extends BaseTest {

    private static final String KEYWORD = "XXX";

    @Autowired
    private SearchJobPageHandler searchJobPageHandler;
    @Autowired
    private SearchJobPageChecker searchJobPageChecker;

    @Override
    public void execute() {
        searchJobPageHandler.searchJobByKeyword(KEYWORD);
        searchJobPageChecker.checkFilterPresent(KEYWORD);
        searchJobPageChecker.checkEmptySearchResults();
    }
}
