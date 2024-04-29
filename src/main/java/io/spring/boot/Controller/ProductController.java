package io.spring.boot.Controller;

import io.spring.boot.Entity.*;
import io.spring.boot.Repository.Category_productRepository;
import io.spring.boot.Service.CategoryService;
import io.spring.boot.Service.Category_productService;
import io.spring.boot.Service.ImageService;
import io.spring.boot.Service.ProductService;
import io.spring.boot.Validate.ValidateFileType;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/admin/product")


public class ProductController {


    Login login = new Login();
    Integer idAnh =0;
    Integer idThumbnail=0;
    List<Product> productList = new ArrayList<>();

    List<Category> categoryList = new ArrayList<>();



    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/uploads/";
    private ProductService productService;

    private CategoryService categoryService;

    private Category_productService category_productService;

    private ImageService imageService;
    private final Category_productRepository category_productRepository;

    public ProductController(ProductService productService, CategoryService categoryService, Category_productService category_productService, ImageService imageService,
                             Category_productRepository category_productRepository) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.category_productService = category_productService;
        this.imageService = imageService;
        this.category_productRepository = category_productRepository;
    }

    @GetMapping(value = "/list")
    public String ProductHome(Model model, HttpSession httpSession){



        return listByPages(1,model,httpSession);
    }


    @GetMapping(value = "/get/{id}")
    public String ProductGet(Model model,@PathVariable Long id,HttpSession httpSession){

        Integer checkLogin =  login.checkSession(httpSession,model,"admin");



        if(checkLogin==0){
            return "redirect:/admin/login";
        }

        Product product = productService.findById(id);

        model.addAttribute("products",product);

        AddOject(model);

        List<Category_product> categoryProductList = category_productService.fetchAll();

        List<Image> imageList = imageService.fetchAll();

        List<String> imageExistedList = new ArrayList<>();
        for (Image item: imageList) {
            if(item.getProduct().getId() == id){
                imageExistedList.add(item.getLink_image());
            }
        }





        List<Category> categoryListOfProduct = new ArrayList<>();

        for (Category_product item: categoryProductList ) {
            if(item.getProduct().getId() == id){
                categoryListOfProduct.add(item.getCategory());
            }
        }

        model.addAttribute("imageExistedList",imageExistedList);

        model.addAttribute("categoryListOfProduct",categoryListOfProduct);

        return "main/admin/Product/edit";
    }




    @GetMapping(value= "/add")
    public String ProductAdd(Model model, HttpSession httpSession){

        Integer checkLogin =  login.checkSession(httpSession,model,"admin");



        if(checkLogin==0){
            return "redirect:/admin/login";
        }

        Product product = new Product();
        model.addAttribute("product",product);
        AddOject(model);
        return "main/admin/Product/add";

    }

    @PostMapping(value = "/save")
    public String ProductSave(@ModelAttribute("product") Product product , @RequestParam("files") MultipartFile[] files, @RequestParam("fileThumbnail") MultipartFile fileThumbnail, @ModelAttribute("checkboxData") CheckboxData checkboxData , BindingResult br, @RequestParam String form){

       if(form.equals("add")) {

           if (br.hasErrors()) {
               return "redirect:/admin/product/add";
           }
       }else{
           if (br.hasErrors()) {
               return "redirect:/admin/product/get/"+product.getId();
           }
       }

        Product ProductNewest = product;




        Long timeNow = System.nanoTime();

        Date currentDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
        String formattedDate = format.format(currentDate);


        List<String> selectedOptions = checkboxData.getOptions();


        if(selectedOptions == null){
            if(form.equals("add")){
                return "redirect:/admin/product/add";
            }

        }



        Integer imageThumbnail= ImageThumbnail(fileThumbnail,product,form);

        if( imageThumbnail ==1){
            return "redirect:/admin/product/add";
        } else if (imageThumbnail == 2) {
            return "redirect:/admin/product/get/"+product.getId();
        }


        // Handle the file image upload logic here
        if (files != null && files.length > 0 && files[0].getSize() > 0) {

            if(form.equals("edit")){
                List<Image> imageList = imageService.fetchAll();

                for(Image image: imageList){
                    if(image.getProduct().getId() == product.getId()){
                        imageService.delete(image.getId());
                    }
                }
            }


            for (MultipartFile file : files) {

                // Process each file as needed (e.g., save to a directory, perform operations, etc.)
                // Example: file.transferTo(new File("path/to/save/" + file.getOriginalFilename()));
                idAnh+=1;
                String file_name = file.getOriginalFilename();

                int DotIndex = file_name.lastIndexOf(".");

                String File_extension = file_name.substring(DotIndex);



                String newFileName = timeNow+idAnh+formattedDate;


//                String New_file_name = file_name.substring(0,DotIndex)+timeNow + File_extension;

                // tên file mới
                String New_file_name = newFileName + File_extension;

                ValidateFileType validateFileType = new ValidateFileType();

                int Result = validateFileType.IsValidImage(New_file_name);


                if(Result == 1) {
                    Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY+"image/", New_file_name);







                    if(form.equals("add")){
                        productList = productService.fetchDesc();

                        ProductNewest = productList.get(0);
                    }




                    //Lưu ảnh
                    String LinkImg = "/uploads/image/"+New_file_name;
                    Image image = new Image();
                    image.setProduct(ProductNewest);
                    image.setLink_image(LinkImg);


                    imageService.save(image);





                    try {
                        Files.write(fileNameAndPath, file.getBytes());
                    } catch (IOException e) {
                        System.out.println(e);

                        if(form.equals("add")){
                            return "redirect:/admin/product/add";
                        }else {
                            return "redirect:/admin/product/get/"+product.getId();
                        }

                    }
                }



            }



            if(form.equals("add")){
                productList = productService.fetchDesc();

                ProductNewest = productList.get(0);
            }








            // Lưu category product
            if(form.equals("edit")){
                List<Category_product> categoryProductList = category_productService.fetchAll();
                for (Category_product item:categoryProductList) {
                    if(item.getProduct().getId() == product.getId()){
                        category_productService.delete(item.getId());
                    }
                }
            }


            for (String idCate: selectedOptions) {

                Category_product category_product = new Category_product();
                category_product.setProduct(ProductNewest);

                Category category = categoryService.findById(Long.valueOf(idCate));
                category_product.setCategory(category);

                if(selectedOptions ==null && form.equals("edit") ){
                    return "redirect:/admin/product/list";
                }

                category_productService.save(category_product);


            }


        }else if(form.equals("add")) {
            return "redirect:/admin/product/add";
        }


        return "redirect:/admin/product/list";
    }


    public void AddOject(Model model){


        categoryList = categoryService.fetchStatus0();

        CheckboxData checkboxData = new CheckboxData();

        model.addAttribute("checkboxData",checkboxData);

        model.addAttribute("categoryList",categoryList);
    }

    public Integer ImageThumbnail(MultipartFile fileThumbnail, Product product, String form){
        Integer result =0;

        Long timeNow = System.nanoTime();

        Date currentDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
        String formattedDate = format.format(currentDate);


        if (!fileThumbnail.isEmpty()) {


            idThumbnail+=1;
            String file_name = fileThumbnail.getOriginalFilename();

            int DotIndex = file_name.lastIndexOf(".");

            String File_extension = file_name.substring(DotIndex);



            String newFileName = timeNow+idThumbnail+formattedDate;


//                String New_file_name = file_name.substring(0,DotIndex)+timeNow + File_extension;

            // tên file mới
            String New_file_name = newFileName + File_extension;

            ValidateFileType validateFileType = new ValidateFileType();

            int Result = validateFileType.IsValidImage(New_file_name);


            if(Result == 1) {
                Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY+ "thumbnail/", New_file_name);

                String LinkImgThumbnail = "/uploads/thumbnail/"+New_file_name;

                product.setStatus(0);
                product.setThumbnail(LinkImgThumbnail);

                productService.save(product);




                try {
                    Files.write(fileNameAndPath, fileThumbnail.getBytes());
                } catch (IOException e) {
                    System.out.println(e);

                    if(form.equals("add")){
                        result = 1;
                    }else {
                        result =2;
                    }

                }
            }



        }

        return result;
    }


    @GetMapping("/page/{currentPage}")
    public String listByPages(@PathVariable Integer currentPage, Model model, HttpSession httpSession){


        Integer checkLogin =  login.checkSession(httpSession,model,"admin");



        if(checkLogin==0){
            return "redirect:/admin/login";
        }



        Page<Product> PageProduct = productService.getUsersSortedByLastNameAndPaged(currentPage);


        Long totalItem = PageProduct.getTotalElements();
        int totalPage = PageProduct.getTotalPages();
        List<Integer> pageTotal = new ArrayList<>();



        for (int i=1;i<=totalPage;i++) {
            pageTotal.add(i);
        }


        productList = PageProduct.getContent();


        model.addAttribute("currentPage",currentPage);
        model.addAttribute("totalItem",totalItem);
        model.addAttribute("totalPage",totalPage);
        model.addAttribute("pageTotal",pageTotal);


        model.addAttribute("productList",productList);



        return "/main/admin/Product/list";
    }


}
