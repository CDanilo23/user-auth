package com.users.auth.util;

import com.users.auth.dto.PhoneDto;
import com.users.auth.model.Phone;
import com.users.auth.model.UserLogin;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class UserBuilder {
    private UserLogin userLogin;

    public UserBuilder() {
        userLogin = new UserLogin();
        LocalDateTime now = LocalDateTime.now();
        userLogin.setCreated(now);
        userLogin.setModified(now);
        userLogin.setLastLogin(now);
        userLogin.setActive(true);
    }

    public UserBuilder withName(String name) {
        userLogin.setName(name);
        return this;
    }

    public UserBuilder withEmail(String email) {
        userLogin.setEmail(email);
        return this;
    }

    public UserBuilder withPassword(String password, PasswordEncoder encoder) {
        userLogin.setPassword(encoder.encode(password));
        return this;
    }

    public UserBuilder withPhones(List<PhoneDto> phoneDtos) {
        userLogin.setPhones(phoneDtos.stream()
                .map(phoneDto -> new Phone(phoneDto.getNumber(), phoneDto.getCitycode(), phoneDto.getContrycode()))
                .collect(Collectors.toList()));
        return this;
    }

    public UserBuilder withToken(String token) {
        userLogin.setToken(token);
        return this;
    }

    public UserLogin build() {
        return userLogin;
    }
}
