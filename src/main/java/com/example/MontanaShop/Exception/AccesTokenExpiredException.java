package com.example.MontanaShop.Exception;

public class AccesTokenExpiredException extends RuntimeException {
    public AccesTokenExpiredException() {
        super("Brak autoryzacji");
    }
}