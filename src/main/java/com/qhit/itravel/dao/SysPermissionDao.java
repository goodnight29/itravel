package com.qhit.itravel.dao;

import com.qhit.itravel.entity.SysPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (SysPermission)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-12 09:48:33
 */
@Mapper
@Repository
public interface SysPermissionDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysPermission queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysPermission> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param sysPermission 实例对象
     * @return 对象列表
     */
    List<SysPermission> queryAll(SysPermission sysPermission);

    /**
     * 新增数据
     *
     * @param sysPermission 实例对象
     * @return 影响行数
     */
    int insert(SysPermission sysPermission);

    /**
     * 修改数据
     *
     * @param sysPermission 实例对象
     * @return 影响行数
     */
    int update(SysPermission sysPermission);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    //用户角色表
    //角色权限表
    //权限表
    @Select("select distinct p.* from sys_permission p inner join sys_role_permission  rp on p.id = rp.permissionId inner join sys_role_user sr on rp.roleId = sr.roleId where userId=#{id}")
    List<SysPermission> findPermissionsByUserId(Integer id);


    @Select("select * from sys_permission t order by t.sort")
    List<SysPermission> getAll();

    /**
     * 查询所有的一级菜单
     * @return
     */
    @Select("select * from sys_permission t  where type=1 order by t.sort")
    List<SysPermission> getParentsMenus();
}