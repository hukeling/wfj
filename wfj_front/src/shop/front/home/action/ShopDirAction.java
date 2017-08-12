package shop.front.home.action;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import shop.customer.pojo.EvaluateGoods;
import shop.customer.service.IEvaluateGoodsService;
import shop.homeIndex.pojo.HomeAdvertisement;
import shop.homeIndex.service.IHomeAdvertisementService;
import shop.product.pojo.ProductInfo;
import shop.product.service.IProductInfoService;
import shop.shops.pojo.Shops;
import shop.shops.service.IShopsService;
import shop.shops.service.IShopsShopinfoService;
import shop.store.pojo.ShopCategory;
import shop.store.pojo.ShopInfo;
import shop.store.service.IShopCategoryService;
import shop.store.service.IShopInfoService;
import util.action.BaseAction;
import util.other.Utils;
import basic.pojo.BasicArea;
import basic.service.IAreaService;
/**
 * 前台首页店铺目录
 * @author mqr
 */
public class ShopDirAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	private String headerType;
	private IShopInfoService shopInfoService;
	private IProductInfoService productInfoService;//商品service
	private IShopCategoryService shopCategoryService;//店铺内部分类service
	private List<Map> mapList=new ArrayList<Map>();
	private List<ShopCategory> shopTypeList=new ArrayList<ShopCategory>();//店铺内部分类list
	private String shopInfoId;//店铺的id
	private String strShopCategoryId;//店铺分类id
	private Integer pageIndex=1;
	private ShopInfo shopInfo=new ShopInfo();//店铺对象
	/** 评价Service **/
	private IEvaluateGoodsService evaluateGoodsService;
	/** 店铺目录广告位Service **/
	private IHomeAdvertisementService homeAdvertisementService;
	
	/**地区信息service*/
	private IAreaService areaService;
	/**商城与店铺关系service*/
	private IShopsShopinfoService shopsShopinfoService;
	/**线下商城service*/
	private IShopsService shopsService;
	/**地区信息对象集合(存放省)*/
	private List<BasicArea> regionLocationList = new ArrayList<BasicArea>();
	/**地区信息对象集合(存放市)*/
	private List<BasicArea> cityList = new ArrayList<BasicArea>();
	/**商城(线下实际商城)对象集合*/
	private List<Shops> shopsList = new ArrayList<Shops>();
	private List<Map<String, Object>> shopsListMap = new ArrayList<Map<String, Object>>();
	/**商城(线下实际商城)对象*/
	private Shops shops;
	/**地区省ID*/
	private String regionLocation;
	/**城市ID*/
	private String city;
	/**商城(线下实际商城)对象ID*/
	private String strShopsId;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String gotoShopDirPage(){
		
		//查询到所有省
		regionLocationList = areaService.findObjects("where o.parentId=0");
		/**店铺信息Id字符串*/
		String shopInfoIds = "";
		if(Utils.objectIsNotEmpty(regionLocationList)){
			//如果商城不为空
			if(Utils.objectIsNotEmpty(strShopsId)){
				//查询一个省下面的所有市
				cityList = areaService.findObjects("where o.parentId="+regionLocation);
				//商城ID集合
				List<?> shopsIdList = shopsShopinfoService.findUnDistinctList("shopsId", "where o.city="+city+" group by o.shopsId");
				//商城ID字符串
				String shopsIds = Utils.listToString(shopsIdList,",");
				//商城集合
				if(Utils.objectIsNotEmpty(shopsIds)){
//					shopsList = shopsService.findObjects("where o.shopsId in("+shopsIds+")");
					String shopsListSql="SELECT ss.shopsId as shopsId,ss.shopsCode as shopsCode,ss.shopsName as shopsName,ss.city as city,ss.regionLocation as regionLocation, COUNT(sss.shopInfoId) AS shopInfoIdCount FROM shop_shops ss, shop_shops_shopinfo sss WHERE sss.shopsId=ss.shopsId AND sss.shopsId IN("+shopsIds+")";
					if(Utils.objectIsNotEmpty(strShopCategoryId)){
						shopsListSql+=" and sss.shopCategoryId="+strShopCategoryId;
					}
					shopsListMap = shopsService.findListMapBySql(shopsListSql+" GROUP BY sss.shopsId");
				}
				//店铺ID集合
				List<?> shopInfoIdList = shopsShopinfoService.findUnDistinctList("shopInfoId", "where o.shopsId="+strShopsId);
				//店铺信息Id字符串
				shopInfoIds = Utils.listToString(shopInfoIdList,",");
			}else if(Utils.objectIsNotEmpty(city)){//如果商城为空且市不为空
				//当前市
				BasicArea basicArea = (BasicArea) areaService.getObjectById(city);
				//与当前市平级的所有市
				cityList = areaService.findObjects("where o.parentId="+basicArea.getParentId());
				//商城ID集合
				List<?> shopsIdList = shopsShopinfoService.findUnDistinctList("shopsId", "where o.city="+city+" group by o.shopsId");
				//商城ID字符串
				String shopsIds = Utils.listToString(shopsIdList,",");
				//商城集合
				if(Utils.objectIsNotEmpty(shopsIds)){
//					shopsList = shopsService.findObjects("where o.shopsId in("+shopsIds+")");
					String shopsListSql="SELECT ss.shopsId as shopsId,ss.shopsCode as shopsCode,ss.shopsName as shopsName,ss.city as city,ss.regionLocation as regionLocation, COUNT(sss.shopInfoId) AS shopInfoIdCount FROM shop_shops ss, shop_shops_shopinfo sss WHERE sss.shopsId=ss.shopsId AND sss.shopsId IN("+shopsIds+")";
					if(Utils.objectIsNotEmpty(strShopCategoryId)){
						shopsListSql+=" and sss.shopCategoryId="+strShopCategoryId;
					}
					shopsListMap = shopsService.findListMapBySql(shopsListSql+" GROUP BY sss.shopsId");
				}
				//店铺ID集合
				List<?> shopInfoIdList = shopsShopinfoService.findUnDistinctList("shopInfoId", "where o.city="+city);
				//店铺信息Id字符串
				shopInfoIds = Utils.listToString(shopInfoIdList,",");
			}else if(Utils.objectIsNotEmpty(regionLocation)){//如果商城为空且市为空且省不为空
				//查询一个省下面的所有市
				cityList = areaService.findObjects("where o.parentId="+regionLocation);
				//商城ID集合
				List<?> shopsIdList = shopsShopinfoService.findUnDistinctList("shopsId", "where o.regionLocation="+regionLocation+" group by o.shopsId");
				//商城ID字符串
				String shopsIds = Utils.listToString(shopsIdList,",");
				if(Utils.objectIsNotEmpty(shopsIds)){
//					shopsList = shopsService.findObjects("where o.shopsId in("+shopsIds+")");
					String shopsListSql="SELECT ss.shopsId as shopsId,ss.shopsCode as shopsCode,ss.shopsName as shopsName,ss.city as city,ss.regionLocation as regionLocation, COUNT(sss.shopInfoId) AS shopInfoIdCount FROM shop_shops ss, shop_shops_shopinfo sss WHERE sss.shopsId=ss.shopsId AND sss.shopsId IN("+shopsIds+")";
					if(Utils.objectIsNotEmpty(strShopCategoryId)){
						shopsListSql+=" and sss.shopCategoryId="+strShopCategoryId;
					}
					shopsListMap = shopsService.findListMapBySql(shopsListSql+" GROUP BY sss.shopsId");
				}
				//店铺ID集合
				List<?> shopInfoIdList = shopsShopinfoService.findUnDistinctList("shopInfoId", "where o.regionLocation="+regionLocation);
				//店铺信息Id字符串
				shopInfoIds = Utils.listToString(shopInfoIdList,",");
			}else{//如果商城为空且市为空且省为空,则进行默认查询北京市
				//默认省的id
				int areaId = 0;
				//默认查询北京市下面的所有地区
				for(BasicArea ba :regionLocationList){
					int ai = ba.getAreaId();
					if(ai==1&&"北京市".equals(ba.getName())){
						//areaId = ba.getAreaId();
						areaId = 351;
						regionLocation = String.valueOf(areaId);
						cityList = areaService.findObjects("where o.parentId="+areaId);
						List<?> shopsIdList = shopsShopinfoService.findUnDistinctList("shopsId", "where o.regionLocation="+areaId+" group by o.shopsId");
						String shopsIds = Utils.listToString(shopsIdList,",");
						if(Utils.objectIsNotEmpty(shopsIds)){
							//shopsList = shopsService.findObjects("where o.shopsId in("+shopsIds+")");
							String shopsListSql="SELECT ss.shopsId as shopsId,ss.shopsCode as shopsCode,ss.shopsName as shopsName,ss.city as city,ss.regionLocation as regionLocation, COUNT(sss.shopInfoId) AS shopInfoIdCount FROM shop_shops ss, shop_shops_shopinfo sss WHERE sss.shopsId=ss.shopsId AND sss.shopsId IN("+shopsIds+")";
							if(Utils.objectIsNotEmpty(strShopCategoryId)){
								shopsListSql+=" and sss.shopCategoryId="+strShopCategoryId;
							}
							shopsListMap = shopsService.findListMapBySql(shopsListSql+" GROUP BY sss.shopsId");
						}
						break;
					}
				}
				//店铺ID集合
				List<?> shopInfoIdList = shopsShopinfoService.findUnDistinctList("shopInfoId", "where o.regionLocation="+areaId);
				//店铺信息Id字符串
				shopInfoIds = Utils.listToString(shopInfoIdList,",");
			}

			//右侧店铺分类
			shopTypeList= shopCategoryService.findSome(0, 11," where o.parentId>0 order by o.sortCode");
			//查询店铺信息
			String shopDirHql="SELECT a.shopInfoId as shopInfoId ,a.shopName as shopName,a.customerName as customerName,a.marketBrandUrl as marketBrandUrl,a.logoUrl as logoUrl FROM ShopInfo a where 1=1";
			//所在商城
			if(Utils.objectIsNotEmpty(shopInfoIds)){
				shopDirHql+=" and a.shopInfoId in("+shopInfoIds+")";
				
				//店铺类型
				if(StringUtils.isNotEmpty(strShopCategoryId)){
					shopDirHql+=" and a.shopCategoryId="+strShopCategoryId;
				}
				//追加查询条件、审核通过（isPass1、待审核2、不通过3、通过）、未关闭（isClose0：不关闭1：关闭）
				shopDirHql+=" and a.isPass=3 and a.isClose=0";

				List<Map> findListMapByHql = shopInfoService.findListMapByHql(shopDirHql);

				if(findListMapByHql!=null&&findListMapByHql.size()>0){
					Integer totalRecordCount = findListMapByHql.size();
					pageHelper.setPageInfo(12, totalRecordCount, currentPage);
					List<Map> mList=shopInfoService.findListMapPage(shopDirHql, pageHelper);
					for(Map m:mList){
						List<ProductInfo> list = productInfoService.findSome(0, 6," where o.shopInfoId="+m.get("shopInfoId")+" and o.isPass=1 and o.isPutSale=2 and o.isShow=1  order by o.createDate desc");
						Integer photoNum = getPercent(Integer.parseInt(m.get("shopInfoId").toString()));
						m.put("photoNum", photoNum);
						m.put("photoNum2", 5-photoNum);
						m.put("list", list);
						mapList.add(m);
					}
				}
				//查询店铺目录广告位图片信息
				HomeAdvertisement homeAdvertisement = (HomeAdvertisement) homeAdvertisementService.getObjectByParams(" where o.isShow=1 and o.showLocation=7");
				request.setAttribute("homeAdvertisement", homeAdvertisement);
			}
		}
		
		return SUCCESS;
	}
	/**
	 * 查询店铺评价等级
	 * @param shopId
	 * @return
	 */
	public  Integer getPercent(Integer shopId){  
		Integer photoNum=0;
		List<EvaluateGoods> evaluateGoodsList = evaluateGoodsService.findObjects(" where o.shopInfoId = "+shopId);
		//判断查询出来的集合是否为空
		if(evaluateGoodsList!=null&&evaluateGoodsList.size()>0){
			//定义一个Integer用来保存好评的数量
			Integer dividend = 0;
			Integer sum = evaluateGoodsList.size();
			for(EvaluateGoods evaluateGoods : evaluateGoodsList){
				Integer level = evaluateGoods.getLevel();
				if(level==1){
					dividend++;
				}
			}
			//计算好评百分比
			Integer grade = (dividend*100)/sum;
			//给前台判定图片数量
			if(grade<=20){
				photoNum = 1;
			}
			if(grade>20&&grade<=40){
				photoNum = 2;
			}
			if(grade>40&&grade<=60){
				photoNum = 3;
			}
			if(grade>60&&grade<=80){
				photoNum = 4;
			}
			if(grade>80){
				photoNum = 5;
			}
		}
		return photoNum;
	} 
	//setter getter
	public void setShopInfoService(IShopInfoService shopInfoService) {
		this.shopInfoService = shopInfoService;
	}
	public void setProductInfoService(IProductInfoService productInfoService) {
		this.productInfoService = productInfoService;
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
	public void setShopCategoryService(IShopCategoryService shopCategoryService) {
		this.shopCategoryService = shopCategoryService;
	}
	public List<ShopCategory> getShopTypeList() {
		return shopTypeList;
	}
	public void setShopTypeList(List<ShopCategory> shopTypeList) {
		this.shopTypeList = shopTypeList;
	}
	public List<Map> getMapList() {
		return mapList;
	}
	public void setMapList(List<Map> mapList) {
		this.mapList = mapList;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public String getHeaderType() {
		return headerType;
	}
	public void setHeaderType(String headerType) {
		this.headerType = headerType;
	}
	public void setEvaluateGoodsService(IEvaluateGoodsService evaluateGoodsService) {
		this.evaluateGoodsService = evaluateGoodsService;
	}
	public void setHomeAdvertisementService(
			IHomeAdvertisementService homeAdvertisementService) {
		this.homeAdvertisementService = homeAdvertisementService;
	}
	public List<Shops> getShopsList() {
		return shopsList;
	}
	public void setShopsList(List<Shops> shopsList) {
		this.shopsList = shopsList;
	}
	public Shops getShops() {
		return shops;
	}
	public void setShops(Shops shops) {
		this.shops = shops;
	}
	public void setShopsShopinfoService(IShopsShopinfoService shopsShopinfoService) {
		this.shopsShopinfoService = shopsShopinfoService;
	}
	public void setShopsService(IShopsService shopsService) {
		this.shopsService = shopsService;
	}
	public String getRegionLocation() {
		return regionLocation;
	}
	public void setRegionLocation(String regionLocation) {
		this.regionLocation = regionLocation;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public List<BasicArea> getRegionLocationList() {
		return regionLocationList;
	}
	public void setRegionLocationList(List<BasicArea> regionLocationList) {
		this.regionLocationList = regionLocationList;
	}
	public List<BasicArea> getCityList() {
		return cityList;
	}
	public void setCityList(List<BasicArea> cityList) {
		this.cityList = cityList;
	}
	public void setAreaService(IAreaService areaService) {
		this.areaService = areaService;
	}
	public String getStrShopsId() {
		return strShopsId;
	}
	public void setStrShopsId(String strShopsId) {
		this.strShopsId = strShopsId;
	}
	public String getStrShopCategoryId() {
		return strShopCategoryId;
	}
	public void setStrShopCategoryId(String strShopCategoryId) {
		this.strShopCategoryId = strShopCategoryId;
	}
	public List<Map<String, Object>> getShopsListMap() {
		return shopsListMap;
	}
	public void setShopsListMap(List<Map<String, Object>> shopsListMap) {
		this.shopsListMap = shopsListMap;
	}
}
