package io.spring.boot.RestController;

import io.spring.boot.Entity.Customer;
import io.spring.boot.Entity.JsonResponse;
import io.spring.boot.Entity.Login;
import io.spring.boot.Entity.Product;
import io.spring.boot.Service.CustomerService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(value = "/api/admin/customer")
public class CustomerAdminRestController {

    Login login = new Login();
    JsonResponse js = new JsonResponse();
    private CustomerService customerService;

    public CustomerAdminRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(value = "/list")
    public JsonResponse CategoryList(HttpSession httpSession){

        Integer checkLogin =  login.checkSessionAPI(httpSession,"admin");



        if(checkLogin==0){
            js.setStatus("error");
            return js;
        }

        List<Customer> DataList = customerService.fetchAll();


        js.setResult(DataList);
        return js;
    }



    @GetMapping("/delete")
    public JsonResponse setLock(@RequestParam String listIdCheck, HttpSession httpSession){

        Integer checkLogin =  login.checkSessionAPI(httpSession,"admin");



        if(checkLogin==0){
            js.setStatus("error");
            return js;
        }

        String [] listId = listIdCheck.split(",");

        for (String item: listId) {
            Customer customer = customerService.findById(Long.valueOf(item));
            customer.setStatus(1);
            customerService.save(customer);
        }

        js.setStatus("delete");

        return js;
    }

    @GetMapping("/unlock")
    public JsonResponse setUnlock(@RequestParam String listIdCheck, HttpSession httpSession){

        Integer checkLogin =  login.checkSessionAPI(httpSession,"admin");


        if(checkLogin==0){
            js.setStatus("error");
            return js;
        }

        String [] listId = listIdCheck.split(",");

        for (String item: listId) {
            Customer customer = customerService.findById(Long.valueOf(item));
            customer.setStatus(0);
            customerService.save(customer);
        }

        js.setStatus("unlock");

        return js;
    }

    @GetMapping("/search")
    public JsonResponse searchCustomer(@RequestParam String search, HttpSession httpSession){

        Integer checkLogin =  login.checkSessionAPI(httpSession,"admin");


        if(checkLogin==0){
            js.setStatus("error");
            return js;
        }

        String newSearch = search.toLowerCase().trim().replace(" ","");
        JsonResponse js = new JsonResponse();
        List<Customer> customerList = customerService.searchCustomer(newSearch);
        js.setResult(customerList);


        return js;
    }

}
