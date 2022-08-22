package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.dao.abstracts.ReviewDao;
import com.amr.project.model.entity.Review;
import com.amr.project.model.entity.Shop;
import com.amr.project.service.abstracts.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl extends ReadWriteServiceImpl<Review,Long> implements ReviewService {

    private ReviewDao reviewDao;
    public ReviewServiceImpl(ReadWriteDao<Review, Long> dao) {
        super(dao);
    }


    @Override
    public List<Review> findReviewsNotModerated() {
        return reviewDao.findReviewsNotModerated();
    }
}
