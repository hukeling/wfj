package shop.front.hotsale.action;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import shop.product.pojo.ProductInfo;
import shop.product.pojo.ProductType;
import shop.product.service.IProductInfoService;
import shop.product.service.IProductTypeService;
import util.action.BaseAction;
import util.other.Utils;
/**
 * CouponAction - 热销产品Action
 * @author XHT
 */
@SuppressWarnings("serial")
public class HotSaleAction extends BaseAction{
	private String headerType;
	/** 商品Service **/
	private IProductInfoService productInfoService;
	/** 商品分类Service **/
	private IProductTypeService productTypeService;
	/** 商品实体类  **/
	private ProductInfo productInfo;
	/** 顶部的两个 **/
	@SuppressWarnings("unchecked")
	private List<Map> productInfoList = new ArrayList<Map>();
	/** 大分类全部数据 **/
	@SuppressWarnings("unchecked")
	private Map<Object,List> map = new LinkedHashMap<Object,List>();
	/** 商品分类ID **/
	private String productTypeId;
	/** 分类的商品集合 **/
	@SuppressWarnings("unchecked")
	private List<Map> mapList = new ArrayList<Map>();
	/** 商品分类实体类 **/
	private ProductType productType;
	/**
	 * 查询并跳转热销总页面
	 */
	@SuppressWarnings("unchecked")
	public String gotoHotSale(){
		//最顶层两个
		String hql = "SELECT o.productName as productName, o.logoImg as logoImg,o.productId as productId, o.totalSales as totalSales, o.shopInfoId as shopInfoId, o.specification as specification, o.salesPrice as salesPrice  FROM ProductInfo o WHERE o.isHot = 1 and o.isTop = 1 and  o.isPutSale = 2 and o.isPass = 1 ";
		pageHelper.setPageSize(2);
		productInfoList = productInfoService.findListMapPage(hql, pageHelper);
		//所有大分类
		List<ProductType> productTypeList = productTypeService.findObjects("where o.parentId = 1 order by o.sortCode desc");
		for(ProductType productType : productTypeList){
			//遍历集合得到大分类下的ID,查询大分类下ID的下属集合
			String categoryIds = "";
			productType = (ProductType) productTypeService.getObjectById(String.valueOf(productType.getProductTypeId()));
			List<Object> ptList = new ArrayList<Object>();
			ptList.add(productType.getProductTypeId());
			if (productType.getLevel() == 1) {// 二级分类
				// 得到二级分类集合
				productTypeList = productTypeService.findObjects("where o.parentId='"+ productType.getProductTypeId() + "' and o.level=2");
				if (productTypeList != null) {
					for (ProductType pt : productTypeList) {
						ptList.add(pt.getProductTypeId());
					List<ProductType> ptList2 = productTypeService.findObjects("where o.parentId='"+ pt.getProductTypeId() + "' and o.level=3");// 得到三级分类集合
						if (ptList2 != null) {
							for (ProductType pt2 : ptList2) {
								ptList.add(pt2.getProductTypeId());
							}
						}
					}
				}
				categoryIds = Utils.listToString(ptList, ",");
			}
			String mHql = "SELECT o.productName as productName, o.logoImg as logoImg, o.productId as productId, o.totalSales as totalSales, o.shopInfoId as shopInfoId, o.specification as specification, o.salesPrice as salesPrice  FROM ProductInfo o WHERE o.isHot = 1 and o.isPutSale = 2 and o.isPass = 1  and o.productTypeId in (" + categoryIds + ") order by o.totalSales desc";
			pageHelper.setPageSize(3);
			List<Map> listMap = productInfoService.findListMapPage(mHql, pageHelper);
			if(listMap!=null&&listMap.size()>0){
				map.put(productType, listMap);
			}
		}
		return SUCCESS;
	}
	/**
	 * 跳转单独的分类页面
	 */
	@SuppressWarnings("unchecked")
	public String gotoHotSaleList(){
		//最顶层两个
		String hql = "SELECT o.productName as productName, o.logoImg as logoImg, o.productId as productId, o.totalSales as totalSales, o.shopInfoId as shopInfoId, o.specification as specification, o.salesPrice as salesPrice   FROM ProductInfo o  WHERE o.isHot = 1 and o.isPutSale = 2 and o.isPass = 1 and o.isTop = 1 ";
		pageHelper.setPageSize(2);
		productInfoList = productInfoService.findListMapPage(hql, pageHelper);
		//下面单独分类的数据
		productType = (ProductType) productTypeService.getObjectByParams(" where o.productTypeId = "+productTypeId);
		String categoryIds = "";
		List<Object> ptList1 = new ArrayList<Object>();
		ptList1.add(productType.getProductTypeId());
		if (productType.getLevel() == 1) {// 二级分类
			// 得到二级分类集合
			List<ProductType> productTypeList = productTypeService.findObjects("where o.parentId='"+ productType.getProductTypeId() + "' and o.level=2");
			if (productTypeList != null) {
				for (ProductType pt : productTypeList) {
					ptList1.add(pt.getProductTypeId());
				List<ProductType> ptList12 = productTypeService.findObjects("where o.parentId='"+ pt.getProductTypeId() + "' and o.level=3");// 得到三级分类集合
					if (ptList12 != null) {
						for (ProductType pt2 : ptList12) {
							ptList1.add(pt2.getProductTypeId());
						}
					}
				}
			}
			categoryIds = Utils.listToString(ptList1, ",");
		}
		String mHqll = "SELECT o.productName as productName, o.logoImg as logoImg, o.productId as productId, o.totalSales as totalSales, o.shopInfoId as shopInfoId, o.specification as specification, o.salesPrice as salesPrice  FROM ProductInfo o  WHERE o.isHot = 1 and o.isPutSale = 2 and o.isPass = 1 and o.productTypeId in (" + categoryIds + ") order by o.totalSales desc";
		mapList = productInfoService.findListMapByHql(mHqll);
		//所有大分类
		List<ProductType> productTypeList = productTypeService.findObjects("where o.parentId = 1 order by o.sortCode desc");
		for(ProductType productType : productTypeList){
			//遍历集合得到大分类下的ID,查询大分类下ID的下属集合
			String categoryIdss = "";
			productType = (ProductType) productTypeService.getObjectById(String.valueOf(productType.getProductTypeId()));
			List<Object> ptList = new ArrayList<Object>();
			ptList.add(productType.getProductTypeId());
			if (productType.getLevel() == 1) {// 二级分类
				// 得到二级分类集合
				productTypeList = productTypeService.findObjects("where o.parentId='"+ productType.getProductTypeId() + "' and o.level=2");
				if (productTypeList != null) {
					for (ProductType pt : productTypeList) {
						ptList.add(pt.getProductTypeId());
					List<ProductType> ptList2 = productTypeService.findObjects("where o.parentId='"+ pt.getProductTypeId() + "' and o.level=3");// 得到三级分类集合
						if (ptList2 != null) {
							for (ProductType pt2 : ptList2) {
								ptList.add(pt2.getProductTypeId());
							}
						}
					}
				}
				categoryIdss = Utils.listToString(ptList, ",");
			}
			String mHql = "SELECT o.productName as productName, o.logoImg as logoImg, o.productId as productId, o.totalSales as totalSales, o.shopInfoId as shopInfoId, o.specification as specification, o.salesPrice as salesPrice  FROM ProductInfo o  WHERE o.isHot = 1 and o.isPutSale = 2 and o.isPass = 1 and o.productTypeId in (" + categoryIdss + ") order by o.totalSales desc";
			pageHelper.setPageSize(3);
			List<Map> listMap = productInfoService.findListMapPage(mHql, pageHelper);
			if(listMap!=null&&listMap.size()>0){
				map.put(productType, listMap);
			}
		}
		return SUCCESS;
	}
	public ProductInfo getProductInfo() {
		return productInfo;
	}
	public void setProductInfo(ProductInfo productInfo) {
		this.productInfo = productInfo;
	}
	public void setProductInfoService(IProductInfoService productInfoService) {
		this.productInfoService = productInfoService;
	}
	@SuppressWarnings("unchecked")
	public List<Map> getProductInfoList() {
		return productInfoList;
	}
	@SuppressWarnings("unchecked")
	public void setProductInfoList(List<Map> productInfoList) {
		this.productInfoList = productInfoList;
	}
	public void setProductTypeService(IProductTypeService productTypeService) {
		this.productTypeService = productTypeService;
	}
	@SuppressWarnings("unchecked")
	public Map<Object, List> getMap() {
		return map;
	}
	@SuppressWarnings("unchecked")
	public void setMap(Map<Object, List> map) {
		this.map = map;
	}
	@SuppressWarnings("unchecked")
	public List<Map> getMapList() {
		return mapList;
	}
	@SuppressWarnings("unchecked")
	public void setMapList(List<Map> mapList) {
		this.mapList = mapList;
	}
	public String getProductTypeId() {
		return productTypeId;
	}
	public void setProductTypeId(String productTypeId) {
		this.productTypeId = productTypeId;
	}
	public ProductType getProductType() {
		return productType;
	}
	public void setProductType(ProductType productType) {
		this.productType = productType;
	}
	public String getHeaderType() {
		return headerType;
	}
	public void setHeaderType(String headerType) {
		this.headerType = headerType;
	}
}
