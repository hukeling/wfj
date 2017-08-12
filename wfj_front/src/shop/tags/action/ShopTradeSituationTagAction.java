package shop.tags.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;

import shop.tags.pojo.ShopSituationTag;
import shop.tags.pojo.ShopTradeSituationTag;
import shop.tags.service.IShopSituationTagService;
import shop.tags.service.IShopTradeSituationTagService;
import shop.tags.service.IShopTradeTagService;
import util.action.BaseAction;

/**
 * 适合行业标签和使用场合标签关联表
 * @author mqr
 */
public class ShopTradeSituationTagAction extends BaseAction {
	private static final long serialVersionUID = 4959961695071766795L;
	/**适合行业标签和使用场合标签关联表Service**/
	private IShopTradeSituationTagService shopTradeSituationTagService;
	/**适合行业标签Service**/
	private IShopTradeTagService shopTradeTagService;
	/**使用场合标签Service**/
	private IShopSituationTagService shopSituationTagService;
	/**适合行业标签POJO**/
	private ShopTradeSituationTag shopTradeSituationTag;
	private String ids;
	private String tstId;
	
	/** 
	 * 页面跳转 
	 */
	public String gotoShopTradeSituationTagPage(){
		//获取标签名称 并进行解码操作
		String tageName = request.getParameter("tageName");
		tageName = tageName.replaceAll("_", "%");
		tageName=util.other.Escape.unescape(tageName);
		//将解码后的标签名称存储到request域中
		request.setAttribute("tageName", tageName);
		return SUCCESS;
	}
	/**查看已关联的标签
	 * @throws IOException 
	 */
	@SuppressWarnings("rawtypes")
	public void listShopTradeSituationTag() throws IOException{
		String tageName = request.getParameter("tageName");
		String useState = request.getParameter("useState");
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		String hql="select a.tstId as tstId,c.tageName as tageName,c.stId as stId,c.useState as useState from ShopTradeSituationTag a ,ShopTradeTag b ,ShopSituationTag c where a.stId=c.stId and a.ttId=b.ttId and a.ttId="+ids;
		String hqlCount="select count(a.tstId) from ShopTradeSituationTag a ,ShopTradeTag b ,ShopSituationTag c where a.stId=c.stId and a.ttId=b.ttId and a.ttId="+ids;
		if(tageName!=null&&!"".equals(tageName.trim())){
			hql+=" and c.tageName like'%"+tageName+"%'";
			hqlCount+=" and c.tageName like'%"+tageName+"%'";
		}
		if(useState!=null&&!"-1".equals(useState)){
			hql+=" and c.useState ="+useState;
			hqlCount+=" and c.useState="+useState;
		}
		int totalRecordCount=shopTradeSituationTagService.getMultilistCount(hqlCount);
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		List<Map> listMap=shopTradeSituationTagService.findListMapPage(hql+" order by a.tstId desc", pageHelper);
		jsonMap.put("total", totalRecordCount);
		jsonMap.put("rows", listMap);
		JSONObject jo = JSONObject.fromObject(jsonMap);
		response.setContentType("text/html;charset=utf-8"); 
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	
	
	/**
	 * 查看未关联标签
	 */
	@SuppressWarnings("unchecked")
	public void listShopSituationTag() throws IOException{
		String tageName = request.getParameter("tageName");
		String useState = request.getParameter("useState");
		String where="";
		//查看已经关联的中间表id
		List<ShopTradeSituationTag> ststList = shopTradeSituationTagService.findObjects(new String[]{"tstId","stId"}, " where o.ttId="+ids);
		String sbHasStId="";//声明已有使用行业标签id
		if(ststList!=null&&ststList.size()>0){
			for(ShopTradeSituationTag stst:ststList){
				sbHasStId+=stst.getStId()+",";
			}
			if(StringUtils.isNotEmpty(sbHasStId)){
				sbHasStId=sbHasStId.substring(0, sbHasStId.lastIndexOf(","));
				where+=" where o.stId not in("+sbHasStId+")";
			}
		}
		if(tageName!=null&&!"".equals(tageName.trim())){
			where+=" and o.tageName like'%"+tageName+"%'";
		}
		if(useState!=null&&!"-1".equals(useState)){
			where+=" and o.useState ="+useState;
		}
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		int totalRecordCount=shopSituationTagService.getCount(where);
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		List<ShopSituationTag> list=shopSituationTagService.findListByPageHelper(null, pageHelper, where);
		jsonMap.put("total", totalRecordCount);
		jsonMap.put("rows", list);
		JSONObject jo = JSONObject.fromObject(jsonMap);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	
	
	/**保存新的记录**/
	public void savaOrUpdateShopTradeSituationTag() throws Exception{
		JSONObject jo = new JSONObject();
		//获取ttid
		String ttId = request.getParameter("ttId");
		String[] split = ids.split(",");
		for(String s:split){
			ShopTradeSituationTag stst=new ShopTradeSituationTag();
			stst.setTtId(Integer.parseInt(ttId));
			stst.setStId(Integer.parseInt(s));
			shopTradeSituationTagService.saveOrUpdateObject(stst);
		}
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**删除记录**/
	public void deleteShopTradeSituationTag() throws IOException{
		Boolean isSuccess = shopTradeSituationTagService.deleteObjectsByIds("tstId",ids);
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	
	//setter getter
	public ShopTradeSituationTag getShopTradeSituationTag() {
		return shopTradeSituationTag;
	}
	public void setShopTradeSituationTag(ShopTradeSituationTag shopTradeSituationTag) {
		this.shopTradeSituationTag = shopTradeSituationTag;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getTstId() {
		return tstId;
	}
	public void setTstId(String tstId) {
		this.tstId = tstId;
	}
	public void setShopTradeSituationTagService(
			IShopTradeSituationTagService shopTradeSituationTagService) {
		this.shopTradeSituationTagService = shopTradeSituationTagService;
	}
	public void setShopTradeTagService(IShopTradeTagService shopTradeTagService) {
		this.shopTradeTagService = shopTradeTagService;
	}
	public void setShopSituationTagService(
			IShopSituationTagService shopSituationTagService) {
		this.shopSituationTagService = shopSituationTagService;
	}
}
