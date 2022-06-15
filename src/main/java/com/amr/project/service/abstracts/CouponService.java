package com.amr.project.service.abstracts;

import com.amr.project.model.entity.Coupon;

import java.util.List;

public interface CouponService extends ReadWriteService<Coupon, Long> {

    List<Coupon> findCouponsByIds(List<Long> couponIds);
}
