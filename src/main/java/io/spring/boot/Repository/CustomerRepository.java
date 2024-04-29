package io.spring.boot.Repository;


import io.spring.boot.Entity.Admin;
import io.spring.boot.Entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    Page<Customer> findAll(Pageable pageable);

    @Query("select c from Customer c where c.email like concat('%', :input, '%') ")
    List<Customer> searchCustomer (String input);

    @Query("select c from Customer c where (c.email =?1 and c.password =?2) and c.status=0")
    List<Customer> LoginCustomer (String email, String password);
}
