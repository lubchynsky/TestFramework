package framework;

import framework.util.ContextFactory;
import framework.util.TestsRunner;

public class Main {

    public static void main(String[] args) {
        TestsRunner.runAllTests(ContextFactory.getContext());
    }
}
