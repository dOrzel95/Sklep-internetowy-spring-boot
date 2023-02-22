package com.example.MontanaShop.Controller;

import com.example.MontanaShop.Model.Category;
import com.example.MontanaShop.Service.CategoryManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/montanashop/category")
public class CategoryController {

    private CategoryManager categoryManager;

    public CategoryController(CategoryManager categoryManager) {
        this.categoryManager = categoryManager;
    }
    @GetMapping
    public Category getById(@RequestParam long id){
        return categoryManager.getById(id);
    }
    @GetMapping("/all")
    public Iterable<Category> getAll(){
        return categoryManager.getAll();
    }
    @PostMapping("/add")
    public ResponseEntity add(@RequestBody Category category){
        ResponseEntity entity;
        if(categoryManager.addCategory(category)){
            entity = new ResponseEntity(HttpStatus.CREATED);
        }else {
            entity = new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return entity;
    }
    @PutMapping("/update")
    public ResponseEntity update(@RequestBody Category category)
    {
        ResponseEntity entity;
        if(categoryManager.updateCategory(category)){
            entity = new ResponseEntity(HttpStatus.OK);
        }else {
            entity = new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return entity;
    }
    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestParam long id){
        {
            ResponseEntity entity;
            if(categoryManager.deleteCategory(id)){
                entity = new ResponseEntity(HttpStatus.OK);
            }else {
                entity = new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            return entity;
        }
    }
}
