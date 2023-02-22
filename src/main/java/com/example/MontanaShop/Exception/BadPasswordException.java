package com.example.MontanaShop.Exception;

public class BadPasswordException extends RuntimeException {
    private final String  id = "password";

    public BadPasswordException() {
        super("Podane hasło jest nie prawidłowe");
    }

    public String getId() {
        return id;
    }
}
