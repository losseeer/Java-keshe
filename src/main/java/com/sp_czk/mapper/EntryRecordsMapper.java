package com.sp_czk.mapper;

import com.sp_czk.pojo.Emp;
import com.sp_czk.pojo.EntryRecords;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 员工管理
 */
@Mapper
public interface EntryRecordsMapper {
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
	public List<EntryRecords> list(String name, Short identity, Integer id, LocalDateTime begin, LocalDateTime end);

	void delete(List<Integer> ids);
	@Insert("insert into project_czk.entry_records(name, age, gender, identity, etime, id, image) " +
			"values (#{name}, #{age}, #{gender}, #{identity}, #{etime}, #{id}, #{image})")
	void insert(EntryRecords er);
	@Select("select * from project_czk.entry_records where id = #{id}")
	EntryRecords getById(Integer id);

	void update(EntryRecords er);
}
