package com.example.demo.dto;

public class JwtResponseDTO {
    private String accessToken;
    private String token;

    private JwtResponseDTO() {}

    public String getAccessToken() {
        return accessToken;
    }

    public String getToken() {
        return token;
    }

    public static JwtResponseDTOBuilder builder() {
        return new JwtResponseDTOBuilder();
    }

    public static class JwtResponseDTOBuilder {
        private String accessToken;
        private String token;

        public JwtResponseDTOBuilder accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public JwtResponseDTOBuilder token(String token) {
            this.token = token;
            return this;
        }

        public JwtResponseDTO build() {
            JwtResponseDTO dto = new JwtResponseDTO();
            dto.accessToken = this.accessToken;
            dto.token = this.token;
            return dto;
        }
    }
}
