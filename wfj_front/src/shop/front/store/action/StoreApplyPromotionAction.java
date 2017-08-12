package shop.front.store.action;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import promotion.pojo.StoreApplyPromotion;
import promotion.service.IStoreApplyPromotionService;
import shop.customer.pojo.Customer;
import shop.product.pojo.ProductInfo;
import shop.product.pojo.ProductType;
import shop.product.service.IProductInfoService;
import shop.product.service.IProductTypeService;
import shop.store.pojo.ShopInfo;
import shop.store.service.IShopInfoService;
import util.action.BaseAction;
import util.pojo.PageHelper;
/** 
* StoreApplyPromotionAction - 前台店铺申请参加促销Action类 
* ============================================================================ 
* 版权所有 2010-2013 XXXX软件有限公司，并保留所有权利。 
* 官方网站：http://www.shopjsp.com
* ============================================================================ 
* @author 孟琦瑞
*/ 
@SuppressWarnings("serial")
public class StoreApplyPromotionAction extends BaseAction{
	private IStoreApplyPromotionService storeApplyPromotionService;//店铺申请促销service
	private StoreApplyPromotion storeApplyPromotion = new StoreApplyPromotion();//店铺申请促销对象
	private IProductInfoService productInfoService;//商品信息service
	private IShopInfoService shopInfoService;//店铺信息service
	private IProductTypeService productTypeService;//商品分类service
	private List<Map> storeApplyPromotionList = new ArrayList<Map>();//参加促销的集合
	private List<Map> noHasProductList = new ArrayList<Map>();//没有参加促销的商品集合
	private String shopInfoId;//店铺id
	private String promotionId;//促销活动id
	private ProductInfo productInfo=new ProductInfo();//商品对象
	private ShopInfo shopInfo=new ShopInfo();//店铺对象
	private String pageIndex;//参加促销商品的当前页
	private String pageIndex2;//没有参加促销商品的当前页
	private PageHelper pageHelper2=new PageHelper();//没有参加促销商品的分页对象
	private Integer pageSize=10;//分页-每一页显示的个数
	//批量操作
	public String batchApplication() throws Exception{
		Customer customer = (Customer) session.getAttribute("customer");
		if(null!=customer){
			ShopInfo si=(ShopInfo) shopInfoService.getObjectByParams(" where o.customerId="+customer.getCustomerId());	
			if(null !=si){
				shopInfoId =String.valueOf(si.getShopInfoId());
				String shopName=si.getShopName();
				String[] checkArray = request.getParameterValues("checkbox");
				if(checkArray!=null){
						for(String pid:checkArray){
							productInfo =(ProductInfo) productInfoService.getObjectById(pid);
							storeApplyPromotion=new StoreApplyPromotion();
							storeApplyPromotion.setPromotionId(Integer.parseInt(promotionId));
							storeApplyPromotion.setProductId(Integer.parseInt(pid));
							storeApplyPromotion.setProductFullName(productInfo.getProductName());
							storeApplyPromotion.setShopInfoId(Integer.parseInt(shopInfoId));
							storeApplyPromotion.setShopName(shopName);
							try {
								StoreApplyPromotion sap =(StoreApplyPromotion) storeApplyPromotionService.getObjectByParams("where o.productId="+pid+" and promotionId="+promotionId);
								if(sap!=null){
									storeApplyPromotion.setApplyPromotionId(sap.getApplyPromotionId());
									storeApplyPromotion.setPromotionState(3);
								}else{
									storeApplyPromotion.setPromotionState(0);
								}
								SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改
								Date createTime=dateFormat.parse(dateFormat.format(new Date()));
								storeApplyPromotion.setCreateTime(createTime);
								storeApplyPromotionService.saveOrUpdateObject(storeApplyPromotion);
							} catch (Exception e) {
								e.printStackTrace();
								// TODO: handle exception
							}
						}
				}
				String hql="SELECT c.productName as productName,c.productTypeId as productTypeId,f.promotionState as promotionState,c.logoImg as logoImg  FROM ShopInfo a,ProductInfo c,StoreApplyPromotion f WHERE a.shopInfoId="+shopInfoId+" AND a.shopInfoId=c.shopInfoId AND  c.isPutSale=2 and c.isPass=1 AND  c.productId=f.productId and f.promotionId="+promotionId+" AND f.promotionState IN (0,1,3) ";
				List<Map> totalList=storeApplyPromotionService.findListMapByHql(hql);
				int totalRecordCount=totalList.size();
				pageHelper.setPageInfo(pageSize, totalRecordCount,currentPage);
				List<Map> list = storeApplyPromotionService.findListMapPage(hql, pageHelper);
					for(Map<String, Object> map:list){
						String sortName="";
						ProductType pt =(ProductType) productTypeService.getObjectById(map.get("productTypeId").toString());
//						if(pt.getParentId()==1){//1
//							sortName+=pt.getSortName();
//						}else{//2 or 3
//							ProductType pt2 =(ProductType) productTypeService.getObjectById(pt.getParentId().toString());
//							if(pt2.getParentId()==1){//2
//								sortName+=pt2.getSortName()+">"+pt.getSortName();
//							}else{
//								ProductType pt3 =(ProductType) productTypeService.getObjectById(pt2.getParentId().toString());
//								
//								sortName+=pt3.getSortName()+">"+pt2.getSortName()+">"+pt.getSortName();
//							}
//						}
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
	public void setStoreApplyPromotionService(
			IStoreApplyPromotionService storeApplyPromotionService) {
		this.storeApplyPromotionService = storeApplyPromotionService;
	}
	public void setProductInfoService(IProductInfoService productInfoService) {
		this.productInfoService = productInfoService;
	}
	public ProductInfo getProductInfo() {
		return productInfo;
	}
	public void setProductInfo(ProductInfo productInfo) {
		this.productInfo = productInfo;
	}
	public StoreApplyPromotion getStoreApplyPromotion() {
		return storeApplyPromotion;
	}
	public void setStoreApplyPromotion(StoreApplyPromotion storeApplyPromotion) {
		this.storeApplyPromotion = storeApplyPromotion;
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
	public ShopInfo getShopInfo() {
		return shopInfo;
	}
	public void setShopInfo(ShopInfo shopInfo) {
		this.shopInfo = shopInfo;
	}
	public void setShopInfoService(IShopInfoService shopInfoService) {
		this.shopInfoService = shopInfoService;
	}
	@SuppressWarnings("unchecked")
	public List<Map> getStoreApplyPromotionList() {
		return storeApplyPromotionList;
	}
	@SuppressWarnings("unchecked")
	public void setStoreApplyPromotionList(List<Map> storeApplyPromotionList) {
		this.storeApplyPromotionList = storeApplyPromotionList;
	}
	@SuppressWarnings("unchecked")
	public List<Map> getNoHasProductList() {
		return noHasProductList;
	}
	@SuppressWarnings("unchecked")
	public void setNoHasProductList(List<Map> noHasProductList) {
		this.noHasProductList = noHasProductList;
	}
	public void setProductTypeService(IProductTypeService productTypeService) {
		this.productTypeService = productTypeService;
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
}
