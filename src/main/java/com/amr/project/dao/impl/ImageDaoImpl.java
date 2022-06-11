package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.ImageDao;
import com.amr.project.model.entity.Image;
import org.springframework.stereotype.Repository;

@Repository
public class ImageDaoImpl extends ReadWriteDaoImpl<Image, Long> implements ImageDao {
}
