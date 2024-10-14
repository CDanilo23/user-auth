package com.users.auth.service.impl;

import com.users.auth.config.JwtTokenProvider;
import com.users.auth.dto.UserRequestDto;
import com.users.auth.dto.response.UserResponseDto;
import com.users.auth.exception.InvalidEmailException;
import com.users.auth.exception.InvalidPasswordException;
import com.users.auth.exception.EmailAlreadyExistsException;
import com.users.auth.model.UserLogin;
import com.users.auth.repository.UserRepository;
import com.users.auth.service.UserService;
import com.users.auth.util.UserBuilder;
import com.users.auth.util.UserResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    @Value("${app.security.password.pattern}")
    String passwordPattern;

    @Value("${app.security.email.pattern}")
    String emailPattern;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDto createUsuario(UserRequestDto userRequestDto) {

        if (userRepository.existsByEmail(userRequestDto.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("El correo ya está registrado");
        }

        if (!Pattern.matches(emailPattern, userRequestDto.getEmail())) {
            throw new InvalidEmailException("El formato del correo es incorrecto. Debe seguir el formato nombre@dominio.cl");
        }

        if(!Pattern.matches(passwordPattern, userRequestDto.getPassword())){
            throw new InvalidPasswordException("La contraseña no cumple con el formato requerido");
        }

        UserLogin userLogin = getUserLogin(userRequestDto);

        UserLogin savedUserLogin = userRepository.save(userLogin);

        return new UserResponseBuilder()
                .withId(savedUserLogin.getId())
                .withName(savedUserLogin.getName())
                .withEmail(savedUserLogin.getEmail())
                .withCreated(savedUserLogin.getCreated())
                .withModified(savedUserLogin.getModified())
                .withLastLogin(savedUserLogin.getLastLogin())
                .withToken(savedUserLogin.getToken())
                .withIsActive(savedUserLogin.isActive())
                .build();
    }

    public UserLogin getUserLogin(UserRequestDto userRequestDto) {
        return new UserBuilder()
                .withName(userRequestDto.getName())
                .withEmail(userRequestDto.getEmail())
                .withPassword(userRequestDto.getPassword(), passwordEncoder)
                .withPhones(userRequestDto.getPhones())
                .withToken(jwtTokenProvider.createToken(userRequestDto.getEmail()))
                .build();
    }
}
