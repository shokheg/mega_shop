package com.amr.project.service.abstracts;

import com.amr.project.model.dto.UserRegistrationDto;
import com.amr.project.model.entity.User;
import com.amr.project.webapp.config.security.oauth.OAuth2UserDto;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import java.util.Map;

public interface UserService extends ReadWriteService<User, Long> {

    boolean activateUser(Long id, String code);

    User registerNewUser(UserRegistrationDto userRegistrationDto) throws OAuth2AuthenticationException;

    User findUserByEmail(String email);

    OAuth2UserDto processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo);
}

