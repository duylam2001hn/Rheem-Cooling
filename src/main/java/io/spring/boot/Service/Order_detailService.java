package io.spring.boot.Service;

import io.spring.boot.Entity.Admin;
import io.spring.boot.Entity.Order_detail;
import io.spring.boot.Repository.AdminRepository;
import io.spring.boot.Repository.Order_detailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class Order_detailService {

    @Autowired
    private Order_detailRepository order_detailRepository;

    public List<Order_detail> fetchAll(){
        return order_detailRepository.findAll();
    }

    public Order_detail findById(long id){
        return order_detailRepository.findById(id).get();
    }

    public void save(Order_detail orderDetail){
        order_detailRepository.save(orderDetail);
    }

    




}
