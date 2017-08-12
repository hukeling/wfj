package util.listener;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

import basic.pojo.OPLog;
import basic.pojo.Users;
import basic.service.IUsersService;
import basic.service.imp.OPLogService;
public class LogListener {
	private OPLogService opLogService;
	private IUsersService usersService;//后台管理员Service
	//定义前置通知
	@SuppressWarnings({ "unchecked" })
	public void doAccessCheck(JoinPoint jp) {
		  //获取action定义名称
		String name=jp.getSignature().getName();
		HttpServletRequest request = ServletActionContext.getRequest();
		//1、获取访问者IP
		String ip=request.getRemoteHost();
		if(!"setServletRequest".equals(name)&&!"setServletResponse".equals(name)){
			//2、获取action动作的描述
			Map opLogConfig=(Map) ServletActionContext.getServletContext().getAttribute("opLogConfig");
			String opDesc=(String) opLogConfig.get(name);
			if(opDesc!=null&&!"".equals(opDesc)){
				HttpSession session=request.getSession();
				//3、获取登录用户的真是姓名
				Users users=(Users)session.getAttribute("users");
				String userTrueName=null;
				if(users!=null){
					userTrueName=users.getTrueName();
				}
			    //4、获取操作参数信息
				StringBuilder sb = new StringBuilder();
				Iterator<Entry<String, String[]>> it = request.getParameterMap().entrySet().iterator();
				boolean isFirst = true;
				if(it!=null){
					while (it.hasNext()) {
						Entry<String, String[]> entry = it.next();
						if (isFirst) isFirst = false;
						else sb.append(",");
						sb.append(entry.getKey() + ":");
						Object[] allValue = (Object[]) entry.getValue();
						for (int i = 0; i < allValue.length; i++) {
							if (i != 0) sb.append(",");
							sb.append(allValue[i].toString());
						}
					}
					//处理登录日志 userName:admin,password:admin
					if("userLogin".equals(name)){
						String parameters=sb.toString();
						if(!"".equals(parameters)){
							String [] params=parameters.split(",");
							//	
							if(params!=null&&params.length>0){
									String [] paramValues0=params[0].split(":");
									String [] paramValues1=params[1].split(":");
									if(paramValues0!=null&&paramValues0.length>0&&paramValues1!=null&&paramValues1.length>0){
										users = (Users) usersService.getObjectByParams(" where o.userName='"+paramValues0[1]+"' and o.password='"+paramValues1[1]+"'");
										if(users!=null){
											userTrueName=users.getTrueName();
										}
									}
							}
						}
					}
				}
				String opParams=sb.toString();	
				//5、获取操作时间
				Date opDate=new Date();
				//6、创建日志对象
				OPLog oplog=new OPLog();
				oplog.setUserTrueName(userTrueName);
				oplog.setIp(ip);
				oplog.setOpDesc(opDesc);
				oplog.setOpParams(opParams);
				oplog.setOpDate(opDate);
				//7、保存操作日志
				opLogService.saveOrUpdateObject(oplog);
			}
		}
	}
	//环绕通知
	public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
		return pjp.proceed();
	}
	public void setOpLogService(OPLogService opLogService) {
		this.opLogService = opLogService;
	}
	public void setUsersService(IUsersService usersService) {
		this.usersService = usersService;
	}
}
