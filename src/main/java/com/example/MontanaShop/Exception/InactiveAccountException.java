package com.example.MontanaShop.Exception;

public class InactiveAccountException extends RuntimeException {
    private final String id = "account";

    public InactiveAccountException() {
        super("Twoje konto jest nie aktywne sprawdż swój adres email");
    }

    public String getId() {
        return id;
    }
}
