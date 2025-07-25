package vn.ltdt.SocialNetwork.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import vn.ltdt.SocialNetwork.dtos.ErrorDTO;

@ControllerAdvice
@RestController
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleUsernameNotFoundException(HttpServletRequest request, UsernameNotFoundException e) {
        log.error("Username not found exception: {}", e.getMessage());
        return new ResponseEntity<>(
                new ErrorDTO(request.getRequestURI(),HttpStatus.UNAUTHORIZED,  "Email or password isn't corrected"), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleUserExistedException(HttpServletRequest request, MethodArgumentNotValidException e) {
        log.error("Invalid Request Params or Request Body's properties: {}", e.getMessage());
        StringBuilder errorMessage = new StringBuilder();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            final String field = fieldError.getField();
            final String message = fieldError.getDefaultMessage();
            errorMessage.append(field).append(" : ").append(message).append("\n");
        });
        if (!errorMessage.isEmpty() && errorMessage.charAt(errorMessage.length() - 1) == ';') {
            errorMessage.setLength(errorMessage.length() - 1);
        }
        return ResponseEntity
                .badRequest()
                .body(new ErrorDTO(request.getRequestURI(), HttpStatus.BAD_REQUEST, errorMessage.toString()));
    }

    @ExceptionHandler(AlreadyExistedException.class)
    public ResponseEntity<ErrorDTO> handleUserExistedException(HttpServletRequest request, AlreadyExistedException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(new ErrorDTO(request.getRequestURI(), HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDTO> handleRuntimeException(HttpServletRequest request, RuntimeException e) {
        log.error("Runtime exception: {}", e.getMessage());
        return  ResponseEntity.internalServerError().body(new ErrorDTO(request.getRequestURI(),HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage()));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorDTO> handleAuthenticationException(HttpServletRequest request, AuthenticationException e) {
        log.error("Authentication exception: {}", e.getMessage());
        return new ResponseEntity<>(new ErrorDTO(request.getRequestURI(),HttpStatus.UNAUTHORIZED, "Wrong email or password"), HttpStatus.UNAUTHORIZED);
    }

}
