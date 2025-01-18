package com.polling.polling_api.dtos;


import lombok.Data;

import java.util.Set;

@Data
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String username;
    private Set<String> roles;
    private String createdAt;
    private String updatedAt;
}
