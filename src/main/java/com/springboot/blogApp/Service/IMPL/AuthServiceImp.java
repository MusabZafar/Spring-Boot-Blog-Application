package com.springboot.blogApp.Service.IMPL;

import DTO_payLoad.LoginDto;
import DTO_payLoad.RegisterDto;
import com.springboot.blogApp.Entity.Role;
import com.springboot.blogApp.Entity.User;
import com.springboot.blogApp.Exception.BlogAPIException;
import com.springboot.blogApp.Repository.RoleRespository;
import com.springboot.blogApp.Repository.UserRepository;
import com.springboot.blogApp.Security.JwtTokenProvider;
import com.springboot.blogApp.Service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImp  implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRespository roleRespository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    public AuthServiceImp(AuthenticationManager authenticationManager, UserRepository userRepository,
                          RoleRespository roleRespository,
                          PasswordEncoder passwordEncoder,
                          JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRespository = roleRespository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication=authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken
                (loginDto.getUsername(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        return token;
    }

    @Override
    public String register(RegisterDto registerDto) {

        //Add check for username exist in database
        if (userRepository.existsByUsername(registerDto.getUsername())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"username is already exists");
        }
        User user=new User();
        user.setName(registerDto.getUsername());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles=new HashSet<>();
        Role userRole=roleRespository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);
        return "User Registered Successfully";
    }
}
