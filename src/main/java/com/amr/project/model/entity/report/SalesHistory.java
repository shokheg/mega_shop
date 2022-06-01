package com.amr.project.model.entity.report;

import com.amr.project.model.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Objects;


@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
//@NoArgsConstructor
@AllArgsConstructor
public class SalesHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Calendar orderDate;     //Дата продажи товара
    private int count;              //Кол-во проданного товара в этот день
    private BigDecimal basePrice;   //Себестоимость товара ("= basePrice" в таблице "item")
    private BigDecimal price;       //Цена проданного товара в этот день ("= price" в таблице "item)


    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Item item;              //Ссылка на проданную позицию товара

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SalesHistory that = (SalesHistory) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
