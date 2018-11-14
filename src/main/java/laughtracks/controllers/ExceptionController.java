package laughtracks;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import main.java.laughtracks.exceptions.comedian.ComedianNotfoundException;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = ComedianNotfoundException.class)
    public ResponseEntity<Object> handleException(ComedianNotfoundException exception) {
        return new ResponseEntity<>("Product Not Found", HttpStatus.NOT_FOUND);
    }
}
