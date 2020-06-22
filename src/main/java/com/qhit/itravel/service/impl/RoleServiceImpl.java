package com.qhit.itravel.service.impl;

import java.util.List;

import com.qhit.itravel.dao.RoleDao;
import com.qhit.itravel.dto.RoleDto;
import com.qhit.itravel.entity.Role;
import com.qhit.itravel.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;



@Service("roleService")
public class RoleServiceImpl implements RoleService {


	@Autowired
	private RoleDao roleDao;

	@Override
	@Transactional
	public void saveRole(RoleDto roleDto) {
		Role role = roleDto;

		if (role.getId() != null) {// 修改
			Role r = roleDao.getRole(role.getName());
			if (r != null && r.getId() != role.getId()) {
				throw new IllegalArgumentException(role.getName() + "已存在");
			}

			roleDao.update(role);
		} else {// 新增
			Role r = roleDao.getRole(role.getName());
			if (r != null) {
				throw new IllegalArgumentException(role.getName() + "已存在");
			}

			roleDao.save(role);

		}

		saveRolePermission(role.getId(), roleDto.getPermissionIds());
	}

	private void saveRolePermission(Long roleId, List<Long> permissionIds) {
		roleDao.deleteRolePermission(roleId);
		permissionIds.remove(0L);
		if (!CollectionUtils.isEmpty(permissionIds)) {
			roleDao.saveRolePermission(roleId, permissionIds);
		}
	}

	@Override
	@Transactional
	public void deleteRole(Long id) {
		//处理事务
		roleDao.deleteRolePermission(id);
		roleDao.deleteRoleUser(id);
		roleDao.delete(id);

	}

}
