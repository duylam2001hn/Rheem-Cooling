package io.spring.boot.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customer")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotEmpty(message = "Name is not Empty")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Address is not Empty")
    @Column(name = "address")
    private String address;

    @NotEmpty(message = "Phone is not Empty")
    @Column(name = "phone")
    private String phone;

    @Email
    @NotEmpty(message = "Email is not Empty")
    @Column(name = "email")
    private String email;

    @NotEmpty(message = "Password is not Empty")
    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private Integer status;

    @OneToMany(mappedBy = "customer")
    private Set<Order_buy> orderBuys = new HashSet<>();

    public Customer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {



        this.password =password ;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
