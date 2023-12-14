package com.sp_czk.controller;

import com.sp_czk.pojo.*;
import com.sp_czk.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.time.LocalDate;
import java.util.List;


/**
 * 上传控制类
 */
@Slf4j
@RestController
public class UploadController {
	@PostMapping("/list/emps/upload")
	public Result upload(@RequestPart("photo") MultipartFile photo,
	                     @RequestPart("name") String name) {
		log.info("图片，姓名上传：{},{}", photo,name);
		Runtime runtime = Runtime.getRuntime();
		String inStr, errStr,newname = "";
		newname = name.replaceAll(" ","_");
		try {
			photo.transferTo(new File("E:/upgit/photos/"+ newname + ".jpg"));
			String cmd = "upgit " + "E:/upgit/photos/" + newname + ".jpg" + " -t " + " emp_photos/";
			Process process = runtime.exec(cmd);
			inStr = consumeInputStream(process.getInputStream()); //获取命令行输出(即图片url)
			// 标准错误流（必须写在 waitFor 之前）
			errStr = consumeInputStream(process.getErrorStream()); //若有错误信息则输出
			int proc = process.waitFor();
			if (proc == 0) {
				log.info("执行成功:{}",inStr);
			} else {
				log.info("执行失败:{}", errStr);
			}
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException(e);
		}
		return Result.success(inStr);
	}
	public static String consumeInputStream(InputStream is) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"GBK"));
		String s;
		StringBuilder sb = new StringBuilder();
		while ((s = br.readLine()) != null) {
			System.out.println(s);
			sb.append(s);
		}
		return sb.toString();
	}
}
