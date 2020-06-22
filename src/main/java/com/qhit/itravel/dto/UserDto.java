package com.qhit.itravel.dto;

import com.qhit.itravel.entity.SysUser;

import java.util.List;

public class UserDto extends SysUser {
    private static final long serialVersionUID = -198258276864645788L;

    private List<Long> roleIds;

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }
}
