package io.spring.boot.Repository;

import io.spring.boot.Entity.Order_buy;
import io.spring.boot.Entity.Order_detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface Order_detailRepository extends JpaRepository<Order_detail,Long> {


}
