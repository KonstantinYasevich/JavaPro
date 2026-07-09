package app.exception;

public class CustomException extends RuntimeException {
    private final int statusCode;

    public CustomException(int statusCode, String message){
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public CustomException(int statusCode) {
        this.statusCode = statusCode;
    }
}
