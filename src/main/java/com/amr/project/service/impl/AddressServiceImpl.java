package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.model.entity.Address;
import com.amr.project.service.abstracts.AddressService;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl extends ReadWriteServiceImpl<Address, Long> implements AddressService {

    public AddressServiceImpl(ReadWriteDao<Address, Long> dao) {
        super(dao);
    }
}
