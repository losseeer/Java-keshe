package com.sp_czk.pojo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 出入记录实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntryRecords {
	private String name; //姓名
	private Short gender; //性别 , 0 男, 1 女
	private Integer age;//年龄
	private Short identity; //身份 , 0 主管理员 , 1  普通管理员, 2 研究人员 , 3 访客
	private Integer id; //进门编号(月日+个人编号构成)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date etime; //年月日时分秒
	private String image; //进门图像url
}

