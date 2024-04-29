package io.spring.boot.Controller;

import io.spring.boot.Entity.Category;
import io.spring.boot.Entity.CheckboxData;
import io.spring.boot.Entity.Order_detail;
import io.spring.boot.Service.CategoryService;
import io.spring.boot.Service.Order_detailService;
import io.spring.boot.Validate.ValidateFileType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.awt.image.ImageWatched;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class TestUploadController {

    Integer idAnh = 0;
    private CategoryService categoryService;

    List<Category> categoryList = new ArrayList<>();

    private Order_detailService orderDetailService;



    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/uploads/";

    public TestUploadController(CategoryService categoryService, Order_detailService orderDetailService) {
        this.categoryService = categoryService;
        this.orderDetailService = orderDetailService;
    }

    @GetMapping("/form")
    public String form(Model model){



        categoryList = categoryService.fetchStatus0();
        CheckboxData checkboxData = new CheckboxData();

        model.addAttribute("checkboxData",checkboxData);

        model.addAttribute("categoryList",categoryList);


        return "/main/test/upload";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("files") MultipartFile[] files, @ModelAttribute("checkboxData") CheckboxData checkboxData) {

        Long timeNow = System.nanoTime();

        Date currentDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
        String formattedDate = format.format(currentDate);



        // Handle the file upload logic here
        if (files != null && files.length > 0) {


            for (MultipartFile file : files) {
                // Process each file as needed (e.g., save to a directory, perform operations, etc.)
                // Example: file.transferTo(new File("path/to/save/" + file.getOriginalFilename()));
                idAnh+=1;
                String file_name = file.getOriginalFilename();

                int DotIndex = file_name.lastIndexOf(".");

                String File_extension = file_name.substring(DotIndex);

                System.out.println(idAnh);

                String newFileName = timeNow+idAnh+formattedDate;


//                String New_file_name = file_name.substring(0,DotIndex)+timeNow + File_extension;

                // tên file mới
                String New_file_name = newFileName + File_extension;

                ValidateFileType validateFileType = new ValidateFileType();

                int Result = validateFileType.IsValidImage(New_file_name);


                if(Result == 1) {
                    Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, New_file_name);

                    String LinkImg = "/uploads/"+New_file_name;
                    System.out.println(LinkImg);

                    try {
                        Files.write(fileNameAndPath, file.getBytes());
                    } catch (IOException e) {
                        System.out.println(e);

                        return "redirect:/form";
                    }
                }

                System.out.println(Result);
                System.out.println(New_file_name);

            }
        }


        List<String> selectedOptions = checkboxData.getOptions();
        if(selectedOptions == null){
            return "redirect:/form";
        }
        System.out.println(selectedOptions.size());

        for (String integer:selectedOptions) {
            System.out.println("CateId: "+integer+"\n");
        }


        // Redirect or return a response as needed
        return "main/test/success";
    }


//    @PostMapping(value = "/testCate")
//    public String testCateList(@ModelAttribute("checkboxData") CheckboxData checkboxData){
//
//        List<String> selectedOptions = checkboxData.getOptions();
//        if(selectedOptions == null){
//            return "redirect:/form";
//        }
//        System.out.println(selectedOptions.size());
//
//        for (String integer:selectedOptions) {
//            System.out.println("CateId: "+integer+"\n");
//        }
//
//        return "main/test/success";
//    }
}
