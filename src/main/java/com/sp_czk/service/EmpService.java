package com.sp_czk.service;

import com.sp_czk.pojo.Emp;
import com.sp_czk.pojo.PageBean;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

/**
 * 员工管理
 */
public interface EmpService {
	PageBean page(Integer page, Integer pageSize,String name, Integer id, Short identity);

	void delete(List<Integer> ids);
	//新增员工
	void save(Emp emp);

	Emp getById(Integer id);

	void update(Emp emp);

	Emp login(Emp emp);
}
