package framework.exception;

public class FrameworkException extends RuntimeException {
    public FrameworkException(String errorMessage){
        super(errorMessage);
    }
}
