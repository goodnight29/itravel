package com.qhit.itravel.controller;

import java.io.IOException;
import java.util.List;

import com.qhit.itravel.dao.RouteDao;
import com.qhit.itravel.entity.FileInfo;
import com.qhit.itravel.entity.Route;
import com.qhit.itravel.service.FileService;
import com.qhit.itravel.service.RouteService;
import com.qhit.itravel.utils.UserUtil;
import com.qhit.itravel.utils.page.PageTableHandler;
import com.qhit.itravel.utils.page.PageTableRequest;
import com.qhit.itravel.utils.page.PageTableResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

//RestController = controler + responseBody;复合注解
@RestController
@RequestMapping("/routes")
public class RouteController {

    @Autowired
    private RouteDao routeDao;

    @Autowired
    private FileService fileService;

    @Autowired
    private RouteService routeService;

    /**
     * 同时上传文件和表单数据
     * @param file
     * @param route
     * @return
     * @throws IOException
     */
    @PostMapping(headers = "content-type=multipart/*")
    public Route save(@RequestParam(value = "file",required = false) MultipartFile file,
                      Route route) throws IOException {

        FileInfo fileInfo = fileService.save(file);
        route.setRimage("/img/product"+fileInfo.getUrl());
        routeDao.save(route);

        return route;
    }

    @GetMapping("/{id}")
    public Route get(@PathVariable Long id) {
        return routeDao.getById(id);
    }

    @PutMapping
    public Route update(@RequestBody Route route) {
        routeDao.update(route);

        return route;
    }

    @GetMapping
    public PageTableResponse list(PageTableRequest request) {
        return new PageTableHandler(new PageTableHandler.CountHandler() {

            @Override
            public int count(PageTableRequest request) {
                return routeDao.count(request.getParams());
            }
        }, new PageTableHandler.ListHandler() {

            @Override
            public List<Route> list(PageTableRequest request) {
                return routeDao.list(request.getParams(), request.getOffset(), request.getLimit());
            }
        }).handle(request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        routeDao.delete(id);
    }



    @GetMapping("/favorite")
    public Route addToFavorite(@RequestParam("rid") Long rid){

        //拿到uid，当前登录用户ID
        Integer uid = UserUtil.getCurrentUser().getId();
        //更新路线表及收藏表
        routeService.saveFavorite(rid,uid);

        //收藏此时增加之后再次查询
        return routeDao.getById(rid);
    }
}
