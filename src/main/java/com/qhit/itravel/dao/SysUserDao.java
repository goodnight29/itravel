package com.qhit.itravel.dao;

import com.qhit.itravel.entity.Role;
import com.qhit.itravel.entity.SysUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * (SysUser)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-11 08:40:19
 */
@Mapper
@Repository
public interface SysUserDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysUser queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysUser> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param sysUser 实例对象
     * @return 对象列表
     */
    List<SysUser> queryAll(SysUser sysUser);

    /**
     * 新增数据
     *
     * @param sysUser 实例对象
     * @return 影响行数
     */
    int insert(SysUser sysUser);

    /**
     * 修改数据
     *
     * @param sysUser 实例对象
     * @return 影响行数
     */
    int update(SysUser sysUser);


    SysUser getUserByName(String username);
    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    @Select("select * from sys_user where username=#{username}")
    SysUser findUserByName(String username);


    @Select("select * from sys_role r inner join sys_role_user s on r.id=s.roleId where userId = #{id}")
    List<Role> findUserRolesByUserId(Integer id);

    int count(@Param("params") Map<String, Object> params);

    List<SysUser> getPageData(@Param("params")Map<String, Object> params, @Param("offset") Integer offset, @Param("limit") Integer limit);

    @Delete("delete from sys_role_user where userId = #{id}")
    void deleteUserRole(Integer id);

    void saveUserRoles(@Param("userId") Integer id, @Param("roleIds") List<Long> roleIds);
}