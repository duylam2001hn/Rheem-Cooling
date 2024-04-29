package io.spring.boot.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class OrderDTO {


    private Long id;

    private Customer customer;

    private List<Product> products;

    private BigDecimal totalPrice;

    private List<ProductDTO> productDTOS;

    private String note;

    private Integer Status;

    public OrderDTO(Long id, Customer customer, List<Product> products, BigDecimal totalPrice, String note, Integer status) {
        this.id = id;
        this.customer = customer;
        this.products = products;
        this.totalPrice = totalPrice;
        this.note = note;
        this.Status = status;
    }

    public OrderDTO(Long id, BigDecimal totalPrice, List<ProductDTO> productDTOS, Integer status) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.productDTOS = productDTOS;
        Status = status;
    }

    public OrderDTO() {
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public List<ProductDTO> getProductDTOS() {
        return productDTOS;
    }

    public void setProductDTOS(List<ProductDTO> productDTOS) {
        this.productDTOS = productDTOS;
    }

    public String getTotalPriceString(){
        NumberFormat numberFormat = new DecimalFormat("#,###", new DecimalFormatSymbols(Locale.getDefault()));
        String formattedNumber = numberFormat.format(this.totalPrice);
        return formattedNumber;
    }


    public String GetStatusString(){
        String[] arraySample= {"chưa xử lí","Chờ thanh toán","Đang đóng gói","Đang vận chuyển","Đã giao","Đã hủy"};

            String Status = null;
            if(this.Status.equals(0)){
              Status= arraySample[0];
            }else if(this.Status.equals(1)){
                Status= arraySample[1];
            } else if (this.Status.equals(2)) {
                Status= arraySample[2];
            } else if (this.Status.equals(3)) {
                Status= arraySample[3];
            } else if(this.Status.equals(4)){
                Status= arraySample[4];
            }else{
                Status= arraySample[5];
            }

            return Status;
    }
}
