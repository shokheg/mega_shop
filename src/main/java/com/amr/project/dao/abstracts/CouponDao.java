package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.Coupon;

import java.util.List;

public interface CouponDao extends ReadWriteDao<Coupon, Long> {

    List<Coupon> findCouponsByIds(List<Long> couponIds);
}
