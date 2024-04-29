package io.spring.boot.Controller;

import io.spring.boot.Entity.*;
import io.spring.boot.Service.AdminService;
import io.spring.boot.Service.Order_buyService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.*;
import java.util.*;


@Controller
@RequestMapping("/admin")
public class AdminController {

    Login login = new Login();
    private List<Admin> adminList = new ArrayList<>();
    private AdminService adminService;

    private Order_buyService order_buyService;

    public AdminController(AdminService adminService, Order_buyService order_buyService) {
        this.adminService = adminService;
        this.order_buyService = order_buyService;
    }

    @GetMapping("/home")
    public String home(Model model ,HttpSession httpSession){


       Integer checkLogin =  login.checkSession(httpSession,model,"admin");



        if(checkLogin==0){
            return "redirect:/admin/login";
        }

        List<Order_buy> orderBuyList = order_buyService.fetchMoney();

        DataHomePage(model);

        MoneyStatistical(model,orderBuyList);

        return "main/admin/HomePage";

    }


    @PostMapping("/home")
    public String chart(Model model, String startDate, String EndDate, HttpSession httpSession) throws ParseException {

      Login login = new Login();
      login.checkSession(httpSession,model,"admin");

      List<Order_buy> orderBuyList = getListInTime(startDate,EndDate,0);
        List<Order_buy> orderBuyList1 = getListInTime(startDate,EndDate,1);

      MoneyStatistical(model,orderBuyList);
      model.addAttribute("startDate",startDate);
      model.addAttribute("endDate",EndDate);

      model.addAttribute("dataBar",dataBarChart(model,orderBuyList));
      model.addAttribute("dataPie",dataPieChart(model,orderBuyList1));
      DataHomePage(model);

      return "main/admin/HomePage";
    }

    public List<DataChart> dataBarChart(Model model, List<Order_buy> orderBuyList) throws ParseException {

        List<DataChart> ListData = new ArrayList<>();



        SimpleDateFormat formatDB = new SimpleDateFormat("yyyy-MM-dd");


        for (Order_buy item : orderBuyList){

            Date timestamp = formatDB.parse(item.getTime_stamp());
            String dateString = formatDB.format(timestamp);
            String month = dateString.substring(5, 7);

            DataChart dataChart = new DataChart();
            dataChart.setName(month);
            dataChart.setTotal(item.getTotal_price());
            ListData.add(dataChart);


        }

        List<DataChart> dataChart = new ArrayList<>();

        Map<String, BigDecimal> monthTotalMap =  TotalMoneyPerMonth(ListData);

        for (Map.Entry<String, BigDecimal> entry : monthTotalMap.entrySet()) {
            String month = entry.getKey();
            BigDecimal totalMoney = entry.getValue();

            DataChart dataBarChart = new DataChart();
            dataBarChart.setName(month);
            dataBarChart.setTotal(totalMoney);

            dataChart.add(dataBarChart);


            System.out.println("Total money for " + month + ": " + totalMoney);
        }

        System.out.println(dataChart.size());



        return dataChart;
    }

    public List<DataChart> dataPieChart(Model model, List<Order_buy> orderBuyList){
        List<DataChart> dataChartList = new ArrayList<>();

        List<String> StatusList = new ArrayList<>();

        String[] arraySample= {"chưa xử lí","pending","processing","shipped","delivery","cancelled"};

        for (Order_buy item:orderBuyList) {
            if(item.getStatus().equals(0)){
                StatusList.add(arraySample[0]);
            }else if(item.getStatus().equals(1)){
                StatusList.add(arraySample[1]);
            } else if (item.getStatus().equals(2)) {
                StatusList.add(arraySample[2]);
            } else if (item.getStatus().equals(3)) {
                StatusList.add(arraySample[3]);
            } else if(item.getStatus().equals(4)){
                StatusList.add(arraySample[4]);
            }else{
                StatusList.add(arraySample[5]);
            }
        }



        for (String item: StatusList){
            DataChart dataChart = new DataChart();
            dataChart.setName(item);
            dataChart.setTotal(BigDecimal.ONE);
            dataChartList.add(dataChart);
        }

        List<DataChart> dataChartList1 = new ArrayList<>();

        Map<String, BigDecimal> monthTotalMap =  TotalMoneyPerMonth(dataChartList);

        for (Map.Entry<String, BigDecimal> entry : monthTotalMap.entrySet()) {
            String month = entry.getKey();
            BigDecimal totalMoney = entry.getValue();


            DataChart dataChart = new DataChart();
            dataChart.setName(month);
            dataChart.setTotal(totalMoney);

            dataChartList1.add(dataChart);


            System.out.println("Total money for " + month + ": " + totalMoney);
        }

        System.out.println("Số lượng dữ liệu StatusList: "+StatusList.size());
        System.out.println("Số lượng dữ liệu dataChartList: "+dataChartList1.size());

        return dataChartList1;
    }


    public Map<String, BigDecimal> TotalMoneyPerMonth(List<DataChart> data){

        Map<String, BigDecimal> monthTotalMap = new HashMap<>();

        for (DataChart order : data) {
            String month = order.getName();
            BigDecimal amount = order.getTotal();

            BigDecimal totalMoney = monthTotalMap.getOrDefault(month, BigDecimal.ZERO);
            totalMoney = totalMoney.add(amount);
            monthTotalMap.put(month, totalMoney);
        }

        return monthTotalMap;


    }



    public void DataHomePage(Model model){
        List<Order_buy> orderBuysPending = order_buyService.fetchStatus1();

        List<Order_buy> orderBuysCancelled = order_buyService.fetchStatus5();

        model.addAttribute("Pending",orderBuysPending.size());
        model.addAttribute("Cancelled",orderBuysCancelled.size());
    }

    public BigDecimal MoneyStatistical(Model model, List<Order_buy> orderBuyList){


        BigDecimal Summary = BigDecimal.ZERO;

        for (Order_buy item: orderBuyList) {

            BigDecimal totalPrice = item.getTotal_price();

            BigDecimal tien = totalPrice.add(Summary);

            Summary = tien;

        }

        NumberFormat numberFormat = new DecimalFormat("#,###", new DecimalFormatSymbols(Locale.getDefault()));
        String formattedNumber = numberFormat.format(Summary);

        model.addAttribute("tongDoanhThu",formattedNumber);

        return Summary;
    }

    public List<Order_buy> getListInTime(String startDate,String EndDate,Integer check){
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM, yyyy");
        SimpleDateFormat formatDB = new SimpleDateFormat("yyyy-MM-dd");

        List<Order_buy> DataFill = new ArrayList<>();
        try {
            Date startDay = format.parse(startDate);


//
//            if (comparisonResult > 0) {
//                System.out.println("Start Date is after the current date");
//            } else if (comparisonResult < 0) {
//                System.out.println("Start Date is before the current date");
//            } else {
//                System.out.println("Start Date is the same as the current date");
//            }

            Date endDay = format.parse(EndDate);

            System.out.println("Start_day: "+startDay);
            System.out.println("End_day:" +endDay);



            List<Order_buy> orderBuyList = new ArrayList<>();
            if(check ==0){
                orderBuyList = order_buyService.fetchMoney();
            }else {
                orderBuyList = order_buyService.fetchAll();
            }

                    order_buyService.fetchMoney();
            for (Order_buy item:orderBuyList) {
                Date Current = new Date();
                Current = formatDB.parse(item.getTime_stamp());


                int compareStart = startDay.compareTo(Current);
                int compareEnd = endDay.compareTo(Current);

                if(compareStart<=0 && compareEnd>=0){
                    DataFill.add(item);
                }
            }



        } catch (Exception e) {
            e.printStackTrace();
        }

        return DataFill;

    }

    @GetMapping(value = "/list")
    public String AdminList(Model model, HttpSession httpSession){




        return listByPages(1,model,httpSession);
    }

    @GetMapping("/page/{currentPage}")
    public String listByPages(@PathVariable Integer currentPage, Model model , HttpSession httpSession){


        Integer checkLogin =  login.checkSession(httpSession,model,"admin");


        Integer checkRole = (Integer) httpSession.getAttribute("UserRole");



        if(checkLogin==0 || checkRole != 0){
            return "redirect:/admin/home";
        }



        Page<Admin> PageCate = adminService.getUsersSortedByLastNameAndPaged(currentPage);


        Long totalItem = PageCate.getTotalElements();
        int totalPage = PageCate.getTotalPages();
        List<Integer> pageTotal = new ArrayList<>();



        for (int i=1;i<=totalPage;i++) {
            pageTotal.add(i);
        }


        adminList = PageCate.getContent();




//        List<Category> cateParent = categoryService.fetchCateChild(0);




//        model.addAttribute("cateParent",cateParent);

        model.addAttribute("currentPage",currentPage);
        model.addAttribute("totalItem",totalItem);
        model.addAttribute("totalPage",totalPage);
        model.addAttribute("pageTotal",pageTotal);
//        model.addAttribute("categoryList",)




        model.addAttribute("adminList",adminList);



        return "/main/admin/Admin/list";
    }

    @GetMapping(value = "/login")
    public String LoginForm(Model model){
        Login login = new Login();
        model.addAttribute("login",login);

        PasswordHash passwordHash = new PasswordHash();

        System.out.println(passwordHash.PasswordHash("123456"));
        return "main/admin/Admin/Login";
    }

    @PostMapping(value = "/login")
    public String Login(@Valid @ModelAttribute Login login, BindingResult br, Model model, HttpSession httpSession){

        if(br.hasErrors()){

            return "main/admin/Admin/Login";
        }


        List<Admin> adminList = adminService.LoginAdmin(login.getEmail(), login.getPassword());


        if(adminList.size()<1){
            model.addAttribute("ErrorExist","Tài khoản hoặc mật khẩu không đúng");
            return "main/admin/Admin/Login";
        }


        for (Admin user1:adminList) {
            httpSession.setAttribute("UserId",user1.getId());
            httpSession.setAttribute("UserLogin",user1.getEmail());
            httpSession.setAttribute("UserRole",user1.getRole());


        }



        return "redirect:/admin/home";
    }


    @GetMapping(value = "/logout")
    public String logout(HttpSession httpSession){
        httpSession.removeAttribute("UserLogin");
        httpSession.removeAttribute("UserId");
        httpSession.removeAttribute("UserRole");
        return "redirect:/admin/login";
    }


}
