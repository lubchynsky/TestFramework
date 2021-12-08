package framework.page;

import framework.exception.FrameworkException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SearchJobPageChecker {

    private final String RESULTS_COUNT_REGEX = "^We found (\\d+) jobs based on your search criteria$";

    @Autowired
    private SearchJobPageObject searchJobPageObject;
    @Autowired
    private Logger log;

    public void checkFilterPresent(String filter) {
        if (searchJobPageObject.searchFiltersForName(filter).isEmpty()) {
            throw new FrameworkException(String.format("Filter with name '%s' wasn't added", filter));
        }
        log.info("Filter '{}' added successfully", filter);
    }

    public void checkThereAreAnySearchResults() {
        if (!searchJobPageObject.jobSearchResults().isEmpty()) {
            log.info("Search results count are positive as expected");
        } else {
            throw new FrameworkException("Search results should be positive");
        }
    }

    public void checkEmptySearchResults() {
        int searchResultsCount = getResultsCount();
        if (searchResultsCount == 0) {
            log.info("There are 0 results count, as expected");
        } else {
            String errorMessage = String.format("Expected 0 results count, but there was '%d' results present", searchResultsCount);
            throw new FrameworkException(errorMessage);
        }
    }

    private int getResultsCount() {
        String searchResult = searchJobPageObject.foundSearchResults().getText().trim();
        Pattern pattern = Pattern.compile(RESULTS_COUNT_REGEX);
        Matcher matcher = pattern.matcher(searchResult);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        } else {
            String errorMessage = String.format("Line, which match regex '%s' was not found", RESULTS_COUNT_REGEX);
            throw new FrameworkException(errorMessage);
        }
    }
}
