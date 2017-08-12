package util.interceptor;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
/**
 * 功能权限拦截器
 * 
 * @author 刘青松
 * 
 */
public class FunctionPurviewInterceptor implements Interceptor {
	private static final long serialVersionUID = 7209614024628103192L;
	public void init() {
	}
	@SuppressWarnings("rawtypes")
	public String intercept(ActionInvocation ai) throws Exception {
		Map map = ActionContext.getContext().getParameters();
		if (map != null) {
			String[] purviewId=(String[])map.get("purviewId");
			if(purviewId!=null){
				ActionContext.getContext().getSession().put("purviewId", purviewId[0]);
			}
		}
		return ai.invoke();// 调用action中的方法
	}
	public void destroy() {
	}
}
