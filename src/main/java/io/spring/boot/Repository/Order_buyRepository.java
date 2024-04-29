package io.spring.boot.Repository;



import io.spring.boot.Entity.OrderDTO;
import io.spring.boot.Entity.Order_buy;

import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface Order_buyRepository extends JpaRepository<Order_buy,Long> {

    @Query("select o from Order_buy o order by o.time_stamp desc")
    List<Order_buy> fetchDescTime();

    @Query(value = "select o from Order_buy o order by o.time_stamp desc ")
    Page<Order_buy> findAll(Pageable pageable);

    @Query(value = "select o from  Order_buy o where o.time_stamp >= :startDate and o.time_stamp <= :endDate")
    List<Order_buy> fetchLimitByTime(Date startDate, Date endDate);


    @Query(value = "select o from  Order_buy  o where o.status >1 and o.status<5")
    List<Order_buy> fetchDataMoney();

    @Query(value = "select o from Order_buy o where o.status =5")
    List<Order_buy> fetchStatus5();

    @Query(value = "select o from Order_buy o where o.status =1")
    List<Order_buy> fetchStatus1();

    @Query(value = "select o from Order_buy o where o.customer.id = :id")
    List<Order_buy> fetchByCustomer(Long id);


    @Query(value = "SELECT o FROM Order_buy o ORDER BY CASE WHEN :input = 'asc' THEN o.status END ASC, CASE WHEN :input = 'desc' THEN o.status END DESC")
    public List<Order_buy> fetchAllSortByStatus(String input);
}
