package com.example.demo.entity;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String token;
    private Instant expiryDate;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserInfo userInfo;

    // Private constructor to prevent direct instantiation
    private RefreshToken() {}

    // Create a static builder method
    public static RefreshTokenBuilder builder() {
        return new RefreshTokenBuilder();
    }

    // Getter methods

    public int getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    // Builder class
    public static class RefreshTokenBuilder {
        private int id;
        private String token;
        private Instant expiryDate;
        private UserInfo userInfo;

        public RefreshTokenBuilder id(int id) {
            this.id = id;
            return this;
        }

        public RefreshTokenBuilder token(String token) {
            this.token = token;
            return this;
        }

        public RefreshTokenBuilder expiryDate(Instant expiryDate) {
            this.expiryDate = expiryDate;
            return this;
        }

        public RefreshTokenBuilder userInfo(UserInfo userInfo) {
            this.userInfo = userInfo;
            return this;
        }

        public RefreshToken build() {
            RefreshToken refreshToken = new RefreshToken();
            refreshToken.id = this.id;
            refreshToken.token = this.token;
            refreshToken.expiryDate = this.expiryDate;
            refreshToken.userInfo = this.userInfo;
            return refreshToken;
        }
    }
}
