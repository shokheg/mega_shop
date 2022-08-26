package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.OAuthUserJpaRepository;
import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.dao.abstracts.UserDao;
import com.amr.project.exception.ErrorMessage;
import com.amr.project.exception.OAuth2Exception;
import com.amr.project.model.dto.UserRegistrationDto;
import com.amr.project.model.entity.User;
import com.amr.project.model.enums.Social;
import com.amr.project.service.abstracts.UserService;
import com.amr.project.webapp.config.security.oauth.OAuth2UserDto;
import com.amr.project.webapp.config.security.oauth.principal.OAuth2UserInfo;
import com.amr.project.webapp.config.security.oauth.principal.OAuth2UserInfoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

@Service
public class UserServiceImpl extends ReadWriteServiceImpl<User, Long> implements UserService {

    @Autowired
    private OAuthUserJpaRepository oAuthUserJpaRepository;

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

    @Override
    @Transactional
    public User registerNewUser(final UserRegistrationDto userRegistrationDto) throws OAuth2Exception {
        if (userRegistrationDto.getUserID() != null && oAuthUserJpaRepository.existsById(userRegistrationDto.getUserID())) {
            throw new OAuth2Exception(new ErrorMessage(500,
                    new Date(),
                    "User already exist",
                    "User with User id " + userRegistrationDto.getUserID() + " already exist"));
        } else if (oAuthUserJpaRepository.existsByEmail(userRegistrationDto.getEmail())) {
            throw new OAuth2Exception(new ErrorMessage(500,
                    new Date(),
                    "User already exist",
                    "User with email id " + userRegistrationDto.getEmail() + " already exist"));
        } else if (userRegistrationDto.getEmail() == null) {
            throw new OAuth2Exception(new ErrorMessage(500,
                    new Date(),
                    "Incorrect user data",
                    "Incorrect user data. Email not found, check your privacy settings."));
        }
        User user = buildUser(userRegistrationDto);
        user = oAuthUserJpaRepository.save(user);
        return user;
    }

    @Override
    public User findUserByEmail(final String email) {
        return oAuthUserJpaRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public OAuth2UserDto processUserRegistration(String registrationId,
                                                 Map<String, Object> attributes,
                                                 OidcIdToken idToken,
                                                 OidcUserInfo userInfo) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, attributes);

        UserRegistrationDto userDetails = toUserRegistrationObject(registrationId, oAuth2UserInfo);
        User user = findUserByEmail(oAuth2UserInfo.getEmail());

        if (user != null) {
            if (!user.getProvider().equals(registrationId) && !user.getProvider().equals(Social.LOCAL.getProviderType())) {
                throw new OAuth2Exception(new ErrorMessage(500,
                        new Date(),
                        "Incorrect user data",
                        "Looks like you're signed up with " + user.getProvider() + " account. Please use your " + user.getProvider() + " account to login."));
            }
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(userDetails);
        }

        return OAuth2UserDto.create(user, attributes, idToken, userInfo);
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setUsername(oAuth2UserInfo.getName());
        return oAuthUserJpaRepository.save(existingUser);
    }

    private UserRegistrationDto toUserRegistrationObject(String registrationId, OAuth2UserInfo oAuth2UserInfo) {
        return UserRegistrationDto.getBuilder()
                .addProviderUserID(oAuth2UserInfo.getId())
                .addDisplayName(oAuth2UserInfo.getName())
                .addEmail(oAuth2UserInfo.getEmail())
                .addFirstName(oAuth2UserInfo.getFirstName())
                .addLastName(oAuth2UserInfo.getLastName())
                .addSocialProvider(com.amr.project.utils.GeneralUtils.toSocialProvider(registrationId)).addPassword("changeit").build();
    }

    private User buildUser(final UserRegistrationDto userRegistrationDto) {
        User user = new User();
        String userName = userRegistrationDto.getEmail().split("@")[0];
        user.setUsername(userName);
        user.setFirstName(userRegistrationDto.getFirstName());
        user.setLastName(userRegistrationDto.getLastName());
        user.setEmail(userRegistrationDto.getEmail());
        user.setPassword(userRegistrationDto.getPassword());
        user.setProvider(userRegistrationDto.getSocial().getProviderType());
        user.setActivate(true);
        user.setProviderUserId(userRegistrationDto.getProviderUserId());

        return user;
    }
}
