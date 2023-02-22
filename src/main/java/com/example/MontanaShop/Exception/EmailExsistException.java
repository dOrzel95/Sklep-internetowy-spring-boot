package com.example.MontanaShop.Exception;

public class EmailExsistException extends  RuntimeException{
    private final String id = "email";
    public EmailExsistException(String email) {
        super("Podany emial: "+email+" jest zajęty :");

    }

    public String getId() {
        return id;
    }
}
