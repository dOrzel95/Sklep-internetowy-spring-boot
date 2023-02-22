package com.example.MontanaShop.Service;


import com.example.MontanaShop.Model.Client;
import com.example.MontanaShop.Model.PositionShopping;
import com.example.MontanaShop.Model.Prodyct;
import com.example.MontanaShop.Model.ShoppingCart;
import com.example.MontanaShop.Repository.ProductRepo;
import javassist.NotFoundException;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import java.io.*;
import java.util.Optional;



@Service
public class ProductManager {

    private ProductRepo productRepo;
    private ClientManager clientManager;

    @Autowired
    public ProductManager(ProductRepo productRepo, ClientManager clientManager) {
        this.productRepo = productRepo;
        this.clientManager = clientManager;
    }


    public Optional<Prodyct> getById(@RequestParam long id){
        return productRepo.findById(id);

    }
    public Iterable<Prodyct> getAll(){
        return productRepo.findAll();
    }
    public Iterable<Prodyct> getAllByUser(long id){
        return productRepo.findAllByClientId(id);
    }
    public boolean add(@RequestBody Prodyct prodyct){
        boolean add=false;
        try {
            prodyct.setClient(clientManager.getAuthenticatedClient());
            productRepo.save(prodyct);
            add = true;
        }
        catch (Exception e){
            add=false;
            e.printStackTrace();
        }
        return add;
    }

    public Iterable<Prodyct> getAllByCategory(long id)
    {
        return productRepo.findAllByCategoryId(id);
    }

    public Iterable<Prodyct> getByName(String name)
    {
        return productRepo.findAllByName(name);
    }
    public Iterable<Prodyct> getAllByUserNot(long id)
    {
        return productRepo.findAllByClientIdNot(id);
    }



    public Iterable<Prodyct> getAllByNameAndCategory(long id, String name)
    {
        productRepo.findAllByNameAndCategoryId(name,id).forEach(e-> System.out.println(e.getName()));
        return productRepo.findAllByNameAndCategoryId(name,id);
    }
    public Iterable<Prodyct> getAllByCategoryAndUser(long id, long userId)
    {
        return productRepo.findAllByCategoryIdAndClientId(id,userId);
    }
    public Iterable<Prodyct> getAllByCategoryAndUserNot(long id, long userId)
    {
        return productRepo.findAllByCategoryIdAndClientIdNot(id,userId);
    }
    public Iterable<Prodyct> getByNameAndUser(String name, long id)
    {
        return productRepo.findAllByNameAndClientId(name,id);
    }
    public Iterable<Prodyct> getByNameAndUserNot(String name, long id)
    {
        return productRepo.findAllByNameAndClientIdNot(name,id);
    }
    public Iterable<Prodyct> getAllByNameAndCategoryAndUser(String name, long id, long userId)
    {
        return productRepo.findAllByNameAndCategoryIdAndClientId(name,id,userId);

    }
    public Iterable<Prodyct> getAllByNameAndCategoryAndUserNot(String name, long id, long userId)
    {
        return productRepo.findAllByNameAndCategoryIdAndClientIdNot(name,id,userId);

    }
    public Iterable<Prodyct> getAllById(Iterable<Long> ids)
    {
        return productRepo.findAllById(ids);
    }

    public boolean update(Prodyct prodyct){
        System.out.println(prodyct.getQuantity());
        boolean update=false;
        try {
            productRepo.save(prodyct);
            update = true;
        }
        catch (Exception e){
            update=false;
            e.printStackTrace();
        }
        return update;
    }
    public boolean updateAll(Iterable<Prodyct> prodycts){
        boolean update=false;
        try {
            productRepo.saveAll(prodycts);
            update = true;
        }
        catch (Exception e){
            update=false;
            e.printStackTrace();
        }
        return update;
    }
    public boolean delete(@RequestParam long id){
        boolean delete=false;
        try {
            productRepo.deleteById(id);

            delete = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return delete;
    }

}
