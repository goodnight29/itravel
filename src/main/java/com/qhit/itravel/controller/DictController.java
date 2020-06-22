package com.qhit.itravel.controller;

import java.util.List;

import com.qhit.itravel.dao.DictDao;
import com.qhit.itravel.entity.Dict;
import com.qhit.itravel.utils.page.PageTableHandler;
import com.qhit.itravel.utils.page.PageTableRequest;
import com.qhit.itravel.utils.page.PageTableResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/dicts")
public class DictController {

	@Autowired
	private DictDao dictDao;

	@RequiresPermissions("dict:add")
	@PostMapping
	public Dict save(@RequestBody Dict dict) {
		Dict d = dictDao.getByTypeAndK(dict.getType(), dict.getK());
		if (d != null) {
			throw new IllegalArgumentException("类型和key已存在");
		}
		dictDao.save(dict);

		return dict;
	}

	@GetMapping("/{id}")
	public Dict get(@PathVariable Long id) {
		return dictDao.getById(id);
	}

	@RequiresPermissions("dict:add")
	@PutMapping
	public Dict update(@RequestBody Dict dict) {
		dictDao.update(dict);

		return dict;
	}

	@RequiresPermissions("dict:query")
	@GetMapping(params = { "start", "length" })
	public PageTableResponse list(PageTableRequest request) {
		return new PageTableHandler(new PageTableHandler.CountHandler() {

			@Override
			public int count(PageTableRequest request) {
				return dictDao.count(request.getParams());
			}
		}, new PageTableHandler.ListHandler() {

			@Override
			public List<Dict> list(PageTableRequest request) {
				return dictDao.list(request.getParams(), request.getOffset(), request.getLimit());
			}
		}).handle(request);
	}

	@RequiresPermissions("dict:del")
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		dictDao.delete(id);
	}

	@GetMapping(params = "type")
	public List<Dict> listByType(String type) {
		return dictDao.listByType(type);
	}
}
