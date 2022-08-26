package com.amr.project.service.abstracts;

import com.amr.project.model.dto.report.GrandSalesDto;
import com.amr.project.model.entity.report.SalesHistory;


public interface SalesHistoryService extends ReadWriteService<SalesHistory, Long>{
    GrandSalesDto findByDates(String fromDate, String toDate, Long shopId);
}
