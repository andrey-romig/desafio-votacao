package br.com.cooperativa.pauta.v1.exception;

public class CPFValidationException extends RuntimeException {
    public CPFValidationException(String message) {
        super(message);
    }
}
