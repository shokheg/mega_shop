package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.UserDao;
import com.amr.project.model.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends ReadWriteDaoImpl<User, Long> implements UserDao {
    @Override
    public boolean activateCodeUser(Long id, String code) {
        User user = findById(id);
        if (user.getActivationCode().equals(code)) {
            user.setActivate(true);
            update(user);
            return true;
        }
        else {
            return false;
        }
    }
}
