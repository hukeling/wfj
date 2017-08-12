package shop.lineofcreditItem.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import shop.lineofcreditItem.pojo.LineofcreditItem;
import shop.lineofcreditItem.service.ILineofcreditItemService;
import util.action.BaseAction;
import util.other.JSONFormatDate;
/**
 * 授信额度Action
 * @author wsy
 *
 */
public class LineofcreditItemAction extends BaseAction{
	private static final long serialVersionUID = 7034206694063394841L;
	/**虚拟金币service**/
	private ILineofcreditItemService lineofcreditItemService;
	/**用户id**/
	private Integer customerId;
	/***************************************end*********************************************/
	/**
	 * 跳转到虚拟金币页面
	 * @return
	 */
	public String gotoLineofcreditItem(){
		return SUCCESS;
	}
	/**
	 * 查询用户授信额度list
	 */
	public void lineofcreditItemList(){
		try {
			String loginName = request.getParameter("loginName");
			String condition = "";
			if(loginName!=null&&!"".equals(loginName.trim())){
				condition = " and b.loginName like '%"+loginName.trim()+"%' ";
			}
			String countHql = "select count(a.lineOfCreditId)  from LineofcreditItem a ,Customer b where a.customerId = b.customerId";
			String hql = "select a.lineOfCreditId as lineOfCreditId,a.customerId as customerId,b.loginName as loginName,a.remainingNumber as remainingNumber from (select x.lineOfCreditId,x.remainingNumber,x.customerId from shop_lineofcredit_item x order by x.tradeTime desc)a ,basic_customer b where a.customerId = b.customerId "+condition+" group by a.customerId";
			int totalRecordCount = lineofcreditItemService.getMoreTableCount(countHql+condition);
			pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
			List<Map<String,Object>> lineofcreditItemList = lineofcreditItemService.findListMapPageBySql(hql, pageHelper);
			Map<String, Object> jsonMap = new HashMap<String, Object>();// 定义map
			jsonMap.put("total", totalRecordCount);// total键 存放总记录数，必须的
			jsonMap.put("rows", lineofcreditItemList);// rows键 存放每页记录 list
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd HH:mm:ss"));
			JSONObject jo = JSONObject.fromObject(jsonMap,jsonConfig);// 格式化result
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out;
			out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 通过用户Id查找授信额度明细
	 */
	public void getVirtualCoinById(){
		try {
			if(customerId!=null){
				List<LineofcreditItem> itemDetailList = lineofcreditItemService.findObjects("where o.customerId="+customerId+" order by o.tradeTime desc");
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd HH:mm:ss"));
				JSONArray jo = JSONArray.fromObject(itemDetailList,jsonConfig);
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out;
				out = response.getWriter();
				out.println(jo.toString());
				out.flush();
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void setLineofcreditItemService(
			ILineofcreditItemService lineofcreditItemService) {
		this.lineofcreditItemService = lineofcreditItemService;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
}
