package com.example.MontanaShop.Service;

import com.example.MontanaShop.Model.ShoppingCart;
import com.example.MontanaShop.Repository.ShoppingCartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@Service
public class ShoppingCartManager {
    private ShoppingCartRepo shoppingCartRepo;

    @Autowired
    public ShoppingCartManager(ShoppingCartRepo shoppingCartRepo) {
        this.shoppingCartRepo = shoppingCartRepo;
    }
    public ShoppingCart getById(@RequestParam long id){
        return shoppingCartRepo.findById(id).get();
    }
    public Iterable<ShoppingCart> getAll(){
        return shoppingCartRepo.findAll();
    }
    public boolean add(@RequestParam ShoppingCart shoppingCart){
        boolean add=false;
        try {
            shoppingCartRepo.save(shoppingCart);
            add = true;
        }
        catch (Exception e){
            add = false;
            e.printStackTrace();
        }
        return add;
    }
    public boolean update(@RequestBody ShoppingCart shoppingCart){
        boolean update=false;
        try {
            shoppingCartRepo.save(shoppingCart);
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
            shoppingCartRepo.deleteById(id);

            delete = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return delete;
    }
}
