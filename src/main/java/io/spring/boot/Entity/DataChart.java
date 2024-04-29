package io.spring.boot.Entity;

import java.math.BigDecimal;

public class DataChart {

    String name;

    BigDecimal total;

    public DataChart() {
    }

    public DataChart(String name, BigDecimal total) {
        this.name = name;
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
