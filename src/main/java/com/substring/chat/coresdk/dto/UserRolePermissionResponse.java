package com.substring.chat.coresdk.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRolePermissionResponse {
    private String id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String password;
    private Boolean isActive = Boolean.FALSE;
    private Set<RolePermissionListResponse> roles = new HashSet<>();
}