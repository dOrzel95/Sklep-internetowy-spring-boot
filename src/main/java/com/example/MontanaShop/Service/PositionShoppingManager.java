package com.example.MontanaShop.Service;


import com.example.MontanaShop.Model.*;
import com.example.MontanaShop.Repository.PositionShoppingCartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class PositionShoppingManager {
    PositionShoppingCartRepo positionShoppingCartRepo;
    EntityManager entityManager;


    @Autowired
    public PositionShoppingManager(PositionShoppingCartRepo positionShoppingCartRepo, EntityManager entityManager) {
        this.positionShoppingCartRepo = positionShoppingCartRepo;
        this.entityManager = entityManager;
    }

    public PositionShopping getById(@RequestParam long id){
        return positionShoppingCartRepo.findById(id).get();
    }

    public Iterable<PositionShopingJoinProduct> getAll(){
        return positionShoppingCartRepo.findAllPostionShopping();
    }
    public Iterable<PositionShopingJoinProduct> getPositionShoppigById(long id){
        return positionShoppingCartRepo.findPositionShoppigById(id);
    }

    public List<PositionShopingJoinProduct> getAllByShoppingcart(ShoppingCart shoppingCart){
        List<PositionShopingJoinProduct> positions = null;
        try {
            positions = (List<PositionShopingJoinProduct>) positionShoppingCartRepo.findAllByShoppingCart(shoppingCart);
        }catch (Exception e){
            e.printStackTrace();
        }
        return positions;
    }
    public long getAllByShoppingcartQuantity(long id){
        long quantity = 0;
        try {
            quantity =  positionShoppingCartRepo.countAllByShoppingCartId(id);
        }catch (Exception e){
            quantity = 0;
            e.printStackTrace();
        }
        return quantity;
    }


    public boolean add(PositionShopping positionShopping){
        System.out.println("DDXXX");
        boolean add=false;
        try {
            positionShoppingCartRepo.save(positionShopping);
            add = true;
        }
        catch (Exception e){
            add = false;
            e.printStackTrace();
        }
        return add;
    }
    public boolean update(@RequestBody PositionShopping positionShopping){
        boolean update=false;
        try {
            positionShoppingCartRepo.save(positionShopping);
            update = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return update;
    }
    public boolean delete(@RequestParam long id){
        boolean delete=false;
        try {
            positionShoppingCartRepo.deleteById(id);

            delete = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return delete;
    }
    public boolean deleteAll(){
        boolean delete=false;
        try {
            positionShoppingCartRepo.deleteAll();

            delete = true;
        }
        catch (Exception e){
            delete = false;
            e.printStackTrace();
        }
        return delete;
    }
}
