package util.filter;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.other.WebUtil;
import basic.pojo.Users;
/**
 * 判断后台用户是否具有权限过滤器
 * @author CYS
 *
 */
public class PrivilegeFilter implements Filter {
	public void destroy() {
	}
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		Users users = WebUtil.getUsers(req);
		if(users==null){//如果员工未登录，重定向到员工登陆界面
			HttpServletResponse res = (HttpServletResponse) response;
			//res.sendRedirect(req.getContextPath()+"/backLogin/backLogin.jsp");
			StringBuilder sbr = new StringBuilder();
			sbr.append("<script type='text/javascript'>")
				.append("window.open ('"+req.getContextPath()+"/','_top')")
				.append("</script>");
			//将跳转页在整个窗口中显示（如有iframe的可以跳出）
			res.setContentType("text/html;charset=utf-8");
			res.setHeader("Cache-Control", "no-cache, must-revalidate");
			java.io.PrintWriter out = response.getWriter();
			out.write(sbr.toString());  //写到页面会自动执行，print只是写，不一定执行
			out.flush();  
			out.close(); 
		}else{
			chain.doFilter(request, response);
		}
	}
	public void init(FilterConfig arg0) throws ServletException {
	}
}
