package com.qhit.itravel.entity;

import java.io.Serializable;
import java.util.List;

/**
 * (SysPermission)实体类
 *
 * @author makejava
 * @since 2020-03-12 09:48:33
 */
public class SysPermission implements Serializable {
    private static final long serialVersionUID = -21146698776799563L;

    //菜单本身的ID
    private Integer id;

    //父菜单的ID
    private Integer parentid;
    
    private String name;
    
    private String css;
    
    private String href;
    
    private Integer type;

    /**
     * 权限
     */
    private String permission;
    
    private Integer sort;

    //在Java描述里体现这个父子菜单的关系
    private List<SysPermission> child;

    public List<SysPermission> getChild() {
        return child;
    }

    public void setChild(List<SysPermission> child) {
        this.child = child;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

}