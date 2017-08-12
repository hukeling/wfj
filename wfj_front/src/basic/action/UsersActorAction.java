package basic.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.action.BaseAction;
import util.other.Escape;
import basic.pojo.Actor;
import basic.pojo.UsersActor;
import basic.service.IActorService;
import basic.service.IUsersActorService;
/**
 * 管理员角色Action
 * @author ss
 *
 */
@SuppressWarnings("serial")
public class UsersActorAction extends BaseAction{
	private IUsersActorService usersActorService;//管理员角色Service
	private IActorService actorService;//角色Service
	private List<UsersActor> usersActorList = new ArrayList<UsersActor>();//管理员角色List
	private List<Actor> actorList = new ArrayList<Actor>();
	private String usersId;
	private UsersActor usersActor;
	private String actorName;
	private List<String> actorInfos;//提交多个角色信息
	private String ids;
	private String userName;
	//查选所有的角色和用户已经选择的角色
	public String findActorListByUsersId(){
		request.setAttribute("usersId", usersId);
		//用户名解码
		String replaceAll = userName.replaceAll("_", "%");
		userName = Escape.unescape((replaceAll.trim()));
		request.setAttribute("userNameInfo",userName);
		return SUCCESS;
	}
	//查询管理员已经分配的角色
	public void listUsersActorByUsersId() throws IOException{
		int totalRecordCount = usersActorService.getCount(" where o.usersId='"+usersId+"'");
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		usersActorList = usersActorService.findListByPageHelper(null,pageHelper, " where o.usersId='"+usersId+"' order by o.userActorId desc");
		Map<String, Object> jsonMap = new HashMap<String, Object>();// 定义map
		jsonMap.put("total", totalRecordCount);// total键 存放总记录数，必须的
		jsonMap.put("rows", usersActorList);// rows键 存放每页记录 list
		JSONObject jo = JSONObject.fromObject(jsonMap);// 格式化result
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//根据条件查询角色信息
	public void listActorInfo() throws IOException{
		String hql = "";
		if("".equals(actorName) || actorName==null){
			hql = " order by o.actorId";
		}else{
			hql = " where o.actorName like'%"+actorName+"%' order by o.actorId";
		}
		actorList = actorService.findObjects(hql);
		JSONArray jo = JSONArray.fromObject(actorList);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//保存管理员选择的角色
	public String saveOrUpdateUsersActor() throws UnsupportedEncodingException{
		usersId = usersActor.getUsersId().toString();
		if(actorInfos!=null && actorInfos.size()>0){
			for(String actorIdName : actorInfos){
				UsersActor usersActor = new UsersActor();
				String [] mbis= actorIdName.split("_");
				usersActor.setUsersId(Integer.parseInt(usersId));
				usersActor.setActorId(Integer.parseInt(mbis[0]));
				String txt=new String(mbis[1]);
				usersActor.setActorName(txt);
				usersActorService.saveOrUpdateObject(usersActor);
			}
		}
		return SUCCESS;
	}
	//删除管理员角色信息
	public void deleteUsersActor() throws IOException{
		Boolean isSuccess = usersActorService.deleteObjectsByIds("userActorId",ids);
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	public List<UsersActor> getUsersActorList() {
		return usersActorList;
	}
	public void setUsersActorList(List<UsersActor> usersActorList) {
		this.usersActorList = usersActorList;
	}
	public String getUsersId() {
		return usersId;
	}
	public void setUsersId(String usersId) {
		this.usersId = usersId;
	}
	public UsersActor getUsersActor() {
		return usersActor;
	}
	public void setUsersActor(UsersActor usersActor) {
		this.usersActor = usersActor;
	}
	public void setUsersActorService(IUsersActorService usersActorService) {
		this.usersActorService = usersActorService;
	}
	public List<Actor> getActorList() {
		return actorList;
	}
	public void setActorList(List<Actor> actorList) {
		this.actorList = actorList;
	}
	public String getActorName() {
		return actorName;
	}
	public void setActorName(String actorName) {
		this.actorName = actorName;
	}
	public void setActorService(IActorService actorService) {
		this.actorService = actorService;
	}
	public List<String> getActorInfos() {
		return actorInfos;
	}
	public void setActorInfos(List<String> actorInfos) {
		this.actorInfos = actorInfos;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
