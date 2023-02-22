package com.example.MontanaShop.Repository;

import com.example.MontanaShop.Model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Token findByValue(String value);
}
