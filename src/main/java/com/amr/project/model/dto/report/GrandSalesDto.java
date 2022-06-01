package com.amr.project.model.dto.report;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


@Data
public class GrandSalesDto {

    private List<SalesDto> sales;                   //Список по отчету продаж товара в магазине
    private BigDecimal grandTotalSum;       //Общая сумма проданного товара по отчету (=Sum(totalSum) по всем объектам списка sales)
    private BigDecimal grandTotalProfit;    //Общая сумма прибыли проданного товара по отчету (=Sum(totalProfit) по всем объектам списка sales)
}
