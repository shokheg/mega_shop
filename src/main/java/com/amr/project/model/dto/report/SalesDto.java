package com.amr.project.model.dto.report;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Calendar;

@Data
public class SalesDto {

    //поля класса: строки таблицы по запросу продаж товара в магазине

    private String item;            //Имя товара
    private Calendar orderDate;     //Дата заказа товара
    private int count;              //Кол-во проданного товара в этот день
    private BigDecimal totalSum;    //Сумма проданного товара в этот день (= price * count)
    private BigDecimal basePrice;   //Себестоимость товара ("= basePrice" в таблице "item")
    private BigDecimal profit;      //ПС (прибавочная стоимость) для расчета прибыли ("= price - basePrice" в таблице "item")
    private BigDecimal totalProfit; //Общая прибыль по проданному товару в этот день ("= profit * count")
}
