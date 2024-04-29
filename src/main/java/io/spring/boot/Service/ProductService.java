package io.spring.boot.Service;

import io.spring.boot.Entity.Admin;
import io.spring.boot.Entity.Category;
import io.spring.boot.Entity.Product;
import io.spring.boot.Repository.AdminRepository;
import io.spring.boot.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> fetchAll(){
        return productRepository.findAll();
    }

    public Product findById(long id){
        return productRepository.findById(id).get();
    }

    public void save(Product product){
        productRepository.save(product);
    }

    
    public List<Product> fetchStatus0(){
        return productRepository.FetchStatus0();
    }

    public List<Product> fetchStatusDiff0() {return  productRepository.FetchStatusDiff0();}

    public List<Product> fetchDesc(){
        return productRepository.FetchDesc();
    }

    public Page<Product> getUsersSortedByLastNameAndPaged(int pageNumber) {
//        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        Pageable pageable = PageRequest.of(pageNumber-1, 5);
        return productRepository.findAll(pageable);
    }
    
    public List<Product> SearchProduct(String input){
        return productRepository.searchProduct(input);
    }


    public List<Product> findDistinctByCategoryAndPriceRange(Long idCategory, BigDecimal minPrice, BigDecimal maxPrice){
        return productRepository.findDistinctByCategoryAndPriceRange(idCategory,minPrice,maxPrice);
    }
}
