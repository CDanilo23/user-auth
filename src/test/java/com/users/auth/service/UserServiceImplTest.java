package com.users.auth.service;

import com.users.auth.config.JwtTokenProvider;
import com.users.auth.dto.UserRequestDto;
import com.users.auth.dto.response.UserResponseDto;
import com.users.auth.exception.EmailAlreadyExistsException;
import com.users.auth.exception.InvalidEmailException;
import com.users.auth.exception.InvalidPasswordException;
import com.users.auth.model.UserLogin;
import com.users.auth.repository.UserRepository;
import com.users.auth.service.impl.UserServiceImpl;
import com.users.auth.util.UserBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserBuilder userBuilder;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    private static final String NAME = "Cristian Ordonez";
    private static final String EMAIL = "cristian@any.cl";
    private static final String PASSWORD = "password123";
    private static final String MOCKED_TOKEN = "mockedToken123";
    private static final String ENCODED_PASSWORD = "encodedPassword123";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(userServiceImpl, "passwordPattern", "^.{8,}$");
        ReflectionTestUtils.setField(userServiceImpl, "emailPattern", "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.cl$");
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUsuario_Success() {

        UserRequestDto userRequestDto = new UserRequestDto(NAME, EMAIL, PASSWORD, List.of());

        when(passwordEncoder.encode(anyString())).thenReturn(ENCODED_PASSWORD);
        when(jwtTokenProvider.createToken(anyString())).thenReturn(MOCKED_TOKEN);

        when(userRepository.existsByEmail(anyString())).thenReturn(Optional.empty());

        UserLogin savedUserLogin = new UserLogin();
        savedUserLogin.setId(UUID.randomUUID());
        savedUserLogin.setName(NAME);
        savedUserLogin.setEmail(EMAIL);
        savedUserLogin.setPassword(PASSWORD);
        savedUserLogin.setCreated(LocalDateTime.now());
        savedUserLogin.setModified(LocalDateTime.now());
        savedUserLogin.setLastLogin(LocalDateTime.now());
        savedUserLogin.setActive(true);
        savedUserLogin.setToken(MOCKED_TOKEN);

        when(userRepository.save(any(UserLogin.class))).thenReturn(savedUserLogin);

        UserResponseDto response = userServiceImpl.createUsuario(userRequestDto);

        verify(userRepository, times(1)).save(any(UserLogin.class));
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertNotNull(response.getToken());
    }

    @Test
    void testCreateUsuario_EmailAlreadyExists() {
        UserRequestDto userRequestDto = new UserRequestDto(NAME, EMAIL, PASSWORD, List.of());
        when(userRepository.existsByEmail(anyString())).thenReturn(Optional.of(new UserLogin()));

        assertThrows(EmailAlreadyExistsException.class, () -> userServiceImpl.createUsuario(userRequestDto));
    }

    @Test
    void testCreateUsuario_InvalidEmailFormat() {
        UserRequestDto userRequestDto = new UserRequestDto(NAME,"otro@email.com",PASSWORD, List.of());

        assertThrows(InvalidEmailException.class, () -> userServiceImpl.createUsuario(userRequestDto));
    }

    @Test
    void testCreateUsuario_InvalidPasswordFormat() {
        UserRequestDto userRequestDto = new UserRequestDto(NAME, EMAIL, "short", List.of());

        assertThrows(InvalidPasswordException.class, () -> userServiceImpl.createUsuario(userRequestDto));
    }
}
