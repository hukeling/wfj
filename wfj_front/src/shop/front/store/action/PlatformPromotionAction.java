package shop.front.store.action;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import promotion.pojo.PlatformPromotion;
import promotion.service.IPlatformPromotionService;
import promotion.service.IStoreApplyPromotionService;
import shop.customer.pojo.Customer;
import shop.product.pojo.ProductType;
import shop.product.service.IProductTypeService;
import shop.store.pojo.ShopInfo;
import shop.store.service.IShopInfoService;
import util.action.BaseAction;
import util.pojo.PageHelper;
/** 
* PlatformPromotionAction - 前台促销活动Action类 
* ============================================================================ 
* 版权所有 2010-2013 XXXX软件有限公司，并保留所有权利。 
* 官方网站：http://www.shopjsp.com
* ============================================================================ 
* @author 孟琦瑞
*/ 
public class PlatformPromotionAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private IPlatformPromotionService platformPromotionService;//促销活动service
	private IStoreApplyPromotionService storeApplyPromotionService;//店铺申请促销service
	private IProductTypeService productTypeService;//商品分类service
	private IShopInfoService shopInfoService;//店铺service
	private List<PlatformPromotion> platformPromotionList = new ArrayList<PlatformPromotion>();//促销活动List
	@SuppressWarnings("unchecked")
	private List<Map> storeApplyPromotionList = new ArrayList<Map>();//参加促销的商品list
	@SuppressWarnings("unchecked")
	private List<Map> noHasProductList = new ArrayList<Map>();//没有参加促销的商品list
	private String shopInfoId;//店铺id
	private String promotionId;//促销活动id
	private String pageIndex="1";//参加促销商品的当前页
	private String pageIndex2="1";//没有参加促销商品的当前页
	private PageHelper pageHelper2=new PageHelper();//没有参加促销商品的分页对象
	private Integer pageSize=10;//分页-每一页显示的个数
	//促销活动列表
	public String gotoPlatformPromotionListPage(){
		Date nowDate = new Date();//创建当前时间  用于比较促销活动是否过期
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = fm.format(nowDate);//格式化后的时间
		int totalRecordCount=platformPromotionService.getCount("  where o.isPass=1 and UNIX_TIMESTAMP(o.endTime) > UNIX_TIMESTAMP('"+format+"')  order by o.createTime ");
		pageHelper.setPageInfo(pageSize, totalRecordCount,currentPage);
		platformPromotionList=platformPromotionService.findListByPageHelper(null, pageHelper, " where o.isPass=1 and UNIX_TIMESTAMP(o.endTime) > UNIX_TIMESTAMP('"+format+"')  order by o.createTime ");
		return SUCCESS;
	}
	//店铺申请参加促销活动
	@SuppressWarnings("unchecked")
	public String gotoStoreApplyPromotionListPage(){
		Customer customer = (Customer) session.getAttribute("customer");
		if(null!=customer){
			ShopInfo si=(ShopInfo) shopInfoService.getObjectByParams(" where o.customerId="+customer.getCustomerId());	
			if(null !=si){//判断店铺是否存在
				shopInfoId =String.valueOf(si.getShopInfoId());//用于查找该店铺的商品
				String hql="SELECT c.productName as productName,c.productTypeId as productTypeId,f.promotionState as promotionState,c.logoImg as logoImg  FROM ShopInfo a,ProductInfo c,StoreApplyPromotion f WHERE a.shopInfoId="+shopInfoId+" AND a.shopInfoId=c.shopInfoId  AND c.isPutSale=2 and c.isPass=1 AND  c.productId=f.productId and f.promotionId="+promotionId+" AND f.promotionState IN (0,1,3) ";
				List<Map> totalList=storeApplyPromotionService.findListMapByHql(hql);
				int totalRecordCount=totalList.size();
				pageHelper.setPageInfo(pageSize, totalRecordCount,currentPage);
				List<Map> list = storeApplyPromotionService.findListMapPage(hql, pageHelper);
					for(Map<String, Object> map:list){
						String sortName="";//分类名称
						ProductType pt =(ProductType) productTypeService.getObjectById(map.get("productTypeId").toString());			
						sortName+=pt.getSortName();
						map.put("sortName", sortName);
						storeApplyPromotionList.add(map);
					}
					//得到没有参加该活动的商品
					String hql2="SELECT c.productId as productId, c.productName as productName,c.logoImg as logoImg  FROM ShopInfo a,ProductInfo c WHERE a.shopInfoId="+shopInfoId+" AND a.shopInfoId=c.shopInfoId   AND  c.isPutSale=2 and c.isPass=1 and c.productId  not in (SELECT f.productId from StoreApplyPromotion f where f.shopInfoId="+shopInfoId+" and f.promotionId="+promotionId+" and f.promotionState in (0,1,3) )";
					List<Map> totalList2=storeApplyPromotionService.findListMapByHql(hql2);
					int totalRecordCount2=totalList2.size();
					pageHelper2.setPageInfo(pageSize, totalRecordCount2, Integer.parseInt(pageIndex2));
					noHasProductList=storeApplyPromotionService.findListMapPage(hql2, pageHelper2);
			}
		}
		return SUCCESS;
	}
	public void setPlatformPromotionService(
			IPlatformPromotionService platformPromotionService) {
		this.platformPromotionService = platformPromotionService;
	}
	public List<PlatformPromotion> getPlatformPromotionList() {
		return platformPromotionList;
	}
	public void setPlatformPromotionList(
			List<PlatformPromotion> platformPromotionList) {
		this.platformPromotionList = platformPromotionList;
	}
	public String getShopInfoId() {
		return shopInfoId;
	}
	public void setShopInfoId(String shopInfoId) {
		this.shopInfoId = shopInfoId;
	}
	public String getPromotionId() {
		return promotionId;
	}
	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}
	public void setStoreApplyPromotionService(
			IStoreApplyPromotionService storeApplyPromotionService) {
		this.storeApplyPromotionService = storeApplyPromotionService;
	}
	public void setProductTypeService(IProductTypeService productTypeService) {
		this.productTypeService = productTypeService;
	}
	@SuppressWarnings("unchecked")
	public void setStoreApplyPromotionList(List<Map> storeApplyPromotionList) {
		this.storeApplyPromotionList = storeApplyPromotionList;
	}
	@SuppressWarnings("unchecked")
	public List<Map> getStoreApplyPromotionList() {
		return storeApplyPromotionList;
	}
	@SuppressWarnings("unchecked")
	public List<Map> getNoHasProductList() {
		return noHasProductList;
	}
	@SuppressWarnings("unchecked")
	public void setNoHasProductList(List<Map> noHasProductList) {
		this.noHasProductList = noHasProductList;
	}
	public String getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getPageIndex2() {
		return pageIndex2;
	}
	public void setPageIndex2(String pageIndex2) {
		this.pageIndex2 = pageIndex2;
	}
	public PageHelper getPageHelper2() {
		return pageHelper2;
	}
	public void setPageHelper2(PageHelper pageHelper2) {
		this.pageHelper2 = pageHelper2;
	}
	public void setShopInfoService(IShopInfoService shopInfoService) {
		this.shopInfoService = shopInfoService;
	}
}
