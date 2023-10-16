package com.example.demo.dto;

import org.springframework.stereotype.Component;

import com.example.demo.entity.UserInfo;

@Component
public class UserCreateDTOMapper {
	public UserCreateDTO toDto(UserInfo userInfo) {
		return new UserCreateDTO(userInfo.getId(), userInfo.getName(), userInfo.getEmail(),
				userInfo.getRoles(), userInfo.getPassword());
	}

	public UserInfo toUser(UserCreateDTO userCreateDTO) {
		System.out.println("JE SUIS LAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		return new UserInfo(userCreateDTO.getId(), userCreateDTO.getName(), userCreateDTO.getEmail(), 
				userCreateDTO.getRoles(), userCreateDTO.getPassword());
	}
}
