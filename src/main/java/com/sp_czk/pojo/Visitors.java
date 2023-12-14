package com.sp_czk.pojo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
/**
 * 访客实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visitors {
	private String name; //姓名
	private Short gender; //性别 , 1 男, 2 女
	private int age;//年龄
	private Short status; // 审批/访问状态 , 1 待审批，2 审批通过，3 审批拒绝 ， 4已来访
	private Short identity; //身份 , 1 主管理员 , 2  普通管理员, 3 研究人员 , 4 访客
	private Integer id; //个人（显式）编号，未审批时为9999
	private String pnumber; //手机号
	private String approver; //审批人
	private String memo;//备忘录
	private Integer uid;//隐式编号唯一，用于按id查找，无实际意义
	@DateTimeFormat(pattern = "yyyy-MM-dd") @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
	private Date vdate; //访问时间（年月日）
}

