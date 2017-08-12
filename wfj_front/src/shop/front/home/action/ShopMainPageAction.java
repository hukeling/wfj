package shop.front.home.action;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import shop.customer.pojo.Customer;
import shop.customer.pojo.CustomerCollectShop;
import shop.customer.pojo.EvaluateGoods;
import shop.customer.service.ICustomerCollectShopService;
import shop.customer.service.IEvaluateGoodsService;
import shop.customer.service.IShopCustomerServiceService;
import shop.product.pojo.Brand;
import shop.product.pojo.ProductType;
import shop.product.service.IBrandService;
import shop.product.service.IProductInfoService;
import shop.product.service.IProductTypeService;
import shop.store.pojo.ShopInfo;
import shop.store.pojo.ShopProCategory;
import shop.store.service.IShopBrandService;
import shop.store.service.IShopInfoService;
import shop.store.service.IShopProCategoryService;
import util.action.BaseAction;
import util.other.Utils;
/**
 * 	前台店铺首页
 * @author mqr
 */
public class ShopMainPageAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	private IShopInfoService shopInfoService;//店铺service
	private IProductInfoService productInfoService;//商品service
	private IBrandService brandService;//品牌service
	private IShopBrandService shopBrandService;
	private IShopProCategoryService shopProCategoryService;//店铺内部分类service
	private List<Map> productList=new ArrayList<Map>();//商品list 
	private List<ShopProCategory> productTypeList=new ArrayList<ShopProCategory>();//店铺内部分类list
	private String shopInfoId;//店铺的id
	private ShopInfo shopInfo=new ShopInfo();//店铺对象
	private String brandId;//品牌id
	private Brand brand=new Brand();//品牌对象
	private String[] tagSplit;//店铺tag数组
	/**店铺商品内部分类id**/
	private String shopProCategoryId;
	/** 评价Service **/
	private IEvaluateGoodsService evaluateGoodsService;
	/** 评价总数 **/
	private Integer sum;
	/** 积分等级和百分比 **/
	private Integer grade;
	/** 图片循环次数  **/
	private Integer photoNum = 4;
    private IProductTypeService productTypeService;//分类Service
	private List productInfoList=new ArrayList();//非顶置商品集合 
	private String prodTypeNames=""; 
    private ProductType productType;//商品分类
	private Integer productTypeId;//商品分类ID
	private Integer pageSize=12;//分页-每一页显示的个数
	private String minPrice;//价格
	private String maxPrice;//价格范围：minPrice-maxPrice之间
	private String orderBy;//标记(用于排序)
	private String shopProCategoryNames="";
	 /**品牌service**/
    private List<Brand> brandList = new ArrayList<Brand>();//品牌列表集合(下方图片展示)
	private List<Map> brandList2 = new ArrayList<Map>();//品牌列表集合(查询条件)
	/**用于参数的回显**/
	private Map<String ,Object> mapParams=new LinkedHashMap<String ,Object>();
	/**商品hql语句select部分**/
	private StringBuffer hqlSelect = new StringBuffer(" SELECT a.goods as goods, a.productId as productId,a.salesPrice as salesPrice, a.productName as productName, a.describle as describle,a.marketPrice as marketPrice,a.salesPrice as salesPrice,a.logoImg as logoImg,a.brandId as brandId,a.shopInfoId as shopInfoId FROM ProductInfo a,ShopInfo b");
	/**商品hql语句where部分**/
	private StringBuffer hqlWhere = new StringBuffer(" WHERE a.isPass=1 and a.isPutSale=2 and a.isShow=1 and  a.shopInfoId=b.shopInfoId and b.isClose=0 and b.isPass=3");
	/**商品的总条数select部分**/
	private StringBuffer coutHql = new StringBuffer(" SELECT count(a.productId)  FROM ProductInfo a,ShopInfo b");
	/**分类ids**/
	private String categoryIds="";
	private String brandParams;
	/**店铺收藏**/
	private ICustomerCollectShopService customerCollectShopService;
	/**QQ集合**/
	private List<Map<String,Object>> qqList;
	/**客服与店铺关系Service*/
	private IShopCustomerServiceService shopCustomerServiceService;
	
	//跳转到page页
	public String gotoShopInfoPage(){
		if(shopInfoId!=null&&!"".equals(shopInfoId)){
			shopInfo = (ShopInfo) shopInfoService.getObjectById(shopInfoId);
			if(shopInfo!=null){
				try{
					//根据店铺的ID查询出所有的商品并计算该店铺的积分等级
					List<EvaluateGoods> evaluateGoodsList = evaluateGoodsService.findObjects(" where o.shopInfoId = "+shopInfoId);
					if(Utils.objectIsNotEmpty(shopInfo)){
						/**查询QQ的sql语句*/
						String qqSql="select b.trueName as trueName,b.nikeName as nikeName,b.qq as qq from shop_customerservice b ,shop_shop_customerservice c where b.useState=1 and c.customerServiceId=b.customerServiceId and c.customerId="+shopInfo.getCustomerId();
						//查询qq
						qqList=shopCustomerServiceService.findListMapBySql(qqSql);
					}
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
					//查询店铺中的商品
					Integer ts = null;
					if(Utils.objectIsNotEmpty(shopInfo)){
						ts = shopInfo.getTemplateSet();//店铺模板类型（1，2，3，4，5）
					}
					if(Utils.objectIsEmpty(ts)) ts=1;//如果为空则为1
					photoAndList(Integer.parseInt(shopInfoId),ts);
					//查询店铺的模版
					/*1、店铺模板1(默认)
						2、店铺模板2
						3、店铺模板3*/
					if(ts != null){
						if(ts==1){
							return "tempLateSet1";
						}else if(ts==2){
							return "tempLateSet2";
						}else if(ts==3){
							return "tempLateSet3";
						}else if(ts==4){
							return "tempLateSet4";
						}else{
							return "tempLateSet5";
						}
					}else{
						return "tempLateSet3";
					}
				}catch(Exception e){
					e.printStackTrace();
					return null;
				}
			}
		}
		return null;
	}
	/**
	 * 排序查询
	 * @param specificationsParams 规格ids 例(1,2,3)
	 * @param orderByParams 要排序的字段
	 *@param  collate 升序或降序
	 * @return pList 商品集合  格式(List<Map>)
	 */
	@SuppressWarnings("unchecked")
	public List<Map> getProductListMapBySpecifications(String orderByParams,String collate){
		List<Map> pList=new ArrayList<Map>();//商品list
		StringBuffer hql = new StringBuffer();//最终要查询的hql
		hqlWhere.append("  order by a."+orderByParams+" "+collate);
		coutHql.append(hqlWhere);//	总条数hql
		hql.append(hqlSelect).append(hqlWhere);//商品hql
		int totalRecordCount = productInfoService.getMultilistCount(coutHql.toString());
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		pList = productInfoService.findListMapPage(hql.toString(),pageHelper);//非顶置商品分页
		return pList;
	}
	/**
	 * 图片与列表-是主方法
	 * @param shopTempType 店铺模板类型（1，2，3，4，5）
	 */
	public void photoAndList(Integer shopInfoId,Integer shopTempType){
			//根据店铺模板的不同 列表显示商品个数不同
		    if(shopTempType==2||shopTempType==3){
		    	pageSize=20;
		    }
			//推荐品牌
			brandList =brandService.findSome(0, 5, "where o.isShow=1 and o.isRecommend=1 order by o.sortCode");
			//店铺的品牌
//			String brandHql="SELECT b.brandId as brandId,a.brandName as brandName FROM Brand a ,ShopBrand b where a.brandId=b.brandId and b.shopInfoId="+shopInfoId;
//			brandList2 = shopBrandService.findListMapByHql(brandHql);
			//查询列表页面的分类
			productTypeList =shopProCategoryService.findObjects(" where o.shopInfoId="+shopInfoId);
			//根据价格范围搜索
			 String priceStr="";
		    if(Utils.objectIsNotEmpty(minPrice)){
			    hqlWhere.append(" and a.salesPrice>="+String.valueOf(minPrice));
			    hqlWhere.append(" and a.salesPrice<="+String.valueOf(maxPrice));
			    priceStr+=minPrice+"-"+maxPrice;
			    mapParams.put("价格",priceStr);
			}	
			//品牌回显
			String brands="";//回显的字符串(品牌)
			if(!StringUtils.isEmpty(brandParams)){//品牌
				String[] split = brandParams.split(",");
				for(int i=0;i<split.length;i++){
					Brand brand =  (Brand) brandService.getObjectById(split[i]);//品牌对象
					if(i<split.length&&i>0){
						brands+=" 或 "+brand.getBrandName();
					}else{
						brands+=brand.getBrandName();
					}
				}
				mapParams.put("品牌",brands);
			}
			//品牌查询条件
			if(!StringUtils.isEmpty(brandParams)){//追加品牌查询条件
				hqlWhere.append(" and a.brandId in ("+brandParams+")");
			}
			if(Utils.objectIsNotEmpty(shopProCategoryId)){
				hqlSelect.append(" ,ShopProCategory c, ShopProCateClass d ");
				coutHql.append(" ,ShopProCategory c ,ShopProCateClass d ");
				hqlWhere.append(" and c.shopProCategoryId="+String.valueOf(shopProCategoryId)+" and c.shopInfoId=a.shopInfoId and a.productId=d.productId and c.shopProCategoryId=d.shopProCategoryId ");
				//递归当前位置
				address(shopProCategoryId);
			}
			if("normal".equals(orderBy)){//初始化进入产品图片（列表）
				StringBuffer hql = new StringBuffer();//最终要查询的hql
				coutHql.append(hqlWhere);//	总条数hql
				coutHql.append("and a.shopInfoId="+shopInfoId);
				if(Utils.objectIsNotEmpty(shopProCategoryId)){
					coutHql.append(" and c.shopProCategoryId=d.shopProCategoryId and d.productId=a.productId ");
					hqlWhere.append(" and c.shopProCategoryId=d.shopProCategoryId and d.productId=a.productId ");
				}
				hql.append(hqlSelect).append(hqlWhere).append(" and a.shopInfoId="+shopInfoId+" order by a.productId desc");//商品hql
				int totalRecordCount = productInfoService.getMultilistCount(coutHql.toString());
				pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
				productInfoList = productInfoService.findListMapPage(hql.toString(),pageHelper);//非顶置商品分页
				if(productInfoList!=null){
					for(int i=0;i<productInfoList.size();i++){
						Map map = (Map) productInfoList.get(i);
						String goodsCountHql="select count(a.goods) as goodsCount from ProductInfo a where a.goods="+map.get("goods")+"  and a.isPutSale=2 and a.isPass=1";
						int goodsCount = productInfoService.getCountByHQL(goodsCountHql);
						map.put("goodsCount", goodsCount);
					}
				}
			}else if("salesPriceAsc".equals(orderBy)){//3:排序：（按价格排序）：salesPrice升序   Price
				hqlWhere.append("and a.shopInfoId="+shopInfoId);
				productInfoList=getProductListMapBySpecifications( "salesPrice", "asc");
			}else if("salesPriceDesc".equals(orderBy)){//4:排序：（按价格排序）：salesPrice降序   Price
				hqlWhere.append("and a.shopInfoId="+shopInfoId);
				productInfoList=getProductListMapBySpecifications( "salesPrice", "desc");
			}else if("putSaleDate".equals(orderBy)){//6:排序（产品上架时间降序）：putSaleDate  Latest
				hqlWhere.append("and a.shopInfoId="+shopInfoId);
				productInfoList=getProductListMapBySpecifications( "putSaleDate", "desc");
			}else if("totalSales".equals(orderBy)){//7:排序（产品销售量降序）：totalSales     Popular
				hqlWhere.append("and a.shopInfoId="+shopInfoId);
				productInfoList=getProductListMapBySpecifications( "totalSales", "desc");
			}
			Map map = null;
			if(productInfoList!=null){
				for(int i=0;i<productInfoList.size();i++){
					map = (Map) productInfoList.get(i);
					String goodsCountHql="select count(a.goods) as goodsCount from ProductInfo a ,ShopInfo b where a.shopInfoId=b.shopInfoId and a.goods="+map.get("goods")+"  and a.isPutSale=2 and a.isPass=1 and b.isPass=3 and b.isClose=0";
					int goodsCount = productInfoService.getCountByHQL(goodsCountHql);
					map.put("goodsCount", goodsCount);
				}
			}
	}
	//递归位置信息
	public void address(String shopProCategoryId){
		String path = request.getContextPath();
		ShopProCategory shopProCategory=(ShopProCategory) shopProCategoryService.getObjectByParams(" where o.shopProCategoryId="+shopProCategoryId);
		if(StringUtils.isNotEmpty(shopProCategoryNames)){
			shopProCategoryNames =">><a href='"+path+"/store/frontShopMainPage/gotoShopInfoPage.action?shopInfoId="+shopInfoId+"&shopProCategoryId="+shopProCategoryId+"&orderBy="+orderBy+"&brandParams="+brandParams+"&minPrice="+String.valueOf(minPrice)+"&maxPrice="+String.valueOf(maxPrice)+"'>"+shopProCategory.getShopProCategoryName()+"</a>"+"&nbsp;&gt&gt;&nbsp;"+shopProCategoryNames;
		}else{
			shopProCategoryNames =">><a href='"+path+"/store/frontShopMainPage/gotoShopInfoPage.action?shopInfoId="+shopInfoId+"&shopProCategoryId="+shopProCategoryId+"&orderBy="+orderBy+"&brandParams="+brandParams+"&minPrice="+String.valueOf(minPrice)+"&maxPrice="+String.valueOf(maxPrice)+"'>"+shopProCategory.getShopProCategoryName()+"</a>";
		}
		if(shopProCategory!=null&&shopProCategory.getParentId()!=1){
			address(String.valueOf(shopProCategory.getShopProCategoryId()));
		}
	}
	/**
	 * 收藏店铺
	 * 注释部分为之前使用异步的方式，由于异步拦截器无法跳出登录页，改为同步，加入之后访问用户中心的收藏店铺列表
	 * @return
	 * @throws IOException
	 */
	public String favoriteShops()throws IOException{
		Customer c=(Customer) session.getAttribute("customer");
		if(c!=null&&c.getCustomerId()!=null&&c.getType()!=2){
			if(Utils.objectIsNotEmpty(shopInfoId)){
				CustomerCollectShop obj = (CustomerCollectShop) customerCollectShopService.getObjectByParams(" where o.shopInfoId="+shopInfoId+" and o.customerId="+c.getCustomerId());
				if(obj==null){
					ShopInfo si=(ShopInfo)shopInfoService.getObjectByParams(" where o.shopInfoId="+shopInfoId);
					if(si!=null&&si.getShopInfoId()!=null){
						CustomerCollectShop ccs=new CustomerCollectShop();//创建收藏对象
						ccs.setCustomerId(c.getCustomerId());//客户id
						ccs.setShopInfoId(Integer.parseInt(shopInfoId));//店铺id
						ccs.setShopName(si.getShopName());//店铺名称
						CustomerCollectShop ro=(CustomerCollectShop) customerCollectShopService.saveOrUpdateObject(ccs);
//						if(ro!=null&&ro.getCustomerCollectShopId()!=null){
//							JSONObject jo=new JSONObject();
//							jo.accumulate("isSuccess", true);
//							PrintWriter out = response.getWriter();
//							out.println(jo.toString());
//							out.flush();
//							out.close();
//						}
					}
				}
//				else{
//					JSONObject jo=new JSONObject();
//					jo.accumulate("isSuccess", false);
//					PrintWriter out = response.getWriter();
//					out.println(jo.toString());
//					out.flush();
//					out.close();
//				}
			}
		}
		return SUCCESS;
	}
	//setter and  getter
	public void setShopInfoService(IShopInfoService shopInfoService) {
		this.shopInfoService = shopInfoService;
	}
	public List<Map> getProductList() {
		return productList;
	}
	public void setProductList(List<Map> productList) {
		this.productList = productList;
	}
	public List<ShopProCategory> getProductTypeList() {
		return productTypeList;
	}
	public void setProductTypeList(List<ShopProCategory> productTypeList) {
		this.productTypeList = productTypeList;
	}
	public String getShopInfoId() {
		return shopInfoId;
	}
	public void setShopInfoId(String shopInfoId) {
		this.shopInfoId = shopInfoId;
	}
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public ShopInfo getShopInfo() {
		return shopInfo;
	}
	public void setShopInfo(ShopInfo shopInfo) {
		this.shopInfo = shopInfo;
	}
	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	public String[] getTagSplit() {
		return tagSplit;
	}
	public void setCustomerCollectShopService(
			ICustomerCollectShopService customerCollectShopService) {
		this.customerCollectShopService = customerCollectShopService;
	}
	public void setTagSplit(String[] tagSplit) {
		this.tagSplit = tagSplit;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getShopProCategoryId() {
		return shopProCategoryId;
	}
	public void setShopProCategoryId(String shopProCategoryId) {
		this.shopProCategoryId = shopProCategoryId;
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
	public void setProductInfoService(IProductInfoService productInfoService) {
		this.productInfoService = productInfoService;
	}
	public void setBrandService(IBrandService brandService) {
		this.brandService = brandService;
	}
	public void setShopProCategoryService(
			IShopProCategoryService shopProCategoryService) {
		this.shopProCategoryService = shopProCategoryService;
	}
	public void setEvaluateGoodsService(IEvaluateGoodsService evaluateGoodsService) {
		this.evaluateGoodsService = evaluateGoodsService;
	}
	public void setShopBrandService(IShopBrandService shopBrandService) {
		this.shopBrandService = shopBrandService;
	}
	public List getProductInfoList() {
		return productInfoList;
	}
	public void setProductInfoList(List productInfoList) {
		this.productInfoList = productInfoList;
	}
	public String getProdTypeNames() {
		return prodTypeNames;
	}
	public void setProdTypeNames(String prodTypeNames) {
		this.prodTypeNames = prodTypeNames;
	}
	public ProductType getProductType() {
		return productType;
	}
	public void setProductType(ProductType productType) {
		this.productType = productType;
	}
	public Integer getProductTypeId() {
		return productTypeId;
	}
	public void setProductTypeId(Integer productTypeId) {
		this.productTypeId = productTypeId;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public List<Brand> getBrandList() {
		return brandList;
	}
	public void setBrandList(List<Brand> brandList) {
		this.brandList = brandList;
	}
	public List<Map> getBrandList2() {
		return brandList2;
	}
	public void setBrandList2(List<Map> brandList2) {
		this.brandList2 = brandList2;
	}
	public Map<String, Object> getMapParams() {
		return mapParams;
	}
	public void setMapParams(Map<String, Object> mapParams) {
		this.mapParams = mapParams;
	}
	public StringBuffer getHqlSelect() {
		return hqlSelect;
	}
	public void setHqlSelect(StringBuffer hqlSelect) {
		this.hqlSelect = hqlSelect;
	}
	public StringBuffer getHqlWhere() {
		return hqlWhere;
	}
	public void setHqlWhere(StringBuffer hqlWhere) {
		this.hqlWhere = hqlWhere;
	}
	public StringBuffer getCoutHql() {
		return coutHql;
	}
	public void setCoutHql(StringBuffer coutHql) {
		this.coutHql = coutHql;
	}
	public String getCategoryIds() {
		return categoryIds;
	}
	public void setCategoryIds(String categoryIds) {
		this.categoryIds = categoryIds;
	}
	public String getBrandParams() {
		return brandParams;
	}
	public void setBrandParams(String brandParams) {
		this.brandParams = brandParams;
	}
	public void setProductTypeService(IProductTypeService productTypeService) {
		this.productTypeService = productTypeService;
	}
	public String getShopProCategoryNames() {
		return shopProCategoryNames;
	}
	public void setShopProCategoryNames(String shopProCategoryNames) {
		this.shopProCategoryNames = shopProCategoryNames;
	}
	public String getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(String minPrice) {
		this.minPrice = minPrice;
	}
	public String getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(String maxPrice) {
		this.maxPrice = maxPrice;
	}
	public List<Map<String, Object>> getQqList() {
		return qqList;
	}
	public void setQqList(List<Map<String, Object>> qqList) {
		this.qqList = qqList;
	}
	public void setShopCustomerServiceService(
			IShopCustomerServiceService shopCustomerServiceService) {
		this.shopCustomerServiceService = shopCustomerServiceService;
	}
}
