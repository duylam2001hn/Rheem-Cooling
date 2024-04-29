package io.spring.boot.Service;


import io.spring.boot.Entity.Category;

import io.spring.boot.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> fetchAll(){
        return categoryRepository.findAll();
    }

    public Category findById(long id){
        return categoryRepository.findById(id).get();
    }

    public void save(Category category){
        categoryRepository.save(category);
    }



    public List<Category> fetchStatus0() { return categoryRepository.findStatus0();}

    public Page<Category> getUsersSortedByLastNameAndPaged(int pageNumber) {
//        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        Pageable pageable = PageRequest.of(pageNumber-1, 5);
        return categoryRepository.findAll(pageable);
    }

    public List<Category> ListCateParent0(){
        return categoryRepository.findAllParent0();
    }

    public List<Category> ListCateParentExcept0(){
        return categoryRepository.findAllParenExcept0();
    }

}
