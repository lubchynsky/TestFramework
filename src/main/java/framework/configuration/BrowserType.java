package framework.configuration;

import java.util.Arrays;

public enum BrowserType {
    FIREFOX,
    CHROME,
    EDGE;

    public static boolean contains(String browser) {
        return Arrays.stream(BrowserType.values())
                .anyMatch(type -> type.toString().equals(browser));
    }
}
