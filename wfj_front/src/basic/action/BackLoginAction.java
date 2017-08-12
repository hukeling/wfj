package basic.action;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;
import util.action.BaseAction;
import util.action.SecurityCode;
import util.action.SecurityImage;
import basic.pojo.ActorPurview;
import basic.pojo.Purview;
import basic.pojo.Users;
import basic.pojo.UsersActor;
import basic.service.IActorPurviewService;
import basic.service.IPurviewService;
import basic.service.IUsersActorService;
import basic.service.IUsersService;
@SuppressWarnings("serial")
public class BackLoginAction extends BaseAction{
	private IUsersService usersService;//管理员Service
	private IPurviewService purviewService;//权限Service
	private IUsersActorService usersActorService;//管理员角色Service
	private IActorPurviewService actorPurviewService;//角色权限Service
	private Users users;//管理员
	private String falseMsg;
	@SuppressWarnings({ "rawtypes" })
	private Map<String,List> pruviewMap = new HashMap<String,List>();
	/**图片流**/
    private ByteArrayInputStream imageStream;
    /***************************end********************************************/
    public String gotoLoginPage(){
    	return SUCCESS;
    }
    public String left(){
    	return SUCCESS;
    }
    public String welcome(){
    	return SUCCESS;
    }
	/**获取默认 宽度和长度的验证码**/
	public String userFirstLogin(){
		  //如果开启Hard模式，可以不区分大小写
 //        String securityCode = SecurityCode.getSecurityCode(4,SecurityCodeLevel.Hard, false).toLowerCase();
        String securityCode = SecurityCode.getSecurityCode();
        imageStream = SecurityImage.getImageAsInputStream(securityCode);
        request.getSession().setAttribute("verificationCode", securityCode);
        request.getSession().setMaxInactiveInterval(180);//设置验证码的失效时间为3分钟
		return SUCCESS;
	}
	//验证用户是否存在和密码是否正确
	public void checkingUsers() throws IOException{
		String userName = request.getParameter("userName");//管理员名称
		String password = request.getParameter("password");//密码
		String verificationCode = request.getParameter("verificationCode");//前台传递的验证码
		String serverCode = (String) request.getSession().getAttribute("verificationCode");//服务器上的
		String isExsit="0";//0默认，1:用户名或者密码错误，2验证码错误,3:验证码失效
		if(null==serverCode){
			isExsit="3";
		}else if(serverCode.equals(verificationCode)){
			users = (Users)usersService.getObjectByParams(" where o.userName='"+userName+"' and o.password='"+password+"'");
			if(users==null){
				isExsit="1";
			}else if(users.getLockState()==1){
				isExsit="4";
			}else{
				request.getSession().setAttribute("users", users);
				//重置session失效时间，防止验证码3分钟失效后影响用户登录的session信息使用
				request.getSession().setMaxInactiveInterval(Integer.parseInt(String.valueOf(fileUrlConfig.get("session_user"))));
			}
		}else{
			isExsit="2";
		}
		JSONObject jo=new JSONObject();
		jo.accumulate("isExsit", isExsit);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw=response.getWriter();
		pw.print(jo.toString());
		pw.flush();
		pw.close();
	}
	//后台管理员登录查询管理员权限
	/**
	 * 将已经选中的模块权限下对应的功能权限按照指定的规则进行组装
	 * 组装规则为map<purviewId_functionValue,functionValue>
	 * @param purviewList
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void joinFunctions(List <ActorPurview> actorPurviewList) {
		Map functionsMap=(Map)session.getAttribute("functionsMap");
		if(functionsMap==null){
			functionsMap = new HashMap<String, String>();
		}
		//按照指定的规则进行功能权限的组装
		for (ActorPurview ap : actorPurviewList) {
			String functions=ap.getFunctions();
			if(functions!=null){
				String [] functionsArray=functions.split(",");
				if(functionsArray!=null&&functionsArray.length>0){
					for(String function:functionsArray){
						String [] functionInfos=function.split("_");
						String purviewId=functionInfos[0];
						String functionValue=functionInfos[2];
						functionsMap.put(purviewId+"_"+functionValue, functionValue);
					}
				}
			}
		}
		session.setAttribute("functionsMap", functionsMap);
	}
	// 后台管理员登录查询管理员权限
	@SuppressWarnings("unchecked")
	public String userLogin() {
		Map<String, List<Purview>> map = new HashMap<String, List<Purview>>();
		List<Purview> purviewList = new ArrayList<Purview>();
		users = (Users) request.getSession().getAttribute("users");
		List <UsersActor> usersActorList = usersActorService.findObjects(null, " where o.usersId='" + users.getUsersId() + "'");
		for (UsersActor ua : usersActorList) {
			List<ActorPurview> actorPurviewList = actorPurviewService.findObjects(null, " where o.actorId='" + ua.getActorId() + "'");
			for (ActorPurview ap : actorPurviewList) {
				Purview purview = (Purview) purviewService.getObjectByParams(" where o.purviewId='" + ap.getPurviewId() + "'");
				if (purview != null && purview.getParentId() != 0) {
					if (purviewList == null || purviewList.size() == 0) {
						purviewList.add(purview);
					} else {
						boolean flag = true;
						for (Purview p : purviewList) {
							if (p.getPurviewId().intValue() == purview.getPurviewId().intValue()) {
								flag = false;
								break;
							}
						}
						if (flag) {
							purviewList.add(purview);
						}
					}
				}
			}
			// 将已经选中的模块权限下对应的功能权限按照指定的规则进行组装
			joinFunctions(actorPurviewList);
		}
		String subPurviewIds="";
		// 查询去重以后的所有权限，根据父子关系放到Map中
		Map<String,Object> leftMap = new HashMap<String,Object>();
		int i=0;
		for (Purview p : purviewList) {
			List<Purview> list = new ArrayList<Purview>();
			if (p.getParentId().intValue() == 1) {
				for (Purview sp : purviewList) {
					if (sp.getParentId().intValue() == p.getPurviewId().intValue()) {
						list.add(sp);
					}
				}
				map.put(p.getPurviewName(), list);
				subPurviewIds+=p.getPurviewId()+",";
				if(i==0){
					leftMap.put(p.getPurviewName(), list);
					i++;
				}
			}
		}
		//查询导航栏权限
		if(!"".equals(subPurviewIds)){
			subPurviewIds=subPurviewIds.substring(0, subPurviewIds.lastIndexOf(","));
			List<Purview> subpurviewList=purviewService.findObjects(" where o.purviewId in ( "+subPurviewIds+" ) order by o.sortCode");
			session.setAttribute("purviewNameList", subpurviewList);
		}
		session.setAttribute("users", users);
		session.setMaxInactiveInterval(24*3600);
		session.setAttribute("map", leftMap);
		return SUCCESS;
	}
	//查询单个权限
	public String selectPurview() throws UnsupportedEncodingException{
		String id = request.getParameter("id");
		users = (Users)request.getSession().getAttribute("users");
		String pvIds=findActorPurview(users);//获取用户所有角色的权限
		Purview purview = (Purview) purviewService.getObjectByParams(" where o.purviewId='"+id+"'");
		List<Purview> list = purviewService.findObjects(" where o.parentId="+purview.getPurviewId()+" and o.purviewId in("+pvIds+") order by o.sortCode");
		session.removeAttribute("map");
		pruviewMap.clear();
		pruviewMap.put(purview.getPurviewName(), list);
		session.setAttribute("map", pruviewMap);
		request.getSession().setAttribute("name", purview.getPurviewName());
		return SUCCESS;
	}
	/***
	 * 查找用户角色的所有权限
	 * @return
	 */
	public String findActorPurview(Users users){
		Set<Integer> empSet=new HashSet<Integer>();
		//查用户拥有的角色
		List<UsersActor> usersActorList = usersActorService.findObjects(" where o.usersId='"+users.getUsersId()+"'");
		//查询用户拥有角色对应的权限
		for(UsersActor ua : usersActorList){
			List<ActorPurview> actorPurviewList = actorPurviewService.findObjects(" where o.actorId="+ua.getActorId());
			for(ActorPurview ap : actorPurviewList){
				empSet.add(ap.getPurviewId());
			}
		}
		String pvIds="";
		Iterator<Integer> iterator = empSet.iterator();
		while(iterator.hasNext()){
			Integer next = iterator.next();
			pvIds+=next+","; 
		}
		pvIds=pvIds.substring(0, pvIds.lastIndexOf(","));
		return pvIds;
	}
	//用户退出操作
	public String goToExit(){
		//清空session数据
		session.invalidate();
		return SUCCESS;
	}
	//临时更新sessiong用户信息操作
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	public void updateAdminInfo() throws IOException{
		//清空session数据
		session.removeAttribute("functionsMap");
		users = (Users)usersService.getObjectByParams(" where o.userName='admin' and o.password='admin'");
		Map<String, List<Purview>> map = new HashMap<String, List<Purview>>();
		List<Purview> purviewList = new ArrayList<Purview>();
		List <UsersActor> usersActorList = usersActorService.findObjects(null, " where o.usersId='" + users.getUsersId() + "'");
		for (UsersActor ua : usersActorList) {
			List<ActorPurview> actorPurviewList = actorPurviewService.findObjects(null, " where o.actorId='" + ua.getActorId() + "'");
			for (ActorPurview ap : actorPurviewList) {
				Purview purview = (Purview) purviewService.getObjectByParams(" where o.purviewId='" + ap.getPurviewId() + "'");
				if (purview != null && purview.getParentId() != 0) {
					if (purviewList == null || purviewList.size() == 0) {
						purviewList.add(purview);
					} else {
						boolean flag = true;
						for (Purview p : purviewList) {
							if (p.getPurviewId().intValue() == purview.getPurviewId().intValue()) {
								flag = false;
								break;
							}
						}
						if (flag) {
							purviewList.add(purview);
						}
					}
				}
			}
			// 将已经选中的模块权限下对应的功能权限按照指定的规则进行组装
			Map functionsMap= new HashMap<String, String>();
			//按照指定的规则进行功能权限的组装
			for (ActorPurview ap : actorPurviewList) {
				String functions=ap.getFunctions();
				if(functions!=null){
					String [] functionsArray=functions.split(",");
					if(functionsArray!=null&&functionsArray.length>0){
						for(String function:functionsArray){
							String [] functionInfos=function.split("_");
							String purviewId=functionInfos[0];
							String functionValue=functionInfos[2];
							functionsMap.put(purviewId+"_"+functionValue, functionValue);
						}
					}
				}
			}
			session.setAttribute("functionsMap", functionsMap);
		}
		JSONObject jo=new JSONObject();
		jo.accumulate("isSuccess", "true");
		PrintWriter out=response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
	}
	public void setUsersService(IUsersService usersService) {
		this.usersService = usersService;
	}
	public void setPurviewService(IPurviewService purviewService) {
		this.purviewService = purviewService;
	}
	public void setUsersActorService(IUsersActorService usersActorService) {
		this.usersActorService = usersActorService;
	}
	public void setActorPurviewService(IActorPurviewService actorPurviewService) {
		this.actorPurviewService = actorPurviewService;
	}
	@SuppressWarnings("rawtypes")
	public Map<String, List> getPruviewMap() {
		return pruviewMap;
	}
	@SuppressWarnings("rawtypes")
	public void setPruviewMap(Map<String, List> pruviewMap) {
		this.pruviewMap = pruviewMap;
	}
	public String getFalseMsg() {
		return falseMsg;
	}
	public void setFalseMsg(String falseMsg) {
		this.falseMsg = falseMsg;
	}
	public ByteArrayInputStream getImageStream() {
		return imageStream;
	}
	public void setImageStream(ByteArrayInputStream imageStream) {
		this.imageStream = imageStream;
	}
}
