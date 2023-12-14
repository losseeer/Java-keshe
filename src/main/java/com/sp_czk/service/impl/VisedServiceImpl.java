package com.sp_czk.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sp_czk.mapper.VisedMapper;
import com.sp_czk.pojo.PageBean;
import com.sp_czk.pojo.Visitors;
import com.sp_czk.service.VisedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VisedServiceImpl implements VisedService {
	@Autowired
	private VisedMapper vdm;
	@Override
	public PageBean page(Integer page, Integer pageSize, String name, Integer id, Short status, String approver, LocalDate begin, LocalDate end) {
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
		List<Visitors> list = vdm.list(name,id,status,approver,begin,end);
		Page<Visitors> p = (Page<Visitors>) list;
		//封装结果
		PageBean pb = new PageBean(p.getTotal(),p.getResult());
		return pb;
	}

	@Override
	public void delete(List<Integer> ids) {
		vdm.delete(ids);
	}

	@Override
	public Visitors getByUid(Integer uid) {
		return vdm.getByUid(uid);
	}

	@Override
	public void update(Visitors vis) {
		vdm.update(vis);
	}
}
