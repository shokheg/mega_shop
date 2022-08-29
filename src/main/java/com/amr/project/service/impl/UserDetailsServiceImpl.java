package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.OAuthUserJpaRepository;
import com.amr.project.dao.abstracts.UserDao;
import com.amr.project.model.dto.UserRegistrationDto;
import com.amr.project.model.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private OAuthUserJpaRepository repository;

    public UserDetailsServiceImpl(OAuthUserJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return UserRegistrationDto.build(user);
    }

}
