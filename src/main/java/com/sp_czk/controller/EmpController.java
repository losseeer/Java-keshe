package com.sp_czk.controller;

import com.sp_czk.pojo.*;
import com.sp_czk.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;


/**
 * 员工管理Controller
 */
@Slf4j
@RestController
@RequestMapping("/list/emps")
public class EmpController {
	@Resource
	private EmpService es;

//	@GetMapping
//	public Result page(@RequestParam(defaultValue = "1") Integer page,
//	                   @RequestParam(defaultValue = "10") Integer pageSize,
//	                   String name, Short gender,
//	                   @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
//	                   @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
//		log.info("分页条件查询,参数：{},{},{},{},{},{}", page, pageSize, name, gender, begin, end);
//		PageBean pb = es.page(page, pageSize, name, gender, begin, end);
//		return Result.success(pb);
//	}
	@GetMapping
	public Result page(@RequestParam(defaultValue = "1") Integer page,
	                   @RequestParam(defaultValue = "10") Integer pageSize,
	                   String name, Integer id, Short identity
	                   ) {
		log.info("分页条件查询,参数：{},{},{},{},{}", page, pageSize, name, id, identity);
		PageBean pb = es.page(page, pageSize, name, id, identity);
		return Result.success(pb);
	}

	@DeleteMapping("/{ids}")
	public Result delete(@PathVariable List<Integer> ids) {
		log.info("批量删除：ids:{}", ids);
		es.delete(ids);
		return Result.success();
	}

	@PostMapping
	public Result save(@RequestBody Emp emp) {
		log.info("新增员工, emp: {}",emp);
		es.save(emp);
		return Result.success();
	}
	@GetMapping("/{id}")
	public Result getById(@PathVariable Integer id) {
		log.info("根据id查询：id:{}", id);
		Emp emp = es.getById(id);
		return Result.success(emp);
	}
	@PutMapping
	public Result update(@RequestBody Emp emp) {
		log.info("修改, emp: {}",emp);
		es.update(emp);
		return Result.success();
	}
}

