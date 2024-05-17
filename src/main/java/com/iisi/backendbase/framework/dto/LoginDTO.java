package com.iisi.backendbase.framework.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDTO {

    private String username;
    private String password;
    private String jwtToken;
}