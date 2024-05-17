package com.iisi.backendbase.framework.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Builder
@Accessors(chain = true)
public class LoginDTO {
    private String username;
    private String password;
    private String jwtToken;
    private List<String> roles;
}