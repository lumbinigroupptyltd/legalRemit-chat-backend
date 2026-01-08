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
public class RolePermissionListResponse {
    private String id;
    private String name;
    private String roleName;
    private String descriptions;
    private Boolean isActive;
    private Set<PermissionCustomResponse> permission = new HashSet<>();
}
