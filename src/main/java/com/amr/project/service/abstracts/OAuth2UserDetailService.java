package com.amr.project.service.abstracts;

import com.amr.project.webapp.config.security.oauth.OAuth2UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface OAuth2UserDetailService   extends UserDetailsService {

    OAuth2UserDto loadUserByUsername(final String email) throws UsernameNotFoundException;

}
