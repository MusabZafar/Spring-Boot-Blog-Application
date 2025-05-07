package com.springboot.blogApp.Service;

import DTO_payLoad.LoginDto;
import DTO_payLoad.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
}
