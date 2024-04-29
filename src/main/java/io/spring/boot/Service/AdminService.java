package io.spring.boot.Service;

import io.spring.boot.Entity.Admin;
import io.spring.boot.Entity.Category;
import io.spring.boot.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public List<Admin> fetchAll(){
        return adminRepository.findAll();
    }

    public Admin findById(long id){
        return adminRepository.findById(id).get();
    }

    public void save(Admin admin){
        adminRepository.save(admin);
    }

    public List<Admin> fetchStatus0() { return adminRepository.findStatus0();}


    public Page<Admin> getUsersSortedByLastNameAndPaged(int pageNumber) {
//        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        Pageable pageable = PageRequest.of(pageNumber-1, 5);
        return adminRepository.findAll(pageable);
    }

    public List<Admin> LoginAdmin(String email, String password){
        return adminRepository.LoginAdmin(email,password);
    }

}
