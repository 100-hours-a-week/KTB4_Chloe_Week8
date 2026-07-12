package homework.week4.response;

import lombok.Getter;

import java.util.List;

@Getter
public class ErrorResponse {

    private final String message;
    private String field;

    private ErrorResponse(String message) {
        this.message = message;
    }

    private ErrorResponse(String message, String field) {
        this.message = message;
        this.field = field;
    }

    public static ErrorResponse of (String message) {
        return new ErrorResponse(message);
    }

    public static ErrorResponse of (String message,String field) {
        return new ErrorResponse(message,field);
    }
}

