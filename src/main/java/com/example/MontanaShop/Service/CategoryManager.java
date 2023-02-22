package com.example.MontanaShop.Service;

import com.example.MontanaShop.Model.Category;
import com.example.MontanaShop.Repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class CategoryManager {


    private CategoryRepo categoryRepo;


    @Autowired
    public CategoryManager(CategoryRepo categoryRepo  ) {
        this.categoryRepo = categoryRepo;


    }


    public Category getById(@RequestParam long id){
        return categoryRepo.findById(id).get();
    }
    public Iterable<Category> getAll(){
        return categoryRepo.findAll();
    }
    public boolean addCategory(@RequestBody Category category){
        boolean add=false;
        try {
            categoryRepo.save(category);
            add = true;
        }
        catch (Exception e){
            add = false;
            e.printStackTrace();
        }
        return add;
    }
    public boolean updateCategory(@RequestBody Category category){
        boolean update=false;
        try {
            categoryRepo.save(category);
            update = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return update;
    }
    public boolean deleteCategory(@RequestParam long id){
        boolean delete=false;
        try {
            categoryRepo.deleteById(id);

            delete = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return delete;
    }
}
