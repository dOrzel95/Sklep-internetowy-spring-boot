package com.example.MontanaShop.Exception;

public class NoResultsException extends RuntimeException {
    public NoResultsException() {
        super("Btak wyników");
    }
}
