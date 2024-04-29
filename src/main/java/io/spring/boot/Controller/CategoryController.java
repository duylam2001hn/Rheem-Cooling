package io.spring.boot.Controller;

import io.spring.boot.Entity.Category;
import io.spring.boot.Entity.Login;
import io.spring.boot.Service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/admin/category")
public class CategoryController {

    Login login = new Login();
    List<Category> categoryList = new ArrayList<>();
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/list")
    public String home(Model model,HttpSession httpSession){


        return listByPages(1,model,httpSession);
    }

    @GetMapping("/page/{currentPage}")
    public String listByPages(@PathVariable Integer currentPage, Model model,HttpSession httpSession){


        Integer checkLogin =  login.checkSession(httpSession,model,"admin");



        if(checkLogin==0){
            return "redirect:/admin/login";
        }

//


        Page<Category> PageCate = categoryService.getUsersSortedByLastNameAndPaged(currentPage);


        Long totalItem = PageCate.getTotalElements();
        int totalPage = PageCate.getTotalPages();
        List<Integer> pageTotal = new ArrayList<>();



        for (int i=1;i<=totalPage;i++) {
            pageTotal.add(i);
        }


        List<Category> CateListPage = PageCate.getContent();

        System.out.println(CateListPage.size());


        model.addAttribute("currentPage",currentPage);
        model.addAttribute("totalItem",totalItem);
        model.addAttribute("totalPage",totalPage);
        model.addAttribute("pageTotal",pageTotal);


        model.addAttribute("categoryList",CateListPage);



        return "/main/admin/Category/list";
    }
}
