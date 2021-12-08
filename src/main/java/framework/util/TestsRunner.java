package framework.util;

import framework.exception.FrameworkException;
import framework.tests.BaseTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.io.IOException;

public class TestsRunner {

    private static Logger log;

    private static final String TESTS_LOCATION = "framework.tests.";
    private static final String SPLIT_TESTS_BY_REGEX = "[:;,]";
    private static final String TESTS_PROPERTY = "tests";
    private static final String DEFAULT_TEST_SUITE = "NoJobsFoundNotificationTest;UnloggedCustomerSearchJobTest";

    public static void runAllTests(AnnotationConfigApplicationContext ctx) {
        setupLogger(ctx);
        String testsToExecute = System.getProperty(TESTS_PROPERTY);
        if (testsToExecute == null) {
            testsToExecute = DEFAULT_TEST_SUITE;
            log.warn("Executing default test suite: {}", DEFAULT_TEST_SUITE);
            log.warn("You could specify which tests to be run via -D{}=... parameter.", TESTS_PROPERTY);
        }
        for (String testName : testsToExecute.split(SPLIT_TESTS_BY_REGEX)) {
            try {
                runTest(ctx.getBean(Class.forName(TESTS_LOCATION + testName)));
                takeScreenShot(ctx, testName);
            } catch (ClassNotFoundException e) {
                log.error("Test class '{}' wasn't found! Skipping execution...", testName);
                log.error("Please use test class names under: " + TESTS_LOCATION);
            }
        }
        shutDownBrowser(ctx);
    }

    private static void runTest(Object testObj) {
        String className = testObj.getClass().getSimpleName();
        if (testObj instanceof BaseTest) {
            BaseTest test = (BaseTest) testObj;
            try {
                log.info("Staring test execution '{}'", className);
                test.before();
                test.execute();
                test.after();
                log.info("Test PASSED!");
            } catch (FrameworkException e) {
                log.error("Exception was thrown during test execution: {}", e.getMessage());
                log.error("Test FAILED!");
            }
        } else {
            String errorMessage = String.format("Test class '%s' should extend BaseTest, skipping execution...", className);
            log.error(errorMessage);
        }

    }

    private static void takeScreenShot(AnnotationConfigApplicationContext ctx, String testName) {
        log.info("Logging screenshot...");
        File screenshot = ((TakesScreenshot) ctx.getBean("driver")).getScreenshotAs(OutputType.FILE);
        String location = System.getProperty("LOG_DIR");
        if (location != null) {
            try {
                String fileName = location + File.separator + testName + ".jpeg";
                FileUtils.copyFile(screenshot, new File(fileName));
            } catch (IOException e) {
                String warnMessage = String.format("There was a problem to store screenshot under location '%s'", location);
                log.warn(warnMessage);
            }
        } else {
            log.warn("Log folder location not found. Skipping taking screenshot...");
        }

    }

    private static void shutDownBrowser(AnnotationConfigApplicationContext ctx) {
        log.info("ShutDown Browser...");
        ((WebDriver) ctx.getBean("driver")).quit();
    }

    private static void setupLogger(AnnotationConfigApplicationContext ctx) {
        log = (Logger) ctx.getBean("logger");
    }
}
