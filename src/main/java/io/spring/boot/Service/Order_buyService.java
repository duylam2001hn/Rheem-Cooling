package io.spring.boot.Service;

import io.spring.boot.Entity.Admin;
import io.spring.boot.Entity.OrderDTO;
import io.spring.boot.Entity.Order_buy;
import io.spring.boot.Repository.AdminRepository;
import io.spring.boot.Repository.Order_buyRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class Order_buyService {

    @Autowired
    private Order_buyRepository order_buyRepository;

    public List<Order_buy> fetchAll(){
        return order_buyRepository.findAll();
    }

    public Order_buy findById(long id){
        return order_buyRepository.findById(id).get();
    }

    public void save(Order_buy order_buy){
        order_buyRepository.save(order_buy);
    }

    public List<Order_buy> fetchDesTime(){return  order_buyRepository.fetchDescTime();}


    public List<Order_buy> fetchLimitByTime(Date startDate, Date endDate){
        return order_buyRepository.fetchLimitByTime(startDate,endDate);
    }

    public List<Order_buy> fetchStatus5(){
        return order_buyRepository.fetchStatus5();
    }

    public List<Order_buy> fetchStatus1(){
        return order_buyRepository.fetchStatus1();
    }

    public List<Order_buy> fetchMoney(){
        return order_buyRepository.fetchDataMoney();
    }

    public List<Order_buy> fetchByCustomer(Long id){
            return order_buyRepository.fetchByCustomer(id);
    }

    public List<Order_buy> fetchBySort(String input){
        return order_buyRepository.fetchAllSortByStatus(input);
    }

}
