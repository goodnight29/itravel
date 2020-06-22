package com.qhit.itravel.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qhit.itravel.dao.RouteDao;
import com.qhit.itravel.entity.Category;
import com.qhit.itravel.entity.Route;
import com.qhit.itravel.entity.RouteImg;
import com.qhit.itravel.entity.Seller;
import com.qhit.itravel.service.CategoryService;
import com.qhit.itravel.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RouteDao routeDao;

    @Resource
    private RedisUtil redisUtil;

    @RequestMapping("/index")
    public String toIndex(Model model){

        help(model);
        return "index";
    }

    @GetMapping("/getRouteList")
    public String getRouteList(@RequestParam("cid") Integer cid,Model model,
                               @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize){
        //1)启动分页
        PageHelper.startPage(pageNum, pageSize);
        //2)查找数据
        List<Route> routes = routeDao.getRouteListByCid(cid);
        //3)构建pageInfo并返回
        PageInfo<Route> pageInfo = new PageInfo<>(routes);

        //4)通过model把数据传回去
        model.addAttribute("pageInfo",pageInfo);


        //处理头部的分类信息缓存
        help(model);


        return "route_list";
    }


    @GetMapping("/routedetail")
    public String routeDetail(@RequestParam("rid") Long rid,Model model){
        Route route = routeDao.getById(rid);
        model.addAttribute("route",route);

        //商家信息
        Seller seller = routeDao.getSeller(route.getSid());
        model.addAttribute("seller",seller);

        //根据当前路线rid,路线大图小图信息，
        List<RouteImg> list = routeDao.getRouteImgsByRid(route.getRid());
        model.addAttribute("imgs",list);
        help(model);

        return "route_detail";
    }



    private  void help(Model model){
        //看一下redis里有没有缓存数据，如果有取缓存，如果没有则去查数据库，
        // 从数据库里查出来别忘了往缓存里写一份
        List<Object> categoriesList = redisUtil.rangeList("categoriesList", 0, -1);
        if (categoriesList==null || categoriesList.size()==0){
            // 把分类数据带过去
            List<Category> list = categoryService.queryAll();
            redisUtil.lSet("categoriesList",list);
            redisUtil.expire("categoriesList",60*60*60);
            model.addAttribute("categories",list);
        }else {
            model.addAttribute("categories",categoriesList.get(0));
            System.out.println(categoriesList.get(0));
        }
    }
}
