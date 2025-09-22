package com.api.controllers.auth.dto;

import com.api.services.auth.dto.SignUpInput;
import com.shared.enumeration.EnumValidator;
import com.shared.enumeration.UserRole;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    @EnumValidator(enumClass = UserRole.class)
    private UserRole role;

    public SignUpInput toInput() {
        return SignUpInput.builder()
                .username(this.username)
                .password(this.password)
                .role(this.role)
                .build();
    }

}
