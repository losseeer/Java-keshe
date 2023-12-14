package com.sp_czk.controller;
import com.sp_czk.pojo.*;
import com.sp_czk.service.VisedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/list/vissed")
public class VisedController {

	@Resource
	private VisedService vds;

	@GetMapping
	public Result page(@RequestParam(defaultValue = "1") Integer page,
	                   @RequestParam(defaultValue = "10") Integer pageSize,
	                   String name, Integer id, Short status, String approver,
	                   @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
	                   @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
		log.info("分页条件查询,参数：{},{},{},{},{},{},{},{}", page, pageSize, name, id, status, approver, begin, end);
		PageBean pb = vds.page(page, pageSize, name, id, status, approver, begin, end);
		return Result.success(pb);
	}
//删除功能暂不开发
	/*
	@DeleteMapping("/{ids}")
	public Result delete(@PathVariable List<Integer> ids) {
		log.info("批量删除：ids:{}", ids);
		vds.delete(ids);
		return Result.success();
	}
*/
	@GetMapping("/{uid}")
	public Result getByUid(@PathVariable Integer uid) {
		log.info("根据uid查询：uid:{}", uid);
		Visitors vis = vds.getByUid(uid);
		return Result.success(vis);
	}
	@PutMapping
	public Result update(@RequestBody Visitors vis) {
		log.info("修改, vis: {}",vis);
		vds.update(vis);
		return Result.success();
	}
}
