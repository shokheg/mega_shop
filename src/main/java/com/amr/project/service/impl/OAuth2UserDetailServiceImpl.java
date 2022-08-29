package com.amr.project.service.impl;

import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.OAuth2UserDetailService;
import com.amr.project.service.abstracts.UserService;
import com.amr.project.webapp.config.security.oauth.OAuth2UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OAuth2UserDetailServiceImpl implements OAuth2UserDetailService {
    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public OAuth2UserDto loadUserByUsername(final String email) throws UsernameNotFoundException {
        User user = userService.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " was not found in the database");
        }
        return new OAuth2UserDto(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, com.amr.project.utils.GeneralUtils.buildSimpleGrantedAuthorities(user.getRole()), user);
    }
}
