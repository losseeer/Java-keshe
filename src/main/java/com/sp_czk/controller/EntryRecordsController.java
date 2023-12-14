package com.sp_czk.controller;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sp_czk.pojo.*;
import com.sp_czk.service.EntryRecordsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/access")
public class EntryRecordsController {
	@Resource
	private EntryRecordsService ers;

	@GetMapping("/record")
	public Result page(@RequestParam(defaultValue = "1") Integer page,
	                   @RequestParam(defaultValue = "10") Integer pageSize,
	                   String name, Short identity, Integer id,
	                   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	                       @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	                       LocalDateTime begin,
	                   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	                       @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	                       LocalDateTime end) {
		log.info("分页条件查询,参数：{},{},{},{},{},{},{}", page, pageSize, name, identity, id, begin, end);
		PageBean pb = ers.page(page, pageSize, name, identity, id, begin, end);
		return Result.success(pb);
	}
//	@GetMapping("/stastics")
//	public Result Stastics(){
//		log.info("统计出入记录");
//		return Result.success(ers.stastics());
//	}
	// 删除、修改功能功能暂不开发, 增加记录功能将在嵌入式端开发
	/*
	@DeleteMapping("/{ids}")
	public Result delete(@PathVariable List<Integer> ids) {
		log.info("批量删除：ids:{}", ids);
		ers.delete(ids);
		return Result.success();
	}

	@PostMapping
	public Result save(@RequestBody EntryRecords er) {
		log.info("新增出入记录, er: {}",er);
		ers.save(er);
		return Result.success();
	}
	@GetMapping("/{id}")
	public Result getById(@PathVariable Integer id) {
		log.info("根据id查询：id:{}", id);
		EntryRecords er = ers.getById(id);
		return Result.success(er);
	}
	@PutMapping
	public Result update(@RequestBody EntryRecords er) {
		log.info("修改, er: {}");
		ers.update(er);
		return Result.success();
	}
*/
}
