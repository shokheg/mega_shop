package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.DiscountDao;
import com.amr.project.model.entity.Discount;
import org.springframework.stereotype.Repository;

@Repository
public class DiscountDaoImpl extends ReadWriteDaoImpl<Discount, Long> implements DiscountDao {
}
