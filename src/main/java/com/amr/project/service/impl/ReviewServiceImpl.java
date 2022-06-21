package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.model.entity.Review;
import com.amr.project.service.abstracts.ReviewService;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl extends ReadWriteServiceImpl<Review,Long> implements ReviewService {
    public ReviewServiceImpl(ReadWriteDao<Review, Long> dao) {
        super(dao);
    }
}
