package shop.navigation.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONObject;
import shop.navigation.pojo.NavigationBars;
import shop.navigation.service.INavigationBarsService;
import util.action.BaseAction;
/**
 * 导航条Action
 * @author 张攀攀
 *
 */
@SuppressWarnings("serial")
public class NavigationBarsAction extends BaseAction {
	private INavigationBarsService navigationBarsService;//导航条Service
	private List<NavigationBars> navigationBarsList = new ArrayList<NavigationBars>();//导航条List
	private NavigationBars navigationBars;
	private String id;
	private String navigationBarsId;
	//删除导航条
	public void deleteNavigationBars() throws IOException{
		navigationBarsList = navigationBarsService.findObjects("");
		//判断删除的导航条是否有子类，有就不能删除。
		boolean flag = true;
		for(NavigationBars nb : navigationBarsList){
			Integer parentId = nb.getParentId();
			if(navigationBarsId.equals(parentId.toString())){
				//有子类，不能删除
				flag = false;
			}
		}
		if(flag){
			//可以删除
			navigationBarsService.deleteObjectByParams(" where o.navigationBarsId="+navigationBarsId);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print("true");
			out.flush();
			out.close();
		}
	}
	//查询导航条对象
	public void getNavigationBarsObject() throws IOException{
		navigationBars = (NavigationBars)navigationBarsService.getObjectById(navigationBarsId);
		response.setContentType("text/html;charset=utf-8");
		JSONObject jo = JSONObject.fromObject(navigationBars);
		PrintWriter out = response.getWriter();
		out.print(jo.toString());
		out.flush();
		out.close();
	}
	//保存导航条
	public void saveOrUpdateNavigationBars(){
		navigationBarsService.saveOrUpdateObject(navigationBars);
	}
	//查询导航条树
	public void getNodes() throws IOException {
		//通过父类Id查询子导航条列表
		String hql = " where o.parentId="+id+" order by o.navigationBarsId";
		navigationBarsList = navigationBarsService.findObjects(hql);
		StringBuffer sbf = new StringBuffer();
		sbf.append("<List>");
		for (Iterator<NavigationBars> ite = navigationBarsList.iterator(); ite.hasNext();) {
			navigationBars = (NavigationBars) ite.next();
			if (navigationBars != null) {
				sbf.append("<NavigationBars>");
				sbf.append("<name>").append(navigationBars.getModName()).append(
						"</name>");
				sbf.append("<id>").append(navigationBars.getNavigationBarsId()).append(
						"</id>");
				sbf.append("</NavigationBars>");
			}
		}
		sbf.append("</List>");
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(sbf.toString());
		out.flush();
		out.close();
	}
	//导航条页面初始化
	public String gotoNavigationBarsPage(){
		return SUCCESS;
	}
	public List<NavigationBars> getNavigationBarsList() {
		return navigationBarsList;
	}
	public void setNavigationBarsList(List<NavigationBars> navigationBarsList) {
		this.navigationBarsList = navigationBarsList;
	}
	public NavigationBars getNavigationBars() {
		return navigationBars;
	}
	public void setNavigationBars(NavigationBars navigationBars) {
		this.navigationBars = navigationBars;
	}
	public void setNavigationBarsService(
			INavigationBarsService navigationBarsService) {
		this.navigationBarsService = navigationBarsService;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNavigationBarsId() {
		return navigationBarsId;
	}
	public void setNavigationBarsId(String navigationBarsId) {
		this.navigationBarsId = navigationBarsId;
	}
}
