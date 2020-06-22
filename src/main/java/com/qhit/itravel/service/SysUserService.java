package com.qhit.itravel.service;

import com.qhit.itravel.dto.UserDto;
import com.qhit.itravel.entity.Role;
import com.qhit.itravel.entity.SysUser;
import java.util.List;
import java.util.Map;

/**
 * (SysUser)表服务接口
 *
 * @author makejava
 * @since 2020-03-11 08:40:21
 */
public interface SysUserService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysUser queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysUser> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    SysUser insert(SysUser sysUser);

    /**
     * 修改数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    SysUser update(SysUser sysUser);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    SysUser findUserByName(String username);

    //对原密码进行加密，盐值
    String passwordEncoder(String credentials, String salt);

    List<Role> findUserRolesByUserId(Integer id);

    int count(Map<String, Object> params);

    List<SysUser> getPageData(Map<String, Object> params, Integer offset, Integer limit);

    SysUser addUser(UserDto userDto);

    SysUser editUser(UserDto userDto);
}