package shop.common.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import shop.common.pojo.CoinRules;
import shop.common.service.ICoinRulesService;
import util.action.BaseAction;
import util.other.CreateWhereSQLForSelect;

import com.opensymphony.xwork2.ActionContext;
/**
 * 虚拟金杯赠送规则Action类
 * @author mf
 *
 */
@SuppressWarnings("all")
public class CoinRulesAction extends BaseAction{
	private ICoinRulesService coinRulesService;
	private CoinRules coinRules;
	private List<CoinRules> coinRulesList = new ArrayList<CoinRules>();
	private String coinRulesId;
	private String ids;
	//跳转到数据字典列表页面
	public String gotoCoinRulesPage(){
		return SUCCESS;
	}
	//查询所有信息列表
	public void listCoinRules() throws IOException{
		String selectFlag=request.getParameter("selectFlag");
		StringBuffer hqlsb = new StringBuffer();
		if("true".equals(selectFlag)){//判断是否点击查询按钮
			String name = request.getParameter("name");
			String typeName = request.getParameter("typeName");
			StringBuffer sb = CreateWhereSQLForSelect.appendLike(null, null, null);
			if(name!=null&&!"".equals(name.trim())){
				sb.append(CreateWhereSQLForSelect.appendLike("name","like",name.trim()));
			}
			if(typeName!=null&&!"".equals(typeName.trim())){
				sb.append(CreateWhereSQLForSelect.appendLike("typeName","like",typeName.trim()));
			}
			if(!"".equals(sb.toString()) && sb != null){
				hqlsb=CreateWhereSQLForSelect.createSQL(sb);
			}
		}
		hqlsb.append(CreateWhereSQLForSelect.appendOrderBy(" coinRulesId desc"));
		int totalRecordCount = coinRulesService.getCount(hqlsb.toString());
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		String [] selectColumns={"coinRulesId","value","name","type","typeName"};
		coinRulesList = coinRulesService.findListByPageHelper(selectColumns,pageHelper, hqlsb.toString());
		//keyBookList = keyBookService.findListByPageHelper(null,pageHelper, " order by o.keyBookId desc");
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", totalRecordCount);
		jsonMap.put("rows", coinRulesList);
		JSONObject jo = JSONObject.fromObject(jsonMap);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//保存或者修改
	public void saveOrUpdateCoinRules() throws IOException{
		if(coinRules!=null){
			coinRules = (CoinRules) coinRulesService.saveOrUpdateObject(coinRules);
			if(coinRules.getCoinRulesId()!=null){
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
	public void getCoinRulesInfo() throws IOException{
		coinRules = (CoinRules) coinRulesService.getObjectByParams(" where o.coinRulesId='"+coinRulesId+"'");
		JSONObject jo = JSONObject.fromObject(coinRules);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//删除记录
	public void deleteCoinRules() throws IOException{
		Boolean isSuccess = coinRulesService.deleteObjectsByIds("coinRulesId",ids);
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//更新初始化虚拟金币赠送规则
	@SuppressWarnings("unchecked")
	public void updateInServletContextCoinRules() throws IOException{
		Map<String, Object> servletContext = ActionContext.getContext().getApplication();
		Boolean isSuccess = false;//返回值，是否更新成功，默认是否
		if (servletContext != null) {
			Map<String,List<CoinRules>> map = new HashMap<String,List<CoinRules>>();
			List<String> typeNameList = coinRulesService.distinctType("type", "");//查找类型名称
			for(String typeName : typeNameList){
				List<CoinRules> coinRulesList = coinRulesService.findObjects(" where o.type = '"+typeName+"' order by o.value ");//根据类型名称查出对象集合
				map.put(typeName, coinRulesList);
			}
			servletContext.put("coinRules", map);
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
	//setter getter 
	public CoinRules getCoinRules() {
		return coinRules;
	}
	public void setCoinRules(CoinRules coinRules) {
		this.coinRules = coinRules;
	}
	public List<CoinRules> getCoinRulesList() {
		return coinRulesList;
	}
	public void setCoinRulesList(List<CoinRules> coinRulesList) {
		this.coinRulesList = coinRulesList;
	}
	public String getCoinRulesId() {
		return coinRulesId;
	}
	public void setCoinRulesId(String coinRulesId) {
		this.coinRulesId = coinRulesId;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public void setCoinRulesService(ICoinRulesService coinRulesService) {
		this.coinRulesService = coinRulesService;
	}
}
