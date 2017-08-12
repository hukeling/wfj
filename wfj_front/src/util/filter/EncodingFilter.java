package util.filter;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
/**
 * 工具类 - 过滤器实现filter接口
 * 
 * */
public class EncodingFilter implements Filter {
	/**全局变量 方便doFilter方法和destroy方法调用**/
     String encoding=null;
     /**init()初始化方法，类似于servlet ，但这个在tomcat启动的时候就加载**/
	public void init(FilterConfig filterConfig) throws ServletException {
		this.encoding = filterConfig.getInitParameter("encoding");
		//System.out.println("编码过滤器已经运行 encoding ="+this.encoding);
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//设置全局的请求编码
		request.setCharacterEncoding(this.encoding);
		//设置全局的响应编码
		response.setContentType("text/html;charset="+this.encoding);
		//过滤客户端提交表单中特殊字符：单引号、首尾空格
		HttpServletRequest rq = (HttpServletRequest) request;
		Iterator its = rq.getParameterMap().values().iterator();
		while(its.hasNext()){
			String [] params = (String[]) its.next();
			for (int i = 0; i < params.length; i++) {
				params[i] = params[i].trim().replaceAll("'", "");
			}
		}
		//放过 到下一个过滤器，如果没有过滤器到servlet或jsp或html或其它
		chain.doFilter(request, response);
	}
	
	public void destroy() {
		this.encoding=null;
	}

}
