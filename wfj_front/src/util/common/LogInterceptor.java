package util.common;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import shop.customer.service.ICustomerService;
import util.pojo.Log;
import util.pojo.LogConfig;
import util.service.LogConfigService;
import util.service.LogService;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
/**
 * 拦截器 - 管理日志
 */
public class LogInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 276741467699160227L;
	public static final String[] excludeActionClassNames = new String[] {"net.shopxx.action.admin.InstallAction"};// 需要排除的Action类名称
	public static final String[] excludeActionMethodNames = new String[] {};// 需要排除的Action方法名称
	private util.service.LogService logService;
	@SuppressWarnings("unused")
	private ICustomerService customerService;
	private LogConfigService logConfigService;
	private String logInfo;
	public String intercept(ActionInvocation invocation) throws Exception {
		invocation.invoke();
		String actionClassName = invocation.getAction().getClass().getName();
		String actionMethodName = invocation.getProxy().getMethod();
		if (ArrayUtils.contains(excludeActionClassNames, actionClassName)) {
			return null;
		}
		if (ArrayUtils.contains(excludeActionMethodNames, actionMethodName)) {
			return null;
		}
		List<LogConfig> allLogConfig = logConfigService.findObjects(null);
		if (allLogConfig != null) {
			for (LogConfig logConfig : allLogConfig) {
				if (StringUtils.equals(logConfig.getActionClassName(), actionClassName)
						&& StringUtils.equals(logConfig.getActionMethodName(), actionMethodName)) {
//					String operator = customerService.getLoginAdmin().getUsername();
//					if(operator == null) {
//						operator = "未知用户";
//					}
					String ip = ServletActionContext.getRequest().getRemoteAddr();
					String operationName = logConfig.getOperationName();
					Log log = new Log();
					log.setOperationName(operationName);
					log.setActionClassName(actionClassName);
					log.setActionMethodName(actionMethodName);
//					log.setOperator(operator);
					log.setIp(ip);
					log.setInfo(logInfo);
					logService.saveOrUpdateObject(log);
					break;
				}
			}
		}
		return null;
	}
	public void setLogService(LogService logService) {
		this.logService = logService;
	}
	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}
	public void setLogConfigService(LogConfigService logConfigService) {
		this.logConfigService = logConfigService;
	}
	public String getLogInfo() {
		return logInfo;
	}
	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}
}