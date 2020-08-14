package com.adidas.api.exception;

public class CurrencyException extends RuntimeException{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int statusCode;

    public CurrencyException() {
        super("Currency service exception");
    }

    public CurrencyException(String message) {
        super(message);
    }

    public CurrencyException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }


    public CurrencyException(int statusCode, String message, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
