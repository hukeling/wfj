package basic.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import util.action.BaseAction;
import util.other.CreateWhereSQLForSelect;
import basic.pojo.Actor;
import basic.service.IActorService;
/**
 * 
 * 角色Action
 * @author ss
 *
 */
@SuppressWarnings("serial")
public class ActorAction extends BaseAction{
	private IActorService actorService;//角色Service
	private List<Actor> actorList = new ArrayList<Actor>();
	private Actor actor;
	private String ids;
	private String actorId;
	//跳转到角色列表信息页面
	public String gotoActorPage(){
		return SUCCESS;
	}
	//查询所有角色信息
	public void listActor() throws Exception{
		String selectFlag=request.getParameter("selectFlag");
		StringBuffer hqlsb = new StringBuffer();
		if("true".equals(selectFlag)){//判断是否点击查询按钮
			String actorName = request.getParameter("actorName");
			if(actorName!=null&&!"".equals(actorName.trim())){
				StringBuffer sb=CreateWhereSQLForSelect.appendLike("actorName","like",actorName.trim());
				hqlsb=CreateWhereSQLForSelect.createSQL(sb);
			}
		}
		hqlsb.append(CreateWhereSQLForSelect.appendOrderBy(" actorId desc"));
		int totalRecordCount = actorService.getCount(hqlsb.toString());
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		actorList = actorService.findListByPageHelper(null,pageHelper, hqlsb.toString());
		Map<String, Object> jsonMap = new HashMap<String, Object>();// 定义map
		jsonMap.put("total", totalRecordCount);// total键 存放总记录数，必须的
		jsonMap.put("rows", actorList);// rows键 存放每页记录 list
		JSONObject jo = JSONObject.fromObject(jsonMap);// 格式化result
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//保存或者修改后台管理员
	public void saveOrUpdateActor() throws Exception{
		if(actor!=null){
			actor = (Actor) actorService.saveOrUpdateObject(actor);
			if(actor.getActorId()!=null){
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
	public void getActorObject() throws IOException{
		actor = (Actor) actorService.getObjectById(actorId);
		JSONObject jo = JSONObject.fromObject(actor);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//删除记录
	public void deleteActor() throws IOException{
		Boolean isSuccess = actorService.deleteObjectsByIds("actorId",ids);
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	public List<Actor> getActorList() {
		return actorList;
	}
	public void setActorList(List<Actor> actorList) {
		this.actorList = actorList;
	}
	public Actor getActor() {
		return actor;
	}
	public void setActor(Actor actor) {
		this.actor = actor;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getActorId() {
		return actorId;
	}
	public void setActorId(String actorId) {
		this.actorId = actorId;
	}
	public void setActorService(IActorService actorService) {
		this.actorService = actorService;
	}
}
