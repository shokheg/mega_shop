package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.model.entity.Feedback;
import com.amr.project.service.abstracts.FeedbackService;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImpl extends ReadWriteServiceImpl<Feedback, Long> implements FeedbackService {
    public FeedbackServiceImpl(ReadWriteDao<Feedback, Long> dao) {
        super(dao);
    }
}
