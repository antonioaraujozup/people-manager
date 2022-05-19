package br.com.zup.edu.personmanager.error;

import br.com.zup.edu.personmanager.model.PessoaInexistenteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class ErrorHandler {

    Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<?> handler(Exception e, WebRequest request){

        logger.error("Ex: " + e.getLocalizedMessage(), e);

        var error = new ErrorResponse(LocalDateTime.now().toString(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getClass().toString(),
                e.getLocalizedMessage() , request.getContextPath());

        return ResponseEntity.internalServerError().body(error);
    }

    @ExceptionHandler(value = {PessoaInexistenteException.class})
    protected ResponseEntity<?> handler(PessoaInexistenteException e, WebRequest request){

        logger.warn("Ex: " + e.getLocalizedMessage(), e);

        var error = new ErrorResponse(LocalDateTime.now().toString(),
                HttpStatus.UNPROCESSABLE_ENTITY.value(), e.getClass().toString(),
                e.getLocalizedMessage() , request.getContextPath());

        return ResponseEntity.unprocessableEntity().body(error);
    }

}
