package io.spring.boot.Repository;

import io.spring.boot.Entity.Admin;
import io.spring.boot.Entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {
    @Query("select a from Admin a where a.status = 0")
    List<Admin> findStatus0 ();


    Page<Admin> findAll(Pageable pageable);

    @Query("select a from Admin a where (a.email =?1 and a.password =?2) and a.status=0")
    List<Admin> LoginAdmin (String email, String password);

}
