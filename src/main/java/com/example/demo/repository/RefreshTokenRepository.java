package com.example.demo.repository;

import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Integer> {
    Optional<RefreshToken> findByToken(String token);
    
    @Query("SELECT rt FROM RefreshToken rt WHERE rt.userInfo.id = :userId")
    Optional<RefreshToken> findByUserId(@Param("userId") int userId);
    
    // Method to delete RefreshToken by userId
    @Modifying
    @Query("DELETE FROM RefreshToken rt WHERE rt.userInfo.id = :userId")
    void deleteByUserId(@Param("userId") int userId);
}
