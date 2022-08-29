package com.amr.project.service.impl;

import com.amr.project.converter.SalesHistoryConverter;
import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.dao.abstracts.SalesHistoryDao;
import com.amr.project.model.dto.report.GrandSalesDto;
import com.amr.project.model.dto.report.SalesDto;
import com.amr.project.model.entity.report.SalesHistory;
import com.amr.project.service.abstracts.SalesHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Service
public class SalesHistoryServiceImpl extends ReadWriteServiceImpl<SalesHistory, Long> implements SalesHistoryService {

    private SalesHistoryDao salesHistoryDao;
    public final SalesHistoryConverter salesHistoryConverter;

    @Autowired
    public void setSalesHistoryDao(SalesHistoryDao salesHistoryDao){this.salesHistoryDao = salesHistoryDao;}

    public SalesHistoryServiceImpl(ReadWriteDao<SalesHistory, Long> dao, SalesHistoryConverter salesHistoryConverter) {
        super(dao);
        this.salesHistoryConverter = salesHistoryConverter;
    }
    @Override
    @Transactional(readOnly = true)
    public GrandSalesDto findByDates(String fromDate, String toDate, Long shopId) {

        String pattern = "yyyy-MM-dd";
        Calendar fromDateCalendar = Calendar.getInstance();
        Calendar toDateCalendar = Calendar.getInstance();
        try {
            fromDateCalendar.setTime(new SimpleDateFormat(pattern).parse(fromDate));
            toDateCalendar.setTime(new SimpleDateFormat(pattern).parse(toDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<SalesHistory> salesHistoryList = salesHistoryDao.findByDates(fromDateCalendar, toDateCalendar, shopId);
        List<SalesDto> salesDtoList = salesHistoryConverter.SalesHistoryListToSalesDtoList(salesHistoryList);

        return new GrandSalesDto(salesDtoList);
    }

}
