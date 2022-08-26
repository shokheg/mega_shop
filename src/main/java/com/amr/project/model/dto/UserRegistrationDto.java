package com.amr.project.model.dto;

import com.amr.project.model.enums.Social;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class UserRegistrationDto {

    private Long userID;

    private String providerUserId;

    private String firstName;

    private String lastName;

    @NotEmpty
    private String displayName;

    @NotEmpty
    private String email;

    private Social social;

    @Size(min = 8, message = "{Size.userDto.password}")
    private String password;

    @NotEmpty
    private String matchingPassword;

    public UserRegistrationDto() {
    }

    public UserRegistrationDto(String providerUserId, String displayName, String firstName, String lastName, String email, String password, Social social) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.providerUserId = providerUserId;
        this.displayName = displayName;
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    public static class Builder {
        private String providerUserID;
        private String displayName;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private Social social;

        public Builder addProviderUserID(final String userID) {
            this.providerUserID = userID;
            return this;
        }

        public Builder addDisplayName(final String displayName) {
            this.displayName = displayName;
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
            return new UserRegistrationDto(providerUserID, displayName, firstName, lastName, email, password, social);
        }
    }
}
