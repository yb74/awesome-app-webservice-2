package com.example.demo.dto;

import org.springframework.stereotype.Component;

import com.example.demo.entity.UserInfo;

@Component
public class UserInfoMapper {
	public UserInfoDTO toDto(UserInfo userInfo) {
		return new UserInfoDTO(userInfo.getId(), userInfo.getName(), userInfo.getEmail(),
				userInfo.getRoles());
	}

	public UserInfo toUser(UserInfoDTO userInfoDto) {
		return new UserInfo(userInfoDto.getId(), userInfoDto.getName(), userInfoDto.getEmail(), 
				userInfoDto.getRoles());
	}
}
