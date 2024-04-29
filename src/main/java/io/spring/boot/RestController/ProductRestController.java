package io.spring.boot.RestController;

import io.spring.boot.Entity.Category;
import io.spring.boot.Entity.JsonResponse;
import io.spring.boot.Entity.Login;
import io.spring.boot.Entity.Product;
import io.spring.boot.Service.CategoryService;
import io.spring.boot.Service.ProductService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/admin/product")
public class ProductRestController {
    JsonResponse jsonResponse = new JsonResponse();
    Login login = new Login();
    private ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping(value = "/list")
    public JsonResponse ProductList(HttpSession httpSession){
        Integer checkLogin =  login.checkSessionAPI(httpSession,"admin");


        if(checkLogin==0){
            jsonResponse.setStatus("error");
            return jsonResponse;
        }

        List<Product> productList = productService.fetchStatus0();


        jsonResponse.setResult(productList);
        return jsonResponse;
    }





    @GetMapping("/delete")
    public JsonResponse setNull(@RequestParam String listIdCheck, HttpSession httpSession){

        Integer checkLogin =  login.checkSessionAPI(httpSession,"admin");


        if(checkLogin==0){
            jsonResponse.setStatus("error");
            return jsonResponse;
        }

        String [] listId = listIdCheck.split(",");

        for (String item: listId) {
            Product product = productService.findById(Long.valueOf(item));
            product.setStatus(1);
            productService.save(product);
        }

        jsonResponse.setStatus("delete");

        return jsonResponse;
    }

    @GetMapping("/search")
    public JsonResponse searchProduct(@RequestParam String search){

        String newSearch = search.toLowerCase().trim().replace(" ","");
        JsonResponse js = new JsonResponse();
        List<Product> productList = productService.SearchProduct(newSearch);
        js.setResult(productList);

        System.out.println("Search ra bao nhiêu kết quả: "+productList.size());
        return js;
    }

}
