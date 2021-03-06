package kr.devbox.adadmin.config;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ApiFilter implements Filter{
	
	
	private void setHeader(HttpServletResponse res) {
		 res.setContentType("application/json; charset=utf-8");
//		 res.addHeader("Access-Control-Allow-Headers", "X-Auth-Token");
		 res.addHeader("Access-Control-Allow-Headers", "x-auth-token, content-type, AdminAuthorization, Authorization");
		 res.addHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS,DELETE");
		 res.addHeader("Access-Control-Allow-Origin", "*");
		 res.addHeader("Access-Control-Max-Age", "3600");

	}
	
	
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		
		setHeader((HttpServletResponse)res);
		chain.doFilter(req, res);
	}


}
