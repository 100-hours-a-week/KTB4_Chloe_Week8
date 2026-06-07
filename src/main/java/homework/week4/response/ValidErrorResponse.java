package homework.week4.response;

import lombok.Getter;

import java.util.List;

@Getter
public class ValidErrorResponse {

    //private final String code;
    private final List<String> message;
    private final Object data;

    private ValidErrorResponse(List<String> message) {
        //this.code = code;
        this.message = message;
        this.data = null;


    }

    public static ValidErrorResponse of (List<String> message) {
        return new ValidErrorResponse(message);
    }
}
