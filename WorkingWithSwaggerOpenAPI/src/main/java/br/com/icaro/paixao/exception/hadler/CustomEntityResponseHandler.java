package br.com.icaro.paixao.exception.hadler;

import br.com.icaro.paixao.exception.ExceptionResponse;
import br.com.icaro.paixao.exception.RequiredObjectIsNullException;
import br.com.icaro.paixao.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController
@ControllerAdvice // Permite capturar e tratar exceções em toda a aplicação
public class CustomEntityResponseHandler extends ResponseEntityExceptionHandler {

    // Trata qualquer exceção genérica da aplicação
    @ExceptionHandler({Exception.class})
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) {
        // Cria objeto de resposta com data, mensagem de erro e detalhes da requisição
        ExceptionResponse response = new ExceptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        // Retorna resposta com status 500 (Internal Server Error)
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Trata exceções específicas do tipo UnsupportedMathOperationException
    @ExceptionHandler({ResourceNotFoundException.class})
    public final ResponseEntity<ExceptionResponse> handleNotFound(Exception ex, WebRequest request) {
        // Cria objeto de resposta com data, mensagem de erro e detalhes da requisição
        ExceptionResponse response = new ExceptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        // Retorna resposta com status 400 (Bad Request)
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({RequiredObjectIsNullException.class})
    public final ResponseEntity<ExceptionResponse> handleBadRequestExceptions(Exception ex, WebRequest request) {
        // Cria objeto de resposta com data, mensagem de erro e detalhes da requisição
        ExceptionResponse response = new ExceptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        // Retorna resposta com status 400 (Bad Request)
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
