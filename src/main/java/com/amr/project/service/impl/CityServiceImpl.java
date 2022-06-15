package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.model.entity.City;
import com.amr.project.service.abstracts.CityService;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl extends ReadWriteServiceImpl<City, Long> implements CityService {

    public CityServiceImpl(ReadWriteDao<City, Long> dao) {
        super(dao);
    }
}
