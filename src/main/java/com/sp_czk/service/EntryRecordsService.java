package com.sp_czk.service;

import com.sp_czk.pojo.EntryRecords;
import com.sp_czk.pojo.PageBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 员工管理
 */
public interface EntryRecordsService {
	PageBean page(Integer page, Integer pageSize, String name, Short identity,
	              Integer id, LocalDateTime begin, LocalDateTime end);

	void delete(List<Integer> ids);
	//新增员工
	void save(EntryRecords er);

	EntryRecords getById(Integer id);

	void update(EntryRecords er);
}
