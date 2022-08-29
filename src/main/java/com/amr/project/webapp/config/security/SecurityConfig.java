package com.amr.project.webapp.config.security;

import com.amr.project.webapp.config.security.oauth.CustomOAuth2UserService;
import com.amr.project.webapp.config.security.oauth.CustomOidcUserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private CustomOAuth2UserService customOAuth2UserService;
    private CustomOidcUserService customOidcUserService;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService, CustomOidcUserService customOidcUserService) {
        this.customOAuth2UserService = customOAuth2UserService;
        this.customOidcUserService = customOidcUserService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .defaultSuccessUrl("/")
                .userInfoEndpoint()
                .oidcUserService(customOidcUserService)
                .userService(customOAuth2UserService);
        http
                .csrf().disable();
    }
}