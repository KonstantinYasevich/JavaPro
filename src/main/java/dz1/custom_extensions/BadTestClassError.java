package dz1.custom_extensions;

public class BadTestClassError extends RuntimeException{
    public BadTestClassError(String message){
        super(message);
    }
}
