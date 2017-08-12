package phone.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;

import shop.product.pojo.BrandType;
import shop.product.pojo.ProductType;
import shop.product.pojo.Specification;
import shop.product.service.IAttributeValueService;
import shop.product.service.IBrandTypeService;
import shop.product.service.IProductAttrIndexService;
import shop.product.service.IProductInfoService;
import shop.product.service.IProductTypeService;
import shop.product.service.ISpecificationService;
import shop.product.service.ISpecificationValueService;
import util.action.BaseAction;
import util.other.Utils;

@SuppressWarnings("serial")
@Controller
public class PhoneSweetyAction extends BaseAction {
	// 商品分类ID
	private Integer productTypeId;
	// 品牌ID
	private String brandId;

	private String specificationValueId;
	private String attrValueId;
	@Resource
	private IProductInfoService productInfoService;// 商品信息Service
	@Resource
	private IProductTypeService productTypeService;// 分类Service
	private List<ProductType> productTypeList = new ArrayList<ProductType>();
	@SuppressWarnings("rawtypes")
	private List<Map> productTypeList1;
	@SuppressWarnings("rawtypes")
	private List productInfoList = new ArrayList();// 非顶置商品集合
	@SuppressWarnings({ "rawtypes", "unused" })
	private List productInfoList1 = new ArrayList();// 顶置商品集合
	@SuppressWarnings("unused")
	private String prodTypeNames = "";
	@SuppressWarnings("unused")
	private ProductType productType;// 商品分类
	@SuppressWarnings("unused")
	private Integer pageIndex = 1;// 当前页
	@SuppressWarnings("unused")
	private Integer pageSize = 20;// 分页-每一页显示的个数
	private BigDecimal minPrice;// 价格
	private BigDecimal maxPrice;// 价格范围：minPrice-maxPrice之间
	private String orderBy;// 标记(用于排序)
	@Resource
	private IBrandTypeService brandTypeService;
	@SuppressWarnings("rawtypes")
	private List<Map> brandList = new ArrayList<Map>();// 品牌列表集合(下方图片展示)
	@SuppressWarnings({ "rawtypes", "unused" })
	private List<Map> brandList2 = new ArrayList<Map>();// 品牌列表集合(查询条件)
	/** 用于参数的回显 **/
	private Map<String, Object> mapParams = new LinkedHashMap<String, Object>();
	/** 商品hql语句where部分 **/
	private StringBuffer hqlWhere = new StringBuffer(
			" WHERE  a.shopInfoId=c.shopInfoId and a.isPass=1 and a.isPutSale=2 and c.isPass=3 and c.isClose=0 and a.isShow=1 and a.isTop=0");
	/** 商品hql语句select部分 **/
	private StringBuffer hqlSelect = new StringBuffer(
			" SELECT distinct a.goods as goods,a.productName as productName,a.productFullName as productFullName, "
					+ "a.productId as productId, a.describle as describle,a.marketPrice as marketPrice,a.salesPrice as salesPrice,"
					+ "a.logoImg as logoImg,c.shopName as shopName,a.brandId as brandId,a.shopInfoId as shopInfoId "
					+ "FROM ProductInfo a,ShopInfo c ");
	/** 商品的总条数select部分 **/
	@SuppressWarnings("unused")
	private StringBuffer coutHql = new StringBuffer(
			" SELECT count(a.productId)  FROM ProductInfo a,ShopInfo c ");
	/** 分类ids **/
	@SuppressWarnings("unused")
	private String categoryIds = "";
	/***** 判断是否是4级节点 ******/
	private Boolean isTrue = true;// 如果是false则不是4级节点
	/*** 规格 ***/
	@Resource
	private ISpecificationService specificationService;
	/** 指定分类下的规格 **/
	List<Specification> specificationList = new ArrayList<Specification>();
	@Resource
	private ISpecificationValueService specificationValueService;
	/** 商品扩展属性值service **/
	@Resource
	private IAttributeValueService attributeValueService;
	/** 商品关联扩展属性值中间表service **/
	@Resource
	private IProductAttrIndexService productAttrIndexService;

	private String level = "1";

	/*
	 * public String getBrandsAndProductInfos() { p.photoAndList(productTypeId,
	 * "getBrandsAndProductInfos", brandId, level); return SUCCESS; }
	 */

	/**
	 * 图片与列表-是主方法
	 * 
	 * @param address
	 *            链接调用action的名称
	 * @param productInfoService
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void photoAndList() throws IOException {
		JSONObject jo = new JSONObject();
		PrintWriter pw = response.getWriter();
		jo.accumulate("Status", true);
		String address = "getBrandsAndProductInfos";
		// 判断是否是叶子节点isTrue=true;
		ProductType pType = new ProductType();
		pType = (ProductType) productTypeService.getObjectById(String
				.valueOf(productTypeId));
		if (pType != null) {
			if ("1".equals(pType.getLoadType())) {
				isTrue = true;
			} else {
				isTrue = false;
			}

			// 根据商品查询品牌信息
			brandList2 = getBrandInfo(productTypeId);

			// 使用递归方法得到分类的ids
			// categoryIds=getAllProductTypeId(productTypeId);
			// categoryIds=categoryIds.substring(0,
			// categoryIds.lastIndexOf(","));
			// 查询列表页面的分类
			productTypeList1 = productTypeService
					.findListMapByHql(" select a.productTypeId as productTypeId,a.sortName as sortName,a.level as level from ProductType a where a.parentId ="
							+ pType.getProductTypeId());
			if (productTypeList1.size() == 0) {
				jo.accumulate("BrandList", brandList2);
			} else {
				jo.accumulate("ProductTypeList", productTypeList1);
			}

			if (isTrue) {
				// 查询分类下的品牌
				brandList = productTypeService.queryBrandByProductTypeId(pType
						.getProductTypeId().toString());
				// 查询分类下属性及属性值
				// 查询出当前分类下的所有属性 以map的形式保存
				List<Map<String, Object>> attrListMap = productAttrIndexService
						.findListMapBySql("select a.productAttrId,a.name from shop_product_attribute a where a.productTypeId="
								+ pType.getProductTypeId().toString()
								+ " and a.isListShow=1 order by a.sort");
				if (attrListMap != null && attrListMap.size() > 0) {
					for (Map<String, Object> map : attrListMap) {
						Integer productAttrId = Integer.parseInt(String
								.valueOf(map.get("productAttrId")));
						// 查询对应的属性值
						List<Map<String, Object>> attrValueListMap = attributeValueService
								.findListMapBySql("select a.attrValueId,a.attrValueName from shop_attribute_value a where a.productAttrId="
										+ productAttrId + " order by a.sort");
						if (attrValueListMap != null
								&& attrValueListMap.size() > 0) {
							map.put("attrValueListMap", attrValueListMap);
							// 查询每一个属性值所对应的商品个数
							for (Map<String, Object> avmap : attrValueListMap) {
								Object object = avmap.get("attrValueId");
								if (object != null) {
									String avid = String.valueOf(object);
									String hql = "select count(a.productId) as count from ProductAttrIndex a,ProductInfo b,ShopInfo c where a.productId=b.productId and b.shopInfoId=c.shopInfoId and b.isPass=1 and b.isPutSale=2 and b.isShow=1 and c.isPass=3 and b.categoryLevel"
											+ level
											+ " = "
											+ productTypeId
											+ " and c.isClose=0 and a.productTypeId="
											+ productTypeId
											+ " and a.attrValueId=" + avid;
									List<Map> findListMapBySql = productAttrIndexService
											.findListMapByHql(hql);
									if (findListMapBySql != null
											&& findListMapBySql.size() > 0) {
										Map<String, Object> map2 = findListMapBySql
												.get(0);
										Object object2 = map2.get("count");
										String count = String.valueOf(object2);
										avmap.put("count", count);
									} else {
										avmap.put("count", 0);
									}
								}
							}
						}
					}
				}
				request.setAttribute("attrListMap", attrListMap);
			}
			// 获取当前位置信息
			getProductTypeName(productTypeId, address, level);
			// 根据价格范围搜索
			if (Utils.objectIsNotEmpty(minPrice)) {
				hqlWhere.append(" and a.salesPrice>="
						+ String.valueOf(minPrice));
			}
			if (Utils.objectIsNotEmpty(maxPrice)) {
				hqlWhere.append(" and a.salesPrice<="
						+ String.valueOf(maxPrice));
				String priceStr = minPrice + "-" + maxPrice;
				mapParams.put("价格", priceStr);
			}
			// 如果点击品牌,还是叶子节点追加条件[品牌ID]
			if (isTrue == true) {
				if (brandId != null && !"".equals(brandId)) {// 4级分类,追加查询商品条件
					hqlWhere.append(" and a.brandId in (" + brandId + ") ");
					// coutHql.append(" where a.brandId="+brandId);
					// 点击品牌，则只显示与品牌相关联的分类信息
					List<BrandType> brandTypeList = brandTypeService
							.findObjects(" where o.brandId in (" + brandId
									+ ") ");
					Set<Integer> set = new HashSet<Integer>();
					for (BrandType brandType : brandTypeList) {
						set.add(brandType.getProductTypeId());
					}
					ProductType productType = null;
					for (int i = 0; i < productTypeList.size(); i++) {
						productType = productTypeList.get(i);
						if (!set.contains(productType.getProductTypeId())) {
							productTypeList.remove(i);
							i--;
						}
					}
				}
			}
			// 规格
			if (isTrue == true) {
				List<Specification> specificationList = (List<Specification>) specificationService
						.findObjects(" where o.productTypeId="
								+ pType.getProductTypeId() + " ");
				Map<String, List<Map>> specificationMap = new HashMap<String, List<Map>>();
				for (Specification specification : specificationList) {
					List<Map> list = specificationValueService
							.findListMapByHql(" select a.specificationValueId as specificationValueId,a.name as name from SpecificationValue a where a.specificationId="
									+ specification.getSpecificationId() + " ");
					for (Map m : list) {
						String hql = " SELECT count(a.productId) FROM ProductInfo a,ShopInfo c,ProductSpecificationValue d WHERE a.shopInfoId=c.shopInfoId and a.isPass=1 and a.isPutSale=2 and c.isPass=3 and c.isClose=0 and a.isShow=1 and a.categoryLevel"
								+ level
								+ " = "
								+ productTypeId
								+ "  and a.productTypeId = "
								+ pType.getProductTypeId()
								+ " AND a.isTop=0 and a.productId = d.productId and d.specificationValueId= "
								+ m.get("specificationValueId");
						int total = specificationValueService
								.getCountByHQL(hql);
						m.put("total", total);
					}
					specificationMap.put(specification.getName(), list);
				}
				request.setAttribute("specificationMap", specificationMap);
			}
			if ("normal".equals(orderBy)) {// 初始化进入产品图片（列表）
				productInfoList = getProductListMapBySpecifications(
						"productId", "desc", level);
			} else if ("salesPriceAsc".equals(orderBy)) {// 3:排序：（按价格排序）：salesPrice升序
															// Price
				productInfoList = getProductListMapBySpecifications(
						"salesPrice", "asc", level);
			} else if ("salesPriceDesc".equals(orderBy)) {// 4:排序：（按价格排序）：salesPrice降序
															// Price
				productInfoList = getProductListMapBySpecifications(
						"salesPrice", "desc", level);
			} else if ("putSaleDate".equals(orderBy)) {// 6:排序（产品上架时间降序）：putSaleDate
														// Latest
				productInfoList = getProductListMapBySpecifications(
						"putSaleDate", "desc", level);
			} else if ("totalSales".equals(orderBy)) {// 7:排序（产品销售量降序）：totalSales
														// Popular
				productInfoList = getProductListMapBySpecifications(
						"totalSales", "desc", level);
			}
			jo.accumulate("ProductInfoList", productInfoList);
			// 判断是否是热卖商品
			Map map = null;
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -6);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			Date date = cal.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String d = sdf.format(date);
			int hotSaleNumber = Integer.valueOf(shopConfig.get("hotSaleNumber")
					.toString());
			if (productInfoList != null) {
				for (int i = 0; i < productInfoList.size(); i++) {
					map = (Map) productInfoList.get(i);
					String hql = " select count(a.ordersId) from Orders a,OrdersList b where a.ordersId = b.ordersId and b.productId ="
							+ map.get("productId")
							+ " and unix_timestamp(a.createTime) > unix_timestamp('"
							+ d + "') ";
					int total = productInfoService.getCountByHQL(hql);
					if (total > hotSaleNumber) {
						map.put("recentlySold", 1);
					} else {
						map.put("recentlySold", 0);
					}
					String goodsCountHql = "select count(a.goods) as goodsCount from ProductInfo a where a.goods="
							+ map.get("goods")
							+ " and a.categoryLevel"
							+ level
							+ " = "
							+ productTypeId
							+ "  and a.isPutSale=2 and a.isPass=1";
					int goodsCount = productInfoService
							.getCountByHQL(goodsCountHql);
					map.put("goodsCount", goodsCount);
				}
			}
			// 查询品牌下商品个数
			for (Map map1 : brandList) {
				String hql = " SELECT count(a.productId) FROM ProductInfo a,ShopInfo c WHERE a.shopInfoId=c.shopInfoId and a.isPass=1 and a.isPutSale=2 and c.isPass=3 and c.isClose=0 and a.isShow=1 and a.brandId="
						+ map1.get("brandId")
						+ " and a.categoryLevel"
						+ level
						+ " = " + productTypeId;
				int total = productInfoService.getCountByHQL(hql);
				map1.put("total", total);
			}
			// 查询分类下商品个数
			if (!isTrue) {
				String ids = null;
				for (Map m : productTypeList1) {
					categoryIds = "";
					ids = getAllProductTypeId(Integer.parseInt(m.get(
							"productTypeId").toString()));
					ids = ids.substring(0, ids.length() - 1);
					String hql = " SELECT count(a.productId) FROM ProductInfo a,ShopInfo c WHERE a.shopInfoId=c.shopInfoId and a.isPass=1 and a.isPutSale=2 and c.isPass=3 and c.isClose=0 and a.isShow=1 and a.categoryLevel"
							+ m.get("level").toString()
							+ " = "
							+ m.get("productTypeId").toString()
							+ " AND a.isTop=0 ";
					int total = productInfoService.getCountByHQL(hql);
					m.put("total", total);
				}
			}
		}
		pw.print(jo);
		pw.flush();
		pw.close();
	}

	/**
	 * 递归查询当前位置
	 * 
	 * @param prodTypeId
	 */
	public void getProductTypeName(Integer prodTypeId, String address,
			String level) {
		String path = request.getContextPath();
		ProductType pt = (ProductType) productTypeService
				.getObjectByParams(" where o.productTypeId=" + prodTypeId);
		if (StringUtils.isNotEmpty(prodTypeNames)) {
			prodTypeNames = "<a href='" + path + "/frontBrands/" + address
					+ ".action?productTypeId=" + prodTypeId + "&level="
					+ pt.getLevel() + "&orderBy=normal'>" + pt.getSortName()
					+ "</a>" + "&nbsp;&gt;&nbsp;" + prodTypeNames;
		} else {
			prodTypeNames = "<a href='" + path + "/frontBrands/" + address
					+ ".action?productTypeId=" + prodTypeId + "&level="
					+ pt.getLevel() + "&orderBy=normal'>" + pt.getSortName()
					+ "</a>";
		}
		if (pt != null && pt.getParentId() != 1) {
			getProductTypeName(pt.getParentId(), address, level);
		}
	}

	public Integer countProduct(String ids) {
		String hql = " SELECT count(a.productId) FROM ProductInfo a,ShopInfo c WHERE a.shopInfoId=c.shopInfoId and a.isPass=1 and a.isPutSale=2 and c.isPass=3 and c.isClose=0 and a.isShow=1 and a.productTypeId in ("
				+ ids + ") AND a.isTop=0 ";
		int total = productInfoService.getCountByHQL(hql);
		return total;
	}

	/**
	 * 用递归方式获得子类分类的Id
	 */
	public String getAllProductTypeId(Integer productTypeId) {
		categoryIds += productTypeId.toString() + ",";
		List<ProductType> productTypeList = productTypeService
				.findObjects(" where o.parentId = " + productTypeId
						+ " and o.isShow=1");
		if (productTypeList != null && productTypeList.size() > 0) {
			for (ProductType pt : productTypeList) {
				getAllProductTypeId(pt.getProductTypeId());
			}
		}
		return categoryIds;
	}

	/**
	 * 根据商品分类查询品牌
	 * 
	 * @param productTypeId
	 *            商品分类id
	 * @param categoryIds
	 *            一个空串
	 * @return List<Map> 品牌集合(只有两个字段 brandId,brandName)
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> getBrandInfo(Integer productTypeId) {
		String ids = getAllProductTypeId(productTypeId);
		ids = ids.substring(0, ids.lastIndexOf(","));
		String hql = "SELECT d.brandId as brandId ,d.brandName as brandName FROM ProductType a,BrandType c ,Brand d WHERE a.productTypeId=c.productTypeId and c.brandId=d.brandId  AND a.productTypeId in ("
				+ ids + ") group by d.brandId";
		List<Map> findListMapByHql = productTypeService.findListMapByHql(hql);
		return findListMapByHql;
	}

	/**
	 * 排序查询
	 * 
	 * @param specificationsParams
	 *            规格ids 例(1,2,3)
	 * @param orderByParams
	 *            要排序的字段
	 * @param collate
	 *            升序或降序
	 * @return pList 商品集合 格式(List<Map>)
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> getProductListMapBySpecifications(String orderByParams,
			String collate, String level) {
		List<Map> pList = new ArrayList<Map>();// 商品list
		StringBuffer hql = new StringBuffer();// 最终要查询的hql
		hqlWhere.append(" and a.categoryLevel" + level + " = " + productTypeId
				+ " ");
		if (isTrue && specificationValueId != null
				&& !"".equals(specificationValueId)) {
			List<Map> list = specificationValueService
					.findListMapByHql(" select distinct a.goodId as goodId from ProductSpecificationValue a where a.specificationValueId in ("
							+ specificationValueId + ") ");
			StringBuilder str = new StringBuilder();
			for (Map map : list) {
				str.append(map.get("goodId").toString()).append(",");
			}
			if (str != null && !"".equals(str.toString())) {
				str.deleteCharAt(str.length() - 1);
				hqlWhere.append(" and a.goods in (" + str + ") ");
			} else {
				// 此处增加一个永不成立的条件 因为用户已经勾选了规格 但是没有符合的商品 那么直接控制不出商品即可
				hqlWhere.append(" and 1=2 ");
			}
		}

		if (isTrue && attrValueId != null && !"".equals(attrValueId)) {
			String[] split = attrValueId.split(",");
			String attrSql = "select b.productId as productId from (select COUNT(a.productId) as count ,a.productId as productId from shop_product_attribute_index a where a.productTypeId="
					+ productTypeId
					+ " and a.attrValueId in ("
					+ attrValueId
					+ ") group by a.productId) b where b.count=" + split.length;
			List<Map<String, Object>> list = productAttrIndexService
					.findListMapBySql(attrSql);
			if (list != null && list.size() > 0) {
				// 从定义的临时集合tmpList中取出商品Id 形成商品ids
				String productIds = "";
				for (Map map : list) {
					Object object = map.get("productId");
					productIds += String.valueOf(object) + ",";
				}
				if (StringUtils.isNotEmpty(productIds)) {
					productIds = productIds.substring(0,
							productIds.lastIndexOf(","));
					hqlWhere.append(" and a.productId in (" + productIds + ") ");
				}
			} else {
				// 此处增加一个永不成立的条件 因为用户已经勾选了属性 但是没有符合的商品 那么直接控制不出商品即可
				hqlWhere.append(" and 1=2 ");
			}
		}
		coutHql.append(hqlWhere);// 总条数hql
		hql.append(hqlSelect).append(hqlWhere)
				.append("  order by a." + orderByParams + " " + collate);// 商品hql
		int totalRecordCount = productInfoService.getMultilistCount(coutHql
				.toString());
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		pList = productInfoService.findListMapPage(hql.toString(), pageHelper);// 非顶置商品分页
		return pList;
	}

	public void setProductTypeId(Integer productTypeId) {
		this.productTypeId = productTypeId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public void setSpecificationValueId(String specificationValueId) {
		this.specificationValueId = specificationValueId;
	}

	public void setAttrValueId(String attrValueId) {
		this.attrValueId = attrValueId;
	}

	public void setProductInfoService(IProductInfoService productInfoService) {
		this.productInfoService = productInfoService;
	}

	public void setProductTypeService(IProductTypeService productTypeService) {
		this.productTypeService = productTypeService;
	}

	public void setProductTypeList(List<ProductType> productTypeList) {
		this.productTypeList = productTypeList;
	}

	public void setProductTypeList1(List<Map> productTypeList1) {
		this.productTypeList1 = productTypeList1;
	}

	public void setProductInfoList(List productInfoList) {
		this.productInfoList = productInfoList;
	}

	public void setProductInfoList1(List productInfoList1) {
		this.productInfoList1 = productInfoList1;
	}

	public void setProdTypeNames(String prodTypeNames) {
		this.prodTypeNames = prodTypeNames;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public void setMinPrice(BigDecimal minPrice) {
		this.minPrice = minPrice;
	}

	public void setMaxPrice(BigDecimal maxPrice) {
		this.maxPrice = maxPrice;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public void setBrandTypeService(IBrandTypeService brandTypeService) {
		this.brandTypeService = brandTypeService;
	}

	public void setBrandList(List<Map> brandList) {
		this.brandList = brandList;
	}

	public void setBrandList2(List<Map> brandList2) {
		this.brandList2 = brandList2;
	}

	public void setMapParams(Map<String, Object> mapParams) {
		this.mapParams = mapParams;
	}

	public void setHqlWhere(StringBuffer hqlWhere) {
		this.hqlWhere = hqlWhere;
	}

	public void setHqlSelect(StringBuffer hqlSelect) {
		this.hqlSelect = hqlSelect;
	}

	public void setCoutHql(StringBuffer coutHql) {
		this.coutHql = coutHql;
	}

	public void setCategoryIds(String categoryIds) {
		this.categoryIds = categoryIds;
	}

	public void setIsTrue(Boolean isTrue) {
		this.isTrue = isTrue;
	}

	public void setSpecificationService(
			ISpecificationService specificationService) {
		this.specificationService = specificationService;
	}

	public void setSpecificationList(List<Specification> specificationList) {
		this.specificationList = specificationList;
	}

	public void setSpecificationValueService(
			ISpecificationValueService specificationValueService) {
		this.specificationValueService = specificationValueService;
	}

	public void setAttributeValueService(
			IAttributeValueService attributeValueService) {
		this.attributeValueService = attributeValueService;
	}

	public void setProductAttrIndexService(
			IProductAttrIndexService productAttrIndexService) {
		this.productAttrIndexService = productAttrIndexService;
	}

	public void setLevel(String level) {
		this.level = level;
	}

}
