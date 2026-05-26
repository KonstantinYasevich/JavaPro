package dz1.custom_extensions;

public class TestAssertionError extends RuntimeException{
    public TestAssertionError(String message){
        super(message);
    }
}
