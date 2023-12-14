package com.sp_czk.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sp_czk.mapper.VisMapper;
import com.sp_czk.pojo.Emp;
import com.sp_czk.pojo.PageBean;
import com.sp_czk.pojo.Visitors;
import com.sp_czk.service.VisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VisServiceImpl implements VisService {
	@Autowired
	private VisMapper vm;
	@Override
	public PageBean page(Integer page, Integer pageSize, String name, Short status, LocalDate begin, LocalDate end) {
		/*
		传统分页查询方法
		//获取记录总数
		Long count = em.count();
		//获取分页查询列表
		Integer start = (page-1)*pageSize;
		List<Emp> list = em.page(start,pageSize);
		//封装PageBean对象
		PageBean pb = new PageBean(count,list);
		return pb;
		 */
		//使用PageHelper插件
		//设置分页参数
		PageHelper.startPage(page,pageSize);
		//执行查询
		List<Visitors> list = vm.list(name,status,begin,end);
		Page<Visitors> p = (Page<Visitors>) list;
		//封装结果
		PageBean pb = new PageBean(p.getTotal(),p.getResult());
		return pb;
	}

	@Override
	public void delete(List<Integer> ids) {
		vm.delete(ids);
	}

	@Override
	public void save(Emp emp) {
		vm.insert(emp);
	}

	@Override
	public Visitors getByUid(Integer uid) {
		return vm.getByUid(uid);
	}

	@Override
	public void update(Visitors vis) {
		vm.update(vis);
	}
}
