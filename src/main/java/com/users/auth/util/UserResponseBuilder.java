package com.users.auth.util;

import com.users.auth.dto.response.UserResponseDto;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserResponseBuilder {
    private UserResponseDto userResponse;

    public UserResponseBuilder() {
        userResponse = new UserResponseDto();
    }

    public UserResponseBuilder withId(UUID id) {
        userResponse.setId(id);
        return this;
    }

    public UserResponseBuilder withName(String name) {
        userResponse.setName(name);
        return this;
    }

    public UserResponseBuilder withEmail(String email) {
        userResponse.setEmail(email);
        return this;
    }

    public UserResponseBuilder withCreated(LocalDateTime created) {
        userResponse.setCreated(created);
        return this;
    }

    public UserResponseBuilder withModified(LocalDateTime modified) {
        userResponse.setModified(modified);
        return this;
    }

    public UserResponseBuilder withLastLogin(LocalDateTime lastLogin) {
        userResponse.setLastLogin(lastLogin);
        return this;
    }

    public UserResponseBuilder withToken(String token) {
        userResponse.setToken(token);
        return this;
    }

    public UserResponseBuilder withIsActive(boolean isActive) {
        userResponse.setActive(isActive);
        return this;
    }

    public UserResponseDto build() {
        return userResponse;
    }
}
