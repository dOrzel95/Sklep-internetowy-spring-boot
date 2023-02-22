package com.example.MontanaShop.Repository;

import com.example.MontanaShop.Model.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends CrudRepository<Category, Long> {
//    Category findId(long id);
}
