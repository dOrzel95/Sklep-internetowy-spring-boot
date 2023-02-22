package com.example.MontanaShop.Exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(long id){
        super("Nie ma klienta o id "+id);
    }
}
