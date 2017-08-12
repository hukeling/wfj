package shop.front.store.action;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import shop.customer.pojo.EvaluateGoods;
import shop.customer.service.IEvaluateGoodsService;
import shop.product.pojo.Brand;
import shop.product.service.IBrandService;
import shop.product.service.IProductInfoService;
import shop.store.pojo.ShopBrand;
import shop.store.pojo.ShopInfo;
import shop.store.pojo.ShopProCategory;
import shop.store.service.IShopBrandService;
import shop.store.service.IShopInfoService;
import shop.store.service.IShopProCategoryService;
import util.action.BaseAction;
import discountcoupon.pojo.DiscountCoupon;
import discountcoupon.service.IDiscountCouponService;
/** 
 * ShopHomePageAction - 店铺首页Action类 
 * @author 孟琦瑞
 */
@SuppressWarnings("unused")
public class ShopHomePageAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	private IShopInfoService shopInfoService;//店铺service
	private IProductInfoService productInfoService;//商品service
	private IShopBrandService shopBrandService;//店铺品牌关联表service
	private IDiscountCouponService discountCouponService;//优惠券service
	private IBrandService brandService;//品牌service
	private IShopProCategoryService shopProCategoryService ;//店铺内部分类service
	private List<DiscountCoupon> discountCouponList=new ArrayList<DiscountCoupon>();//优惠券list
	@SuppressWarnings("unchecked")
	private List<Map> productList=new ArrayList<Map>();//商品list 
	private List<ShopProCategory> productTypeList=new ArrayList<ShopProCategory>();//店铺内部分类list
	private String shopInfoId;//店铺的id
	private String brandId;//品牌id
	private ShopInfo shopInfo=new ShopInfo();//店铺对象
	private Brand brand=new Brand();//品牌对象
	private String[] tagSplit;//店铺tag数组
	private Integer pageSize=15;//每页显示商品个数
	private Integer pageIndex=1;//当前页
	/**店铺商品内部分类id**/
	private String shopProCategoryId;
	/** 评价Service **/
	private IEvaluateGoodsService evaluateGoodsService;
	/** 评价总数 **/
	private Integer sum;
	/** 积分等级和百分比 **/
	private Integer grade;
	/** 图片循环次数  **/
	private Integer photoNum = 5;
public String gotoShopHomePage(){
	if(brandId!=null&& !"".equals(brandId)){
		//根据brandId查询出店铺ID
		ShopBrand shopBrandOfUser = (ShopBrand) shopBrandService.getObjectByParams(" where o.brandId = "+brandId);
		if(shopBrandOfUser!=null){
			Integer idOfShop = shopBrandOfUser.getShopInfoId();
			//根据店铺的ID查询出所有的商品并计算该店铺的积分等级
			List<EvaluateGoods> evaluateGoodsList = evaluateGoodsService.findObjects(" where o.shopInfoId = "+idOfShop);
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
				grade = (dividend*100)/sum;
				//给前台判定图片数量
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
			}
		}
		//根据brandid查询品牌的相关信息
		brand =(Brand) brandService.getObjectById(brandId);
		ShopBrand shopBrand = (ShopBrand) shopBrandService.getObjectByParams(" where o.brandId="+brandId);
		if(shopBrand!=null){
			shopInfoId = String.valueOf(shopBrand.getShopInfoId());
			shopInfo=(ShopInfo) shopInfoService.getObjectById(shopInfoId);
//			//解开店铺的tag
//			String tag = shopInfo.getTag();
//			if(tag!=null&&!"".equals(tag)){
//				tagSplit = tag.split(",");
//			}
			//查询店铺中的优惠券
//			PageHelper ph=new PageHelper();
//			ph.setPageSize(4);
//			discountCouponList=discountCouponService.findListByPageHelper(null, ph, " where o.shopInfoId="+shopInfoId);
			//查询店铺中的商品
			String hql;
			String countHql;
				if(StringUtils.isEmpty(shopProCategoryId)){//点击所有(all)
					hql="SELECT b.productId as productId, b.productName as productName, b.describle as describle ,b.salesPrice as salesPrice ,b.logoImg as logoImg FROM ShopInfo a,ProductInfo b  WHERE  a.shopInfoId=b.shopInfoId and b.isPass=1 and b.isPutSale=2 and b.isShow=1 AND a.shopInfoId="+shopInfoId;
					countHql="SELECT COUNT(a.shopInfoId)  FROM ShopInfo a,ProductInfo b ,ShopProCateClass c WHERE  b.isPass=1 and b.isPutSale=2 and  a.shopInfoId=b.shopInfoId  and b.productId = c.productId AND a.shopInfoId="+shopInfoId;
				}else{//点击店铺内部商品分类
					hql="SELECT b.productId as productId, b.productName as productName, b.describle as describle ,b.salesPrice as salesPrice ,b.logoImg as logoImg FROM ShopInfo a,ProductInfo b ,ShopProCateClass c  WHERE a.shopInfoId=b.shopInfoId and b.isPass=1 and b.isPutSale=2 and b.isShow=1 and b.productId = c.productId and c.shopProCategoryId="+shopProCategoryId+" and  a.shopInfoId="+shopInfoId;
					countHql="SELECT COUNT(a.shopInfoId)  FROM ShopInfo a,ProductInfo b ,ShopProCateClass c WHERE  b.isPass=1 and b.isPutSale=2 and  a.shopInfoId=b.shopInfoId  and b.productId = c.productId and c.shopProCategoryId="+shopProCategoryId+" AND a.shopInfoId="+shopInfoId;
				}
				int totalRecordCount=productInfoService.getMultilistCount(countHql);
				pageHelper.setPageInfo(10, totalRecordCount, currentPage);
				productList=productInfoService.findListMapPage(hql, pageHelper);//店铺商品
			//查询一级分类
			productTypeList=shopProCategoryService.findObjects(" where o.parentId=1 and o.shopInfoId="+shopInfoId+" order by o.sortCode");
		}
	}
	return SUCCESS;
}
public String index() throws IOException{
	if(shopInfoId != null){
		List<EvaluateGoods> evaluateGoodsList = evaluateGoodsService.findObjects(" where o.shopInfoId = "+shopInfoId);
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
			grade = (dividend*100)/sum;
			//给前台判定图片数量
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
		}
		ShopBrand brand = (ShopBrand) shopBrandService.getObjectByParams(" where shopInfoId = " + shopInfoId);
		if(brand != null){
			brandId = brand.getBrandId()+"";
			return gotoShopHomePage();
		}
	}
	response.getWriter().print("shop not found!");
	return null;
}
/**
 * setter getter 
 * @return
 */
public List<DiscountCoupon> getDiscountCouponList() {
	return discountCouponList;
}
public void setDiscountCouponList(List<DiscountCoupon> discountCouponList) {
	this.discountCouponList = discountCouponList;
}
@SuppressWarnings("unchecked")
public List<Map> getProductList() {
	return productList;
}
@SuppressWarnings("unchecked")
public void setProductList(List<Map> productList) {
	this.productList = productList;
}
public String getShopInfoId() {
	return shopInfoId;
}
public void setShopInfoId(String shopInfoId) {
	this.shopInfoId = shopInfoId;
}
public ShopInfo getShopInfo() {
	return shopInfo;
}
public void setShopInfo(ShopInfo shopInfo) {
	this.shopInfo = shopInfo;
}
public Integer getPageSize() {
	return pageSize;
}
public void setPageSize(Integer pageSize) {
	this.pageSize = pageSize;
}
public Integer getPageIndex() {
	return pageIndex;
}
public void setPageIndex(Integer pageIndex) {
	this.pageIndex = pageIndex;
}
public void setShopInfoService(IShopInfoService shopInfoService) {
	this.shopInfoService = shopInfoService;
}
public void setProductInfoService(IProductInfoService productInfoService) {
	this.productInfoService = productInfoService;
}
public void setDiscountCouponService(
		IDiscountCouponService discountCouponService) {
	this.discountCouponService = discountCouponService;
}
public String[] getTagSplit() {
	return tagSplit;
}
public void setTagSplit(String[] tagSplit) {
	this.tagSplit = tagSplit;
}
public String getBrandId() {
	return brandId;
}
public void setBrandId(String brandId) {
	this.brandId = brandId;
}
public Brand getBrand() {
	return brand;
}
public void setBrand(Brand brand) {
	this.brand = brand;
}
public void setBrandService(IBrandService brandService) {
	this.brandService = brandService;
}
public void setShopBrandService(IShopBrandService shopBrandService) {
	this.shopBrandService = shopBrandService;
}
public IShopProCategoryService getShopProCategoryService() {
	return shopProCategoryService;
}
public void setShopProCategoryService(
		IShopProCategoryService shopProCategoryService) {
	this.shopProCategoryService = shopProCategoryService;
}
public List<ShopProCategory> getProductTypeList() {
	return productTypeList;
}
public void setProductTypeList(List<ShopProCategory> productTypeList) {
	this.productTypeList = productTypeList;
}
public String getShopProCategoryId() {
	return shopProCategoryId;
}
public void setShopProCategoryId(String shopProCategoryId) {
	this.shopProCategoryId = shopProCategoryId;
}
public void setEvaluateGoodsService(IEvaluateGoodsService evaluateGoodsService) {
	this.evaluateGoodsService = evaluateGoodsService;
}
public Integer getSum() {
	return sum;
}
public void setSum(Integer sum) {
	this.sum = sum;
}
public Integer getGrade() {
	return grade;
}
public void setGrade(Integer grade) {
	this.grade = grade;
}
public Integer getPhotoNum() {
	return photoNum;
}
public void setPhotoNum(Integer photoNum) {
	this.photoNum = photoNum;
}
}
