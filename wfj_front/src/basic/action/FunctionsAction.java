package basic.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import util.action.BaseAction;
import basic.pojo.Functions;
import basic.service.IFunctionsService;
/**
 * 功能权限Action
 * @author 刘青松
 *
 */
@SuppressWarnings("serial")
public class FunctionsAction extends BaseAction {
	private IFunctionsService functionsService;//权限Service
	private Integer purviewId;
	private String ids;
	private String fid;
	private Functions functions;
	private List <Functions>functionsList;
	//查询所有信息列表
	public void listFunctions() throws IOException{
		int totalRecordCount = functionsService.getCount(" where o.purviewId='"+purviewId+"' order by o.fid");
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		functionsList = functionsService.findListByPageHelper(null,pageHelper, " where o.purviewId='"+purviewId+"' order by o.fid desc");
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", totalRecordCount);
		jsonMap.put("rows", functionsList);
		JSONObject jo = JSONObject.fromObject(jsonMap);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//保存或者修改
	public void saveOrUpdateFunctions() throws IOException{
		if(functions!=null){
			functions = (Functions) functionsService.saveOrUpdateObject(functions);
			if(functions.getFid()!=null){
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
	public void getFunctionsObject() throws IOException{
		functions = (Functions) functionsService.getObjectByParams(" where o.fid='"+fid+"'");
		JSONObject jo = JSONObject.fromObject(functions);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//删除记录
	public void deleteFunctions() throws IOException{
		Boolean isSuccess = functionsService.deleteObjectsByIds("fid",ids);
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	public IFunctionsService getFunctionsService() {
		return functionsService;
	}
	public void setFunctionsService(IFunctionsService functionsService) {
		this.functionsService = functionsService;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public Functions getFunctions() {
		return functions;
	}
	public void setFunctions(Functions functions) {
		this.functions = functions;
	}
	public List <Functions> getFunctionsList() {
		return functionsList;
	}
	public void setFunctionsList(List <Functions> functionsList) {
		this.functionsList = functionsList;
	}
	public Integer getPurviewId() {
		return purviewId;
	}
	public void setPurviewId(Integer purviewId) {
		this.purviewId = purviewId;
	}
}
