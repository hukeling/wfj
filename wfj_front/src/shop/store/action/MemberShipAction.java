package shop.store.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import shop.store.pojo.MemberShip;
import shop.store.service.IMemberShipService;
import util.action.BaseAction;
import util.other.JSONFormatDate;
/**
 * 店铺会员关系
 * @author jxw
 * 2014-10-13
 */
@SuppressWarnings("serial")
public class MemberShipAction extends BaseAction{
	/**店铺会员Service**/
	private IMemberShipService memberShipService;
	/**店铺会员集合**/
	private List<MemberShip> memberShiplist;
	
	/**
	 * 跳转到店铺会员列表页
	 */
	public String gotoMemberShipPage(){
		return SUCCESS;
	}
	/**
	 * 查询所有店铺会员信息
	 * @throws IOException 
	 */
	public void listMemberShip() throws IOException{
		int totalRecordCount = memberShipService.getCount(null);
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		memberShiplist = memberShipService.findListByPageHelper(null,pageHelper, " order by o.memberShipId desc");
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", totalRecordCount);
		jsonMap.put("rows", memberShiplist);
		JsonConfig jsonConfig = new JsonConfig();
	    jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd"));
		JSONObject jo = JSONObject.fromObject(jsonMap,jsonConfig);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
		
	public List<MemberShip> getMemberShiplist() {
		return memberShiplist;
	}
	public void setMemberShiplist(List<MemberShip> memberShiplist) {
		this.memberShiplist = memberShiplist;
	}
	public void setMemberShipService(IMemberShipService memberShipService) {
		this.memberShipService = memberShipService;
	}	
}
