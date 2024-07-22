package com.iisi.backendbase.framework.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO extends BaseDTO {
    private String username;
    private String password;
    private String jwtToken;
    private List<String> roles;
}