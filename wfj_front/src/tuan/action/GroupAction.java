package tuan.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import shop.customer.pojo.EvaluateGoods;
import shop.customer.service.IEvaluateGoodsService;
import shop.product.pojo.ProductInfo;
import shop.product.service.IProductInfoService;
import shop.store.pojo.ShopInfo;
import shop.store.service.IShopInfoService;
import tuan.pojo.TuanProduct;
import tuan.pojo.TuanProductType;
import tuan.service.ITuanProductService;
import tuan.service.ITuanProductTypeService;
import util.action.BaseAction;
import util.other.Utils;

@SuppressWarnings("serial")
public class GroupAction extends BaseAction {
	/**
	 * 团购商品分类Service
	 */
	private ITuanProductTypeService tuanProductTypeService;
	/**
	 * 团购商品Service
	 */
	private ITuanProductService tuanProductService;
	/**
	 * 商品Service
	 */
	private IProductInfoService productInfoService;
	/**
	 * 评价Service
	 */
	private IEvaluateGoodsService evaluateGoodsService;
	/**
	 * 店铺Service
	 */
	private IShopInfoService shopInfoService;
	/**
	 * 团购商品分类集合
	 */
	private List<TuanProductType> tuanProductTypeList;
	/**
	 * 团购商品实体类
	 */
	private TuanProduct tuanProduct;
	/**
	 * 店铺实体类
	 */
	private ShopInfo shopInfo;
	/**
	 * 团购商品List
	 */
	@SuppressWarnings("rawtypes")
	private List<Map> tuanProductList = new ArrayList<Map>();
	/**
	 * 团购商品分类ID
	 */
	private Integer tuanProductTypeId;
	/**
	 * 排序
	 */
	private String orderBy;
	/**
	 * 商品ID
	 */
	private String productId;
	/**
	 * 商品实体类
	 */
	private ProductInfo productInfo;
	/** 
	 * 评价总数 
	 */
	private Integer sum;
	/** 
	 * 积分等级和百分比
	 */
	private Integer grade;
	/** 
	 * 图片循环次数  
	 */
	private Integer photoNum = 5;
	/**
	 * 商品属性
	 */
	private JSONArray jaattrList;
	/**
	 * 商品参数
	 */
	private JSONObject joProductPara;
	/**一级导航选中**/
	private String headerType;
	/**团购商品分类**/
	private TuanProductType tuanProductType;
	
	/**
	 * 跳转到团购首页
	 */
	@SuppressWarnings("unchecked")
	public String gotoTuanHome(){
		//查询全部团购商品分类
		String[] selectColumns = {"tuanProductTypeId","sortName"};
		//前端展示团购商品
		tuanProductTypeList = tuanProductTypeService.findObjects(selectColumns," order by o.sortCode asc");
		Date date = new Date();
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = dateformat.format(date);//当前系统时间
		String hql="select o.productId as productId,o.shopInfoId as shopInfoId,o.tuanProductTypeId as tuanProductTypeId,o.price as price,o.bought as bought,s.shopName as shopName,"
				 + "p.productFullName as productFullName,p.logoImg as logoImg from TuanProduct o,ShopInfo s,ProductInfo p where o.productId=p.productId and o.shopInfoId=s.shopInfoId"
				 + " and o.state=1 and p.isPass=1 and p.isPutSale=2 and p.isShow=1 and s.isPass=3 and s.isClose=0 and UNIX_TIMESTAMP(o.endTime)>UNIX_TIMESTAMP('"+currentTime+"')";
		String countHql = " where 1=1";
		//追加条件语句
		if(Utils.objectIsNotEmpty(tuanProductTypeId)){
			hql += " and o.tuanProductTypeId="+tuanProductTypeId;
			countHql += " and o.tuanProductTypeId="+tuanProductTypeId;
		}
		//追加order by 排序参数
		if(orderBy!=null&&"salesPriceAsc".equals(orderBy)){//价钱
			hql+=" order by o.price asc";
		}else if(orderBy!=null&&"totalSales".equals(orderBy)){
			hql+=" order by o.bought desc";
		}else if(orderBy!=null&&"normal".equals(orderBy)){
			hql+=" order by o.createTime desc";
		}
		int totalRecordCount = tuanProductService.getCount(countHql);
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		tuanProductList=tuanProductService.findListMapPage(hql, pageHelper);
		return SUCCESS;
	}
	
	/**
	 * 跳转到团购商品详情页
	 */
	@SuppressWarnings("rawtypes")
	public String productInfoById(){
		//团购商品实体类
		tuanProduct = (TuanProduct) tuanProductService.getObjectByParams(" where o.productId="+productId);
		//查询团购商品分类
		tuanProductType = (TuanProductType) tuanProductTypeService.getObjectByParams(" where o.tuanProductTypeId="+tuanProduct.getTuanProductTypeId());
		//商品实体类
		productInfo = (ProductInfo) productInfoService.getObjectById(productId);
		//Customer customer = (Customer)session.getAttribute("customer");
		try{
			if(productInfo!=null){
				//获取商品所在店铺的星星等级,好评百分比,评价总数
				//根据店铺的ID查询出所有的商品并计算该店铺的积分等级
				List<EvaluateGoods> evaluateGoodsList = evaluateGoodsService.findObjects(" where o.shopInfoId = "+productInfo.getShopInfoId());
				//判断查询出来的集合是否为空
				if(Utils.collectionIsNotEmpty(evaluateGoodsList)){
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
				}else{
					grade=100;
				}
			//取商品的属性
			String sql = "select a.productId as productId, c.attrValueName as attrValueName,d.name as name from ProductInfo a,ProductAttrIndex b,AttributeValue c,ProductAttribute d where a.productId = b.productId and b.productAttrId = d.productAttrId and b.attrValueId = c.attrValueId and a.productId="+productInfo.getProductId();
			List<Map> attrList = productInfoService.findListMapByHql(sql);
			jaattrList = JSONArray.fromObject(attrList);
			//stockUpDate=productInfo.getStockUpDate();	
			//查询店铺详情图片
			shopInfo = (ShopInfo) shopInfoService.getObjectByParams("where o.shopInfoId="+productInfo.getShopInfoId());
			/**查询QQ的sql语句*/
			//String qqSql="select b.trueName as trueName,b.nikeName as nikeName,b.qq as qq from shop_customerservice b ,shop_shop_customerservice c where b.useState=1 and c.customerServiceId=b.customerServiceId and c.customerId="+shopInfo.getCustomerId();
			//查询qq
			//qqList=shopCustomerServiceService.findListMapBySql(qqSql);
			//productImgList = productImgService.findObjects(" where o.productId ="+productInfo.getProductId()+" order by o.orders asc");
			 //获取当前商品的品牌的信息
			 //brand = (Brand) brandService.getObjectByParams(" where o.brandId="+productInfo.getBrandId());
			//取商品的参数
			 JsonConfig jsonConfig = new JsonConfig();
			 jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			 joProductPara =JSONObject.fromObject(productInfo, jsonConfig);
		  }
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 异步校验团购商品是否符合购买条件
	 * @throws IOException 
	 */
	public void checkTuanProduct() throws IOException{
		boolean isExsit = true;
		ShopInfo shopInfo = (ShopInfo) session.getAttribute("shopInfo");
		//团购商品实体类
		tuanProduct = (TuanProduct) tuanProductService.getObjectByParams(" where o.productId="+productId);
		if(Utils.objectIsNotEmpty(shopInfo)){
			if(tuanProduct.getShopInfoId().equals(shopInfo.getShopInfoId())){//判断是否为自己店铺商品
				isExsit = false;
			}
		}
		JSONObject jo = new JSONObject();
		jo.accumulate("isExsit", isExsit);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.print(jo.toString());
		pw.flush();
		pw.close();
	}
	
	
	public void setTuanProductTypeService(
			ITuanProductTypeService tuanProductTypeService) {
		this.tuanProductTypeService = tuanProductTypeService;
	}
	public void setTuanProductService(ITuanProductService tuanProductService) {
		this.tuanProductService = tuanProductService;
	}
	public List<TuanProductType> getTuanProductTypeList() {
		return tuanProductTypeList;
	}
	public void setTuanProductTypeList(List<TuanProductType> tuanProductTypeList) {
		this.tuanProductTypeList = tuanProductTypeList;
	}
	@SuppressWarnings("rawtypes")
	public List<Map> getTuanProductList() {
		return tuanProductList;
	}
	@SuppressWarnings("rawtypes")
	public void setTuanProductList(List<Map> tuanProductList) {
		this.tuanProductList = tuanProductList;
	}
	public TuanProduct getTuanProduct() {
		return tuanProduct;
	}
	public void setTuanProduct(TuanProduct tuanProduct) {
		this.tuanProduct = tuanProduct;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public ProductInfo getProductInfo() {
		return productInfo;
	}
	public void setProductInfo(ProductInfo productInfo) {
		this.productInfo = productInfo;
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
	public JSONArray getJaattrList() {
		return jaattrList;
	}
	public void setJaattrList(JSONArray jaattrList) {
		this.jaattrList = jaattrList;
	}
	public JSONObject getJoProductPara() {
		return joProductPara;
	}
	public void setJoProductPara(JSONObject joProductPara) {
		this.joProductPara = joProductPara;
	}
	public void setProductInfoService(IProductInfoService productInfoService) {
		this.productInfoService = productInfoService;
	}
	public void setEvaluateGoodsService(IEvaluateGoodsService evaluateGoodsService) {
		this.evaluateGoodsService = evaluateGoodsService;
	}
	public Integer getTuanProductTypeId() {
		return tuanProductTypeId;
	}
	public void setTuanProductTypeId(Integer tuanProductTypeId) {
		this.tuanProductTypeId = tuanProductTypeId;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
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
	public String getHeaderType() {
		return headerType;
	}
	public void setHeaderType(String headerType) {
		this.headerType = headerType;
	}
	public TuanProductType getTuanProductType() {
		return tuanProductType;
	}
	public void setTuanProductType(TuanProductType tuanProductType) {
		this.tuanProductType = tuanProductType;
	}	
}
