package com.sp_czk.service.impl;

import com.sp_czk.mapper.EmpMapper;
import com.sp_czk.pojo.PageBean;
import com.sp_czk.service.EmpService;
import com.github.pagehelper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sp_czk.pojo.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmpServiceImpl implements EmpService {
	@Autowired
	private EmpMapper em;
	@Override
	public PageBean page(Integer page, Integer pageSize, String name, Integer id, Short identity) {
		/*
		传统分页查询方法
		//获取记录总数
		Long count = em.count();
		//获取分页查询列表
		Integer start = (page-1)*pageSize;
		List<Emp> list = em.page(start,pageSize);
		//封装PageBean对象
		PageBean pb = new PageBean(count,list);`
		return pb;
		 */
		//使用PageHelper插件
		//设置分页参数
		PageHelper.startPage(page,pageSize);
		//执行查询
		List<Emp> list = em.list(name,id, identity);
		Page<Emp> p = (Page<Emp>) list;
		//封装结果
		PageBean pb = new PageBean(p.getTotal(),p.getResult());
		return pb;
	}

	@Override
	public void delete(List<Integer> ids) {
		em.delete(ids);
	}

	@Override
	public void save(Emp emp) {
		em.insert(emp);
	}

	@Override
	public Emp getById(Integer id) {
		return em.getById(id);
	}

	@Override
	public void update(Emp emp) {
		em.update(emp);
	}

	@Override
	public Emp login(Emp emp) {
		return em.getByPnumberAndPassword(emp);
	}
}
