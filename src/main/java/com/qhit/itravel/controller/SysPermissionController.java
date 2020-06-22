package com.qhit.itravel.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.qhit.itravel.entity.SysPermission;
import com.qhit.itravel.entity.SysUser;
import com.qhit.itravel.service.SysPermissionService;
import com.qhit.itravel.utils.UserUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * (SysPermission)表控制层
 *
 * @author makejava
 * @since 2020-03-12 09:48:33
 */
@RestController
@RequestMapping("/permissions")
public class SysPermissionController {
    /**
     * 服务对象
     */
    @Resource
    private SysPermissionService sysPermissionService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public SysPermission selectOne(Integer id) {
        return this.sysPermissionService.queryById(id);
    }



    @PostMapping("/current")
    public List<SysPermission> getPermissionsCurrent(){
        List<SysPermission> list = UserUtil.getCurrentPermissions();
        if (list==null){
            SysUser currentUser = UserUtil.getCurrentUser();
            list = sysPermissionService.findPermissionsByUserId(currentUser.getId());
            UserUtil.setPermissionSession(list);
        }

        //获取type=1的菜单
        List<SysPermission> permissionsTypeIs1 = new ArrayList<>();
        List<SysPermission> permissionsFirstLevel = new ArrayList<>();

        for (SysPermission permission : list) {
            if (permission.getType()==1){
                permissionsTypeIs1.add(permission);
            }

            //一级菜单
            if (permission.getParentid()==0){
                permissionsFirstLevel.add(permission);
            }
        }

        //为1级菜单绑定其子菜单
        for (SysPermission sysPermission : permissionsFirstLevel) {
            setChildren(sysPermission, permissionsTypeIs1);
        }

        return permissionsFirstLevel;

    }

    /**
     *
     * 递归为每个菜单寻找其子菜单
     * @param p
     * @param permissions
     */
    private void setChildren(SysPermission p, List<SysPermission> permissions) {
        List<SysPermission> children = new ArrayList<>();
        for (SysPermission permission : permissions) {
            if (p.getId() == permission.getParentid()){
                children.add(permission);
            }
        }
        p.setChild(children);

        //递归处理
        if (children!=null && children.size()>0){
            for (SysPermission child : children) {
                setChildren(child, permissions);
            }
        }
    }

    /**
     * 校验权限
     *
     * @return
     */
    @GetMapping("/owns")
    public Set<String> ownsPermission() {
        List<SysPermission> permissions = UserUtil.getCurrentPermissions();
        if (CollectionUtils.isEmpty(permissions)) {
            return Collections.emptySet();
        }

        return permissions.parallelStream().filter(p -> !StringUtils.isEmpty(p.getPermission()))
                .map(SysPermission::getPermission).collect(Collectors.toSet());
    }


    @GetMapping("/all")
    @RequiresPermissions("sys:menu:query")
    public JSONArray listAll(){
        List<SysPermission> allPermissions = sysPermissionService.getAll();

        JSONArray array = new JSONArray();

        buildTreePermissions(0, allPermissions, array);

        return array;
    }

    private void buildTreePermissions(int pId, List<SysPermission> allPermissions,
                                      JSONArray array) {

        for (SysPermission permission : allPermissions) {
            if (permission.getParentid()==pId){
                String json = JSONObject.toJSONString(permission);
                JSONObject parent = (JSONObject)JSONObject.parse(json);
                array.add(parent);
                for (SysPermission p : allPermissions) {
                    if (permission.getId()==p.getParentid()){
                        JSONArray children = new JSONArray();
                        parent.put("child", children);
                        buildTreePermissions(permission.getId(), allPermissions, children);
                    }
                }
            }
        }
    }


    /**
     * 菜单列表
     *
     * @param pId
     * @param permissionsAll
     * @param list
     */
    private void setPermissionsList(Integer pId, List<SysPermission> permissionsAll, List<SysPermission> list) {
        for (SysPermission per : permissionsAll) {
            if (per.getParentid().equals(pId)) {
                list.add(per);

                for (SysPermission p : permissionsAll) {
                    if (per.getId()==p.getParentid()){
                        setPermissionsList(per.getId(), permissionsAll, list);
                        break;
                    }

                }

            }
        }
    }

    @GetMapping("/allType1Menus")
    @RequiresPermissions("sys:menu:query")
    public List<SysPermission> permissionsList() {
        List<SysPermission> permissionsAll = sysPermissionService.queryAll();

        List<SysPermission> list = Lists.newArrayList();
        setPermissionsList(0, permissionsAll, list);

        return list;
    }


    @GetMapping("/parents")
    @RequiresPermissions("sys:menu:query")
    public List<SysPermission> getParentsMenus(){
        return sysPermissionService.getParentsMenus();
    }


    @PostMapping
    @RequiresPermissions("sys:menu:add")
    public void addMenu(@RequestBody SysPermission sysPermission){
        sysPermissionService.insert(sysPermission);
    }


    @DeleteMapping("/{id}")
    @RequiresPermissions("sys:menu:delete")
    public void deleteMenus(@PathVariable Integer id){
        sysPermissionService.deleteById(id);
    }


    @GetMapping("/{id}")
    @RequiresPermissions("sys:menu:query")
    public SysPermission getMenuById(@PathVariable Integer id){
        return sysPermissionService.queryById(id);
    }

    @PutMapping
    @RequiresPermissions("sys:menu:add")
    public void updateMenu(@RequestBody SysPermission sysPermission){
        sysPermissionService.update(sysPermission);
    }
}