package util.other;
import java.io.IOException;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware;

import freemarker.cache.WebappTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
/**
 * FreeMarker工具类
 *
 */
public class FreeMarker implements InitializingBean , ServletContextAware{
	private static FreeMarker freemarker =new FreeMarker();
	private Configuration cfg ;
	private ServletContext context;
	/**
	 * 模版存放路径
	 */
	private static final String TEMPLATE_BASE_PATH = "/WEB-INF/template";
	/**
	 * 编码格式
	 */
	private static final String TEMPLATE_ENCODING ="utf-8";
	private FreeMarker(){
	}
	public static void init(ServletContext context){
		freemarker = new FreeMarker();//初始化生成FreeMarker模版
		freemarker.context =context;
	}
	/**
	 * 设置模版的配置信息
	 */
	public Configuration getCfg(){
		if(cfg == null){
			//实例化配置信息
			cfg = new Configuration();
			cfg.setTemplateLoader(new WebappTemplateLoader(context,TEMPLATE_BASE_PATH));
			cfg.setEncoding(Locale.CHINESE, TEMPLATE_ENCODING);
		}
		return cfg;
	}
	/**
	 * 获取模版内容
	 * @param name 模版路径
	 * @param map 传送的值
	 * @param writer 输出到
	 * @return 是否生成功
	 */
	public static boolean process (String name,Map<String, Object> map,Writer writer){
		try {
			freemarker.getCfg();
			//配置生成静态页模版的模版路径
			freemarker.template.Template template = freemarker.cfg.getTemplate(name);
			//把一个输出流和路径作为参数，模版自动生成静态文件
			template.process(map,writer);
			return true;
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	public void afterPropertiesSet() throws Exception {
	}
	public void setServletContext(ServletContext arg0) {
		FreeMarker.init(arg0);
	}
	public ServletContext getServletContext(){
		return this.context;
	}
	public static ServletContext servletContext(){
		return freemarker.getServletContext();
	}
}
