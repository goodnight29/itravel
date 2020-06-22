package com.qhit.itravel.utils.page;

import java.io.Serializable;
import java.util.Map;

/**
 * 分页查询参数
 *
 */
public class PageTableRequest implements Serializable {

	private static final long serialVersionUID = 7328071045193618467L;

   // limit 0,10;第一页的10条
   // limit (1-0)*10, 10
	private Integer offset;
	private Integer limit;
	private Map<String, Object> params;

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
}
