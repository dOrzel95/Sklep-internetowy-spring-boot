package com.example.MontanaShop.Exception;

public class ExistShoppingCartException extends RuntimeException {

    public ExistShoppingCartException() {
        super("Wybrany klient ma już przypisany koszyk");
    }
}
