package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.report.SalesHistory;

import java.util.Calendar;
import java.util.List;

public interface SalesHistoryDao extends ReadWriteDao<SalesHistory, Long> {

    List<SalesHistory> findByDates(Calendar fromDate, Calendar toDate, Long shopId);
}
