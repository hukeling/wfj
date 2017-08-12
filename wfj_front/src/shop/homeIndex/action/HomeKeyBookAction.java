package shop.homeIndex.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import shop.homeIndex.pojo.HomeKeyBook;
import shop.homeIndex.service.IHomeKeyBookService;
import util.action.BaseAction;

import com.opensymphony.xwork2.ActionContext;
/**
 * 数据字典Action类
 * @author whb
 *	20140114
 */
@SuppressWarnings("serial")
public class HomeKeyBookAction extends BaseAction{
	private IHomeKeyBookService homeKeyBookService;
	private HomeKeyBook homeKeyBook;
	private List<HomeKeyBook> homeKeyBookList = new ArrayList<HomeKeyBook>();
	private String homeKeyBookId;
	private String ids;
	//跳转到数据字典列表页面
	public String gotoHomeKeyBookPage(){
		return SUCCESS;
	}
	//查询所有信息列表
	@SuppressWarnings("unchecked")
	public void listHomeKeyBook() throws IOException{
		String where =" where 1=1 ";
		String type = request.getParameter("type");
		String name = request.getParameter("name");
		if(name!=null&&!"".equals(name.trim())){
			name = name.trim();
			where += " and o.name like '%"+name+"%'";
		}
		if(type!=null&&!"".equals(type.trim())){
			type = type.trim();
			where += " and o.type like '%"+type+"%'";
		}
		int totalRecordCount = homeKeyBookService.getCount(where+" order by o.type,o.value");
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		String [] selectColumns={"homeKeyBookId","value","name","type","typeName"};
		homeKeyBookList = homeKeyBookService.findListByPageHelper(selectColumns,pageHelper, where+" order by o.type,o.value");//" order by o.type,o.value"
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", totalRecordCount);
		jsonMap.put("rows", homeKeyBookList);
		JSONObject jo = JSONObject.fromObject(jsonMap);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//保存或者修改
	public void saveOrUpdateHomeKeyBook() throws IOException{
		if(homeKeyBook!=null){
			homeKeyBook = (HomeKeyBook) homeKeyBookService.saveOrUpdateObject(homeKeyBook);
			if(homeKeyBook.getHomeKeyBookId()!=null){
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
	public void getHomeKeyBookInfo() throws IOException{
		homeKeyBook = (HomeKeyBook) homeKeyBookService.getObjectByParams(" where o.homeKeyBookId='"+homeKeyBookId+"'");
		JSONObject jo = JSONObject.fromObject(homeKeyBook);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//删除记录
	public void deleteHomeKeyBook() throws IOException{
		Boolean isSuccess = homeKeyBookService.deleteObjectsByIds("homeKeyBookId",ids);
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//更新初始化数据字典
	@SuppressWarnings("unchecked")
	public void updateInServletContextHomeKeyBook() throws IOException{
		Map<String, Object> servletContext = ActionContext.getContext().getApplication();
		Boolean isSuccess = false;//返回值，是否更新成功，默认是否
		if (servletContext != null) {
			Map<String,List<HomeKeyBook>> map = new HashMap<String,List<HomeKeyBook>>();
			List<String> typeNameList = homeKeyBookService.distinctType("type", "");//查找类型名称
			for(String typeName : typeNameList){
				List<HomeKeyBook> homeKeyBookList = homeKeyBookService.findObjects(null," where o.type = '"+typeName+"' order by o.homeKeyBookId asc ");//根据类型名称查出对象集合
				map.put(typeName, homeKeyBookList);
			}
			servletContext.put("homekeybook", map);
			isSuccess = true;
		}
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	public HomeKeyBook getHomeKeyBook() {
		return homeKeyBook;
	}
	public void setHomeKeyBook(HomeKeyBook homeKeyBook) {
		this.homeKeyBook = homeKeyBook;
	}
	public List<HomeKeyBook> getHomeKeyBookList() {
		return homeKeyBookList;
	}
	public void setHomeKeyBookList(List<HomeKeyBook> homeKeyBookList) {
		this.homeKeyBookList = homeKeyBookList;
	}
	public String getHomeKeyBookId() {
		return homeKeyBookId;
	}
	public void setHomeKeyBookId(String homeKeyBookId) {
		this.homeKeyBookId = homeKeyBookId;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public void setHomeKeyBookService(IHomeKeyBookService homeKeyBookService) {
		this.homeKeyBookService = homeKeyBookService;
	}
}
