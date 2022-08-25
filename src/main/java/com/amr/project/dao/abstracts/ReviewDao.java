package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.Review;
import com.amr.project.model.entity.Shop;

import java.util.List;

public interface ReviewDao extends ReadWriteDao<Review, Long> {

    public List<Review> findReviewsNotModerated();
}
