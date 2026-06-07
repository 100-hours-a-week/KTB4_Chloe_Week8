package homework.week4.response;

import lombok.Getter;

import java.util.List;

@Getter
public class ErrorResponse {

    //private final String code;
    private final String message;
    private final Object data;

    private ErrorResponse(String message) {
        //this.code = code;
        this.message = message;
        this.data = null;


    }

    public static ErrorResponse of (String message) {
        return new ErrorResponse(message);
    }
}

