package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.CouponDao;
import com.amr.project.model.entity.Coupon;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CouponDaoImpl extends ReadWriteDaoImpl<Coupon, Long> implements CouponDao {

    @Override
    public List<Coupon> findCouponsByIds(List<Long> couponIds) {
        List<Coupon> coupons = new ArrayList<>();
        for (Long id : couponIds) {
            Coupon coupon = em.find(Coupon.class, id);
            coupons.add(coupon);
        }
        return coupons;
    }
}
