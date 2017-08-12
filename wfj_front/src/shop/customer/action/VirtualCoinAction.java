package shop.customer.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import shop.customer.pojo.VirtualCoin;
import shop.customer.service.IVirtualCoinService;
import util.action.BaseAction;
import util.other.JSONFormatDate;
/**
 * 虚拟金币Action
 * @author ss
 *
 */
public class VirtualCoinAction extends BaseAction{
	private static final long serialVersionUID = 7034206694063394841L;
	/**虚拟金币service**/
	private IVirtualCoinService virtualCoinService;
	/**用户id**/
	private Integer customerId;
	/***************************************end*********************************************/
	/**
	 * 跳转到虚拟金币页面
	 * @return
	 */
	public String gotoVirtualCoin(){
		return SUCCESS;
	}
	/**
	 * 查询用户虚拟金币list
	 */
	public void virtualCoinList(){
		try {
			String loginName = request.getParameter("loginName");
			String condition = "";
			if(loginName!=null&&!"".equals(loginName.trim())){
				condition = " and b.loginName like '%"+loginName.trim()+"%' ";
			}
			String countHql = "select count(a.virtualCoinId)  from VirtualCoin a ,Customer b where a.customerId = b.customerId";
			String hql = "select a.virtualCoinId as virtualCoinId,a.customerId as customerId,b.loginName as loginName,a.remainingNumber as remainingNumber from (select x.virtualCoinId,x.remainingNumber,x.customerId from shop_virtual_coin x order by x.tradeTime desc)a ,basic_customer b where a.customerId = b.customerId "+condition+" group by a.customerId";
			int totalRecordCount = virtualCoinService.getMoreTableCount(countHql+condition);
			pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
			List<Map<String,Object>> virtualCoinList = virtualCoinService.findListMapPageBySql(hql, pageHelper);
			Map<String, Object> jsonMap = new HashMap<String, Object>();// 定义map
			jsonMap.put("total", totalRecordCount);// total键 存放总记录数，必须的
			jsonMap.put("rows", virtualCoinList);// rows键 存放每页记录 list
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
	 * 通过用户Id查找虚拟金币明细
	 */
	public void getVirtualCoinById(){
		try {
			if(customerId!=null){
				List<VirtualCoin> coinDetailList = virtualCoinService.findObjects("where o.customerId="+customerId+" order by o.tradeTime desc");
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd HH:mm:ss"));
				JSONArray jo = JSONArray.fromObject(coinDetailList,jsonConfig);
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
	public void setVirtualCoinService(IVirtualCoinService virtualCoinService) {
		this.virtualCoinService = virtualCoinService;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
}
