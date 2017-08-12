package basic.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import util.action.BaseAction;
import util.other.CreateWhereSQLForSelect;
import util.other.JSONFormatDate;
import basic.pojo.Actor;
import basic.pojo.Users;
import basic.service.IUsersService;
/**
 * 后台管理员Action
 * @author ss
 *
 */
@SuppressWarnings("serial")
public class UsersAction extends BaseAction{
	private IUsersService usersService;//后台管理员Service
	private Users users;
	private List<Users> usersList = new ArrayList<Users>();//管理员List
	private List<Actor> actorList = new ArrayList<Actor>();//角色List
	private String ids;
	private String usersId;
	//跳转到后台管理员列表页面
	public String gotoUsersPage(){
		return SUCCESS;
	}
	//验证管理员是否重复
	public void checkUsers() throws IOException{
		String userName = request.getParameter("userName");
		if(userName!=null && !"".equals(userName)){
			Integer count = usersService.getCount(" where o.userName='"+userName+"'");
			if(count.intValue()==0){
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out = response.getWriter();
				out.print("ok");
				out.flush();
				out.close();
			}
		}
	}
	//跳转至修改密码页面
	public String goToupdatePasswordPage(){
		return SUCCESS;
	}
	//修改密码
	public String updatePassword(){
		String password = request.getParameter("password");
		Users users = (Users) session.getAttribute("users");
		if(users!=null){
			if(password!=null && !"".equals(password)){
				users.setPassword(password);
				users = (Users) usersService.saveOrUpdateObject(users);
				//session中删除原users信息
				session.removeAttribute("users");
			}
		}
		return SUCCESS;
	}
	//查询所有后台管理员
	public void listUsers() throws Exception{
		String selectFlag=request.getParameter("selectFlag");
		StringBuffer hqlsb = new StringBuffer();
		if("true".equals(selectFlag)){//判断是否点击查询按钮
			String userName = request.getParameter("userName");
			String registerDate = request.getParameter("registerDate");
			String lockState = request.getParameter("lockState");
			StringBuffer sb = CreateWhereSQLForSelect.appendLike(null, null, null);
			if(userName!=null&&!"".equals(userName.trim())){
				sb.append(CreateWhereSQLForSelect.appendLike("userName","like",userName.trim()));
			}
			if(registerDate!=null&&!"".equals(registerDate)){
				sb.append(CreateWhereSQLForSelect.appendLike("registerDate","like",request.getParameter("registerDate")));
			}
			if(!"-1".equals(lockState)){
				sb.append(CreateWhereSQLForSelect.appendLike("lockState","like",request.getParameter("lockState")));
			}
			if(!"".equals(sb.toString()) && sb != null){
				hqlsb=CreateWhereSQLForSelect.createSQL(sb);
			}
		}
		hqlsb.append(CreateWhereSQLForSelect.appendOrderBy(" usersId desc"));
		int totalRecordCount = usersService.getCount(hqlsb.toString());
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		usersList = usersService.findListByPageHelper(null,pageHelper, hqlsb.toString());
		Map<String, Object> jsonMap = new HashMap<String, Object>();// 定义map
		jsonMap.put("total", totalRecordCount);// total键 存放总记录数，必须的
		jsonMap.put("rows", usersList);// rows键 存放每页记录 list
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd HH:mm:ss"));
		JSONObject jo = JSONObject.fromObject(jsonMap, jsonConfig);//格式化result
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//保存或者修改后台管理员
	public void saveOrUpdateUsers() throws Exception{
		if(users!=null){
			if(users.getUsersId()==null || "".equals(users.getUsersId())){
				SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd HH-mm-ss"); 
				String dateString = formatter.format(new Date()); 
				java.util.Date timeDate = formatter.parse(dateString);
				java.sql.Timestamp timestamp = new java.sql.Timestamp(timeDate.getTime());
				users.setRegisterDate(timestamp);
			}
			users = (Users) usersService.saveOrUpdateObject(users);
			if(users.getUsersId()!=null){
				JSONObject jo = new JSONObject();
				jo.accumulate("isSuccess", "true");
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println(jo.toString());
				out.flush();
				out.close();
			}
		}
	}
	//获取一条记录
	public void getUsersObject() throws IOException{
		users = (Users) usersService.getObjectById(usersId);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd HH:mm:ss"));
		JSONObject jo = JSONObject.fromObject(users,jsonConfig);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//删除记录
	public void deleteUsers() throws IOException{
		Boolean isSuccess = usersService.deleteObjectsByIds("usersId",ids);
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
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
	public List<Users> getUsersList() {
		return usersList;
	}
	public void setUsersList(List<Users> usersList) {
		this.usersList = usersList;
	}
	public List<Actor> getActorList() {
		return actorList;
	}
	public void setActorList(List<Actor> actorList) {
		this.actorList = actorList;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getUsersId() {
		return usersId;
	}
	public void setUsersId(String usersId) {
		this.usersId = usersId;
	}
	public void setUsersService(IUsersService usersService) {
		this.usersService = usersService;
	}
}
