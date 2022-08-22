package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.ReviewDao;
import com.amr.project.model.entity.Review;
import com.amr.project.model.entity.Shop;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewDaoImpl extends ReadWriteDaoImpl<Review, Long> implements ReviewDao {

    @Override
    public List<Review> findReviewsNotModerated() {
        return em.createQuery("select r from Review r where r.isModerated = false", Review.class)
                .getResultList();
    }
}
