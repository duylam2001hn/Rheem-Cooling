package io.spring.boot.Controller;


import io.spring.boot.Entity.*;
import io.spring.boot.Repository.ProductRepository;
import io.spring.boot.Service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/ShopFP")
public class WebController {


    private CategoryService categoryService;

    private ProductService productService;

    private Category_productService category_productService;
    private CustomerService customerService;

    private ImageService imageService;

    private Order_buyService order_buyService;

    private Order_detailService orderDetailService;
    private final ProductRepository productRepository;

    public WebController(CategoryService categoryService, ProductService productService, Category_productService category_productService, CustomerService customerService, ImageService imageService, Order_buyService order_buyService, Order_detailService orderDetailService,
                         ProductRepository productRepository) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.category_productService = category_productService;
        this.customerService = customerService;
        this.imageService = imageService;
        this.order_buyService = order_buyService;
        this.orderDetailService = orderDetailService;
        this.productRepository = productRepository;
    }

    @GetMapping(value = "/home")
    public String home(Model model, HttpSession httpSession){

        AddCategory(model);
        Login login = new Login();
        login.checkSession(httpSession,model,"customer");

        List<Product> productList = productService.fetchStatus0();
        model.addAttribute("productList",productList);




        model.addAttribute("filterPriceList",filterPriceList());



        return  "main/web/HomePage";
    }

    @PostMapping(value = "/filter")
    public String FilterData(@RequestParam("idCate") List<Long> selectedItems, @RequestParam("price") List<BigDecimal> selectedPrices, Model model){

        System.out.println("Selected Items: " + selectedItems.size());
        System.out.println("Selected Price: " + selectedPrices.size());

        List<Product> productList = new ArrayList<>();

        System.out.println("Phần tử đầu tiên của CateList là: "+selectedItems.get(0));

        if((selectedItems.get(0)==-1) && (selectedItems.size()==1 )){

            System.out.println("Có chạy vào if");
            List<Category> categoryList = categoryService.fetchStatus0();

            for (Category item:categoryList) {
                System.out.println("CateId là: "+item);
                for (BigDecimal price:selectedPrices) {
                    System.out.println("giá là: "+price);

                    List<Product> filteredProducts = dataFilterAll(item.getId(),price);

                    productList.addAll(filteredProducts);
                }
            }

        } else if (selectedPrices.get(0).equals(new BigDecimal(-1)) && (selectedPrices.size()==1)) {
            List<DataChart> priceList = filterPriceList();
            for (Long item:selectedItems) {
                System.out.println("CateId là: "+item);
                for (DataChart price:priceList) {
                    System.out.println("giá là: "+price);

                    List<Product> filteredProducts = dataFilterAll(item,price.getTotal());

                    productList.addAll(filteredProducts);
                }
            }
        }
        else {
            for (Long item:selectedItems) {
                System.out.println("CateId là: "+item);
                for (BigDecimal price:selectedPrices) {
                    System.out.println("giá là: "+price);

                    List<Product> filteredProducts = dataFilterAll(item,price);

                    productList.addAll(filteredProducts);
                }
            }

        }


        List<Product> productFilterALL = new ArrayList<>();

       Map<Long,Product> DistinctList = new HashMap<>();
        for (Product pro: productList) {

            DistinctList.put(pro.getId(),pro);
        }

        DistinctList.forEach((key, value) -> {
           productFilterALL.add(value);
            // Perform desired operation for each key-value pair
        });

        System.out.println("Tổng số sản phẩm trong list là: "+productList.size());

        System.out.println("Số sản phẩm sau khi lọc xong là: "+productFilterALL.size());

        for (Product item:productFilterALL) {
            System.out.println("Id product sau khi lọc là: "+item.getId());
        }

        AddCategory(model);



        model.addAttribute("filterPriceList",filterPriceList());

        model.addAttribute("productList",productFilterALL);

        return "/main/web/Product/Filter";
    }

    public List<Product> dataFilterAll(Long idCate, BigDecimal price){
        List<Product> productList = new ArrayList<>();

        if(price.equals(new BigDecimal(10000000))){
            productList = productService.findDistinctByCategoryAndPriceRange(idCate,BigDecimal.ZERO,new BigDecimal(10000000));
        } else if (price.equals(new BigDecimal(20000000))) {
            productList = productService.findDistinctByCategoryAndPriceRange(idCate,new BigDecimal(10000000),new BigDecimal(20000000));
        } else if (price.equals(new BigDecimal(40000000))) {
            productList = productService.findDistinctByCategoryAndPriceRange(idCate,new BigDecimal(20000000),new BigDecimal(40000000));
        }else if(price.equals(new BigDecimal(80000000))){
            productList = productService.findDistinctByCategoryAndPriceRange(idCate,new BigDecimal(40000000),new BigDecimal(80000000));
        }

        System.out.println("Có số sản phẩm là: "+productList.size());

        return productList;
    }



    public List<DataChart> filterPriceList(){
        List<DataChart> filterPriceList = new ArrayList<>();

        DataChart dataChart1 = new DataChart("Dưới 10,000,000đ",new BigDecimal(10000000));

        filterPriceList.add(dataChart1);


        DataChart dataChart2 = new DataChart("Từ 10,000,000đ đến 20,000,000đ",new BigDecimal(20000000));
        filterPriceList.add(dataChart2);


        DataChart dataChart3 = new DataChart("Từ 20,000,000đ đến 40,000,000đ",new BigDecimal(40000000));
        filterPriceList.add(dataChart3);


        DataChart dataChart4 = new DataChart("Từ 40,000,000đ đến 80,000,000đ",new BigDecimal(80000000));
        filterPriceList.add(dataChart4);



        return filterPriceList;
    }

    @GetMapping(value = "/support/payment")
    public String supportPayment(Model model, HttpSession httpSession){
        AddCategory(model);
        Login login = new Login();
        login.checkSession(httpSession,model,"customer");
        return "/main/web/Support/Payment";
    }


    @PostMapping(value = "/customer/detail/")
    public String customerSave(@Valid @ModelAttribute Customer customer, BindingResult br,Model model,  HttpSession httpSession, @RequestParam String passwordNew){


        Customer customerExisted = customerService.findById(customer.getId());
        Login login = new Login();
        Integer check = login.checkSession(httpSession,model,"customer");
        if(br.hasErrors()) {

            System.out.println("Vào lỗi");

            AddCategory(model);
            return "/main/web/Customer/Detail";
        }

        List<Customer> customerList = customerService.fetchAll();
        for (Customer item: customerList) {
            if(customer.getEmail().equals(item.getEmail())){

                AddCategory(model);
                model.addAttribute("EmailExisted","Email này đã được đăng ký");
                return "/main/web/Customer/Detail";
            }
        }

        PasswordHash passwordHash = new PasswordHash();
        String passwordOld = passwordHash.PasswordHash(customer.getPassword());






        if(customerExisted.getPassword().equals(passwordOld) ){
            String passNew = passwordHash.PasswordHash(passwordNew);
            customer.setPassword(passNew);

            customerService.save(customer);
        }



        return "redirect:/ShopFP/customer/detail/"+customer.getId();

    }



    @GetMapping(value = "/customer/detail/{id}")
    public String customerDetail(Model model,@PathVariable Long id,HttpSession httpSession)
    {


        AddCategory(model);
        Login login = new Login();
        Integer check = login.checkSession(httpSession,model,"customer");

        if(check != 1){
            return "redirect:/ShopFP/login";
        }



        Customer customer = customerService.findById(id);
        model.addAttribute("customer",customer);

        return "/main/web/Customer/Detail";
    }

    @GetMapping(value = "/customer/OrderDetail/{id}")
    public String customerOrderDetail(Model model,@PathVariable Long id,HttpSession httpSession){

        AddCategory(model);
        Login login = new Login();
        Integer check = login.checkSession(httpSession,model,"customer");

        if(check != 1){
            return "redirect:/ShopFP/login";
        }

        List<OrderDTO> orderDetailList = ListOrderDTO(id);
        model.addAttribute("ListOrder",orderDetailList);

        return "/main/web/Customer/OrderDetail";
    }

    public List<OrderDTO> ListOrderDTO(Long idCustomer){

        List<OrderDTO> orderDTOList = new ArrayList<>();


        List<Order_detail> orderDetailsList = orderDetailService.fetchAll();

        List<Order_buy> orderBuyList = order_buyService.fetchByCustomer(idCustomer);


        // biểu diễn để hiển thị ra list




        BigDecimal totalPrice = null;

        Integer status = null;
        Long id = null;


        for (Order_buy order:orderBuyList) {
            List<ProductDTO> productList = new ArrayList<>();

                totalPrice = order.getTotal_price();

                status = order.getStatus();
                id = order.getId();



            for (Order_detail item :orderDetailsList) {

                if(item.getOrder().getId().equals(order.getId())){

                   ProductDTO productDTO = new ProductDTO(item.getId(),item.getProduct().getName(),item.getProduct().getThumbnail(),item.getPrice(),item.getQuantity());

                    productList.add(productDTO);


                }
            }

            OrderDTO orderDTO = new OrderDTO(id,totalPrice,productList,status);
            orderDTOList.add(orderDTO);
        }



        System.out.println("có số đơn hàng là: "+orderDTOList.size());



        return orderDTOList;



    }

    @GetMapping(value = "/search")
    public String search(Model model,@RequestParam String search, HttpSession httpSession){

        Login login = new Login();
        login.checkSession(httpSession,model,"customer");
        AddCategory(model);
        String newSearch = search.trim().replace(" ","");
        System.out.println(newSearch);
        List<Product> productSearch = productService.SearchProduct(newSearch);

        model.addAttribute("productList",productSearch);

        return "main/web/Product/search";
    }



    @GetMapping(value = "/get/{id}")
    public String getProduct(Model model, @PathVariable Long id, HttpSession httpSession){

        Login login = new Login();
        login.checkSession(httpSession,model,"customer");

        Product product = productService.findById(id);

        List<Image> imageList = imageService.fetchAll();

        List<Image> imgProList = new ArrayList<>();

        for (Image image: imageList) {
            if(image.getProduct().getId().equals(id)){
                imgProList.add(image);
            }
        }

        List<Product> productList = productService.fetchStatus0();

        model.addAttribute("productList",productList);
        model.addAttribute("product",product);
        model.addAttribute("imgList",imgProList);


        AddCategory(model);
        return "main/web/Product/getItem";
    }


    @GetMapping(value = "/logout")
    public String logout(HttpSession httpSession){
        httpSession.removeAttribute("CustomerLogin");
        httpSession.removeAttribute("CustomerId");

        return "redirect:/ShopFP/login";
    }



    @PostMapping(value = "/login")
    public String Login(@Valid @ModelAttribute Login login, BindingResult br, Model model, HttpSession httpSession){

        AddCategory(model);

        if(br.hasErrors()){

            AddCategory(model);
            return "main/web/Customer/Login";
        }

        List<Customer> customerList = customerService.LoginCustomer(login.getEmail(),login.getPassword());

        if(customerList.size()<1){
            AddCategory(model);
            model.addAttribute("ErrorExist","Tài khoản hoặc mật khẩu không đúng");
            return "main/web/Customer/Login";
        }


        for (Customer customer:customerList) {
            httpSession.setAttribute("CustomerId",customer.getId());
            httpSession.setAttribute("CustomerLogin",customer.getEmail());

        }

        System.out.println("Email login: "+httpSession.getAttribute("CustomerLogin"));

        return "redirect:/ShopFP/home";

    }

    @GetMapping("/login")
    public String LoginForm(Model model , HttpSession httpSession){

        AddCategory(model);



        Login login = new Login();
        if(login.checkSession(httpSession,model,"customer") !=0){
            return "redirect:/ShopFP/home";
        }

        model.addAttribute("login",login);
        return "main/web/Customer/Login";
    }


    @PostMapping(value = "/register")
    public String register(@Valid @ModelAttribute Customer customer , BindingResult br, Model model){

        if(br.hasErrors()){
            AddCategory(model);
            return "main/web/Customer/Register";
        }

        List<Customer> customerList = customerService.fetchAll();
        for (Customer item: customerList) {
            if(customer.getEmail().equals(item.getEmail())){

                AddCategory(model);
                model.addAttribute("EmailExisted","Email này đã được đăng ký");
                return "main/web/Customer/Register";
            }
        }

        PasswordHash passwordHash = new PasswordHash();

        String newPass = customer.getPassword().trim().replace(" ","");

        String passHash = passwordHash.PasswordHash(newPass);

        customer.setPassword(passHash);
        customer.setStatus(0);
        customerService.save(customer);


        return "redirect:/ShopFP/login";
    }


    @GetMapping(value = "/register")
    public String registerForm(Model model, HttpSession httpSession){

        Login login = new Login();
        if(login.checkSession(httpSession,model,"customer")!=0){
            return "redirect:/ShopFP/home";
        }

        Customer customer = new Customer();
        model.addAttribute("customer",customer);
        AddCategory(model);
        return "main/web/Customer/Register";
    }


    @GetMapping(value = "/category/get/{id}")
    public String getListProduct(Model model, @PathVariable Long id, HttpSession httpSession){

        Login login = new Login();

        login.checkSession(httpSession,model,"customer");

        List<Product> productListAll = productService.fetchAll();

        List<Category_product> categoryProductList = category_productService.fetchAll();

        List<Product> productListGet = new ArrayList<>();

        for (Category_product category_product: categoryProductList) {
            for(Product product: productListAll){
                if(category_product.getCategory().getId() == id){
                        if(category_product.getProduct().getId() == product.getId()){
                            productListGet.add(product);
                        }
                    }

            }
        }

        String nameCate = categoryService.findById(id).getName();
        AddCategory(model);


        model.addAttribute("CateName",nameCate);

        model.addAttribute("filterPriceList",filterPriceList());

        model.addAttribute("productList",productListGet);

        return "main/web/Product/getList";
    }


    public void AddCategory(Model model){

        List<Category> categoryList0 = categoryService.ListCateParent0();

        List<Category> categoryListExcept0 = categoryService.ListCateParentExcept0();

        model.addAttribute("cateListParent0", categoryList0);
        model.addAttribute("cateListParentExcept0", categoryListExcept0);
    }

}
