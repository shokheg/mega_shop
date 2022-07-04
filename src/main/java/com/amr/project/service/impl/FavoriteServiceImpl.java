package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.model.entity.Favorite;
import com.amr.project.service.abstracts.FavoriteService;
import org.springframework.stereotype.Service;

@Service
public class FavoriteServiceImpl extends ReadWriteServiceImpl<Favorite, Long> implements FavoriteService {
    public FavoriteServiceImpl(ReadWriteDao<Favorite, Long> dao) {
        super(dao);
    }
}
