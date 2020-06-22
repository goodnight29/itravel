package com.qhit.itravel.entity;

import java.io.Serializable;

/**
 * (RouteImg)实体类
 *
 * @author makejava
 * @since 2020-04-15 16:05:47
 */
public class RouteImg implements Serializable {
    private static final long serialVersionUID = -47891896919315162L;
    
    private Integer rgid;
    
    private Integer rid;
    
    private String bigpic;
    
    private String smallpic;


    public Integer getRgid() {
        return rgid;
    }

    public void setRgid(Integer rgid) {
        this.rgid = rgid;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getBigpic() {
        return bigpic;
    }

    public void setBigpic(String bigpic) {
        this.bigpic = bigpic;
    }

    public String getSmallpic() {
        return smallpic;
    }

    public void setSmallpic(String smallpic) {
        this.smallpic = smallpic;
    }

}