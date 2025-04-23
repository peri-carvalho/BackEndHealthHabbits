package com.BackEnd.BackEndHealthHabbits.infra.exceptions;

import com.BackEnd.BackEndHealthHabbits.dto.StandardError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    // Handler para DataIntegrityCustomException
    @ExceptionHandler(DataIntegrityCustomException.class)
    public ResponseEntity<StandardError> handleDataIntegrityCustomException(
            DataIntegrityCustomException e, HttpServletRequest request) {

        StandardError error = new StandardError();
        error.setTimestamp(Instant.now().toString());
        error.setStatus(HttpStatus.CONFLICT.value());
        error.setError("Conflito de Dados");
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    // Handler para DataIntegrityViolationException (origem do seu erro atual)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> handleDataIntegrityViolation(
            DataIntegrityViolationException e, HttpServletRequest request) {

        // Opcional: Extraia informações específicas da exceção para personalizar a mensagem
        String userMessage = "Erro de integridade de dados. Por favor, verifique os dados fornecidos.";

        // Log completo para desenvolvedores
        // logger.error("Data integrity violation", e);

        StandardError error = new StandardError();
        error.setTimestamp(Instant.now().toString());
        error.setStatus(HttpStatus.CONFLICT.value());
        error.setError("Conflito de Dados");
        error.setMessage(userMessage);
        error.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    // Handler para MultipleValidationException (caso ainda exista)
    @ExceptionHandler(MultipleValidationException.class)
    public ResponseEntity<List<StandardError>> handleMultipleValidationException(
            MultipleValidationException e, HttpServletRequest request) {

        List<StandardError> errors = e.getErrorMessages().stream()
                .map(message -> {
                    StandardError error = new StandardError();
                    error.setTimestamp(Instant.now().toString());
                    error.setStatus(HttpStatus.BAD_REQUEST.value());
                    error.setError("Erro de Validação");
                    error.setMessage(message);
                    error.setPath(request.getRequestURI());
                    return error;
                })
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    // Metodo para tratar erros de validação de DTOs
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<StandardError>> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<StandardError> errors = ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    StandardError standardError = new StandardError();
                    standardError.setTimestamp(Instant.now().toString());
                    standardError.setStatus(HttpStatus.BAD_REQUEST.value());
                    standardError.setError("Erro de Validação");
                    // Inclui o nome do atributo, se disponível
                    String fieldName = (error instanceof FieldError) ? ((FieldError) error).getField() : "atributo desconhecido";
                    String errorMessage = String.format("Campo '%s': %s", fieldName, error.getDefaultMessage());

                    standardError.setMessage(errorMessage);
                    standardError.setPath(request.getRequestURI());
                    return standardError;
                })
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(NotFound.class)
    public ResponseEntity<StandardError> handleNotFoundException(NotFound e, HttpServletRequest request) {
        StandardError error = new StandardError();
        error.setTimestamp(Instant.now().toString());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setError("Não encontrado");
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<StandardError> handleUnauthorizedException(UnauthorizedException e, HttpServletRequest request) {
        StandardError error = new StandardError();
        error.setTimestamp(Instant.now().toString());
        error.setStatus(HttpStatus.UNAUTHORIZED.value());
        error.setError("Acesso negado");
        error.setMessage("Usuário ou senha inválida");
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<StandardError> handleInvalidTokenException(InvalidTokenException e, HttpServletRequest request) {
        StandardError error = new StandardError();
        error.setTimestamp(Instant.now().toString());
        error.setStatus(HttpStatus.UNAUTHORIZED.value());
        error.setError("Token inválido");
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<StandardError> handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request) {
        StandardError error = new StandardError();
        error.setTimestamp(Instant.now().toString());
        error.setStatus(HttpStatus.FORBIDDEN.value());
        error.setError("Acesso negado");
        error.setMessage("Você não tem permissão para acessar este recurso.");
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(SamePasswordException.class)
    public ResponseEntity<StandardError> handleSamePasswordException(SamePasswordException e, HttpServletRequest request) {
        StandardError error = new StandardError();
        error.setTimestamp(Instant.now().toString());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setError("Erro de Validação");
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(error);
    }

    @ExceptionHandler(AlreadyExists.class)
    public ResponseEntity<StandardError> handleAlreadyExistsException(AlreadyExists e, HttpServletRequest request) {
        StandardError error = new StandardError();
        error.setTimestamp(Instant.now().toString());
        error.setStatus(HttpStatus.CONFLICT.value());
        error.setError("Dado já existente");
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

}

