package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.User;

public interface UserDao extends ReadWriteDao<User, Long> {

    boolean activateCodeUser(Long id, String code);
}
