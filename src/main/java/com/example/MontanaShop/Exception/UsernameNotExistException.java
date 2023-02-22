package com.example.MontanaShop.Exception;

public class UsernameNotExistException extends RuntimeException {
    private final String id = "login";
    public UsernameNotExistException() {
        super("Użytkownik o padanym loginie nie istenieje");
    }

    public String getId() {
        return id;
    }
}
