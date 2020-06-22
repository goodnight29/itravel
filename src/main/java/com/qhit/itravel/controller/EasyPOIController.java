package com.qhit.itravel.controller;

import com.qhit.itravel.dao.RouteDao;
import com.qhit.itravel.entity.Route;
import com.qhit.itravel.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * describe:
 *
 * @author dengzl
 * @date 2019/06/10
 */
@RestController
public class EasyPOIController {

    @Autowired
    private RouteDao routeDao;

    @GetMapping("/exportExcel")
    public void export(HttpServletResponse response) throws Exception {
        List<Route> list = routeDao.queryAll();

        //导出
        FileUtil.exportExcel(list,Route.class,"现有旅游路线.xls",response);
    }

    @RequestMapping("/importExcel")
    public void importExcel() throws Exception {
        //本地文件 模拟文件上传解析
        //String filePath = "/Users/dengzhili/Downloads/商品.xls";
        //解析excel
        //List<Goods> goodsList = FileUtil.importExcel(filePath,0,1,Goods.class);
        //也可以使用MultipartFile,使用 FileUtil.importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass)导入
        //System.out.println("导入数据一共【"+goodsList.size()+"】行");

        //TODO 保存数据库

    }
}