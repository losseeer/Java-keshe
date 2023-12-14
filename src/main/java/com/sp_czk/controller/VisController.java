package com.sp_czk.controller;
import com.sp_czk.pojo.*;
import com.sp_czk.service.VisService;
import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/list/viss")
public class VisController {

	@Resource
	private VisService vs;

	@GetMapping
	public Result page(@RequestParam(defaultValue = "1") Integer page,
	                   @RequestParam(defaultValue = "10") Integer pageSize, String name,
	                   @DateTimeFormat(pattern = "yyyy-MM-dd")
	                       @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	                       LocalDate begin,
	                   @DateTimeFormat(pattern = "yyyy-MM-dd")
	                       @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	                       LocalDate end) {
		Short status = 1;
		log.info("分页条件查询,参数：{},{},{},{},{},{}", page, pageSize, name, status, begin, end);
		PageBean pb = vs.page(page, pageSize, name, status, begin, end);
		return Result.success(pb);
	}
//删除功能暂不开发, 新增功能将在用户APP端开发
/*	@DeleteMapping("/{ids}")
	public Result delete(@PathVariable List<Integer> ids) {
		log.info("批量删除：ids:{}", ids);
		vs.delete(ids);
		return Result.success();
	}

	@PostMapping
	public Result save(@RequestBody Emp emp) {
		log.info("新增员工, emp: {}",emp);
		vs.save(emp);
		return Result.success();
	}
	*/
	@GetMapping("/{uid}")
	public Result getByUid(@PathVariable Integer uid) {
		log.info("根据uid查询：uid:{}", uid);
		Visitors vis = vs.getByUid(uid);
		return Result.success(vis);
	}
	@PutMapping
	public Result update(@RequestBody Visitors vis) {
		log.info("修改, vis: {}",vis);
		vs.update(vis);
		return Result.success();
	}
}
