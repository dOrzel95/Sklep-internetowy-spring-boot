package com.example.MontanaShop.Exception;

public class RefreshTokenExpiredException extends RuntimeException {
    public RefreshTokenExpiredException() {
        super("Brak autoryzacji");
    }
}
