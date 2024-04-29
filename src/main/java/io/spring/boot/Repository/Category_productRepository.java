package io.spring.boot.Repository;


import io.spring.boot.Entity.Category_product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Category_productRepository extends JpaRepository<Category_product,Long> {
    
}
