package com.tdidok.literature.identityservice.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class UserDto {
    private String username;
    private String password;
}
