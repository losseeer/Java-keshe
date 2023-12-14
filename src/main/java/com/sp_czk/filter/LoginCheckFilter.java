package com.sp_czk.filter;

import com.alibaba.fastjson.JSONObject;
import com.sp_czk.pojo.Result;
import com.sp_czk.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import netscape.javascript.JSObject;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) servletRequest;
		HttpServletResponse resp = (HttpServletResponse)servletResponse;
		//如果 url 含有login,直接放行
		String url = req.getRequestURL().toString();
		log.info("请求的url:{}", url);
		if(url.contains("login")){
			log.info("登录操作,放行");
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}

		//检查jwt, 不合法就返回error并拦截
		String jwt = req.getHeader("token");
		if(!StringUtils.hasLength(jwt)){
			log.info("token为空,未登录");
			Result error = Result.error("NOT_LOGIN");
			//手动转为 json 对象
			String not_login = JSONObject.toJSONString(error);
			resp.getWriter().write(not_login);
			return;
		}
		try{
			JwtUtils.parseJWT(jwt);
		}catch (Exception e){
			e.printStackTrace();
			log.info("token解析失败,未登录");
			Result error = Result.error("NOT_LOGIN");
			//手动转为 json 对象
			String not_login = JSONObject.toJSONString(error);
			resp.getWriter().write(not_login);
			return;
		}

		//jwt令牌合法, 放行
		log.info("放行");
		filterChain.doFilter(servletRequest, servletResponse);

	}
}
