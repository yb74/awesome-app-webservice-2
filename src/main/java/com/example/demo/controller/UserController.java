package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.JwtResponseDTO;
import com.example.demo.dto.RefreshTokenRequestDTO;
import com.example.demo.dto.UserInfoDTO;
import com.example.demo.entity.AuthRequest;
import com.example.demo.entity.RefreshToken;
import com.example.demo.entity.UserInfo;
import com.example.demo.service.JwtServiceImplementation;
import com.example.demo.service.RefreshTokenService;
import com.example.demo.service.UserInfoService;

import org.springframework.security.core.AuthenticationException;


import jakarta.servlet.http.HttpServletRequest; 

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/auth") 
public class UserController { 

	@Autowired
	private UserInfoService service; 

	@Autowired
	private JwtServiceImplementation jwtService;
	
	 @Autowired
	 private RefreshTokenService refreshTokenService;

	@Autowired
	private AuthenticationManager authenticationManager; 

	@GetMapping("/welcome") 
	public String welcome() { 
		return "Welcome this endpoint is not secure"; 
	} 
	
	@GetMapping("/user/userdetails")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public UserInfoDTO findByUsername(HttpServletRequest request) {
		System.out.println("user details controller");
		String authorizationHeader = request.getHeader("Authorization");
		if (authorizationHeader != null & authorizationHeader.startsWith("Bearer ")) {
			System.out.println(authorizationHeader);
			String token = authorizationHeader.substring(7);
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String username = authentication.getName();

			return service.findUserByUsername(username);
		} else {
			return null;
		}
	}
	
	@PostMapping("/addNewUser") 
	public String addNewUser(@RequestBody UserInfo userInfo) { 
		return service.addUser(userInfo); 
	}
	
	@GetMapping("/admin/adminProfile") 
	@PreAuthorize("hasAuthority('ROLE_ADMIN')") 
	public String adminProfile() { 
		return "Welcome to Admin Profile"; 
	} 


	@PostMapping("/generateToken")
    public JwtResponseDTO authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            
            if (authentication.isAuthenticated()) {
            	// if there is already a refreshToken in db, we delete it
	           	 int userId = service.findUserByUsername(authRequest.getUsername()).getId();
	           	 // deleteRefreshTokenById
	           	 refreshTokenService.deleteTokenByUserId(userId);
           	 
            	 RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequest.getUsername());
                
                return JwtResponseDTO.builder()
                        .accessToken(jwtService.generateToken(authRequest.getUsername()))
                        .token(refreshToken.getToken()).build();
            } else {
                // Handle the case where authentication succeeded but the user is not authorized.
                throw new org.springframework.security.access.AccessDeniedException("Access denied"); // or a custom exception
            }
        } catch (AuthenticationException e) {
            // Handle the case where authentication failed.
            throw new BadCredentialsException("Invalid credentials"); // or a custom exception
        }
    }
	
    @PostMapping("/refreshToken")
    public JwtResponseDTO refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequest) {
        return refreshTokenService.findByToken(refreshTokenRequest.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserInfo)
                .map(userInfo -> {
                    String accessToken = jwtService.generateToken(userInfo.getName());
                    return JwtResponseDTO.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenRequest.getToken())
                            .build();
                }).orElseThrow(() -> new RuntimeException(
                        "Refresh token is not in database!"));
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

} 

