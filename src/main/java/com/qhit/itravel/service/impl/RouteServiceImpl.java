package com.qhit.itravel.service.impl;

import com.qhit.itravel.dao.FavoriteDao;
import com.qhit.itravel.dao.RouteDao;
import com.qhit.itravel.entity.Favorite;
import com.qhit.itravel.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service("routeService")
public class RouteServiceImpl implements RouteService {

    @Autowired
    private RouteDao routeDao;

    @Autowired
    private FavoriteDao favoriteDao;

    @Override
    @Transactional//事务
    public void saveFavorite(Long rid, Integer uid) {
        //操作路线表
        routeDao.updateFavorite(rid);
        //构建Favorite对象
        Favorite favorite = new Favorite();
        favorite.setRid(rid.intValue());
        favorite.setUid(uid);
        favorite.setDate(new Date());

        favoriteDao.insert(favorite);
    }
}
