package util.init;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware;

import shop.homeIndex.pojo.HomeKeyBook;
import shop.homeIndex.service.IHomeKeyBookService;
/**
 * 项目初始化加载首页数据字典
 */
public class InitializingHomeKeyBook implements InitializingBean , ServletContextAware {
	//servlet 上下文
	private ServletContext servletContext;
	//keyBookService
	private IHomeKeyBookService homeKeyBookService;
	/**
	 * 项目初始化加载首页数据字典
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public void afterPropertiesSet() throws Exception {
		if (servletContext != null) {
			Map<String,List<HomeKeyBook>> keybook = new HashMap<String,List<HomeKeyBook>>();
			List<String> typeNameList = homeKeyBookService.distinctType("type", "");//查找类型名称
			if(typeNameList!=null&&typeNameList.size()>0){
				for(String typeName : typeNameList){
					//根据类型名称查出对象集合
					List<HomeKeyBook> keyBookList = homeKeyBookService.findObjects(null," where o.type = '"+typeName+"' order by o.value asc");
					keybook.put(typeName, keyBookList);
				}
			}
			servletContext.setAttribute("homekeybook", keybook);
		}
	}
	public void setHomeKeyBookService(IHomeKeyBookService homeKeyBookService) {
		this.homeKeyBookService = homeKeyBookService;
	}
	public ServletContext getServletContext() {
		return servletContext;
	}
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
}
