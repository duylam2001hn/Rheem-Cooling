package io.spring.boot.Service;




import io.spring.boot.Entity.Category_product;

import io.spring.boot.Repository.Category_productRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class Category_productService {

    @Autowired
    private Category_productRepository category_productRepository;

    public List<Category_product> fetchAll(){
        return category_productRepository.findAll();
    }

    public Category_product findById(long id){
        return category_productRepository.findById(id).get();
    }

    public void save(Category_product category){
        category_productRepository.save(category);
    }

    public void delete(Long id){
        category_productRepository.deleteById(id);
    }




}
