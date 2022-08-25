package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.dao.abstracts.UserDao;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl extends ReadWriteServiceImpl<User, Long> implements UserService {

    private final UserDao userDao;
    public UserServiceImpl(ReadWriteDao<User, Long> dao, UserDao userDao) {
        super(dao);
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public boolean activateUser(Long id, String code) {
        return userDao.activateCodeUser(id, code);
    }
}
