package com.example.MontanaShop.Repository;

import com.example.MontanaShop.Model.PositionShopingJoinProduct;
import com.example.MontanaShop.Model.PositionShopping;
import com.example.MontanaShop.Model.Prodyct;
import com.example.MontanaShop.Model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Prodyct, Long> {
    Iterable<Prodyct> findAllByCategoryId(long id);
    Iterable<Prodyct> findAllByCategoryIdAndClientId(long id, long clientId);
    Iterable<Prodyct> findAllByCategoryIdAndClientIdNot(long id, long clientId);
    Iterable<Prodyct> findAllByClientId(long id);
    Iterable<Prodyct> findAllByClientIdNot(long id);
    Iterable<Prodyct> findAllByName(String name);
    Iterable<Prodyct> findAllByNameAndClientId(String name, long id);
    Iterable<Prodyct> findAllByNameAndClientIdNot(String name, long id);
    Iterable<Prodyct> findAllByNameAndCategoryId(String name, long id);
    Iterable<Prodyct> findAllByNameAndCategoryIdAndClientId(String name, long id, long clientId);
    Iterable<Prodyct> findAllByNameAndCategoryIdAndClientIdNot(String name, long id, long clientId);

}
