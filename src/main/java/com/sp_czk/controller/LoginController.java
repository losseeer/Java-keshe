package com.sp_czk.controller;

import com.sp_czk.pojo.Emp;
import com.sp_czk.pojo.Result;
import com.sp_czk.service.EmpService;
import com.sp_czk.utils.JwtUtils;
import io.jsonwebtoken.JwtBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class LoginController {
	@Autowired
	private EmpService es;

	@PostMapping("/login")
	public Result login(@RequestBody Emp emp) {
		log.info("login: {}", emp);
		Emp e = es.login(emp);
		if(e != null){
			Map<String, Object> claims = new HashMap<>();
			claims.put("id", e.getId());
			claims.put("name", e.getName());
			claims.put("pnumber", e.getPnumber());

			String jwt = JwtUtils.generateJwt(claims);
			return Result.success(jwt);
		}
		return Result.error("用户不存在！");
	}
}
