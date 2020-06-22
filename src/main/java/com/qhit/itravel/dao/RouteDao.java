package com.qhit.itravel.dao;

import java.util.List;
import java.util.Map;

import com.qhit.itravel.entity.RouteImg;
import com.qhit.itravel.entity.Seller;
import org.apache.ibatis.annotations.*;

import com.qhit.itravel.entity.Route;

@Mapper
public interface RouteDao {

    @Select("select * from route t where t.rid = #{id}")
    Route getById(Long id);

    @Delete("delete from route where rid = #{id}")
    int delete(Long id);

    int update(Route route);
    
    @Options(useGeneratedKeys = true, keyProperty = "rid")
    @Insert("insert into route(rid, rname, price, routeIntroduce, rflag, rdate, isThemeTour, count, cid, rimage, sid, sourceId) values(#{rid}, #{rname}, #{price}, #{routeIntroduce}, #{rflag}, #{rdate}, #{isThemeTour}, #{count}, #{cid}, #{rimage}, #{sid}, #{sourceId})")
    int save(Route route);
    
    int count(@Param("params") Map<String, Object> params);

    List<Route> list(@Param("params") Map<String, Object> params, @Param("offset") Integer offset, @Param("limit") Integer limit);

    @Select("select * from route t where t.cid = #{cid}")
    List<Route> getRouteListByCid(Integer cid);

    @Select("select s.* from seller  s where  s.sid = #{sid}")
    Seller getSeller(Integer sid);

    @Update("update route set count = count + 1 where rid = #{rid}")
    void updateFavorite(Long rid);

    @Select("select * from route")
    List<Route> queryAll();


    @Select("select s.* from route_img  r where  r.rid = #{rid}")
    List<RouteImg> getRouteImgsByRid(Integer rid);
}
