package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.CouponDao;
import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.model.entity.Coupon;
import com.amr.project.service.abstracts.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CouponServiceImpl extends ReadWriteServiceImpl<Coupon, Long> implements CouponService {

    private CouponDao couponDao;

    @Autowired
    public void setCouponDao(CouponDao couponDao) {
        this.couponDao = couponDao;
    }

    public CouponServiceImpl(ReadWriteDao<Coupon, Long> dao) {
        super(dao);
    }


    @Override
    @Transactional(readOnly = true)
    public List<Coupon> findCouponsByIds(List<Long> couponIds) {
        return couponDao.findCouponsByIds(couponIds);
    }
}
