package com.example.MontanaShop.Exception;

public class FailedToAddResource extends RuntimeException {
    public FailedToAddResource() {
        super("Nie udało się dodać zasobu");
    }
}
