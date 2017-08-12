package util.other;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.dispatcher.FilterDispatcher;
@SuppressWarnings("deprecation")
public class SessionValidFilter extends FilterDispatcher implements Filter {
	protected FilterConfig config;
	private String loginpage;
	private String adminLoginPage;
	public void destroy() {
	   //this.loginpage=null;
	}
	public void doFilter(ServletRequest request, ServletResponse response,
	    FilterChain chain) throws IOException, ServletException {
	   HttpServletRequest req = (HttpServletRequest) request;   
	     HttpServletResponse res = (HttpServletResponse) response;
	     Object para=req.getSession().getAttribute("student");  
	     String path=req.getServletPath();   
	    //||!path.equals("/common/admin/adminLogin.action"
	     if(path.contains("admin")){
	    	    if (req.getSession().getAttribute("manager")== null&&!path.equals("/common/admin/adminLogin.action")) {
	    		   	//  chain.doFilter(req, res);
	    		         res.sendRedirect(adminLoginPage);
	    		     }
	    		     else{
	    		   	  chain.doFilter(req, res);
	    		     }
	     }else{
	     if (para == null&&!path.equals("/study/userLogin.action")) {
	   	//  chain.doFilter(req, res);
	         res.sendRedirect(loginpage);
	     }
	     else{
	   	  chain.doFilter(req, res);
	     }
	     }
	    // else if(para.equals("doLogin")){                               //如果请求为登录则继续
		 //     chain.doFilter(req, res);
		 //   }else if(req.getSession().getAttribute("curUser")!=null){    //如果请求不为登录则判断当前用户是否为空
		 //   chain.doFilter(req, res);
		  // }
	}
	public void init(FilterConfig filterConfig) throws ServletException {
	   this.config = filterConfig;   
	   loginpage ="/cleverspace"+ config.getInitParameter("loginPage"); 
	   adminLoginPage="/cleverspace"+ config.getInitParameter("adminLoginPage"); 
	     if (loginpage == null) {   
	       throw new ServletException("---------------没有找到登陆页，请检查配置文件！------------------");   
	     }
	}
}
