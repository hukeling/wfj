package shop.front.customer.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import shop.customer.pojo.Customer;
import shop.customer.pojo.CustomerCollectShop;
import shop.customer.pojo.EvaluateGoods;
import shop.customer.service.ICustomerCollectShopService;
import shop.customer.service.IEvaluateGoodsService;
import shop.store.pojo.ShopInfo;
import shop.store.service.IShopInfoService;
import util.action.BaseAction;
import util.pojo.PageHelper;
public class AccountFollowingShopAction extends BaseAction {
	//private IShopInfoService shopInfoService;
	private ICustomerCollectShopService customerCollectShopService;
	private IShopInfoService shopInfoService;//店铺信息Service
	private String credit; //信誉排序
	private String shopInfoId;//店铺ID
	@SuppressWarnings("unchecked")
	private List<Map> shopList; //收藏店铺列表
	private String followingShopIds;
	/** 评价Service **/
	private IEvaluateGoodsService evaluateGoodsService;
	//分页
	private static final long serialVersionUID = 1L;
	/**
	 * 店铺收藏列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String followingShopList(){
		int userId = ((Customer) session.getAttribute("customer")).getCustomerId();
		String hql = "SELECT u.loginName as loginName,u.customerId as customerId, c.customerCollectShopId as customerCollectShopId,s.shopInfoId as shopInfoId,s.logoUrl as logoUrl,s.shopName as shopName FROM CustomerCollectShop c ,ShopInfo s ,Customer u where c.shopInfoId = s.shopInfoId and s.customerId = u.customerId and c.customerId =" + userId;
		String hql_count = "SELECT count(c.customerCollectShopId) FROM CustomerCollectShop c ,ShopInfo s ,Customer u where c.shopInfoId = s.shopInfoId and s.customerId = u.customerId and c.customerId =" + userId;
		//总条数
		int count =customerCollectShopService.getMultilistCount(hql_count);
		pageHelper.setPageInfo(pageSize, count, currentPage);
		shopList = customerCollectShopService.findListMapPage(hql, pageHelper);
		if(shopList!=null&&shopList.size()>0){
			//解开shopList并加入店铺的积分等级
			for(Map map : shopList){
				//获取店铺ID并且取得积分等级
				Integer spId = (Integer) map.get("shopInfoId");
				//根据店铺的ID查询出所有的商品并计算该店铺的积分等级
				List<EvaluateGoods> evaluateGoodsList = evaluateGoodsService.findObjects(" where o.shopInfoId = "+spId);
				Integer sum = 0;
				//判断查询出来的集合是否为空
				if(evaluateGoodsList!=null&&evaluateGoodsList.size()>0){
					//定义一个Integer用来保存好评的数量
					Integer dividend = 0;
					sum = evaluateGoodsList.size();
					for(EvaluateGoods evaluateGoods : evaluateGoodsList){
						Integer level = evaluateGoods.getLevel();
						if(level==1){
							dividend++;
						}
					}
					//计算好评百分比
					Integer grade = (dividend*100)/sum;
					//给前台判定图片数量
					Integer photoNum = 5;
					if(grade<=20){
						photoNum = 0;
					}
					if(grade>20&&grade<=40){
						photoNum = 1;
					}
					if(grade>40&&grade<=60){
						photoNum = 2;
					}
					if(grade>60&&grade<=80){
						photoNum = 3;
					}
					if(grade>80){
						photoNum = 4;
					}
					//在MAP中添加店铺评分数据
					map.put("photoNum",photoNum);
				}
			}
		}
 		return SUCCESS;
	}
	/**
	 * 删除选中店铺收藏
	 * @throws IOException
	 */
	public void deleteFollowingShop() throws IOException{
		Customer customer = (Customer) session.getAttribute("customer");
		if(followingShopIds!=null){
			boolean rs= false;
			try {
				rs = customerCollectShopService.deleteObjectByParams(" where o.customerCollectShopId in ("+followingShopIds+") and o.customerId="+customer.getCustomerId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			response.getWriter().print(String.format("{\"success\":%s}", rs));
		}
	}
	/**
	 * 用户收藏店铺
	 * @throws IOException 
	 */
	public void customerFollowingShop() throws IOException{
		Customer customer =  (Customer) session.getAttribute("customer");
		String state = "";
		if(null!=customer){
			Object obj = customerCollectShopService.getObjectByParams("where o.customerId = "+customer.getCustomerId()+" and o.shopInfoId = "+shopInfoId);
			//判定是否需要添加收藏，并实行操作
			if(obj==null){
				CustomerCollectShop customerCollectShop = new CustomerCollectShop();
				ShopInfo shopInfo = (ShopInfo) shopInfoService.getObjectByParams(" where o.shopInfoId='"+shopInfoId+"'");
				customerCollectShop.setCustomerId(customer.getCustomerId());
				customerCollectShop.setShopInfoId(Integer.valueOf(shopInfoId));
				customerCollectShop.setShopName(shopInfo.getShopName());
				customerCollectShopService.saveOrUpdateObject(customerCollectShop);
				state = "on";
			}else{
				state = "off";
			}
		}
		JSONObject jo = new JSONObject();
		jo.accumulate("state", state);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw=response.getWriter();
		pw.print(jo.toString());
		pw.flush();
		pw.close();
	}
	public String getCredit() {
		return credit;
	}
	public void setCredit(String credit) {
		this.credit = credit;
	}
	public void setCustomerCollectShopService(
			ICustomerCollectShopService customerCollectShopService) {
		this.customerCollectShopService = customerCollectShopService;
	}
	@SuppressWarnings("unchecked")
	public List<Map> getShopList() {
		return shopList;
	}
	@SuppressWarnings("unchecked")
	public void setShopList(List<Map> shopList) {
		this.shopList = shopList;
	}
	public String getFollowingShopIds() {
		return followingShopIds;
	}
	public void setFollowingShopIds(String followingShopIds) {
		this.followingShopIds = followingShopIds;
	}
	public PageHelper getPageHelper() {
		return pageHelper;
	}
	public void setPageHelper(PageHelper pageHelper) {
		this.pageHelper = pageHelper;
	}
	public String getShopInfoId() {
		return shopInfoId;
	}
	public void setShopInfoId(String shopInfoId) {
		this.shopInfoId = shopInfoId;
	}
	public void setShopInfoService(IShopInfoService shopInfoService) {
		this.shopInfoService = shopInfoService;
	}
	public void setEvaluateGoodsService(IEvaluateGoodsService evaluateGoodsService) {
		this.evaluateGoodsService = evaluateGoodsService;
	}
}
