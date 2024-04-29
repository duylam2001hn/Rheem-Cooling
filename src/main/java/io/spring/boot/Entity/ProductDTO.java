package io.spring.boot.Entity;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class ProductDTO {
    private Long id;

    private String name;

    private String thumbnail;

    private BigDecimal price;

    private Integer Quantity;

    public ProductDTO(Long id, String name, String thumbnail, BigDecimal price, Integer quantity) {
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
        this.price = price;
        Quantity = quantity;
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

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPriceString() {

        NumberFormat numberFormat = new DecimalFormat("#,###", new DecimalFormatSymbols(Locale.getDefault()));
        String formattedNumber = numberFormat.format(price);
        return formattedNumber;
    }

}
