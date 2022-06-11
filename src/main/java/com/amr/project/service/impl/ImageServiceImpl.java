package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.model.entity.Image;
import com.amr.project.service.abstracts.ImageService;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl extends ReadWriteServiceImpl<Image, Long> implements ImageService {

    public ImageServiceImpl(ReadWriteDao<Image, Long> dao) {
        super(dao);
    }
}
