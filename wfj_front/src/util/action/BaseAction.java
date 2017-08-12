package util.action;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.web.context.ServletContextAware;

import util.pojo.PageHelper;

import com.opensymphony.xwork2.ActionSupport;
/**
 * Action类 - 基础Action
 * 
 * */
public class BaseAction extends ActionSupport implements ServletRequestAware,ServletResponseAware,ServletContextAware{
	private static final long serialVersionUID = 179714478054136093L;
	protected PageHelper pageHelper;
	protected HttpServletRequest request;
    protected HttpSession session ;
    protected HttpServletResponse response ;
    protected Integer pageSize=10;
    protected Integer currentPage=1;
	protected static Map<Object,Object> fileUrlConfig = new HashMap<Object,Object>();
	protected static Map<Object,Object> shopConfig = new HashMap<Object,Object>();
	protected ServletContext servletContext;//servlet 上下文
	static{
		String filePath = "systemConfig.properties";
		String shopConfigFilePath = "shopConfig.properties";
		Properties ps = new Properties();
		Properties shopConfigPS = new Properties();
		try {
			InputStream	in = BaseAction.class.getClassLoader().getResourceAsStream(filePath);
			InputStream	shopConfigIS = BaseAction.class.getClassLoader().getResourceAsStream(shopConfigFilePath);
			ps.load(in);
			shopConfigPS.load(shopConfigIS);
		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		fileUrlConfig = ps; 
		shopConfig = shopConfigPS;
    }
	public BaseAction() {
		pageHelper=new PageHelper();
	}
	public PageHelper getPageHelper() {
		return pageHelper;
	}
	public void setPageHelper(PageHelper pageHelper) {
		this.pageHelper = pageHelper;
	}
	//request session 的 getter setter方法
	public void setServletRequest(HttpServletRequest req) {
		servletContext.setAttribute("fileUrlConfig", fileUrlConfig);
		this.request=req;
		this.session = this.request.getSession();
//		this.session.setAttribute("fileUrlConfig", fileUrlConfig);
	}
	public void setServletResponse(HttpServletResponse res) {
		this.response=res;
	}
	public HttpServletRequest getRequest() {
		return request;
	}
	public HttpServletResponse getResponse() {
		return response;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public ServletContext getServletContext() {
		return servletContext;
	}
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	public static Map<Object, Object> getFileUrlConfig() {
		return fileUrlConfig;
	}
}
