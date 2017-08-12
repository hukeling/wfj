package shop.common.action;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import shop.common.pojo.ShopSeo;
import shop.common.service.IShopSeoService;
import shop.common.service.imp.ShopSeoService;
import util.action.BaseAction;
/**
 * 商城SEOAction类
 * @author ss
 *
 */
@SuppressWarnings("serial")
public class ShopSeoAction extends BaseAction {
	private IShopSeoService shopSeoService;
	private ShopSeo shopSeo;
	private List<ShopSeo> shopSeoList = new ArrayList<ShopSeo>();
	private String ids;
	private String id;
	//跳转到商城SEO列表信息页
	public String gotoShopSeoPage() throws Exception {
		return SUCCESS;
	}
	//保存或者修改信息
	public void saveOrUpdateShopSeo() throws Exception {
		shopSeo=(ShopSeo)shopSeoService.saveOrUpdateObject(shopSeo);
		if(shopSeo.getShopSeoId()!=null){
			JSONObject jo = new JSONObject();
			jo.accumulate("isSuccess", "true");
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		}
	}
	//得到一条信息
	public void getShopseoObject() throws Exception {
		shopSeo = (ShopSeo) shopSeoService.getObjectById(id);
		JSONObject jo = JSONObject.fromObject(shopSeo);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//删除信息
	public void deleteShopseo() throws Exception {
		Boolean isSuccess = shopSeoService.deleteObjectsByIds("shopSeoId",ids);
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//得到所有信息
	public void listShopSeo() throws Exception {
		int totalRecordCount = shopSeoService.getCount(" order by o.shopSeoId ");
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		shopSeoList = shopSeoService.findListByPageHelper(null,pageHelper, " order by o.shopSeoId desc");
		Map<String, Object> jsonMap = new HashMap<String, Object>();// 定义map
		jsonMap.put("total", totalRecordCount);// total键 存放总记录数，必须的
		jsonMap.put("rows", shopSeoList);// rows键 存放每页记录 list
		JSONObject jo = JSONObject.fromObject(jsonMap);// 格式化result
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	public ShopSeo getShopSeo() {
		return shopSeo;
	}
	public void setShopSeo(ShopSeo shopSeo) {
		this.shopSeo = shopSeo;
	}
	public void setShopSeoService(ShopSeoService shopSeoService) {
		this.shopSeoService = shopSeoService;
	}
	public List<ShopSeo> getShopSeoList() {
		return shopSeoList;
	}
	public void setShopSeoList(List<ShopSeo> shopSeoList) {
		this.shopSeoList = shopSeoList;
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
	public IShopSeoService getShopSeoService() {
		return shopSeoService;
	}
	public void setShopSeoService(IShopSeoService shopSeoService) {
		this.shopSeoService = shopSeoService;
	}
}
