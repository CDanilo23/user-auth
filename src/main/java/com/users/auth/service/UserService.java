package com.users.auth.service;

import com.users.auth.dto.UserRequestDto;
import com.users.auth.dto.response.UserResponseDto;

public interface UserService {

    UserResponseDto createUsuario(UserRequestDto usuario);

}
