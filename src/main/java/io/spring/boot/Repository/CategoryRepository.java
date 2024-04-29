package io.spring.boot.Repository;


import io.spring.boot.Entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    @Query("select c from Category c where c.status = 0")
    List<Category> findStatus0 ();

//    @Query(value = "select c from Category  c where c.status = 0")
//    Page<Category> findAllNewsInPerCategoryMaster(Pageable pageable, Sort sort);


    @Query("select c from Category c where c.status = 0")
    Page<Category> findAll(Pageable pageable);

    @Query("select c from Category c where c.parent_id =0 and c.status =0" )
    List<Category> findAllParent0();


    @Query("select c from Category c where c.parent_id !=0 and c.status =0" )
    List<Category> findAllParenExcept0();
}
