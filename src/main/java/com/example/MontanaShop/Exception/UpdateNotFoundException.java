package com.example.MontanaShop.Exception;

public class UpdateNotFoundException extends RuntimeException {
    public UpdateNotFoundException() {
        super("Nie udało się zaaktualizować zasobu");
    }
}
