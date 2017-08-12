package shop.front.coupon.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import shop.customer.pojo.Customer;
import shop.store.pojo.CustomerHaveCoupon;
import shop.store.pojo.ShopCategory;
import shop.store.service.ICustomerHaveCouponService;
import shop.store.service.IShopCategoryService;
import shop.store.service.IShopInfoService;
import util.action.BaseAction;
import cms.article.pojo.CmsArticle;
import cms.article.service.IArticleService;
import discountcoupon.pojo.DiscountCoupon;
import discountcoupon.service.IDiscountCouponService;
/**
 * CouponAction - 优惠券信息Action
 * @author XHT
 */
@SuppressWarnings("all")
public class CouponAction extends BaseAction{
	private String headerType;
	/** 用户拥有优惠券Service **/
	private ICustomerHaveCouponService customerHaveCouponService;
	/** 优惠劵Service **/
	private IDiscountCouponService discountCouponService;
	/** 店铺分类Service **/
	private IShopCategoryService shopCategoryService;
	/** 店铺Service **/
	private IShopInfoService shopInfoService;
	/** 店铺分类对象 **/
	private ShopCategory shopCategory;
	/** 店铺分类ID **/
	private String shopCategoryId;
	/** 优惠劵分类map集合 **/
	@SuppressWarnings("unchecked")
	private Map<Object,List> map = new LinkedHashMap<Object,List>();
	/** 优惠劵分类map集合 **/
	@SuppressWarnings("unchecked")
	private List<Map> listMap = new ArrayList<Map>();
	/** 优惠券ID **/
	private String discountCouponID;
	/** 文章service **/
	private IArticleService articleService;
	/** 文章集合 **/
	private List<CmsArticle> cmsArticleList = new ArrayList<CmsArticle>();
	/**
	 * 优惠劵过期验证
	 * @throws ParseException 
	 */
	public void checkCoupons() throws ParseException{
		//获取当前时间
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");
		String date = dateformat.format(new Date());
		//取出数据库中会员过期的优惠券并把过期的优惠券状态修改
		List<CustomerHaveCoupon> list = customerHaveCouponService.findObjects(" where UNIX_TIMESTAMP(o.expirationTime) < UNIX_TIMESTAMP('"+date+"')");
		for(CustomerHaveCoupon customerHaveCoupon : list){
			customerHaveCoupon.setState(3);
			customerHaveCouponService.saveOrUpdateObject(customerHaveCoupon);
		}
	}
	/**
	 * 用户获取优惠券
	 */
	public void gotoCustomerHaveCoupon() throws IOException{
		Customer customer =  (Customer) session.getAttribute("customer");
		String state = "";
		if(customer!=null){
			DiscountCoupon discountCoupon = (DiscountCoupon) discountCouponService.getObjectById(discountCouponID);
			CustomerHaveCoupon customerHaveCoupon = new CustomerHaveCoupon();
			//依次塞数据
			customerHaveCoupon.setDiscountCouponID(discountCoupon.getDiscountCouponID());
			customerHaveCoupon.setCustomerId(customer.getCustomerId());//客户ID
			//customerHaveCoupon.setShopInfoId(discountCoupon.getShopInfoId());
			customerHaveCoupon.setDiscountCouponCode(discountCoupon.getDiscountCouponCode());
			customerHaveCoupon.setDiscountCouponName(discountCoupon.getDiscountCouponName());
			customerHaveCoupon.setDiscountCouponAmount(discountCoupon.getDiscountCouponAmount());
			customerHaveCoupon.setDiscountCouponLowAmount(discountCoupon.getDiscountCouponLowAmount());
			customerHaveCoupon.setBeginTime(discountCoupon.getBeginTime());
			customerHaveCoupon.setExpirationTime(discountCoupon.getExpirationTime());
			customerHaveCoupon.setDiscountImage(discountCoupon.getDiscountImage());
			customerHaveCoupon.setCreateTime(new Date());
			customerHaveCoupon.setState(1);
			customerHaveCouponService.saveOrUpdateObject(customerHaveCoupon);
			state = "on";
			JSONObject jo = new JSONObject();
			jo.accumulate("state", state);
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter pw=response.getWriter();
			pw.print(jo.toString());
			pw.flush();
			pw.close();
		}else{
			state = "off";
			JSONObject jo = new JSONObject();
			jo.accumulate("state", state);
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter pw=response.getWriter();
			pw.print(jo.toString());
			pw.flush();
			pw.close();
		}
	}
	/**
	 * 首页跳转优惠券页面
	 */
	@SuppressWarnings("unchecked")
	public String gotoCoupon(){
		//查询提示文章
		cmsArticleList = articleService.findSome(0, 5, " where o.categoryId = 46 order by o.createTime desc");
		//一般放 3-4个
//		for(CmsArticle article : list){
//			cmsArticleList.add(article);
//			if(cmsArticleList.size()==5){
//				break;
//			}
//		}
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");
		String date = dateformat.format(new Date());
		List<ShopCategory> shopCategoryList = shopCategoryService.findObjects("where o.parentId = 1 ");
		//迭代上面的LIST取得所有分类ID下的所有商品的优惠券的所需属性
		for(ShopCategory shopCategory :shopCategoryList){
			String hql = "SELECT o.shopInfoId as shopInfoId, o.shopName as shopName, d.discountCouponID as discountCouponID,d.discountCouponLowAmount as discountCouponLowAmount, d.discountCouponName as discountCouponName, d.discountImage as discountImage, d.expirationTime as expirationTime,d.discountCouponAmount as discountCouponAmount, d.beginTime as beginTime FROM ShopInfo o,DiscountCoupon d WHERE o.shopInfoId=d.shopInfoId and o.shopCategoryId ="+shopCategory.getShopCategoryId()+" and UNIX_TIMESTAMP(d.expirationTime) >= UNIX_TIMESTAMP('"+date+"')) order by d.createTime desc";
			pageHelper.setPageSize(8);
			List<Map> mapList = shopInfoService.findListMapPage(hql, pageHelper);
			if(mapList!=null&&mapList.size()>0){
				map.put(shopCategory, mapList);
			}
		}
		return SUCCESS;
	}
	/**
	 * 优惠劵页面跳转其中分类页面
	 */
	@SuppressWarnings("unchecked")
	public String gotoCouponList(){
		//查询提示文章
		cmsArticleList = articleService.findSome(0, 5, " where o.categoryId = 46 order by o.createTime desc");
		//一般放 3-4个
//		for(CmsArticle article : list){
//			cmsArticleList.add(article);
//			if(cmsArticleList.size()==5){
//				break;
//			}
//		}
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");
		String date = dateformat.format(new Date());
		shopCategory = (ShopCategory) shopCategoryService.getObjectById(shopCategoryId);
		String hql = "SELECT o.shopInfoId as shopInfoId, o.shopName as shopName, d.discountCouponID as discountCouponID,d.discountCouponLowAmount as discountCouponLowAmount, d.discountCouponName as discountCouponName, d.discountImage as discountImage, d.expirationTime as expirationTime,d.discountCouponAmount as discountCouponAmount, d.beginTime as beginTime FROM ShopInfo o,DiscountCoupon d WHERE o.shopInfoId=d.shopInfoId and o.shopCategoryId ="+shopCategoryId+" and UNIX_TIMESTAMP(d.expirationTime) >= UNIX_TIMESTAMP('"+date+"')) order by d.createTime desc";
		listMap = shopInfoService.findListMapByHql(hql);
		List<ShopCategory> shopCategoryList = shopCategoryService.findObjects("where o.parentId = 1 ");
		for(ShopCategory shopCategory :shopCategoryList){
			String hqll = "SELECT o.shopInfoId as shopInfoId, o.shopName as shopName, d.discountCouponID as discountCouponID,d.discountCouponLowAmount as discountCouponLowAmount, d.discountCouponName as discountCouponName, d.discountImage as discountImage, d.expirationTime as expirationTime,d.discountCouponAmount as discountCouponAmount, d.beginTime as beginTime FROM ShopInfo o,DiscountCoupon d WHERE o.shopInfoId=d.shopInfoId and o.shopCategoryId ="+shopCategory.getShopCategoryId()+" and UNIX_TIMESTAMP(d.expirationTime) >= UNIX_TIMESTAMP('"+date+"')) order by d.createTime desc";
			pageHelper.setPageSize(8);
			List<Map> mapList = shopInfoService.findListMapPage(hqll, pageHelper);
			if(mapList!=null&&mapList.size()>0){
				map.put(shopCategory, mapList);
			}
		}
		return SUCCESS;
	}
	public void setDiscountCouponService(
			IDiscountCouponService discountCouponService) {
		this.discountCouponService = discountCouponService;
	}
	public void setShopCategoryService(IShopCategoryService shopCategoryService) {
		this.shopCategoryService = shopCategoryService;
	}
	public void setShopInfoService(IShopInfoService shopInfoService) {
		this.shopInfoService = shopInfoService;
	}
	public ShopCategory getShopCategory() {
		return shopCategory;
	}
	public void setShopCategory(ShopCategory shopCategory) {
		this.shopCategory = shopCategory;
	}
	@SuppressWarnings("unchecked")
	public Map<Object, List> getMap() {
		return map;
	}
	@SuppressWarnings("unchecked")
	public void setMap(Map<Object, List> map) {
		this.map = map;
	}
	public String getShopCategoryId() {
		return shopCategoryId;
	}
	public void setShopCategoryId(String shopCategoryId) {
		this.shopCategoryId = shopCategoryId;
	}
	@SuppressWarnings("unchecked")
	public List<Map> getListMap() {
		return listMap;
	}
	@SuppressWarnings("unchecked")
	public void setListMap(List<Map> listMap) {
		this.listMap = listMap;
	}
	public void setCustomerHaveCouponService(
			ICustomerHaveCouponService customerHaveCouponService) {
		this.customerHaveCouponService = customerHaveCouponService;
	}
	public String getDiscountCouponID() {
		return discountCouponID;
	}
	public void setDiscountCouponID(String discountCouponID) {
		this.discountCouponID = discountCouponID;
	}
	public void setArticleService(IArticleService articleService) {
		this.articleService = articleService;
	}
	public List<CmsArticle> getCmsArticleList() {
		return cmsArticleList;
	}
	public void setCmsArticleList(List<CmsArticle> cmsArticleList) {
		this.cmsArticleList = cmsArticleList;
	}
	public String getHeaderType() {
		return headerType;
	}
	public void setHeaderType(String headerType) {
		this.headerType = headerType;
	}
}
