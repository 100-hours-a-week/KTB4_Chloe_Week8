package homework.week4.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {

    private final String message;
    private final HttpStatus status;
    private String field;

    public BusinessException(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }

    public BusinessException(String message, HttpStatus status, String field) {
        super(message);
        this.message = message;
        this.status = status;
        this.field = field;
    }

}
