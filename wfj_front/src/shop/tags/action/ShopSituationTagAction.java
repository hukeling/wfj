package shop.tags.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import shop.tags.pojo.ShopSituationTag;
import shop.tags.service.IShopSituationTagService;
import util.action.BaseAction;
import util.other.CreateWhereSQLForSelect;

/**
 * 使用场合标签
 * @author mqr
 */
public class ShopSituationTagAction extends BaseAction {
	private static final long serialVersionUID = 4959961695071766795L;
	/**使用场合标签Service**/
	private IShopSituationTagService shopSituationTagService;
	/**使用场合标签POJO**/
	private ShopSituationTag shopSituationTag;
	private String ids;
	private String stId;
	
	/** 
	 * 页面跳转 
	 */
	public String gotoShopSituationTagPage(){
		return SUCCESS;
	}
	/**查看所有标签
	 * @throws IOException 
	 */
	public void listShopSituationTag() throws IOException{
		String selectFlag=request.getParameter("selectFlag");
		StringBuffer hqlsb = new StringBuffer();
		if("true".equals(selectFlag)){//判断是否点击查询按钮
			String tageName = request.getParameter("tageName");
			String useState = request.getParameter("useState");
			StringBuffer sb = CreateWhereSQLForSelect.appendLike(null, null, null);
			if(tageName!=null&&!"".equals(tageName.trim())){
				sb.append(CreateWhereSQLForSelect.appendLike("tageName","like",tageName.trim()));
			}
			if(!"-1".equals(useState)){
				sb.append(CreateWhereSQLForSelect.appendEqual("useState",useState));
			}
			if(!"".equals(sb.toString()) && sb != null){
				hqlsb=CreateWhereSQLForSelect.createSQL(sb);
			}
		}
		hqlsb.append(CreateWhereSQLForSelect.appendOrderBy(" stId desc"));
		int totalRecordCount = shopSituationTagService.getCount(hqlsb.toString());
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		String [] selectColumns={"stId","tageName","useState"};
		List<ShopSituationTag> shopSituationTagList = shopSituationTagService.findListByPageHelper(selectColumns,pageHelper, hqlsb.toString());
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", totalRecordCount);
		jsonMap.put("rows", shopSituationTagList);
		JSONObject jo = JSONObject.fromObject(jsonMap);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**保存新的记录**/
	public void savaOrUpdateShopSituationTag() throws Exception{
		JSONObject jo = new JSONObject();
		if(shopSituationTag!=null){
			shopSituationTag = (ShopSituationTag) shopSituationTagService.saveOrUpdateObject(shopSituationTag);
			if(shopSituationTag.getStId()!=null){
				jo.accumulate("isSuccess", "true");
			}else{
				jo.accumulate("isSuccess", "false");
			}
		}else{
			jo.accumulate("isSuccess", "false");
		}
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**获取最新一条记录**/
	public void getShopSituationTagObject() throws IOException{
		shopSituationTag = (ShopSituationTag) shopSituationTagService.getObjectByParams(" where o.stId="+stId);
		JSONObject jo = JSONObject.fromObject(shopSituationTag);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**删除记录**/
	public void deleteShopSituationTag() throws IOException{
		Boolean isSuccess = shopSituationTagService.deleteObjectsByIds("stId",ids);
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	
	/**
	 * 校验标签名称的唯一性
	 * @return
	 */
	public void checkTageName()throws IOException{
		//声明返回的布尔类型变量
		boolean isHas=false;
		//获取标签名称
		String tageName = request.getParameter("tageName");
		if(StringUtils.isNotEmpty(tageName)){
			Integer count = shopSituationTagService.getCount(" where o.tageName='"+tageName+"'");
			if(count>0){
				isHas=true;
			}
		}
		JSONObject jo = new JSONObject();
		jo.accumulate("isHas", isHas + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	
	//setter getter
	public ShopSituationTag getShopSituationTag() {
		return shopSituationTag;
	}
	public void setShopSituationTag(ShopSituationTag shopSituationTag) {
		this.shopSituationTag = shopSituationTag;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getStId() {
		return stId;
	}
	public void setStId(String stId) {
		this.stId = stId;
	}
	public void setShopSituationTagService(
			IShopSituationTagService shopSituationTagService) {
		this.shopSituationTagService = shopSituationTagService;
	}
}
