package io.spring.boot.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Entity
@Table(name = "product")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "description")
    private String description;


    @Column(name = "status")
    private Integer status;

    @OneToMany(mappedBy = "product")
    private Set<Category_product> categoryProducts = new HashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<Image> images = new HashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<Order_detail> orderDetails = new HashSet<>();

//    private String priceString;

    public Product() {
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPriceString() {

        NumberFormat numberFormat = new DecimalFormat("#,###", new DecimalFormatSymbols(Locale.getDefault()));
        String formattedNumber = numberFormat.format(price);
        return formattedNumber;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    //    public void setPriceString(String priceString) {
//        this.priceString = priceString;
//    }
}
