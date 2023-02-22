package com.example.MontanaShop.Controller;

import com.example.MontanaShop.Model.*;
import com.example.MontanaShop.Service.ClientManager;
import com.example.MontanaShop.Service.PositionShoppingManager;
import com.example.MontanaShop.Service.ProductManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import reactor.util.function.Tuples;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("montanashop/positionshopping")
public class PositionShoppingController {

    private PositionShoppingManager positionShoppingManager;
    private ClientManager clientManager;
    private ProductManager productManager;


    @Autowired
    public PositionShoppingController(PositionShoppingManager positionShoppingManager, ClientManager clientManager, ProductManager productManager) {
        this.positionShoppingManager = positionShoppingManager;
        this.clientManager = clientManager;
        this.productManager= productManager;

    }


    @GetMapping("/all")
    public HttpEntity<List<PositionShopingJoinProduct>> getAll(){

        return new ResponseEntity<List<PositionShopingJoinProduct>>((List<PositionShopingJoinProduct>) positionShoppingManager.getAll(),new HttpHeaders(), HttpStatus.OK);
    }
    @GetMapping()
    public HttpEntity<List<PositionShopingJoinProduct>> getPositionShoppigById(long id){

        return new ResponseEntity<List<PositionShopingJoinProduct>>((List<PositionShopingJoinProduct>) positionShoppingManager.getPositionShoppigById(id),new HttpHeaders(), HttpStatus.OK);
    }


    @GetMapping("/quantity-by-shoppingcart")
    public ResponseEntity<Long> getQuantityByShopingcart(){
        return new ResponseEntity<Long>(positionShoppingManager.getAllByShoppingcartQuantity(clientManager.getAuthenticatedClient().getShoppingCart().getId()),new HttpHeaders(), HttpStatus.OK);
    }


    @GetMapping("/all-by-shoppingcart")
    public HttpEntity<Iterable<PositionShopingJoinProduct>> getAllByShopingcart(){
        return new ResponseEntity<Iterable<PositionShopingJoinProduct>>(positionShoppingManager.getAllByShoppingcart(clientManager.getAuthenticatedClient().getShoppingCart()),new HttpHeaders(), HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity add(@RequestBody PositionShopping positionShopping) throws JsonProcessingException {
        Prodyct prodyct = productManager.getById(positionShopping.getProdyct().getId()).get();
        prodyct.setQuantity(prodyct.getQuantity()-1);
        ResponseEntity entity = null;
        if(positionShoppingManager.add(positionShopping) && productManager.update(prodyct)){
           entity = new ResponseEntity(HttpStatus.CREATED);
        }else{
            entity = new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return entity;
    }
    @PutMapping("/update")
    public ResponseEntity update(@RequestBody PositionShopping positionShopping) throws JsonProcessingException {
        ResponseEntity entity = null;
        if (positionShoppingManager.update(positionShopping)){
            entity = new ResponseEntity<String>(HttpStatus.OK);
        }else{
            entity = new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
        return entity;
    }

    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestParam long id){
        ResponseEntity entity;
        if (positionShoppingManager.delete(id)){
            entity = new ResponseEntity<String>(HttpStatus.OK);
        }else{
            entity = new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
        return entity;
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<String> deleteAll(){
        ResponseEntity<String> entity = null;
        if(positionShoppingManager.deleteAll()){
            entity = new ResponseEntity<String>("s", new HttpHeaders(), HttpStatus.OK);
        }else{
            entity = new ResponseEntity<String>("s", new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        return entity;
    }
}
