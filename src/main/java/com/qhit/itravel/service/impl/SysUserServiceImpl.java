package com.qhit.itravel.service.impl;

import com.qhit.itravel.dto.UserDto;
import com.qhit.itravel.entity.Role;
import com.qhit.itravel.entity.SysUser;
import com.qhit.itravel.dao.SysUserDao;
import com.qhit.itravel.service.SysUserService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * (SysUser)表服务实现类
 *
 * @author makejava
 * @since 2020-03-11 08:40:22
 */
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {
    @Resource
    private SysUserDao sysUserDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysUser queryById(Integer id) {
        return this.sysUserDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<SysUser> queryAllByLimit(int offset, int limit) {
        return this.sysUserDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    @Override
    public SysUser insert(SysUser sysUser) {
        this.sysUserDao.insert(sysUser);
        return sysUser;
    }

    /**
     * 修改数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    @Override
    public SysUser update(SysUser sysUser) {
        this.sysUserDao.update(sysUser);
        return this.queryById(sysUser.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.sysUserDao.deleteById(id) > 0;
    }

    @Override
    public SysUser findUserByName(String username) {

        return sysUserDao.findUserByName(username);
    }

    @Override
    public String passwordEncoder(String credentials, String salt) {
        Object object = new SimpleHash("MD5", credentials, salt, 3);
        return object.toString();
    }

    @Override
    public List<Role> findUserRolesByUserId(Integer id) {
        return sysUserDao.findUserRolesByUserId(id);
    }

    @Override
    public int count(Map<String, Object> params) {
        return sysUserDao.count(params);
    }

    @Override
    public List<SysUser> getPageData(Map<String, Object> params, Integer offset, Integer limit) {
        return sysUserDao.getPageData(params, offset, limit);
    }

    @Override
    @Transactional
    public SysUser addUser(UserDto userDto) {
        //1)密码
        SysUser user = userDto;
        //加盐
        String salt = DigestUtils.md5DigestAsHex((UUID.randomUUID().toString()+
                System.currentTimeMillis()+toString()).getBytes());
        user.setSalt(salt);
        user.setPassword(passwordEncoder(user.getPassword(), user.getSalt()));
        user.setStatus(1L);
        //2)处理用户表
        sysUserDao.insert(user);
        //3)用户角色表
        savaUserRoles(user.getId(), userDto.getRoleIds());

        return user;
    }

    @Override
    @Transactional
    public SysUser editUser(UserDto userDto) {
        //1)修改用户信息
        SysUser user = userDto;
        sysUserDao.update(user);
        //2)更新用户角色角色信息
        savaUserRoles(user.getId(), userDto.getRoleIds());
        return userDto;
    }

    private void savaUserRoles(Integer id, List<Long> roleIds) {
        if (roleIds!=null){
            //先删除该用户已有的角色
            sysUserDao.deleteUserRole(id);
            if (roleIds.size()>0){
                sysUserDao.saveUserRoles(id, roleIds);
            }
        }

    }

}