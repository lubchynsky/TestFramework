package framework.util;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ContextFactory {

    private static final String BASE_PACKAGE_TO_SCAN = "framework";

    public static AnnotationConfigApplicationContext getContext() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.scan(BASE_PACKAGE_TO_SCAN);
        ctx.refresh();
        return ctx;
    }
}
