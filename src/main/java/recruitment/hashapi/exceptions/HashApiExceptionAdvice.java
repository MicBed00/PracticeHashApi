package recruitment.hashapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HashApiExceptionAdvice {

    @ExceptionHandler(HashApiException.class)
    public ResponseEntity<String> hashApiExceptionHandler(HashApiException ex) {
        if(ex instanceof UserException || ex instanceof AlgorithmException)
            return new ResponseEntity<>(ex.toString(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(ex.toString(), HttpStatus.NOT_FOUND);
    }
}
