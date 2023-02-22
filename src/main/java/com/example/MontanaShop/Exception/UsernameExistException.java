package com.example.MontanaShop.Exception;

public class UsernameExistException extends RuntimeException{
    private final String id = "login";
    public UsernameExistException(String username) {
        super("Podany login: " +username+  " jest zajęty");
    }

    public String getId() {
        return id;
    }
}
