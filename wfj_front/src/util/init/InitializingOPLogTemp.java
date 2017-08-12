package util.init;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware;

import util.action.BaseAction;
/**
 * 项目初始化加载操作日志
 */
public class InitializingOPLogTemp implements InitializingBean , ServletContextAware {
	//servlet 上下文
	private ServletContext servletContext;
	/**
	 * 项目初始化加载操作日志
	 * 
	 * */
	public void afterPropertiesSet() throws Exception {
		if (servletContext != null) {
			String oplogPath = "oplog.properties";
			Map opLogConfig = new HashMap();
			InputStream	oplogIn = BaseAction.class.getClassLoader().getResourceAsStream(oplogPath);
			Properties  ps = new Properties ();
			ps.load(oplogIn);
			opLogConfig=ps;
			servletContext.setAttribute("opLogConfig", opLogConfig);
		}
	}
	public ServletContext getServletContext() {
		return servletContext;
	}
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
}
