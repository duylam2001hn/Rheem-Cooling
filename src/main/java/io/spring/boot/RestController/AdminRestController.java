package io.spring.boot.RestController;

import io.spring.boot.Entity.*;
import io.spring.boot.Service.AdminService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/admin")
public class AdminRestController {
    JsonResponse js = new JsonResponse();
    Login login = new Login();

    private AdminService adminService;

    public AdminRestController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping(value = "/list")
    public JsonResponse CategoryList(HttpSession httpSession){


        List<Admin> adminList = adminService.fetchAll();

        Integer checkLogin =  login.checkSessionAPI(httpSession,"admin");

        System.out.println("Role "+httpSession.getAttribute("UserRole"));
        System.out.println("checkLogin "+checkLogin);

        Integer checkRole = (Integer) httpSession.getAttribute("UserRole");

        if(checkLogin ==0 || checkRole != 0){
            js.setStatus("error");

            return js;
        }


        js.setResult(adminList);
        return js;
    }



    @PostMapping("/save/{IdEdit}")
    public JsonResponse CategoryAdd(@Valid @RequestBody Admin admin, BindingResult br , @PathVariable String IdEdit , HttpSession httpSession){



        Integer checkLogin =  login.checkSessionAPI(httpSession,"admin");

        System.out.println("Role"+httpSession.getAttribute("UserRole"));


        Integer checkRole = (Integer) httpSession.getAttribute("UserRole");

        if(checkLogin==0 || checkRole != 0){
            js.setStatus("error");
            return js;
        }


        System.out.println(IdEdit);
//        Integer role = Integer.valueOf(admin.getRole());

        if(br.hasErrors()){
            js.setStatus("error");
            return js;
        }
        js.setStatus("success");

        PasswordHash passwordHash = new PasswordHash();

        String Encode_pass = passwordHash.PasswordHash(admin.getPassword());



        //Save Edit
        if(!IdEdit.equals("add")){
            js.setStatus("EditSuccess");
            Long id = Long.valueOf(IdEdit);
            Admin adminEdit = adminService.findById(id);

            adminEdit.setEmail(admin.getEmail());

            adminEdit.setRole(admin.getRole());



            adminEdit.setPassword(Encode_pass);

            System.out.println(Encode_pass);

            adminService.save(adminEdit);
            return js;

        }

        //Save add
        admin.setPassword(Encode_pass);

        adminService.save(admin);
        System.out.println(admin.getEmail());

        return js;
    }

    @GetMapping("/get/{id}")
    public JsonResponse CategoryGet(@PathVariable Long id,HttpSession httpSession){


        Integer checkLogin =  login.checkSessionAPI(httpSession,"admin");

        System.out.println("Role"+httpSession.getAttribute("UserRole"));


        Integer checkRole = (Integer) httpSession.getAttribute("UserRole");

        if(checkLogin==0 || checkRole != 0){
            js.setStatus("error");
            return js;
        }

        Admin admin = adminService.findById(id);

        js.setStatus("success");
        js.setResult(admin);
        return js;
    }

    @GetMapping("/delete")
    public JsonResponse setLock(@RequestParam String listIdCheck, HttpSession httpSession){


        Integer checkLogin =  login.checkSessionAPI(httpSession,"admin");

        System.out.println("Role"+httpSession.getAttribute("UserRole"));


        Integer checkRole = (Integer) httpSession.getAttribute("UserRole");

        if(checkLogin==0 || checkRole != 0){
            js.setStatus("error");
            return js;
        }

        String [] listId = listIdCheck.split(",");

        for (String item: listId) {
            Admin admin = adminService.findById(Long.valueOf(item));
            admin.setStatus(1);
            adminService.save(admin);
        }

        js.setStatus("delete");

        return js;
    }

    @GetMapping("/unlock")
    public JsonResponse setUnlock(@RequestParam String listIdCheck, HttpSession httpSession){


        Integer checkLogin =  login.checkSessionAPI(httpSession,"admin");




        Integer checkRole = (Integer) httpSession.getAttribute("UserRole");

        if(checkLogin==0 || checkRole != 0){
            js.setStatus("error");
            return js;
        }


        String [] listId = listIdCheck.split(",");

        for (String item: listId) {
            Admin admin = adminService.findById(Long.valueOf(item));
            admin.setStatus(0);
            adminService.save(admin);
        }

        js.setStatus("unlock");

        return js;
    }

}
