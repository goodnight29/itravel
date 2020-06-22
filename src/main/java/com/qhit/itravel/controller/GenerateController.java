package com.qhit.itravel.controller;

import java.util.List;

import com.qhit.itravel.dto.BeanField;
import com.qhit.itravel.dto.GenerateDetail;
import com.qhit.itravel.dto.GenerateInput;
import com.qhit.itravel.service.GenerateService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * 代码生成接口
 * 
 * @author 小威老师 xiaoweijiagou@163.com
 *
 */

@RestController
@RequestMapping("/generate")
public class GenerateController {

	@Autowired
	private GenerateService generateService;


	@GetMapping(params = { "tableName" })
	@RequiresPermissions("generate:edit")
	public GenerateDetail generateByTableName(String tableName) {
		GenerateDetail detail = new GenerateDetail();
		detail.setBeanName(generateService.upperFirstChar(tableName));
		List<BeanField> fields = generateService.listBeanField(tableName);
		detail.setFields(fields);

		return detail;
	}


	@PostMapping
	@RequiresPermissions("generate:edit")
	public void save(@RequestBody GenerateInput input) {
		generateService.saveCode(input);
	}

}
