package com.amr.project.model.dto;

import com.amr.project.model.entity.User;
import com.amr.project.model.enums.Social;
import com.nimbusds.jose.shaded.json.annotate.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class UserRegistrationDto implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Long userID;

    private String providerUserId;

    private String firstName;

    private String lastName;

    @NotEmpty
    private String username;

    @NotEmpty
    private String email;

    private Social social;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    @NotEmpty
    private String matchingPassword;

    public UserRegistrationDto() {
    }

    public UserRegistrationDto(Long userID, String username, String email, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public UserRegistrationDto(String providerUserId,
                               String username,
                               String firstName,
                               String lastName,
                               String email,
                               String password,
                               Social social) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.providerUserId = providerUserId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.social = social;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public Social getSocial() {
        return social;
    }

    public void setSocial(final Social socialDto) {
        this.social = socialDto;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public static UserRegistrationDto build(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>(user.getAuthorities());

        return new UserRegistrationDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities);
    }

    public static class Builder {
        private String providerUserID;
        private String username;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private Social social;

        public Builder addProviderUserID(final String userID) {
            this.providerUserID = userID;
            return this;
        }

        public Builder addUsername(final String username) {
            this.username = username;
            return this;
        }

        public Builder addFirstName(final String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder addLastName(final String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder addEmail(final String email) {
            this.email = email;
            return this;
        }

        public Builder addPassword(final String password) {
            this.password = password;
            return this;
        }

        public Builder addSocialProvider(final Social social) {
            this.social = social;
            return this;
        }

        public UserRegistrationDto build() {
            return new UserRegistrationDto(providerUserID, username, firstName, lastName, email, password, social);
        }
    }
}
