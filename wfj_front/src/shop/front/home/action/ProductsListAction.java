package shop.front.home.action;

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

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import shop.product.pojo.Brand;
import shop.product.pojo.BrandType;
import shop.product.pojo.ProductInfo;
import shop.product.pojo.ProductType;
import shop.product.pojo.Specification;
import shop.product.pojo.SpecificationValue;
import shop.product.service.IAttributeValueService;
import shop.product.service.IBrandService;
import shop.product.service.IBrandTypeService;
import shop.product.service.IProductAttrIndexService;
import shop.product.service.IProductAttributeService;
import shop.product.service.IProductInfoService;
import shop.product.service.IProductTypeService;
import shop.product.service.ISpecificationService;
import shop.product.service.ISpecificationValueService;
import shop.search.service.ISearchService;
import shop.store.pojo.ShopInfo;
import shop.store.service.IShopInfoService;
import util.action.BaseAction;
import util.other.Escape;
import util.other.Pager;
import util.other.Pager.OrderType;
import util.other.Utils;

/** 
 * getBrandsAndProductInfos - 品牌商品Action类
 */
/**
 * @author CTR
 *
 */
@SuppressWarnings("serial")
public class ProductsListAction extends BaseAction {
	@Resource
	private IProductInfoService productInfoService;// 商品信息Service
	@Resource
	private IProductTypeService productTypeService;// 分类Service
	private List<ProductType> productTypeList = new ArrayList<ProductType>();
	private List<Map> productTypeList1;
	@SuppressWarnings("rawtypes")
	private List productInfoList = new ArrayList();// 非顶置商品集合
	@SuppressWarnings("rawtypes")
	private List productInfoList1 = new ArrayList();// 顶置商品集合
	private String prodTypeNames = "";
	private ProductType productType;// 商品分类
	private Integer productTypeId;// 商品分类ID
	private String level = "1";
	private Integer pageIndex = 1;// 当前页
	private Integer pageSize = 20;// 分页-每一页显示的个数
	private BigDecimal minPrice;// 价格
	private BigDecimal maxPrice;// 价格范围：minPrice-maxPrice之间
	private String orderBy;// 标记(用于排序)
	/** 品牌service **/
	private IBrandService brandService;
	private IBrandTypeService brandTypeService;
	private List<Map> brandList = new ArrayList<Map>();// 品牌列表集合(下方图片展示)
	@SuppressWarnings("rawtypes")
	private List<Map> brandList2 = new ArrayList<Map>();// 品牌列表集合(查询条件)
	/** 用于参数的回显 **/
	private Map<String, Object> mapParams = new LinkedHashMap<String, Object>();
	/** 商品hql语句select部分 **/
	private StringBuffer hqlSelect = new StringBuffer(
			" SELECT distinct a.goods as goods,a.productName as productName,a.productFullName as productFullName, "
					+ "a.productId as productId, a.describle as describle,a.marketPrice as marketPrice,a.salesPrice as salesPrice,"
					+ "a.logoImg as logoImg,c.shopName as shopName,a.brandId as brandId,a.shopInfoId as shopInfoId "
					+ "FROM ProductInfo a,ShopInfo c ");
	/** 商品hql语句where部分 **/
	private StringBuffer hqlWhere = new StringBuffer(
			" WHERE  a.shopInfoId=c.shopInfoId and a.isPass=1 and a.isPutSale=2 and c.isPass=3 and c.isClose=0 and a.isShow=1 and a.isTop=0");
	/** 商品的总条数select部分 **/
	private StringBuffer coutHql = new StringBuffer(" SELECT count(a.productId)  FROM ProductInfo a,ShopInfo c ");
	/** 分类ids **/
	private String categoryIds = "";
	private String brandParams;
	/** 全文检索定义所需属性 **/
	@SuppressWarnings("rawtypes")
	private List<Map> productToBrandList = new ArrayList<Map>();// 根据商品查找品牌列表集合(查询条件)
	@SuppressWarnings("rawtypes")
	private List<Map> productToTypeList = new ArrayList<Map>();// 根据商品查找分类列表集合(查询条件)
	private Pager pager;// 定义pager对象，保存分页和分页查询集合
	private String orderType;// 排序字段(商品排序)
	private String viewType;// 展示字段(商品展示方式：表格列表，图片)
	/***** 判断是否是4级节点 ******/
	private Boolean isTrue = true;// 如果是false则不是4级节点
	/** 根据点击的品牌查询商品 **/
	private String brandId;
	/*** 规格 ***/
	private ISpecificationService specificationService;
	/** 指定分类下的规格 **/
	List<Specification> specificationList = new ArrayList<Specification>();
	private ISpecificationValueService specificationValueService;
	private List<SpecificationValue> specificationValueList;
	private String specificationValueId;
	/** 商品扩展属性service **/
	private IProductAttributeService productAttributeService;
	/** 商品扩展属性值service **/
	private IAttributeValueService attributeValueService;
	/** 商品关联扩展属性值中间表service **/
	private IProductAttrIndexService productAttrIndexService;
	private String attrValueId;
	private String attrIds;
	/** 搜索引擎service **/
	private ISearchService searchService;
	/** ShopsService */
	private IShopInfoService shopInfoService;

	private List<ShopInfo> shops;

	/**
	 * 产品图片
	 * 
	 * @return
	 */
	public String getBrandsAndProductInfos() {
		photoAndList(productTypeId, "getBrandsAndProductInfos", brandId, level);
		return SUCCESS;
	}

	/**
	 * 产品图片
	 * 
	 * @return
	 */
	public String getShopsAndProductInfos() {
		String address = "getBrandsAndProductInfos";
		// 判断是否是叶子节点isTrue=true;
		ProductType pType = new ProductType();
		pType = (ProductType) productTypeService.getObjectById(String.valueOf(productTypeId));
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
			productTypeList1 = productTypeService.findListMapByHql(
					" select a.productTypeId as productTypeId,a.sortName as sortName,a.level as level from ProductType a where a.parentId ="
							+ pType.getProductTypeId());
			if (isTrue) {
				// 查询分类下的品牌
				brandList = productTypeService.queryBrandByProductTypeId(pType.getProductTypeId().toString());
				// 查询分类下属性及属性值
				// 查询出当前分类下的所有属性 以map的形式保存
				List<Map<String, Object>> attrListMap = productAttrIndexService.findListMapBySql(
						"select a.productAttrId,a.name from shop_product_attribute a where a.productTypeId="
								+ pType.getProductTypeId().toString() + " and a.isListShow=1 order by a.sort");
				if (attrListMap != null && attrListMap.size() > 0) {
					for (Map<String, Object> map : attrListMap) {
						Integer productAttrId = Integer.parseInt(String.valueOf(map.get("productAttrId")));
						// 查询对应的属性值
						List<Map<String, Object>> attrValueListMap = attributeValueService.findListMapBySql(
								"select a.attrValueId,a.attrValueName from shop_attribute_value a where a.productAttrId="
										+ productAttrId + " order by a.sort");
						if (attrValueListMap != null && attrValueListMap.size() > 0) {
							map.put("attrValueListMap", attrValueListMap);
							// 查询每一个属性值所对应的商品个数
							for (Map<String, Object> avmap : attrValueListMap) {
								Object object = avmap.get("attrValueId");
								if (object != null) {
									String avid = String.valueOf(object);
									String hql = "select count(a.productId) as count from ProductAttrIndex a,ProductInfo b,ShopInfo c where a.productId=b.productId and b.shopInfoId=c.shopInfoId and b.isPass=1 and b.isPutSale=2 and b.isShow=1 and c.isPass=3 and b.categoryLevel"
											+ level + " = " + productTypeId + " and c.isClose=0 and a.productTypeId="
											+ productTypeId + " and a.attrValueId=" + avid;
									List<Map> findListMapBySql = productAttrIndexService.findListMapByHql(hql);
									if (findListMapBySql != null && findListMapBySql.size() > 0) {
										Map<String, Object> map2 = findListMapBySql.get(0);
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
				hqlWhere.append(" and a.salesPrice>=" + String.valueOf(minPrice));
			}
			if (Utils.objectIsNotEmpty(maxPrice)) {
				hqlWhere.append(" and a.salesPrice<=" + String.valueOf(maxPrice));
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
							.findObjects(" where o.brandId in (" + brandId + ") ");
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
						.findObjects(" where o.productTypeId=" + pType.getProductTypeId() + " ");
				Map<String, List<Map>> specificationMap = new HashMap<String, List<Map>>();
				for (Specification specification : specificationList) {
					List<Map> list = specificationValueService.findListMapByHql(
							" select a.specificationValueId as specificationValueId,a.name as name from SpecificationValue a where a.specificationId="
									+ specification.getSpecificationId() + " ");
					for (Map m : list) {
						String hql = " SELECT count(a.productId) FROM ProductInfo a,ShopInfo c,ProductSpecificationValue d WHERE a.shopInfoId=c.shopInfoId and a.isPass=1 and a.isPutSale=2 and c.isPass=3 and c.isClose=0 and a.isShow=1 and a.categoryLevel"
								+ level + " = " + productTypeId + "  and a.productTypeId = " + pType.getProductTypeId()
								+ " AND a.isTop=0 and a.productId = d.productId and d.specificationValueId= "
								+ m.get("specificationValueId");
						int total = specificationValueService.getCountByHQL(hql);
						m.put("total", total);
					}
					specificationMap.put(specification.getName(), list);
				}
				request.setAttribute("specificationMap", specificationMap);
			}
			if ("normal".equals(orderBy)) {// 初始化进入产品图片（列表）
				productInfoList = getProductListMapBySpecifications("productId", "desc", level);
			} else if ("salesPriceAsc".equals(orderBy)) {// 3:排序：（按价格排序）：salesPrice升序
															// Price
				productInfoList = getProductListMapBySpecifications("salesPrice", "asc", level);
			} else if ("salesPriceDesc".equals(orderBy)) {// 4:排序：（按价格排序）：salesPrice降序
															// Price
				productInfoList = getProductListMapBySpecifications("salesPrice", "desc", level);
			} else if ("putSaleDate".equals(orderBy)) {// 6:排序（产品上架时间降序）：putSaleDate
														// Latest
				productInfoList = getProductListMapBySpecifications("putSaleDate", "desc", level);
			} else if ("totalSales".equals(orderBy)) {// 7:排序（产品销售量降序）：totalSales
														// Popular
				productInfoList = getProductListMapBySpecifications("totalSales", "desc", level);
			}
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
			int hotSaleNumber = Integer.valueOf(shopConfig.get("hotSaleNumber").toString());
			if (productInfoList != null) {
				for (int i = 0; i < productInfoList.size(); i++) {
					map = (Map) productInfoList.get(i);
					String hql = " select count(a.ordersId) from Orders a,OrdersList b where a.ordersId = b.ordersId and b.productId ="
							+ map.get("productId") + " and unix_timestamp(a.createTime) > unix_timestamp('" + d + "') ";
					int total = productInfoService.getCountByHQL(hql);
					if (total > hotSaleNumber) {
						map.put("recentlySold", 1);
					} else {
						map.put("recentlySold", 0);
					}
					String goodsCountHql = "select count(a.goods) as goodsCount from ProductInfo a where a.goods="
							+ map.get("goods") + " and a.categoryLevel" + level + " = " + productTypeId
							+ "  and a.isPutSale=2 and a.isPass=1";
					int goodsCount = productInfoService.getCountByHQL(goodsCountHql);
					map.put("goodsCount", goodsCount);
				}
			}
			// 查询品牌下商品个数
			for (Map map1 : brandList) {
				String hql = " SELECT count(a.productId) FROM ProductInfo a,ShopInfo c WHERE a.shopInfoId=c.shopInfoId and a.isPass=1 and a.isPutSale=2 and c.isPass=3 and c.isClose=0 and a.isShow=1 and a.brandId="
						+ map1.get("brandId") + " and a.categoryLevel" + level + " = " + productTypeId;
				int total = productInfoService.getCountByHQL(hql);
				map1.put("total", total);
			}
			// 查询分类下商品个数
			if (!isTrue) {
				String ids = null;
				for (Map m : productTypeList1) {
					categoryIds = "";
					ids = getAllProductTypeId(Integer.parseInt(m.get("productTypeId").toString()));
					ids = ids.substring(0, ids.length() - 1);
					String hql = " SELECT count(a.productId) FROM ProductInfo a,ShopInfo c WHERE a.shopInfoId=c.shopInfoId and a.isPass=1 and a.isPutSale=2 and c.isPass=3 and c.isClose=0 and a.isShow=1 and a.categoryLevel"
							+ m.get("level").toString() + " = " + m.get("productTypeId").toString() + " AND a.isTop=0 ";
					int total = productInfoService.getCountByHQL(hql);
					m.put("total", total);
				}
			}
			//
			String hql = hqlSelect.append(hqlWhere) + " and o.shopInfoId=a.shopInfoId";
			shops = shopInfoService.findSome(0, 10, "where exists (" + hql + ")");
			for (int i = 0; i < shops.size(); i++) {
				shops.get(i).setProductInfoMap(productInfoService
						.findListMapByHql(hqlSelect.toString() + "and a.shopInfoId=" + shops.get(i).getShopInfoId()));
			}
		}
		return SUCCESS;
	}

	/**
	 * 产品列表
	 * 
	 * @return
	 */
	public String list() {
		photoAndList(productTypeId, "list", brandId, level);
		return SUCCESS;
	}

	/**
	 * 全局搜索查询产品图片
	 * 查询商品需要满足的条件：isTop=0(默认非置顶)、isPass=1(默认审核通过)、isPutSale=2(默认上架)、isShow=1(
	 * 商品显示)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public String search() throws Exception {
		// 推荐品牌
		List<Brand> brandList = brandService.findSome(0, 20, "where o.isShow=1 and o.isRecommend=0 order by o.sortCode");
		request.setAttribute("brandList", brandList);
		String keyword = pager.getKeyword();
		if (!StringUtils.isEmpty(keyword)) {
			keyword = Escape.unescape((keyword.trim()));
			if (Utils.stringIsNotEmpty(keyword))
				pager.setKeyword(keyword);
		}
		// 根据价格范围搜索
		/*
		 * if(StringUtils.isNotEmpty(maxPrice)){ String
		 * priceStr="$"+minPrice+"-"+"$"+maxPrice;
		 * mapParams.put("Price",priceStr); }
		 */
		// 对象如果为空
		if (pager == null) {
			pager = new Pager();
			// 搜索结果为空，随机查询商品集合
			return "noResults";
		} else {
			if (StringUtils.isNotEmpty(pager.getKeyword())) {
				String str = pager.getKeyword().trim();
				str = str.replaceAll("[()\\[\\]\\|~|$|^|<|>|\\||\\+|=?]*", "");
				if (Utils.objectIsNotEmpty(str)) {
					pager.setKeyword(str);// 清除首尾空格
				} else {
					// 替换特殊字符为空
					return "noResults";
				}
			} else {
				// 搜索结果为空，随机查询商品集合
				return "noResults";
			}
		}
		pager.setPageSize(12);// 设置默认每页显示多少条
		if (StringUtils.equalsIgnoreCase(orderType, "salesPriceAsc")) {// 判断价格是否排序asc
			pager.setOrderBy("salesPrice");
			pager.setOrderType(OrderType.asc);
		} else if (StringUtils.equalsIgnoreCase(orderType, "salesPriceDesc")) {// 判断价格是否排序desc
			pager.setOrderBy("salesPrice");
			pager.setOrderType(OrderType.desc);
		} else if (StringUtils.equalsIgnoreCase(orderType, "putSaleDateDesc")) {// 判断上架时间是否排序desc
			pager.setOrderBy("putSaleDate");
			pager.setOrderType(OrderType.desc);
		} else if (StringUtils.equalsIgnoreCase(orderType, "totalSalesDesc")) {// 判断销售量是否排序desc
			pager.setOrderBy("totalSales");
			pager.setOrderType(OrderType.desc);
		} else {// 排序条件为空时，默认排序规则：按照更新时间倒序
			pager.setOrderBy(null);
			pager.setOrderType(null);
		}
		try {
			// 根据某一分类搜索
			if (Utils.objectIsEmpty(productTypeId) && Utils.objectIsEmpty(minPrice) && Utils.objectIsEmpty(maxPrice)) {
				// 根据分页内容，全文检索商品，返回商品检索集合 pager.getList()
				pager = searchService.searchProductInfos(pager);
			} else {
				// 根据分页内容，查询条件，全文检索商品，返回商品检索集合 pager.getList()
				pager = searchService.searchProductInfos(pager, productTypeId, minPrice, maxPrice);
			}
		} catch (org.apache.lucene.queryParser.ParseException e) {
			return "noResults";// 抛出特殊字符异常转发到无结果
		} catch (org.compass.core.engine.SearchEngineQueryParseException e) {
			return "noResults";// 抛出特殊字符异常转发到无结果
		}
		// 转换分页中商品集合
		productInfoList = pager.getList();
		if (Utils.collectionIsNotEmpty(productInfoList)) {// 搜索商品集合不为空
			List productListAll = pager.getListAll();// 取出查询的全部商品信息
			// 根据全部商品ID查询商品对应分类信息
			productToTypeList = productInfoService.getProductToTypeInfo(productListAll);
			// 根据全部商品ID查询商品对应品牌信息
			productToBrandList = productInfoService.getProductToBrandInfo(productListAll);
			// 查询分类下的推荐品牌
			ProductInfo productInfo = (ProductInfo) productInfoList.get(0);
			// 如果商品集合不为空 则统计商品种类数量
			for (Object o : productListAll) {
				String goodsCountHql = "select count(a.goods) as goodsCount from ProductInfo a where a.goods="
						+ ((ProductInfo) o).getGoods() + " and a.isPutSale=2 and a.isPass=1";
				int goodsCount = productInfoService.getCountByHQL(goodsCountHql);
				((ProductInfo) o).setGoodsCount(goodsCount);
			}
		} else {
			// 搜索结果为空，随机查询商品集合
			productInfoList = productInfoService.findRandomProductInfoList();
			return "noResults";
		}
		if (StringUtils.equalsIgnoreCase(viewType, "tableType")) {
			return "tableSearch";
		} else {
			return "pictureSearch";
		}
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
	public List<Map> getProductListMapBySpecifications(String orderByParams, String collate, String level) {
		List<Map> pList = new ArrayList<Map>();// 商品list
		StringBuffer hql = new StringBuffer();// 最终要查询的hql
		hqlWhere.append(" and a.categoryLevel" + level + " = " + productTypeId + " ");
		if (isTrue && specificationValueId != null && !"".equals(specificationValueId)) {
			List<Map> list = specificationValueService.findListMapByHql(
					" select distinct a.goodId as goodId from ProductSpecificationValue a where a.specificationValueId in ("
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
					+ productTypeId + " and a.attrValueId in (" + attrValueId
					+ ") group by a.productId) b where b.count=" + split.length;
			List<Map<String, Object>> list = productAttrIndexService.findListMapBySql(attrSql);
			if (list != null && list.size() > 0) {
				// 从定义的临时集合tmpList中取出商品Id 形成商品ids
				String productIds = "";
				for (Map map : list) {
					Object object = map.get("productId");
					productIds += String.valueOf(object) + ",";
				}
				if (StringUtils.isNotEmpty(productIds)) {
					productIds = productIds.substring(0, productIds.lastIndexOf(","));
					hqlWhere.append(" and a.productId in (" + productIds + ") ");
				}
			} else {
				// 此处增加一个永不成立的条件 因为用户已经勾选了属性 但是没有符合的商品 那么直接控制不出商品即可
				hqlWhere.append(" and 1=2 ");
			}
		}
		coutHql.append(hqlWhere);// 总条数hql
		hql.append(hqlSelect).append(hqlWhere).append("  order by a." + orderByParams + " " + collate);// 商品hql
		int totalRecordCount = productInfoService.getMultilistCount(coutHql.toString());
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		pList = productInfoService.findListMapPage(hql.toString(), pageHelper);// 非顶置商品分页
		return pList;
	}

	/**
	 * 递归查询当前位置
	 * 
	 * @param prodTypeId
	 */
	public void getProductTypeName(Integer prodTypeId, String address, String level) {
		String path = request.getContextPath();
		ProductType pt = (ProductType) productTypeService.getObjectByParams(" where o.productTypeId=" + prodTypeId);
		if (StringUtils.isNotEmpty(prodTypeNames)) {
			prodTypeNames = "<a href='" + path + "/frontBrands/" + address + ".action?productTypeId=" + prodTypeId
					+ "&level=" + pt.getLevel() + "&orderBy=normal'>" + pt.getSortName() + "</a>" + "&nbsp;&gt;&nbsp;"
					+ prodTypeNames;
		} else {
			prodTypeNames = "<a href='" + path + "/frontBrands/" + address + ".action?productTypeId=" + prodTypeId
					+ "&level=" + pt.getLevel() + "&orderBy=normal'>" + pt.getSortName() + "</a>";
		}
		if (pt != null && pt.getParentId() != 1) {
			getProductTypeName(pt.getParentId(), address, level);
		}
	}

	/**
	 * 图片与列表-是主方法
	 * 
	 * @param address
	 *            链接调用action的名称
	 * @param productInfoService
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void photoAndList(Integer productTypeId, String address, String brandId, String level) {
		// 判断是否是叶子节点isTrue=true;
		ProductType pType = new ProductType();
		pType = (ProductType) productTypeService.getObjectById(String.valueOf(productTypeId));
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
			productTypeList1 = productTypeService.findListMapByHql(
					" select a.productTypeId as productTypeId,a.sortName as sortName,a.level as level from ProductType a where a.parentId ="
							+ pType.getProductTypeId());
			if (isTrue) {
				// 查询分类下的品牌
				brandList = productTypeService.queryBrandByProductTypeId(pType.getProductTypeId().toString());
				// 查询分类下属性及属性值
				// 查询出当前分类下的所有属性 以map的形式保存
				List<Map<String, Object>> attrListMap = productAttrIndexService.findListMapBySql(
						"select a.productAttrId,a.name from shop_product_attribute a where a.productTypeId="
								+ pType.getProductTypeId().toString() + " and a.isListShow=1 order by a.sort");
				if (attrListMap != null && attrListMap.size() > 0) {
					for (Map<String, Object> map : attrListMap) {
						Integer productAttrId = Integer.parseInt(String.valueOf(map.get("productAttrId")));
						// 查询对应的属性值
						List<Map<String, Object>> attrValueListMap = attributeValueService.findListMapBySql(
								"select a.attrValueId,a.attrValueName from shop_attribute_value a where a.productAttrId="
										+ productAttrId + " order by a.sort");
						if (attrValueListMap != null && attrValueListMap.size() > 0) {
							map.put("attrValueListMap", attrValueListMap);
							// 查询每一个属性值所对应的商品个数
							for (Map<String, Object> avmap : attrValueListMap) {
								Object object = avmap.get("attrValueId");
								if (object != null) {
									String avid = String.valueOf(object);
									String hql = "select count(a.productId) as count from ProductAttrIndex a,ProductInfo b,ShopInfo c where a.productId=b.productId and b.shopInfoId=c.shopInfoId and b.isPass=1 and b.isPutSale=2 and b.isShow=1 and c.isPass=3 and b.categoryLevel"
											+ level + " = " + productTypeId + " and c.isClose=0 and a.productTypeId="
											+ productTypeId + " and a.attrValueId=" + avid;
									List<Map> findListMapBySql = productAttrIndexService.findListMapByHql(hql);
									if (findListMapBySql != null && findListMapBySql.size() > 0) {
										Map<String, Object> map2 = findListMapBySql.get(0);
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
				hqlWhere.append(" and a.salesPrice>=" + String.valueOf(minPrice));
			}
			if (Utils.objectIsNotEmpty(maxPrice)) {
				hqlWhere.append(" and a.salesPrice<=" + String.valueOf(maxPrice));
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
							.findObjects(" where o.brandId in (" + brandId + ") ");
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
						.findObjects(" where o.productTypeId=" + pType.getProductTypeId() + " ");
				Map<String, List<Map>> specificationMap = new HashMap<String, List<Map>>();
				for (Specification specification : specificationList) {
					List<Map> list = specificationValueService.findListMapByHql(
							" select a.specificationValueId as specificationValueId,a.name as name from SpecificationValue a where a.specificationId="
									+ specification.getSpecificationId() + " ");
					for (Map m : list) {
						String hql = " SELECT count(a.productId) FROM ProductInfo a,ShopInfo c,ProductSpecificationValue d WHERE a.shopInfoId=c.shopInfoId and a.isPass=1 and a.isPutSale=2 and c.isPass=3 and c.isClose=0 and a.isShow=1 and a.categoryLevel"
								+ level + " = " + productTypeId + "  and a.productTypeId = " + pType.getProductTypeId()
								+ " AND a.isTop=0 and a.productId = d.productId and d.specificationValueId= "
								+ m.get("specificationValueId");
						int total = specificationValueService.getCountByHQL(hql);
						m.put("total", total);
					}
					specificationMap.put(specification.getName(), list);
				}
				request.setAttribute("specificationMap", specificationMap);
			}
			if ("normal".equals(orderBy)) {// 初始化进入产品图片（列表）
				productInfoList = getProductListMapBySpecifications("productId", "desc", level);
			} else if ("salesPriceAsc".equals(orderBy)) {// 3:排序：（按价格排序）：salesPrice升序
															// Price
				productInfoList = getProductListMapBySpecifications("salesPrice", "asc", level);
			} else if ("salesPriceDesc".equals(orderBy)) {// 4:排序：（按价格排序）：salesPrice降序
															// Price
				productInfoList = getProductListMapBySpecifications("salesPrice", "desc", level);
			} else if ("putSaleDate".equals(orderBy)) {// 6:排序（产品上架时间降序）：putSaleDate
														// Latest
				productInfoList = getProductListMapBySpecifications("putSaleDate", "desc", level);
			} else if ("totalSales".equals(orderBy)) {// 7:排序（产品销售量降序）：totalSales
														// Popular
				productInfoList = getProductListMapBySpecifications("totalSales", "desc", level);
			}
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
			int hotSaleNumber = Integer.valueOf(shopConfig.get("hotSaleNumber").toString());
			if (productInfoList != null) {
				for (int i = 0; i < productInfoList.size(); i++) {
					map = (Map) productInfoList.get(i);
					String hql = " select count(a.ordersId) from Orders a,OrdersList b where a.ordersId = b.ordersId and b.productId ="
							+ map.get("productId") + " and unix_timestamp(a.createTime) > unix_timestamp('" + d + "') ";
					int total = productInfoService.getCountByHQL(hql);
					if (total > hotSaleNumber) {
						map.put("recentlySold", 1);
					} else {
						map.put("recentlySold", 0);
					}
					String goodsCountHql = "select count(a.goods) as goodsCount from ProductInfo a where a.goods="
							+ map.get("goods") + " and a.categoryLevel" + level + " = " + productTypeId
							+ "  and a.isPutSale=2 and a.isPass=1";
					int goodsCount = productInfoService.getCountByHQL(goodsCountHql);
					map.put("goodsCount", goodsCount);
				}
			}
			// 查询品牌下商品个数
			for (Map map1 : brandList) {
				String hql = " SELECT count(a.productId) FROM ProductInfo a,ShopInfo c WHERE a.shopInfoId=c.shopInfoId and a.isPass=1 and a.isPutSale=2 and c.isPass=3 and c.isClose=0 and a.isShow=1 and a.brandId="
						+ map1.get("brandId") + " and a.categoryLevel" + level + " = " + productTypeId;
				int total = productInfoService.getCountByHQL(hql);
				map1.put("total", total);
			}
			// 查询分类下商品个数
			if (!isTrue) {
				String ids = null;
				for (Map m : productTypeList1) {
					categoryIds = "";
					ids = getAllProductTypeId(Integer.parseInt(m.get("productTypeId").toString()));
					ids = ids.substring(0, ids.length() - 1);
					String hql = " SELECT count(a.productId) FROM ProductInfo a,ShopInfo c WHERE a.shopInfoId=c.shopInfoId and a.isPass=1 and a.isPutSale=2 and c.isPass=3 and c.isClose=0 and a.isShow=1 and a.categoryLevel"
							+ m.get("level").toString() + " = " + m.get("productTypeId").toString() + " AND a.isTop=0 ";
					int total = productInfoService.getCountByHQL(hql);
					m.put("total", total);
				}
			}
		}
	}

	public Integer countProduct(String ids) {
		String hql = " SELECT count(a.productId) FROM ProductInfo a,ShopInfo c WHERE a.shopInfoId=c.shopInfoId and a.isPass=1 and a.isPutSale=2 and c.isPass=3 and c.isClose=0 and a.isShow=1 and a.productTypeId in ("
				+ ids + ") AND a.isTop=0 ";
		int total = productInfoService.getCountByHQL(hql);
		return total;
	}

	/**
	 * 异步获取指定规格的规格值
	 */
	@SuppressWarnings("unchecked")
	public void getSpecificationValueBySpecificationId() {
		String specificationId = request.getParameter("specificationId");
		specificationValueList = ((List<SpecificationValue>) specificationValueService.findObjects(null,
				" where o.specificationId=" + specificationId));
		JSONArray jo = JSONArray.fromObject(specificationValueList);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用递归方式获得子类分类的Id
	 */
	public String getAllProductTypeId(Integer productTypeId) {
		categoryIds += productTypeId.toString() + ",";
		List<ProductType> productTypeList = productTypeService
				.findObjects(" where o.parentId = " + productTypeId + " and o.isShow=1");
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

	public void setProductInfoService(IProductInfoService productInfoService) {
		this.productInfoService = productInfoService;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public void setProductTypeService(IProductTypeService productTypeService) {
		this.productTypeService = productTypeService;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	public List<ProductType> getProductTypeList() {
		return productTypeList;
	}

	public void setProductTypeList(List<ProductType> productTypeList) {
		this.productTypeList = productTypeList;
	}

	@SuppressWarnings("rawtypes")
	public List getProductInfoList() {
		return productInfoList;
	}

	@SuppressWarnings("rawtypes")
	public void setProductInfoList(List productInfoList) {
		this.productInfoList = productInfoList;
	}

	@SuppressWarnings("rawtypes")
	public List getProductInfoList1() {
		return productInfoList1;
	}

	@SuppressWarnings("rawtypes")
	public void setProductInfoList1(List productInfoList1) {
		this.productInfoList1 = productInfoList1;
	}

	public Integer getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(Integer productTypeId) {
		this.productTypeId = productTypeId;
	}

	public Map<String, Object> getMapParams() {
		return mapParams;
	}

	public void setMapParams(Map<String, Object> mapParams) {
		this.mapParams = mapParams;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	@SuppressWarnings("rawtypes")
	public List<Map> getProductToBrandList() {
		return productToBrandList;
	}

	@SuppressWarnings("rawtypes")
	public void setProductToBrandList(List<Map> productToBrandList) {
		this.productToBrandList = productToBrandList;
	}

	public BigDecimal getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(BigDecimal minPrice) {
		this.minPrice = minPrice;
	}

	public BigDecimal getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(BigDecimal maxPrice) {
		this.maxPrice = maxPrice;
	}

	@SuppressWarnings("rawtypes")
	public List<Map> getProductToTypeList() {
		return productToTypeList;
	}

	@SuppressWarnings("rawtypes")
	public void setProductToTypeList(List<Map> productToTypeList) {
		this.productToTypeList = productToTypeList;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getViewType() {
		return viewType;
	}

	public void setViewType(String viewType) {
		this.viewType = viewType;
	}

	public String getProdTypeNames() {
		return prodTypeNames;
	}

	public void setProdTypeNames(String prodTypeNames) {
		this.prodTypeNames = prodTypeNames;
	}

	public List<Map> getBrandList() {
		return brandList;
	}

	public void setBrandList(List<Map> brandList) {
		this.brandList = brandList;
	}

	@SuppressWarnings("rawtypes")
	public List<Map> getBrandList2() {
		return brandList2;
	}

	@SuppressWarnings("rawtypes")
	public void setBrandList2(List<Map> brandList2) {
		this.brandList2 = brandList2;
	}

	public void setBrandService(IBrandService brandService) {
		this.brandService = brandService;
	}

	public String getBrandParams() {
		return brandParams;
	}

	public void setBrandParams(String brandParams) {
		this.brandParams = brandParams;
	}

	public Boolean getIsTrue() {
		return isTrue;
	}

	public void setIsTrue(Boolean isTrue) {
		this.isTrue = isTrue;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public void setSpecificationService(ISpecificationService specificationService) {
		this.specificationService = specificationService;
	}

	public void setSpecificationValueService(ISpecificationValueService specificationValueService) {
		this.specificationValueService = specificationValueService;
	}

	public List<SpecificationValue> getSpecificationValueList() {
		return specificationValueList;
	}

	public void setSpecificationValueList(List<SpecificationValue> specificationValueList) {
		this.specificationValueList = specificationValueList;
	}

	public List<Specification> getSpecificationList() {
		return specificationList;
	}

	public void setSpecificationList(List<Specification> specificationList) {
		this.specificationList = specificationList;
	}

	public void setBrandTypeService(IBrandTypeService brandTypeService) {
		this.brandTypeService = brandTypeService;
	}

	public String getSpecificationValueId() {
		return specificationValueId;
	}

	public void setSpecificationValueId(String specificationValueId) {
		this.specificationValueId = specificationValueId;
	}

	public List<Map> getProductTypeList1() {
		return productTypeList1;
	}

	public void setProductTypeList1(List<Map> productTypeList1) {
		this.productTypeList1 = productTypeList1;
	}

	public void setSearchService(ISearchService searchService) {
		this.searchService = searchService;
	}

	public void setProductAttributeService(IProductAttributeService productAttributeService) {
		this.productAttributeService = productAttributeService;
	}

	public void setAttributeValueService(IAttributeValueService attributeValueService) {
		this.attributeValueService = attributeValueService;
	}

	public void setProductAttrIndexService(IProductAttrIndexService productAttrIndexService) {
		this.productAttrIndexService = productAttrIndexService;
	}

	public String getAttrValueId() {
		return attrValueId;
	}

	public void setAttrValueId(String attrValueId) {
		this.attrValueId = attrValueId;
	}

	public String getAttrIds() {
		return attrIds;
	}

	public void setAttrIds(String attrIds) {
		this.attrIds = attrIds;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public IShopInfoService getShopInfoService() {
		return shopInfoService;
	}

	public void setShopInfoService(IShopInfoService shopInfoService) {
		this.shopInfoService = shopInfoService;
	}

	public List<ShopInfo> getShops() {
		return shops;
	}

	public void setShops(List<ShopInfo> shops) {
		this.shops = shops;
	}

}
