package com.amr.project.model.dto.report;

import com.amr.project.model.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Calendar;


//TODO: Нужен ли данный DTO?
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesHistoryDto {

    private Long id;

    private Calendar orderDate;     //Дата продажи товара
    private int count;              //Кол-во проданного товара в этот день
    private BigDecimal basePrice;   //Себестоимость товара ("= basePrice" в таблице "item")
    private BigDecimal price;       //Цена проданного товара в этот день ("= price" в таблице "item)

    //@JsonManagedReference
    private Item item;
}
