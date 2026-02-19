package Recursos.Exceptions;

public class InvalidDateException extends RuntimeException {

    public InvalidDateException() {
        super();
    }
    public String getMessage() {

        return  "La fecha no puede ser mayor que la fecha actual";
    }



}
