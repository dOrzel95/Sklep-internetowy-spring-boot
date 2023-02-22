package com.example.MontanaShop.Controller;


import com.example.MontanaShop.Model.*;
import com.example.MontanaShop.Service.CategoryManager;
import com.example.MontanaShop.Service.ClientManager;
import com.example.MontanaShop.Service.ProductManager;
import com.example.MontanaShop.Service.UploadManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.ArrayList;
import java.util.List;


@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/montanashop/product")
public class ProductController {

    private ProductManager productManager;
    private ClientManager clientManager;
    private UploadManager uploadManager;
    private CategoryManager categoryManager;
    @Autowired
    public ProductController(ProductManager productManager, ClientManager clientManager, UploadManager uploadManager, CategoryManager categoryManager) {
        this.productManager = productManager;
        this.clientManager = clientManager;
        this.uploadManager = uploadManager;
        this.categoryManager = categoryManager;
    }

    @GetMapping()
    public Prodyct getById(@RequestParam long id){
        return productManager.getById(id).get();
    }
    @GetMapping("/all")
        public HttpEntity<List<Prodyct>> geetAll(){
            return new ResponseEntity<List<Prodyct>>((List<Prodyct>) productManager.getAll(),new HttpHeaders(), HttpStatus.OK);
    }
    @GetMapping("/other-all")
    public ResponseEntity<List<Prodyct>> getAllByUserNot(){
        return new ResponseEntity<List<Prodyct>>((List<Prodyct>) productManager.getAllByUserNot(clientManager.getAuthenticatedClient().getId()),new HttpHeaders(), HttpStatus.OK);
    }
    @GetMapping("/user-all")
    public HttpEntity<List<Prodyct>> getAllByUser(){
        return new ResponseEntity<List<Prodyct>>((List<Prodyct>) productManager.getAllByUser(clientManager.getAuthenticatedClient().getId()),new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/all-by-category")
    public HttpEntity<List<Prodyct>> getAllByCategory(@RequestParam long id){
        return new ResponseEntity<List<Prodyct>>((List<Prodyct>) productManager.getAllByCategory(id),new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/all-by-name")
    public HttpEntity<List<Prodyct>> getByName(@RequestParam String name){
        return new ResponseEntity<List<Prodyct>>((List<Prodyct>) productManager.getByName(name),new HttpHeaders(), HttpStatus.OK);
    }
    @GetMapping("/all-by-category-and-name")
    public HttpEntity<List<Prodyct>> getAllByNameAndCategory(@RequestParam String name, @RequestParam long id){
        System.out.println(name+ " " +id);
        return new ResponseEntity<List<Prodyct>>((List<Prodyct>) productManager.getAllByNameAndCategory(id,name),new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/user-all-by-category")
    public HttpEntity<List<Prodyct>> getAllByCategoryAndUser(@RequestParam long id){
        return new ResponseEntity<List<Prodyct>>((List<Prodyct>) productManager.getAllByCategoryAndUser(id, clientManager.getAuthenticatedClient().getId()),new HttpHeaders(), HttpStatus.OK);
    }
    @GetMapping("/other-all-by-category")
    public HttpEntity<List<Prodyct>> getAllByCategoryAndUserNot(@RequestParam long id){
        return new ResponseEntity<List<Prodyct>>((List<Prodyct>) productManager.getAllByCategoryAndUserNot(id, clientManager.getAuthenticatedClient().getId()),new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/user-all-by-name")
    public HttpEntity<List<Prodyct>> getByNameAndUser(@RequestParam String name){
        return new ResponseEntity<List<Prodyct>>((List<Prodyct>) productManager.getByNameAndUser(name, clientManager.getAuthenticatedClient().getId()),new HttpHeaders(), HttpStatus.OK);
    }
    @GetMapping("/other-all-by-name")
    public HttpEntity<List<Prodyct>> getByNameAndUserNot(@RequestParam String name){
        return new ResponseEntity<List<Prodyct>>((List<Prodyct>) productManager.getByNameAndUserNot(name, clientManager.getAuthenticatedClient().getId()),new HttpHeaders(), HttpStatus.OK);
    }
    @GetMapping("/user-all-by-category-and-name")
    public HttpEntity<List<Prodyct>> getAllByNameAndCategoryAndUser(@RequestParam String name, @RequestParam long id){
        return new ResponseEntity<List<Prodyct>>((List<Prodyct>) productManager.getAllByNameAndCategoryAndUser(name,id, clientManager.getAuthenticatedClient().getId()),new HttpHeaders(), HttpStatus.OK);
    }
    @GetMapping("/other-all-by-category-and-name")
    public HttpEntity<List<Prodyct>> getAllByNameAndCategoryAndUserNot(@RequestParam String name, @RequestParam long id){
        return new ResponseEntity<List<Prodyct>>((List<Prodyct>) productManager.getAllByNameAndCategoryAndUserNot(name,id, clientManager.getAuthenticatedClient().getId()),new HttpHeaders(), HttpStatus.OK);
    }



    @PostMapping("/add")
    public ResponseEntity add(@RequestParam("product") String prodyct, @RequestParam("idCategory") String id,
             @RequestPart("file") MultipartFile file) throws JsonProcessingException {

        ResponseEntity entity = null;
        ObjectMapper mapper = new ObjectMapper();
        Prodyct prodyct1 = mapper.readValue(prodyct, Prodyct.class);
        long idCategory = Long.parseLong(id);
        Category category = categoryManager.getById(idCategory);
        prodyct1.setCategory(category);
        String path = uploadManager.uploadFile(file);
        prodyct1.setClient(clientManager.getAuthenticatedClient());
        prodyct1.setPhoto(path);
        if (productManager.add(prodyct1)){
            entity = new ResponseEntity(HttpStatus.CREATED);
        }
        else{
            entity = new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return entity;
    }
    @PutMapping("/update")
    public ResponseEntity update(@RequestParam("product") String prodyct, @RequestParam("idCategory") String id,
                              @RequestPart("file") MultipartFile file) throws JsonProcessingException {

        ResponseEntity entity = null;
            ObjectMapper mapper = new ObjectMapper();
            Prodyct prodyct1 = mapper.readValue(prodyct, Prodyct.class);
        Prodyct updateProduct = productManager.getById(prodyct1.getId()).get();

        long idCategory = Long.parseLong(id);
        Category category = categoryManager.getById(idCategory);
        updateProduct.setName(prodyct1.getName());
        updateProduct.setQuantity(prodyct1.getQuantity());
        updateProduct.setPrice(prodyct1.getPrice());
        updateProduct.setCategory(category);
        String path = uploadManager.uploadFile(file);
        updateProduct.setPhoto(path);
        if (productManager.update(updateProduct)){
            entity = new ResponseEntity(HttpStatus.OK);
        }
        else{
            entity = new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return entity;
    }
    @PostMapping("/update-all")
    public ResponseEntity  updateAll(@RequestBody List<Prodyct> prodycts){
        ResponseEntity entity = null;

        List<Long> ids = new ArrayList<>();

        int z = 0;
        prodycts.forEach(e->ids.add(e.getId()));
        List<Prodyct> prodyctsUpdate = (List<Prodyct>) productManager.getAllById(ids);
        for (int i = 0;i<prodyctsUpdate.size();i++){
            for (int j = 0;j<prodyctsUpdate.size();j++){
            if (prodyctsUpdate.get(i).getId()==prodycts.get(j).getId()) {
                prodyctsUpdate.get(i).setQuantity(prodyctsUpdate.get(i).getQuantity() + prodycts.get(j).getQuantity());
                }
            }
        }
        if (productManager.updateAll(prodyctsUpdate)){
            entity = new ResponseEntity(HttpStatus.CREATED);
        }
        else{
            entity = new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return entity;
    }
    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestParam long id){
        ResponseEntity entity = null;
        if( productManager.delete(id)){
            entity = new ResponseEntity(HttpStatus.OK);
        }else {
            entity = new ResponseEntity(HttpStatus.NOT_FOUND);

        }
        return entity;
    }
}
