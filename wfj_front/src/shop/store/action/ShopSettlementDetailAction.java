package shop.store.action;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import shop.store.pojo.ShopSettlementDetail;
import shop.store.service.IShopSettlementDetailService;
import util.action.BaseAction;
import util.other.JSONFormatDate;
import basic.pojo.Users;
/**
 * 店铺申请结算明细表后台action
 * 
 * @author 刘钦楠
 * 
 */
public class ShopSettlementDetailAction extends BaseAction {
	private static final long serialVersionUID = 6799162511139100513L;
	/**
	 * 店铺申请结算明细表service
	 */
	private IShopSettlementDetailService shopSettlementDetailService;
	/**
	 * 集合类
	 */
	private List<ShopSettlementDetail> shopSettlementDetailList;
	/**
	 * 
	 */
	private ShopSettlementDetail shopSettlementDetail;
	/**
	 * 
	 */
	private String id;
	/**************************************************************************/
	/**
	 * 进入后台店铺申请页面
	 */
	public String gotoShopSettlementDetailInfoPage() throws Exception {
		return SUCCESS;
	}
	/**
	 * 查询店铺申请结算列表
	 * 
	 * @throws Exception
	 */
	public void listShopSettlementDetail() throws Exception {
		String condition = " where 1=1 ";
		String shopName = request.getParameter("shopName");
		if (shopName != null && !"".equals(shopName.trim())) {
			condition += " and o.shopName like '%" + shopName.trim() + "%' ";
		}
		String ym = request.getParameter("ym");
		if (ym != null && !"".equals(ym.trim())) {
			condition += " and o.settlementMonth = '" + ym.trim() + "' ";
		}
		String verifier = request.getParameter("verifier");
		if (verifier != null && !"".equals(verifier.trim())) {
			condition += " and o.reviewer like '%" + verifier.trim() + "%' ";
		}
		String reviewStatus = request.getParameter("reviewStatus");
		if (reviewStatus != null && !"".equals(reviewStatus)) {
			condition += " and o.reviewStatus = " + reviewStatus + " ";
		}
		int total = shopSettlementDetailService.getCount(condition);
		pageHelper.setPageInfo(pageSize, total, currentPage);
		shopSettlementDetailList = shopSettlementDetailService
				.findListByPageHelper(null, pageHelper, condition
						+ " order by o.createDate desc ");
		Map<String, Object> jsonMap = new HashMap<String, Object>();// 定义map
		jsonMap.put("total", total);// total键 存放总记录数，必须的
		jsonMap.put("rows", shopSettlementDetailList);// rows键 存放每页记录 list
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate(
				"yyyy-MM-dd HH:mm:ss"));
		JSONObject jo = JSONObject.fromObject(jsonMap, jsonConfig);// 格式化result
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * 查询店铺结算申请详细信息
	 * 
	 * @throws Exception
	 */
	public void getShopSettlementDetailObject() throws Exception {
		shopSettlementDetail = (ShopSettlementDetail) shopSettlementDetailService
				.getObjectById(id);
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate(
				"yyyy-MM-dd HH:mm:ss"));
		jsonMap.put("shopSettlementDetail", shopSettlementDetail);
		JSONObject jo = JSONObject.fromObject(jsonMap, jsonConfig);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * 修改店铺申请结算审核状态
	 * 
	 * @throws Exception
	 */
	public void updateReviewStatus() throws Exception {
		Users users = (Users) request.getSession().getAttribute("users");
		String reviewStatus = request.getParameter("reviewStatus");
		shopSettlementDetail = shopSettlementDetailService.updateReviewStatus(
				id, users, reviewStatus);
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", "true");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/********************************** set get方法 *****************************/
	public void setShopSettlementDetailService(
			IShopSettlementDetailService shopSettlementDetailService) {
		this.shopSettlementDetailService = shopSettlementDetailService;
	}
	public List<ShopSettlementDetail> getShopSettlementDetailList() {
		return shopSettlementDetailList;
	}
	public void setShopSettlementDetailList(
			List<ShopSettlementDetail> shopSettlementDetailList) {
		this.shopSettlementDetailList = shopSettlementDetailList;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public ShopSettlementDetail getShopSettlementDetail() {
		return shopSettlementDetail;
	}
	public void setShopSettlementDetail(
			ShopSettlementDetail shopSettlementDetail) {
		this.shopSettlementDetail = shopSettlementDetail;
	}
}
