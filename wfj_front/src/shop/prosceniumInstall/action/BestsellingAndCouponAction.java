package shop.prosceniumInstall.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import shop.product.pojo.Brand;
import shop.product.pojo.ProductInfo;
import shop.product.pojo.ProductType;
import shop.product.service.IBrandService;
import shop.product.service.IProductInfoService;
import shop.product.service.IProductTypeService;
import shop.prosceniumInstall.pojo.BestsellingAndCoupon;
import shop.prosceniumInstall.service.IBestsellingAndCouponService;
import shop.store.pojo.ShopInfo;
import shop.store.service.IShopInfoService;
import util.action.BaseAction;
import util.other.JSONFormatDate;
import util.other.Utils;
import discountcoupon.pojo.DiscountCoupon;
import discountcoupon.service.IDiscountCouponService;
/**
 * BestsellingAndCouponAction - 前台热销和优惠券Action类
 * @author 孟琦瑞
 */
@SuppressWarnings("serial")
public class BestsellingAndCouponAction extends BaseAction{
	private IBestsellingAndCouponService bestsellingAndCouponService;//热销商品优惠券Service
	private IProductTypeService productTypeService;//商品分类Service
	private IShopInfoService shopInfoService;//店铺Service
	private IProductInfoService productInfoService;//商品信息Service
	private IDiscountCouponService discountCouponService;//优惠券Service
	private List<ProductInfo> productInfoList = new ArrayList<ProductInfo>();//商品信息List
	private List<Map> productInfoListMap = new ArrayList<Map>();//商品信息List
	private List<ProductType> productTypeList = new ArrayList<ProductType>();//商品分类List
	private List<ShopInfo> shopInfoList = new ArrayList<ShopInfo>();//店铺List
	private List<DiscountCoupon> discountCouponList = new ArrayList<DiscountCoupon>();//优惠券List
	private List<Map> bestsellingAndCouponList = new ArrayList<Map>();//热销商品优惠券List
	private BestsellingAndCoupon bestsellingAndCoupon = new BestsellingAndCoupon();;//热销商品优惠券对象
	private String type;//类型（1、热销商品；2、优惠券）
	private List<String> sorts;//排序
	private String ids;
	private ProductInfo productInfo=new ProductInfo();
	private DiscountCoupon discountCoupon=new DiscountCoupon();
	private Integer productId;//商品的ID
	private List<String> productInfos;//提交多个商品信息 格式：“productId_productName”
	private List<String> discountCouponInfos;//提交多个优惠券信息 格式：“discountCouponID_discountCouponName”
	private String categoryIds="";
	/**品牌Service**/
	private IBrandService brandService;
	public String gotoRightShowBestshellingAndCouponInfoPage(){
		return SUCCESS;
	}
	public String getBestshellingAndCouponLeftShowType(){
		return SUCCESS;
	}
	//查询商品或优惠券
	public String gotoBestshellingAndCouponListPage() throws IOException{
		//type类型为选择的是哪一种类型的。值为1表示展示商品，2为展示优惠券 , 后继还可以加
//		if(type!=null && !"".equals(type)){
//			if(type.equals("1")){
				productTypeList = productTypeService.findObjects(" where o.parentId not in(0)");
				//productInfoList = productInfoService.findObjects(null);
				return "bestshelling";
//			}else if(type.equals("2")){
//				//查询所有优惠券
//				shopInfoList = shopInfoService.findObjects("order by o.shopInfoId");
//				discountCouponList = discountCouponService.findObjects(null);
//				return "coupon";
//			}
//		}
//		return null;
	}
	//查询热销商品优惠券表
	public void listRightShowBestshellingAndCouponInfo() throws IOException{
		if(type!=null&&!"".equals(type)){
			String sql="";
			String sqlCount="";
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			if(type.equals("1")){//查询此表中的商品
				sql="SELECT a.showId as showId, a.bestsellingAndCouponId as bestsellingAndCouponId , a.isShow as isShow , a.sortCode as sortCode , b.productName as productName,b.productFullName as productFullName FROM BestsellingAndCoupon a ,ProductInfo b where a.showId=b.productId and a.type="+type+" order by a.sortCode asc";
				sqlCount="SELECT count(a.bestsellingAndCouponId) FROM BestsellingAndCoupon a ,ProductInfo b where a.showId=b.productId and a.type="+type+" order by a.showId desc";
			}else if(type.equals("2")){//查询此表中的优惠券
				sql="SELECT a.showId as showId, a.bestsellingAndCouponId as bestsellingAndCouponId , a.isShow as isShow , a.sortCode as sortCode , b.discountCouponName as discountCouponName FROM BestsellingAndCoupon a ,DiscountCoupon b where a.showId=b.discountCouponID and a.type="+type+" order by a.sortCode asc";
				sqlCount="SELECT count(a.bestsellingAndCouponId) FROM BestsellingAndCoupon a ,DiscountCoupon b where a.showId=b.discountCouponID and a.type="+type+" order by a.showId desc";
			}
			int totalRecordCount = bestsellingAndCouponService.getMultilistCount(sqlCount);
			pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
			bestsellingAndCouponList = bestsellingAndCouponService.findListMapPage(sql, pageHelper);
			jsonMap.put("total", totalRecordCount);
			jsonMap.put("rows", bestsellingAndCouponList);
			JSONObject jo = JSONObject.fromObject(jsonMap);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		}
	}
	//根据条件查询商品信息
		public void listProductInfo() throws IOException{
			String productTypeId = request.getParameter("ptId");
			String hql = "";
			if("-1".equals(productTypeId)){
				hql = " select a.productId as productId,a.productName as productName ,a.productFullName as productFullName from ProductInfo a where 1=1 ";
			}else{
				if(Utils.objectIsNotEmpty(productTypeId)){
					getAllProductTypeId(Integer.parseInt(productTypeId));
					categoryIds=categoryIds.substring(0, categoryIds.lastIndexOf(","));
					hql = "select a.productId as productId,a.productName as productName from ProductInfo a where a.productTypeId in ("+categoryIds+") ";
				}
			}
			hql+=" and a.isPutSale=2 and a.isShow=1 and a.isPass=1 order by a.productId";
			productInfoListMap = productInfoService.findListMapByHql(hql);
			JSONArray jo = JSONArray.fromObject(productInfoListMap);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		}
		/**
		 * 用递归方式获得子类分类的Id
		 */
		public String getAllProductTypeId(Integer productTypeId) {
			categoryIds += productTypeId.toString()+",";
			List<ProductType> productTypeList = productTypeService.findObjects(" where o.parentId = "+productTypeId);
			if(productTypeList != null && productTypeList.size()>0){
				for(ProductType pt : productTypeList){
					getAllProductTypeId(pt.getProductTypeId());
				}
			}
			return categoryIds;
		}
		//根据查询条件获取优惠券信息
		public void listDiscountCouponInfo() throws IOException{
			String shopId = request.getParameter("shopId");
			String hql = "";
			if("-1".equals(shopId)){
				hql = " order by o.discountCouponID";
				discountCouponList = discountCouponService.findObjects(hql);
			}else{
				discountCouponList = discountCouponService.findObjects(" where o.shopInfoId="+shopId);
			}
			JSONArray jo = JSONArray.fromObject(discountCouponList);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		}
	//保存展示信息
		public String saveOrUpdateRightShowBestshellingAndCouponInfo(){
			List <String> list=new ArrayList<String>();
			if(sorts!=null && sorts.size()>0){
				for(String sort:sorts){//去掉数组中空字符串值
					if(sort!=null&&!"".equals(sort))
					list.add(sort);
				}
				if("1".equals(type)){//商品
					if(productInfos!=null && productInfos.size()>0){
						for(int i=0;i<list.size();i++){//批量添加
							BestsellingAndCoupon ba=new BestsellingAndCoupon();
							ba.setType(Integer.parseInt(type));
							String [] mbis=productInfos.get(i).split("_");
							ba.setShowId(Integer.parseInt(mbis[0]));
							ba.setIsShow(Integer.parseInt("1"));
							ba.setSortCode(Integer.parseInt(list.get(i)));
							bestsellingAndCouponService.saveOrUpdateObject(ba);
						}
					}
					return "bestshelling";
				}else if("2".equals(type)){//优惠券
					if(discountCouponInfos!=null && discountCouponInfos.size()>0){
						for(int i=0;i<list.size();i++){//批量添加
							BestsellingAndCoupon ba=new BestsellingAndCoupon();
							ba.setType(Integer.parseInt(type));
							String [] mbis=discountCouponInfos.get(i).split("_");
							ba.setShowId(Integer.parseInt(mbis[0]));
							ba.setIsShow(Integer.parseInt("1"));
							ba.setSortCode(Integer.parseInt(list.get(i)));
							bestsellingAndCouponService.saveOrUpdateObject(ba);
						}
					}
					return "coupon";
				}
			}
			return null;
		}
		//删除产品展示信息
		public void deleteRightShowBestshellingInfo(){
			bestsellingAndCouponService.deleteObjectsByIds("bestsellingAndCouponId", ids);
		}
		//得到一条数据用于商品详情
		public void getProductInfoObject() throws IOException{
			if(null!=productId){
				productInfo=(ProductInfo)productInfoService.getObjectById(String.valueOf(productId));
				if(null!=productInfo){
					Brand brand = (Brand) brandService.getObjectByParams(" where o.brandId="+productInfo.getBrandId());
					if(null!=brand){
						ShopInfo shopInfo = (ShopInfo) shopInfoService.getObjectByParams(" where o.shopInfoId="+productInfo.getShopInfoId());
						Map<String,Object> map = new HashMap<String,Object>();
						map.put("productInfo", productInfo);
						map.put("brand", brand);
						map.put("shopInfo", shopInfo);
						JsonConfig jsonConfig = new JsonConfig();
						jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd"));
						JSONObject jo = JSONObject.fromObject(map,jsonConfig);
						response.setContentType("text/html;charset=utf-8");
						PrintWriter out = response.getWriter();
						out.print(jo.toString());
						out.flush();
						out.close();
					}
				}
			}
		}
		//得到一条数据用于优惠券详情
		public void getDiscountCouponById() throws IOException{
			if(ids != null){
				discountCoupon=(DiscountCoupon)discountCouponService.getObjectById(ids);
				if(discountCoupon.getDiscountCouponID() != null){
					JsonConfig jsonConfig = new JsonConfig();
					jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd"));
					JSONObject jo = JSONObject.fromObject(discountCoupon,jsonConfig);
					response.setContentType("text/html;charset=utf-8");
					PrintWriter out = response.getWriter();
					out.print(jo.toString());
					out.flush();
					out.close();
				}
			}
		}
		//获得一个对象
		public void getOneObj() throws IOException{
			bestsellingAndCoupon =(BestsellingAndCoupon) bestsellingAndCouponService.getObjectById(ids);
			JSONObject jo = JSONObject.fromObject(bestsellingAndCoupon);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		}
		//修改
		public void updateIsShow() throws IOException{
			if(bestsellingAndCoupon!=null&&bestsellingAndCoupon.getBestsellingAndCouponId()!=null){
				bestsellingAndCoupon=(BestsellingAndCoupon)bestsellingAndCouponService.saveOrUpdateObject(bestsellingAndCoupon);
				if(bestsellingAndCoupon.getBestsellingAndCouponId()!=null){
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
	/**
	 * setter getter
	 * @param bestsellingAndCouponService
	 */
	public void setBestsellingAndCouponService(
			IBestsellingAndCouponService bestsellingAndCouponService) {
		this.bestsellingAndCouponService = bestsellingAndCouponService;
	}
	public void setProductTypeService(IProductTypeService productTypeService) {
		this.productTypeService = productTypeService;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<ProductInfo> getProductInfoList() {
		return productInfoList;
	}
	public void setProductInfoList(List<ProductInfo> productInfoList) {
		this.productInfoList = productInfoList;
	}
	public List<ProductType> getProductTypeList() {
		return productTypeList;
	}
	public void setProductTypeList(List<ProductType> productTypeList) {
		this.productTypeList = productTypeList;
	}
	public void setProductInfoService(IProductInfoService productInfoService) {
		this.productInfoService = productInfoService;
	}
	public List<DiscountCoupon> getDiscountCouponList() {
		return discountCouponList;
	}
	public void setDiscountCouponList(List<DiscountCoupon> discountCouponList) {
		this.discountCouponList = discountCouponList;
	}
	public void setDiscountCouponService(
			IDiscountCouponService discountCouponService) {
		this.discountCouponService = discountCouponService;
	}
	public List<String> getSorts() {
		return sorts;
	}
	public void setSorts(List<String> sorts) {
		this.sorts = sorts;
	}
	public List<String> getProductInfos() {
		return productInfos;
	}
	public void setProductInfos(List<String> productInfos) {
		this.productInfos = productInfos;
	}
	public BestsellingAndCoupon getBestsellingAndCoupon() {
		return bestsellingAndCoupon;
	}
	public void setBestsellingAndCoupon(BestsellingAndCoupon bestsellingAndCoupon) {
		this.bestsellingAndCoupon = bestsellingAndCoupon;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public ProductInfo getProductInfo() {
		return productInfo;
	}
	public void setProductInfo(ProductInfo productInfo) {
		this.productInfo = productInfo;
	}
	public void setShopInfoService(IShopInfoService shopInfoService) {
		this.shopInfoService = shopInfoService;
	}
	public List<ShopInfo> getShopInfoList() {
		return shopInfoList;
	}
	public void setShopInfoList(List<ShopInfo> shopInfoList) {
		this.shopInfoList = shopInfoList;
	}
	public List<Map> getBestsellingAndCouponList() {
		return bestsellingAndCouponList;
	}
	public void setBestsellingAndCouponList(List<Map> bestsellingAndCouponList) {
		this.bestsellingAndCouponList = bestsellingAndCouponList;
	}
	public DiscountCoupon getDiscountCoupon() {
		return discountCoupon;
	}
	public void setDiscountCoupon(DiscountCoupon discountCoupon) {
		this.discountCoupon = discountCoupon;
	}
	public List<String> getDiscountCouponInfos() {
		return discountCouponInfos;
	}
	public void setDiscountCouponInfos(List<String> discountCouponInfos) {
		this.discountCouponInfos = discountCouponInfos;
	}
	public String getCategoryIds() {
		return categoryIds;
	}
	public void setCategoryIds(String categoryIds) {
		this.categoryIds = categoryIds;
	}
	public List<Map> getProductInfoListMap() {
		return productInfoListMap;
	}
	public void setProductInfoListMap(List<Map> productInfoListMap) {
		this.productInfoListMap = productInfoListMap;
	}
	public void setBrandService(IBrandService brandService) {
		this.brandService = brandService;
	}
}
