package com.example.MontanaShop.Controller;


import com.example.MontanaShop.Exception.BadPasswordException;
import com.example.MontanaShop.Exception.ExistShoppingCartException;
import com.example.MontanaShop.Model.Client;
import com.example.MontanaShop.Model.Prodyct;
import com.example.MontanaShop.Model.ShoppingCart;
import com.example.MontanaShop.Service.ClientManager;
import com.example.MontanaShop.Service.ShoppingCartManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.DataInput;
import java.io.IOException;

@RestController
@RequestMapping("/montanashop/shoppingcart")
public class ShoppingCartController {

    private ShoppingCartManager shoppingCartManager;
    private ClientManager clientManager;

    @Autowired
    public ShoppingCartController(ShoppingCartManager shoppingCartManager, ClientManager clientManager) {
        this.shoppingCartManager = shoppingCartManager;
        this.clientManager = clientManager;
    }
    @GetMapping
    public ShoppingCart getById(@RequestParam long id){
        return shoppingCartManager.getById(id);
    }
    @GetMapping("/all")
    public Iterable<ShoppingCart> getAll(){
        return shoppingCartManager.getAll();
    }
    @PostMapping("/add")
    public ResponseEntity add(@RequestBody String shoppingCart) throws IOException { ;
        ObjectMapper mapper = new ObjectMapper();
        ShoppingCart shoppingCart1 = mapper.readValue(shoppingCart, ShoppingCart.class);
        Client client = clientManager.getById(shoppingCart1.getClient().getId()).get();
        shoppingCart1.setClient(client);

        ResponseEntity entity;
        if (shoppingCartManager.add(shoppingCart1)){
            entity = new ResponseEntity(HttpStatus.OK);
        }else {
            entity = new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return entity;
    }
    @PutMapping("/update")
    public ResponseEntity update(@RequestBody String shoppingCart) throws JsonProcessingException {
        ResponseEntity entity = null;
        ObjectMapper mapper = new ObjectMapper();
        ShoppingCart shoppingCart1 = mapper.readValue(shoppingCart, ShoppingCart.class);
        ShoppingCart shoppingCart2 = shoppingCartManager.getById(shoppingCart1.getId());
        System.out.println(shoppingCart2.getClient().getId());
        shoppingCartManager.add(new ShoppingCart(shoppingCart2.getClient()));
        Client client = clientManager.getById(shoppingCart1.getClient().getId()).get();
        if (client.getShoppingCart()!=null){
            shoppingCartManager.delete(client.getShoppingCart().getId());
        }
        shoppingCart1.setClient(client);
        if (shoppingCartManager.update(shoppingCart1)){
            entity = new ResponseEntity(HttpStatus.OK);
        }else {
            entity = new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return entity;
    }
    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestParam long id){
        ResponseEntity entity;
        if (shoppingCartManager.delete(id)){
            entity = new ResponseEntity(HttpStatus.OK);
        }else {
            entity = new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return entity;
    }
}
