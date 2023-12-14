package com.sp_czk.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 员工实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Emp {
	private String name; //姓名
	private Short gender; //性别 , 1 男, 2 女
	private int age;//年龄
	private Short identity; //身份 , 1 主管理员 , 2 普通管理员, 3 研究人员 , 4 访客
	private Integer id; //个人显式编号
	private String pnumber; //手机号
	private String photo; //图像url
	private String password; //登录密码
}

