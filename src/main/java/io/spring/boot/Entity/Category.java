package io.spring.boot.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotEmpty(message = "Tên thể loại không được để trống")
    private String name;

    @Column(name = "parent_id")
    private Integer parent_id;

    @Column(name = "status")
    private Integer status;

    @OneToMany(mappedBy = "category")
    private Set<Category_product> categoryProducts = new HashSet<>();

//    @Column(name = "link_name")
//    private String link_name;


    public Category() {
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

//    public String getLink_name() {
//        return link_name;
//    }
//
//    public void setLink_name(String link_name) {
//        this.link_name = link_name;
//    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getParent_id() {
        return parent_id;
    }

    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }
}
