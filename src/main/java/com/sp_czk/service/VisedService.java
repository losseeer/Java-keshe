package com.sp_czk.service;

import com.sp_czk.pojo.Visitors;
import com.sp_czk.pojo.PageBean;

import java.time.LocalDate;
import java.util.List;

/**
 * 员工管理
 */
public interface VisedService {
	PageBean page(Integer page, Integer pageSize,String name, Integer id, Short status, String approver, LocalDate begin, LocalDate end);

	void delete(List<Integer> ids);
	//新增员工

	Visitors getByUid(Integer uid);

	void update(Visitors vis);
}
