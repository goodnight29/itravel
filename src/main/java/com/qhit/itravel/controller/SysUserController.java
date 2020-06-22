package com.qhit.itravel.controller;

import com.qhit.itravel.dto.UserDto;
import com.qhit.itravel.entity.SysUser;
import com.qhit.itravel.service.SysUserService;
import com.qhit.itravel.utils.page.PageTableHandler;
import com.qhit.itravel.utils.page.PageTableRequest;
import com.qhit.itravel.utils.page.PageTableResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (SysUser)表控制层
 *
 * @author makejava
 * @since 2020-03-11 08:40:23
 */
@RestController
@RequestMapping("/sys")
public class SysUserController {
    /**
     * 服务对象
     */
    @Resource
    private SysUserService sysUserService;


    @Autowired
    private ServerProperties serverProperties;

    @RequestMapping("/login")
    public void login(String username, String password){
        //将前台传过来的用户名和密码封装成shiro登录需要的口令
        System.out.println(username + "  "+password);
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        subject.login(usernamePasswordToken);

        //给shiro的session过期时间
        SecurityUtils.getSubject().getSession().setTimeout(serverProperties.getServlet().getSession().getTimeout().toMillis());

    }

    @GetMapping("/users")
    //这个方法需要权限才能访问
    @RequiresPermissions("sys:user:query")
    public PageTableResponse getAllUsers(PageTableRequest request){
        PageTableHandler pageTableHandler = new PageTableHandler(new PageCount(), new PageList());
        PageTableResponse pageTableResponse = pageTableHandler.handle(request);

        return pageTableResponse;

    }
    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/selectOne")
    @RequiresPermissions("sys:user:query")
    public SysUser selectOne(Integer id) {
        return this.sysUserService.queryById(id);
    }

    class PageCount implements PageTableHandler.CountHandler{


        @Override
        public int count(PageTableRequest request) {
            int rows = sysUserService.count(request.getParams());
            return rows;
        }
    }

    class PageList implements PageTableHandler.ListHandler{

        @Override
        public List<?> list(PageTableRequest request) {
            Integer offset = request.getOffset();
            Integer limit = request.getLimit();
            List<SysUser> list = sysUserService.getPageData(request.getParams(), offset, limit);
            return list;
        }
    }



    @PostMapping("/add")
    @RequiresPermissions("sys:user:add")
    public SysUser saveUser(@RequestBody UserDto userDto){
        //保持之前先查找，根据名字查找，判断是已经注册过
        SysUser user = sysUserService.findUserByName(userDto.getUsername());

        if (user!=null){
            throw new IllegalArgumentException(userDto.getUsername()+"该用户名已经存在");
        }

        return sysUserService.addUser(userDto);
    }

    @PostMapping("/edit")
    @RequiresPermissions("sys:user:add")
    public SysUser editUser(@RequestBody UserDto userDto){

        return sysUserService.editUser(userDto);
    }

}