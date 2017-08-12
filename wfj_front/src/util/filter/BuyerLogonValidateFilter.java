package util.filter;
import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import shop.customer.pojo.Customer;
import util.other.Escape;
import util.other.WebUtil;
/**
 * 判断顾客是否登录过滤器
 * @author CYS
 *
 */
public class BuyerLogonValidateFilter implements Filter {
	private static final Log log = LogFactory.getLog(BuyerLogonValidateFilter.class);
	public void destroy() {
	}
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
		Customer customer = WebUtil.getCustomer(request);
		String header = request.getHeader("X-Requested-With"); 
		String is_ajax = request.getParameter("is_ajax");
		if (header != null && "XMLHttpRequest".equals(header))  {
			if(customer == null){
				String url = WebUtil.getRequestURIWithParam(request);//得到当前请求路径
				url = Escape.escape(url);
				//response.sendRedirect(request.getContextPath()+"/loginCustomer/gotoLoginPage.action?directUrl="+ directUrl);
				StringBuilder sbr = new StringBuilder();
				sbr.append("<script type='text/javascript'>")
					.append("window.open('"+request.getContextPath()+"/loginCustomer/gotoLoginPage.action?directUrl="+url+"','_top')")
					.append("</script>");
				//将跳转页在整个窗口中显示（如有iframe的可以跳出）
				response.setContentType("text/html;charset=utf-8");
				response.setHeader("Cache-Control", "no-cache, must-revalidate");
				java.io.PrintWriter out = response.getWriter();
				out.write(sbr.toString());  
				out.flush();  
				out.close(); 
			}else{
				chain.doFilter(req, res);
			}	 
		}else if ("2".equals(is_ajax)){
			if(customer == null){
				String url = WebUtil.getRequestURIWithParam(request);//得到当前请求路径
				url = Escape.escape(url);
				//response.sendRedirect(request.getContextPath()+"/loginCustomer/gotoLoginPage.action?directUrl="+ directUrl);
				StringBuilder sbr = new StringBuilder();
				sbr.append("<script type='text/javascript'>")
					.append("window.open('"+request.getContextPath()+"/loginCustomer/gotoLoginPage.action?directUrl="+url+"','_top')")
					.append("</script>");
				//将跳转页在整个窗口中显示（如有iframe的可以跳出）
				response.setContentType("text/html;charset=utf-8");
				response.setHeader("Cache-Control", "no-cache, must-revalidate");
				java.io.PrintWriter out = response.getWriter();
				out.write(sbr.toString());  
				out.flush();  
				out.close(); 
			}else{
				chain.doFilter(req, res);
			}
		}else{
			if(customer == null){
				String url = WebUtil.getRequestURIWithParam(request);//得到当前请求路径
				url=URLDecoder.decode(url,"UTF-8");
				url = Escape.escape(url);
				url=url.replaceAll("%", "_");
				//response.sendRedirect(request.getContextPath()+"/loginCustomer/gotoLoginPage.action?directUrl="+ directUrl);
				StringBuilder sbr = new StringBuilder();
				sbr.append("<script type='text/javascript'>")
					.append("window.open('"+request.getContextPath()+"/loginCustomer/gotoLoginPage.action?type=1&directUrl="+url+"','_top')")
					.append("</script>");
				//将跳转页在整个窗口中显示（如有iframe的可以跳出）
				response.setContentType("text/html;charset=utf-8");
				response.setHeader("Cache-Control", "no-cache, must-revalidate");
				java.io.PrintWriter out = response.getWriter();
				out.write(sbr.toString());  
				out.flush();  
				out.close(); 
			}else{
				chain.doFilter(req, res);
			}
		}
	}
	public void init(FilterConfig arg0) throws ServletException {
		log.info("过滤器");
	}
}
