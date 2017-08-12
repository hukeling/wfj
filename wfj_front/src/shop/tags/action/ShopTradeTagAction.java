package shop.tags.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import shop.tags.pojo.ShopTradeTag;
import shop.tags.service.IShopTradeTagService;
import util.action.BaseAction;
import util.other.CreateWhereSQLForSelect;

/**
 * 适合行业标签
 * @author mqr
 */
public class ShopTradeTagAction extends BaseAction {
	private static final long serialVersionUID = 4959961695071766795L;
	/**适合行业标签Service**/
	private IShopTradeTagService shopTradeTagService;
	/**适合行业标签POJO**/
	private ShopTradeTag shopTradeTag;
	private String ids;
	private String ttId;
	
	/** 
	 * 页面跳转 
	 */
	public String gotoShopTradeTagPage(){
		return SUCCESS;
	}
	/**查看所有标签
	 * @throws IOException 
	 */
	public void listShopTradeTag() throws IOException{
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
		hqlsb.append(CreateWhereSQLForSelect.appendOrderBy(" ttId desc"));
		int totalRecordCount = shopTradeTagService.getCount(hqlsb.toString());
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		String [] selectColumns={"ttId","tageName","useState"};
		List<ShopTradeTag> shopTradeTagList = shopTradeTagService.findListByPageHelper(selectColumns,pageHelper, hqlsb.toString());
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", totalRecordCount);
		jsonMap.put("rows", shopTradeTagList);
		JSONObject jo = JSONObject.fromObject(jsonMap);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**保存新的记录**/
	public void savaOrUpdateShopTradeTag() throws Exception{
		JSONObject jo = new JSONObject();
		if(shopTradeTag!=null){
			shopTradeTag = (ShopTradeTag) shopTradeTagService.saveOrUpdateObject(shopTradeTag);
			if(shopTradeTag.getTtId()!=null){
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
	public void getShopTradeTagObject() throws IOException{
		shopTradeTag = (ShopTradeTag) shopTradeTagService.getObjectByParams(" where o.ttId="+ttId);
		JSONObject jo = JSONObject.fromObject(shopTradeTag);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**删除记录**/
	public void deleteShopTradeTag() throws IOException{
		Boolean isSuccess = shopTradeTagService.deleteObjectsByIds("ttId",ids);
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
			Integer count = shopTradeTagService.getCount(" where o.tageName='"+tageName+"'");
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
	public ShopTradeTag getShopTradeTag() {
		return shopTradeTag;
	}
	public void setShopTradeTag(ShopTradeTag shopTradeTag) {
		this.shopTradeTag = shopTradeTag;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getTtId() {
		return ttId;
	}
	public void setTtId(String ttId) {
		this.ttId = ttId;
	}
	public void setShopTradeTagService(IShopTradeTagService shopTradeTagService) {
		this.shopTradeTagService = shopTradeTagService;
	}
	
}
