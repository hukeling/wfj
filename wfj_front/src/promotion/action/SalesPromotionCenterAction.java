package promotion.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import promotion.pojo.PlatformPromotion;
import promotion.service.IPlatformPromotionService;
import promotion.service.IStoreApplyPromotionService;
import util.action.BaseAction;
/** 
 * SalesPromotionCenterAction -  前台促销Action类 
 * @author 孟琦瑞
 */
@SuppressWarnings("all")
public class SalesPromotionCenterAction extends BaseAction {
	private IStoreApplyPromotionService storeApplyPromotionService ;// 促销活动商品service
	private IPlatformPromotionService platformPromotionService ;// 促销活动service
	private String promotionId;
	private String pageIndex;
	//跳转到jsp
	public String gotoSalesPromotionCenter(){
		return SUCCESS;
	}
	//得到促销活动商品
	@SuppressWarnings("unchecked")
	public void getJson() throws IOException{
		int pageSize=5;//每页展示商品个数
		int totalRecordCount = storeApplyPromotionService.getCount(" where o.promotionState=1 and o.promotionId="+promotionId);
		pageHelper.setPageIndex(currentPage);//设置当前页
		pageHelper.setPageSize(pageSize);//设置每页展示商品的个数
		pageHelper.setPageRecordBeginIndex(pageSize*(currentPage-1));//设置当前页第一个元素的索引
		pageHelper.setPageCount(totalRecordCount);
		String hql="select b.productId as productId, b.productName as productName,b.logoImg as logoImg from StoreApplyPromotion a,ProductInfo b  where a.promotionState=1 and a.promotionId="+promotionId+" and a.productId=b.productId ";
		List<Map> findListMapByHql = storeApplyPromotionService.findListMapPage(hql, pageHelper);
		PlatformPromotion platformPromotion = (PlatformPromotion)platformPromotionService.getObjectById(promotionId);
		Map<String, Object> jsonMap = new HashMap<String, Object>();// 定义map
		jsonMap.put("findListMapByHql", findListMapByHql);
		jsonMap.put("platformPromotion", platformPromotion);
		JSONObject jo = JSONObject.fromObject(jsonMap);// 格式化result
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * setter getter
	 * @return
	 */
	public String getPromotionId() {
		return promotionId;
	}
	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}
	public String getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}
	public void setStoreApplyPromotionService(
			IStoreApplyPromotionService storeApplyPromotionService) {
		this.storeApplyPromotionService = storeApplyPromotionService;
	}
	public void setPlatformPromotionService(
			IPlatformPromotionService platformPromotionService) {
		this.platformPromotionService = platformPromotionService;
	}
}
