package com.example.demo.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserInfoDTO;
import com.example.demo.dto.UserInfoMapper;
import com.example.demo.entity.UserInfo;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserInfoRepository; 

@Service
@Lazy
public class UserInfoService implements UserDetailsService {
	private static final Logger LOG = LoggerFactory.getLogger(UserInfoService.class);

	@Autowired
	private UserInfoRepository repository; 

	@Autowired
	private PasswordEncoder encoder; 
	
	@Autowired
	private UserMapper userMapper; // maybatis mapper (to make sql request)
	
	@Autowired
	private UserInfoMapper userInfoMapper; // mapper to convert entity to DTO and vice-versa

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { 

		Optional<UserInfo> userDetail = repository.findByName(username); 

		// Converting userDetail to UserDetails 
		return userDetail.map(UserInfoDetails::new) 
				.orElseThrow(() -> new UsernameNotFoundException("User not found " + username)); 
	} 
	
	public UserInfoDTO findUserByUsername(String name) {
	    try {
	        if (name != null && !name.isBlank()) {
	            Optional<UserInfo> foundUser = userMapper.findByName(name);
	            
	            UserInfo userInfo = foundUser.orElseThrow(() -> new UsernameNotFoundException("User not found " + name));

	            // Use UserInfoMapper to convert UserInfo to UserInfoDTO
	            UserInfoDTO userInfoDTO = userInfoMapper.toDto(userInfo);

	            return userInfoDTO;
	        } else {
	            throw new IllegalArgumentException("Invalid username");
	        }
	    } catch (UsernameNotFoundException e) {
	        LOG.error("User not found: {}", e.getMessage(), e);
	        e.printStackTrace();
	        return null;
	    }
	}
	
	public String addUser(UserInfo userInfo) {
	    // Use the instance of UserCreateDTOMapper to convert UserCreateDTO to UserInfo
	    userInfo.setPassword(encoder.encode(userInfo.getPassword()));
	    userInfo.setRoles("ROLE_USER");
	    userMapper.insert(userInfo);
	    return "User Added Successfully";
	}


} 
