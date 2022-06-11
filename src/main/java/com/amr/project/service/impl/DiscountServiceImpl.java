package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.model.entity.Discount;
import com.amr.project.service.abstracts.DiscountService;
import org.springframework.stereotype.Service;

@Service
public class DiscountServiceImpl extends ReadWriteServiceImpl<Discount, Long> implements DiscountService {

    public DiscountServiceImpl(ReadWriteDao<Discount, Long> dao) {
        super(dao);
    }
}
