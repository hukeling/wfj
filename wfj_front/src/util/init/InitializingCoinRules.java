package util.init;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware;

import shop.common.pojo.CoinRules;
import shop.common.service.ICoinRulesService;
/**
 * 项目初始化加载虚拟金币赠送规则
 */
public class InitializingCoinRules implements InitializingBean , ServletContextAware {
	//servlet 上下文
	private ServletContext servletContext;
	//coinRulesService
	private ICoinRulesService coinRulesService;
	/**
	 * 项目初始化加载虚拟金币赠送规则
	 * 
	 * */
	public void afterPropertiesSet() throws Exception {
		if (servletContext != null) {
			Map<String,List<CoinRules>> map = new HashMap<String,List<CoinRules>>();
			List<String> typeNameList = coinRulesService.distinctType("type", "");//查找类型名称
			if(typeNameList!=null&&typeNameList.size()>0){
				for(String typeName : typeNameList){
					List<CoinRules> coinRulesList = coinRulesService.findObjects(" where o.type = '"+typeName+"' order by o.value asc ");//根据类型名称查出对象集合
					map.put(typeName, coinRulesList);
				}
			}
			servletContext.setAttribute("coinRules", map);
		}
   }
	public ServletContext getServletContext() {
		return servletContext;
	}
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	public void setCoinRulesService(ICoinRulesService coinRulesService) {
		this.coinRulesService = coinRulesService;
	}
	public ICoinRulesService getCoinRulesService() {
		return coinRulesService;
	}
}