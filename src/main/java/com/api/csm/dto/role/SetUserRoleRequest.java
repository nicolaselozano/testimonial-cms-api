package com.api.csm.dto.role;

import com.api.csm.utils.RoleEnum;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class SetUserRoleRequest {
    private UUID userId;
    private List<RoleEnum> roles;
}
