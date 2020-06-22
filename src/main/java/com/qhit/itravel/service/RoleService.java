package com.qhit.itravel.service;


import com.qhit.itravel.dto.RoleDto;

public interface RoleService {

	void saveRole(RoleDto roleDto);

	void deleteRole(Long id);
}
