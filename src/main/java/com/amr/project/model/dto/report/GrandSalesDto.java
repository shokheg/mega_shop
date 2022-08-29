package com.amr.project.model.dto.report;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


@Data
public class GrandSalesDto {

    private List<SalesDto> sales;           //Список по отчету продаж товара в магазине
    private BigDecimal grandTotalSum;       //Общая сумма проданного товара по отчету (=Sum(totalSum) по всем объектам списка sales)
    private BigDecimal grandTotalProfit;    //Общая сумма прибыли проданного товара по отчету (=Sum(totalProfit) по всем объектам списка sales)

    public GrandSalesDto(List<SalesDto> sales) {
        this.sales = sales;
        this.grandTotalSum = sales.stream()
                .map(e -> e.getTotalSum())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.grandTotalProfit = sales.stream()
                .map(e -> e.getTotalProfit())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
