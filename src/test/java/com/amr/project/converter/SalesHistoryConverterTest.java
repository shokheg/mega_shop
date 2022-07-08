package com.amr.project.converter;

import com.amr.project.model.dto.report.SalesDto;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.report.SalesHistory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;

public class SalesHistoryConverterTest {

    private SalesHistoryConverter salesHistoryConverterUnderTest = new SalesHistoryConverterImpl();
    private Item item = Item.builder().name("Potato").build();
    private Calendar date = new GregorianCalendar(2022, Calendar.APRIL, 1);
    private SalesHistory entity = SalesHistory.builder()
            .item(item)
            .basePrice(BigDecimal.valueOf(10))
            .price(BigDecimal.valueOf(20))
            .count(5)
            .orderDate(date)
            .build();
    private List<SalesHistory> listEntity = Collections.singletonList(entity);

    @Test
    void properlyMappingSalesHistoryToSalesDto() {

        SalesDto dto = salesHistoryConverterUnderTest.SalesHistoryToSalesDto(entity);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals("Potato", dto.getItem());
        Assertions.assertEquals(entity.getOrderDate(), dto.getOrderDate());
        Assertions.assertEquals(5, dto.getCount());
        Assertions.assertEquals(BigDecimal.valueOf(100), dto.getTotalSum());
        Assertions.assertEquals(BigDecimal.valueOf(10), dto.getBasePrice());
        Assertions.assertEquals(BigDecimal.valueOf(10), dto.getProfit());
        Assertions.assertEquals(BigDecimal.valueOf(50), dto.getTotalProfit());
    }

    @Test
    void properlyMappingSalesHistoryListToSalesDtoList() {

        List<SalesDto> dtoList = salesHistoryConverterUnderTest.SalesHistoryListToSalesDtoList(listEntity);

        Assertions.assertNotNull(dtoList);
        Assertions.assertEquals(1, dtoList.size());
        Assertions.assertEquals("Potato", dtoList.get(0).getItem());
        Assertions.assertEquals(entity.getOrderDate(),  dtoList.get(0).getOrderDate());
        Assertions.assertEquals(5,  dtoList.get(0).getCount());
        Assertions.assertEquals(BigDecimal.valueOf(100),  dtoList.get(0).getTotalSum());
        Assertions.assertEquals(BigDecimal.valueOf(10),  dtoList.get(0).getBasePrice());
        Assertions.assertEquals(BigDecimal.valueOf(10),  dtoList.get(0).getProfit());
        Assertions.assertEquals(BigDecimal.valueOf(50),  dtoList.get(0).getTotalProfit());
    }

}
