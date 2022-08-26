package com.amr.project.converter;


import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.dto.report.SalesDto;
import com.amr.project.model.entity.report.SalesHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.math.BigDecimal;
import java.util.List;


@Mapper(componentModel = "spring")
public abstract class SalesHistoryConverter {

    @Mappings({
            @Mapping(target = "item", expression = "java(salesHistory.getItem().getName())"),
            @Mapping(target = "totalSum", expression = "java(getTotalSum(salesHistory))"),
            @Mapping(target = "profit", expression = "java(getProfit(salesHistory))"),
            @Mapping(target = "totalProfit", expression = "java(getTotalProfit(salesHistory))")
    })
    public abstract SalesDto SalesHistoryToSalesDto(SalesHistory salesHistory);

    public abstract List<SalesDto> SalesHistoryListToSalesDtoList(List<SalesHistory> salesHistoryList);

    protected BigDecimal getTotalSum(SalesHistory salesHistory) {
        return salesHistory.getPrice().multiply(BigDecimal.valueOf(salesHistory.getCount()));
    }

    protected BigDecimal getProfit(SalesHistory salesHistory) {
        return salesHistory.getPrice().subtract(salesHistory.getBasePrice());
    }

    protected BigDecimal getTotalProfit(SalesHistory salesHistory) {
        return getProfit(salesHistory).multiply(BigDecimal.valueOf(salesHistory.getCount()));
    }


}
