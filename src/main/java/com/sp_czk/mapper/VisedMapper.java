package com.sp_czk.mapper;

import com.sp_czk.pojo.Visitors;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

/**
 * 员工管理
 */
@Mapper
public interface VisedMapper {
	/*
	传统分页查询实现
	//实现查询记录总数
	@Select("select count(*) from emp")
	public Long count();
	//分页查询实现
	@Select("select * from emp limit #{start}, #{pageSize}")
	public List<Emp> page(Integer start, Integer pageSize);
	 */
	//使用PageHelper插件查询实现
	/*
	普通分页查询（只有分页条件）
	@Select("select * from emp")
	public List<Emp> list();
	 */
	//通过xml文件(动态SQL)进行条件分页查询
	public List<Visitors> list(String name, Integer id, Short status, String approver, LocalDate begin, LocalDate end);

	void delete(List<Integer> ids);
	@Select("select status from project_czk.visitors where uid = #{uid}")
	Visitors getByUid(Integer uid);

	void update(Visitors vis);
}
