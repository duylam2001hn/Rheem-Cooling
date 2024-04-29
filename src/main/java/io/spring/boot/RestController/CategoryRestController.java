package io.spring.boot.RestController;

import io.spring.boot.Entity.Category;
import io.spring.boot.Entity.JsonResponse;
import io.spring.boot.Entity.Login;
import io.spring.boot.Service.CategoryService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/admin/category")
public class CategoryRestController {

    JsonResponse jsonResponse = new JsonResponse();
    Login login = new Login();
    private CategoryService categoryService;

    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping(value = "/list")
    public JsonResponse CategoryList(){

        List<Category> categoryList = categoryService.fetchStatus0();


        jsonResponse.setResult(categoryList);
        return jsonResponse;
    }



    @PostMapping("/save/{IdEdit}")
    public JsonResponse CategoryAdd(@Valid @RequestBody Category category, BindingResult br , @PathVariable String IdEdit, HttpSession httpSession){


        System.out.println(IdEdit);


        Integer checkLogin =  login.checkSessionAPI(httpSession,"admin");



        if(checkLogin==0){
            jsonResponse.setStatus("error");
            return jsonResponse;
        }

        if(br.hasErrors()){
            jsonResponse.setStatus("error");
            return jsonResponse;
        }
        jsonResponse.setStatus("success");


        //Save Edit
        if(!IdEdit.equals("add")){
            jsonResponse.setStatus("EditSuccess");
            Long idCategory = Long.valueOf(IdEdit);
            Category categoryEdit = categoryService.findById(idCategory);

            categoryEdit.setName(category.getName());
            categoryEdit.setParent_id(category.getParent_id());
            categoryEdit.setStatus(category.getStatus());

            categoryService.save(categoryEdit);
            return jsonResponse;

        }

        //Save add
        categoryService.save(category);
        System.out.println(category.getName());

        return jsonResponse;
    }

    @GetMapping("/get/{id}")
    public JsonResponse CategoryGet(@PathVariable Long id){


        Category category = categoryService.findById(id);

        jsonResponse.setStatus("success");
        jsonResponse.setResult(category);
        return jsonResponse;
    }


    @GetMapping("/delete")
    public JsonResponse setNull(@RequestParam String listIdCheck,HttpSession httpSession){

        Integer checkLogin =  login.checkSessionAPI(httpSession,"admin");


        if(checkLogin==0){
            jsonResponse.setStatus("error");
            return jsonResponse;
        }

        String [] listId = listIdCheck.split(",");

        for (String item: listId) {
            Category category = categoryService.findById(Long.valueOf(item));
            category.setStatus(1);
            categoryService.save(category);
        }

        jsonResponse.setStatus("delete");

        return jsonResponse;
    }


}
