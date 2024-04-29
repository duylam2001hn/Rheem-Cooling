package io.spring.boot.Service;


import io.spring.boot.Entity.Admin;
import io.spring.boot.Entity.Customer;

import io.spring.boot.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> fetchAll(){
        return customerRepository.findAll();
    }

    public Customer findById(long id){
        return customerRepository.findById(id).get();
    }

    public void save(Customer customer){
        customerRepository.save(customer);
    }


    public Page<Customer> getUsersSortedByLastNameAndPaged(int pageNumber) {
//        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        Pageable pageable = PageRequest.of(pageNumber-1, 5);
        return customerRepository.findAll(pageable);
    }

    public List<Customer> searchCustomer(String input){
        return customerRepository.searchCustomer(input);
    }

    public List<Customer> LoginCustomer(String email, String password){
        return customerRepository.LoginCustomer(email,password);
    }


}
