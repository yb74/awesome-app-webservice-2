package com.example.demo.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.demo.entity.UserInfo;

@Mapper
public interface UserMapper {
	@Select("SELECT * FROM user_info WHERE name = #{name}")
	Optional<UserInfo> findByName(@Param("name") String state);
	
	@Insert("INSERT INTO user_info(name, email, password, roles) VALUES (#{name}, #{email}, #{password}, #{roles})")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	void insert(UserInfo userInfo);
}
