package com.qhit.itravel.entity;


import cn.afterturn.easypoi.excel.annotation.Excel;

public class Route extends BaseEntity<Long> {


    //必须要有一个空参数的构造方法
	@Excel(name = "编号",width = 20)
	private Integer rid;
	@Excel(name = "名称",width = 60)
	private String rname;
	@Excel(name = "价格",width = 40)
	private Double price;
	@Excel(name = "路线介绍",width = 120)
	private String routeIntroduce;
	private String rflag;
	@Excel(name = "路线创建日期",width = 20,exportFormat = "yyyy-MM-dd")
	private String rdate;
	private String isThemeTour;
	@Excel(name = "点赞数",width = 20)
	private Integer count;
	private Integer cid;
	private String rimage;
	private Integer sid;
	private String sourceId;

	public Integer getRid() {
		return rid;
	}
	public void setRid(Integer rid) {
		this.rid = rid;
	}
	public String getRname() {
		return rname;
	}
	public void setRname(String rname) {
		this.rname = rname;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getRouteIntroduce() {
		return routeIntroduce;
	}
	public void setRouteIntroduce(String routeIntroduce) {
		this.routeIntroduce = routeIntroduce;
	}
	public String getRflag() {
		return rflag;
	}
	public void setRflag(String rflag) {
		this.rflag = rflag;
	}
	public String getRdate() {
		return rdate;
	}
	public void setRdate(String rdate) {
		this.rdate = rdate;
	}
	public String getIsThemeTour() {
		return isThemeTour;
	}
	public void setIsThemeTour(String isThemeTour) {
		this.isThemeTour = isThemeTour;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public String getRimage() {
		return rimage;
	}
	public void setRimage(String rimage) {
		this.rimage = rimage;
	}
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

}
