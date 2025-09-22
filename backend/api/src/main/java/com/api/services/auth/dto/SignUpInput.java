package com.api.services.auth.dto;

import com.shared.enumeration.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpInput {

    private String username;

    private String password;

    private UserRole role;

}
