package promotion.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import promotion.pojo.PromotionApply;
import promotion.service.IPromotionApplyService;
import shop.store.pojo.ShopInfo;
import shop.store.service.IShopInfoService;
import util.action.BaseAction;
import util.other.JSONFormatDate;
import basic.pojo.Users;
/**
 * PromotionApplyAction - 店铺促销活动申请Action
 */
@SuppressWarnings("serial")
public class PromotionApplyAction extends BaseAction {
	private IPromotionApplyService promotionApplyService; //店铺促销活动申请 Service
	private List<PromotionApply> promotionApplyList = new ArrayList<PromotionApply>();//店铺促销活动申请信息列表
	private List<ShopInfo> customerList = new ArrayList<ShopInfo>();//客户信息列表
	private PromotionApply promotionApply;//店铺促销活动申请实体类
	private IShopInfoService shopInfoService;//店铺分类Service
	private String ids;
	private ShopInfo shopInfo;//店铺基本信息实体
	//跳转到店铺促销活动申请列表页面
	public String gotoPromotionApplyPage(){
		customerList = shopInfoService.findObjects(null);
		return SUCCESS;
	}
	//查询所有的促销活动申请记录
	public void listPromotionApply() throws IOException {
		int totalRecordCount = promotionApplyService.getCount(" order by o.promotionApplyId");
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		promotionApplyList = promotionApplyService.findObjects(" order by o.promotionApplyId desc");
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", totalRecordCount);
		jsonMap.put("rows", promotionApplyList);
		JsonConfig jsonConfig = new JsonConfig();
	    jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd"));
		JSONObject jo = JSONObject.fromObject(jsonMap,jsonConfig);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//修改店铺
	public void saveOrUpdatePromotionApply() throws IOException, ParseException{
		Date createTime = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formatTime = sdf.format(createTime);
		Date applyTime = sdf.parse(formatTime);
		String customerName = java.net.URLDecoder.decode(request.getParameter("customerNames"), "utf-8");
		if(promotionApply!=null){
			promotionApply.setCustomerName(customerName);
			promotionApply.setApplyTime(applyTime);
			promotionApply.setIsPass(0);
			promotionApply = (PromotionApply) promotionApplyService.saveOrUpdateObject(promotionApply);
			if(promotionApply.getPromotionApplyId()!=null){
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
	//批量删除
	public void deletePromotionApply() throws IOException{
		Boolean isSuccess = promotionApplyService.deleteObjectsByIds("promotionApplyId",ids);
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//获取一条记录
	public void getPromotionApplyById() throws IOException{
		promotionApply = (PromotionApply) promotionApplyService.getObjectByParams(" where o.promotionApplyId='"+ids+"'");
		JsonConfig jsonConfig = new JsonConfig();
	    jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd"));
		JSONObject jo = JSONObject.fromObject(promotionApply,jsonConfig);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//根据用户Id判断该用户是否有店铺
	public void getCheckById() throws IOException{
		ShopInfo shopInfo =  (ShopInfo)shopInfoService.getObjectByParams(" where o.customerId='"+ids+"' and o.isClose = '0'");
		JSONObject jo = JSONObject.fromObject(shopInfo);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//保存并且提交到下一步流程
	public void saveOrNextPromotionApply() throws IOException, ParseException {
		Date createTime = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formatTime = sdf.format(createTime);
		@SuppressWarnings("unused")
		Date promotionPassTime = sdf.parse(formatTime);
		@SuppressWarnings("unused")
		Users users = (Users)request.getSession().getAttribute("users");
		if(promotionApply!=null){
			promotionApply = (PromotionApply) promotionApplyService.saveOrUpdateObject(promotionApply);
			shopInfo = (ShopInfo)shopInfoService.getObjectById(promotionApply.getShopInfoId().toString());
//			shopInfo.setPromotionPassTime(promotionPassTime);
//			shopInfo.setPromotionVerifier(users.getUserName());
//			shopInfo.setIsPromotion(1);
			shopInfo = (ShopInfo) shopInfoService.saveOrUpdateObject(shopInfo);
			if(promotionApply.getPromotionApplyId()!=null){
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
	public void setPromotionApplyService(
			IPromotionApplyService promotionApplyService) {
		this.promotionApplyService = promotionApplyService;
	}
	public List<PromotionApply> getPromotionApplyList() {
		return promotionApplyList;
	}
	public void setPromotionApplyList(List<PromotionApply> promotionApplyList) {
		this.promotionApplyList = promotionApplyList;
	}
	public PromotionApply getPromotionApply() {
		return promotionApply;
	}
	public void setPromotionApply(PromotionApply promotionApply) {
		this.promotionApply = promotionApply;
	}
	public List<ShopInfo> getCustomerList() {
		return customerList;
	}
	public void setCustomerList(List<ShopInfo> customerList) {
		this.customerList = customerList;
	}
	public void setShopInfoService(IShopInfoService shopInfoService) {
		this.shopInfoService = shopInfoService;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public ShopInfo getShopInfo() {
		return shopInfo;
	}
	public void setShopInfo(ShopInfo shopInfo) {
		this.shopInfo = shopInfo;
	}
}
