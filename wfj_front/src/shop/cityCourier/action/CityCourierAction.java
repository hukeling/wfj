package shop.cityCourier.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import shop.cityCourier.pojo.CityCourier;
import shop.cityCourier.service.ICityCourierService;
import util.action.BaseAction;
import util.other.CreateWhereSQLForSelect;
import util.other.JSONFormatDate;

/**
 * 同城快递员人事档案Action类
 * @author wy
 *
 */

@SuppressWarnings("serial")
public class CityCourierAction extends BaseAction{
	private ICityCourierService cityCourierService;
	private List<CityCourier> cityCourierList;
	private CityCourier cityCourier;
	private String ids;
	private String id;
	
	public String gotoCityCourierPage(){
		return SUCCESS;
	}
	
	public void ListCityCourier() throws IOException{
		String selectFlag=request.getParameter("selectFlag");
		StringBuffer hqlsb = new StringBuffer();
		if("true".equals(selectFlag)){//判断是否点击查询按钮
			String cityCourierName = request.getParameter("cityCourierName");
			String phone = request.getParameter("phone");
			String responsibleAreas = request.getParameter("responsibleAreas");
			StringBuffer sb = CreateWhereSQLForSelect.appendLike(null, null, null);
			if(cityCourierName!=null&&!"".equals(cityCourierName.trim())){
				sb.append(CreateWhereSQLForSelect.appendLike("cityCourierName","like",cityCourierName.trim()));
			}
			if(phone!=null&&!"".equals(phone.trim())){
				sb.append(CreateWhereSQLForSelect.appendLike("phone","like",phone.trim()));
			}
			if(responsibleAreas!=null&&!"".equals(responsibleAreas.trim())){
				sb.append(CreateWhereSQLForSelect.appendLike("responsibleAreas","like",responsibleAreas.trim()));
			}
			if(!"".equals(sb.toString()) && sb != null){
				hqlsb=CreateWhereSQLForSelect.createSQL(sb);
			}
		}
		hqlsb.append(CreateWhereSQLForSelect.appendOrderBy(" cityCourierId desc"));
		int totalRecordCount = cityCourierService.getCount(hqlsb.toString());
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		String [] selectColumns={"cityCourierId","cityCourierName","responsibleAreas","phone","entryTime"};
		cityCourierList = cityCourierService.findListByPageHelper(selectColumns,pageHelper, hqlsb.toString());
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", totalRecordCount);
		jsonMap.put("rows", cityCourierList);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd"));
		JSONObject jo = JSONObject.fromObject(jsonMap, jsonConfig);//格式化result
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//保存或者修改
	public void saveOrUpdateCityCourier() throws IOException{
		if(cityCourier!=null){
			cityCourier = (CityCourier) cityCourierService.saveOrUpdateObject(cityCourier);
			if(cityCourier.getCityCourierId()!=null){
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
	public void getCityCourierInfo() throws IOException{
		cityCourier = (CityCourier) cityCourierService.getObjectById(id);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd"));
		JSONObject jo = JSONObject.fromObject(cityCourier,jsonConfig);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//删除记录
	public void deleteCityCouriers() throws IOException{
		Boolean isSuccess = cityCourierService.deleteObjectsByIds("cityCourierId",ids);
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	
	public List<CityCourier> getCityCourierList() {
		return cityCourierList;
	}
	public void setCityCourierList(List<CityCourier> cityCourierList) {
		this.cityCourierList = cityCourierList;
	}
	public CityCourier getCityCourier() {
		return cityCourier;
	}
	public void setCityCourier(CityCourier cityCourier) {
		this.cityCourier = cityCourier;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setCityCourierService(ICityCourierService cityCourierService) {
		this.cityCourierService = cityCourierService;
	}
	
}
