package com.example.MontanaShop.Repository;

import com.example.MontanaShop.Model.PositionShopingJoinProduct;
import com.example.MontanaShop.Model.PositionShopping;
import com.example.MontanaShop.Model.ShoppingCart;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionShoppingCartRepo extends CrudRepository<PositionShopping,Long> {
    @Query("SELECT NEW com.example.MontanaShop.Model.PositionShopingJoinProduct( p.id, p.name, p.photo, SUM(p.price) AS price, COUNT(p) AS quantity)" +
            " FROM PositionShopping ps JOIN Prodyct p ON ps.prodyct = p WHERE ps.shoppingCart = :shoppingCart GROUP BY p")
    Iterable<PositionShopingJoinProduct> findAllByShoppingCart(@Param("shoppingCart") ShoppingCart shoppingCart);

    long countAllByShoppingCartId(long id);
    @Override
    <S extends PositionShopping> S save(S entity);

    @Query("SELECT NEW com.example.MontanaShop.Model.PositionShopingJoinProduct( p.id, p.name, p.photo, SUM(p.price) AS price, COUNT(p) AS quantity)" +
            " FROM PositionShopping ps JOIN Prodyct p ON ps.prodyct = p WHERE ps.id= :id GROUP BY p")
    Iterable<PositionShopingJoinProduct> findPositionShoppigById(@Param("id") long id);
    @Query("SELECT NEW com.example.MontanaShop.Model.PositionShopingJoinProduct( p.id, p.name, p.photo, SUM(p.price) AS price, COUNT(p) AS quantity)" +
            " FROM PositionShopping ps JOIN Prodyct p ON ps.prodyct = p GROUP BY p")
    Iterable<PositionShopingJoinProduct> findAllPostionShopping();
}