package io.spring.boot.Repository;


import io.spring.boot.Entity.Category;
import io.spring.boot.Entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query(value = "select p from Product p where p.status = 0")
    List<Product> FetchStatus0();

    @Query(value = "select p from Product p where p.status >0")
    List<Product> FetchStatusDiff0();

    @Query(value = "select p from Product p order by p.id desc ")
    List<Product> FetchDesc();


    @Query("select p from Product p where p.status = 0")
    Page<Product> findAll(Pageable pageable);

    @Query(value = "SELECT DISTINCT p FROM Product p JOIN p.categoryProducts cp JOIN cp.category c WHERE p.status = 0 AND (p.name LIKE CONCAT('%', :input, '%') OR p.description LIKE CONCAT('%', :input, '%') OR c.name LIKE CONCAT('%', :input, '%'))")
    List<Product> searchProduct(@Param("input") String input);


    @Query("SELECT DISTINCT p FROM Product p JOIN p.categoryProducts c WHERE c.category.id = :categoryId AND p.price >= :minPrice AND p.price <= :maxPrice")
    List<Product> findDistinctByCategoryAndPriceRange(@Param("categoryId") Long categoryId, @Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);

}
