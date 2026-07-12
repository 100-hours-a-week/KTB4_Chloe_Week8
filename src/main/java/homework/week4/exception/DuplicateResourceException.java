package homework.week4.exception;

import org.springframework.http.HttpStatus;


public class DuplicateResourceException extends BusinessException {

    public DuplicateResourceException(String message,String field) {

        super(message, HttpStatus.CONFLICT,field);

    }

}

