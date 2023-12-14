package com.sp_czk.service;

import com.sp_czk.pojo.Emp;
import com.sp_czk.pojo.PageBean;
import com.sp_czk.pojo.Visitors;

import java.time.LocalDate;
import java.util.List;

/**
 * 员工管理
 */
public interface VisService {
	PageBean page(Integer page, Integer pageSize,String name, Short status, LocalDate begin, LocalDate end);

	void delete(List<Integer> ids);
	//新增员工
	void save(Emp emp);

	Visitors getByUid(Integer uid);

	void update(Visitors vis);
}
