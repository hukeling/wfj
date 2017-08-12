package shop.front.home.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.commons.lang.StringUtils;

import rating.buyersRecord.service.IBuyersRecordService;
import shop.customer.pojo.Customer;
import shop.customer.pojo.CustomerCollectProduct;
import shop.customer.pojo.EvaluateGoods;
import shop.customer.service.ICustomerCollectProductService;
import shop.customer.service.IEvaluateGoodsService;
import shop.customer.service.IShopCustomerServiceService;
import shop.order.service.IOrdersListService;
import shop.order.service.IOrdersService;
import shop.product.pojo.Brand;
import shop.product.pojo.ProductAttribute;
import shop.product.pojo.ProductImg;
import shop.product.pojo.ProductInfo;
import shop.product.pojo.ProductSpecificationValue;
import shop.product.pojo.ProductType;
import shop.product.pojo.Specification;
import shop.product.pojo.SpecificationValue;
import shop.product.service.IBrandService;
import shop.product.service.IProductAttributeService;
import shop.product.service.IProductImgService;
import shop.product.service.IProductInfoService;
import shop.product.service.IProductSpecificationService;
import shop.product.service.IProductSpecificationValueService;
import shop.product.service.IProductTypeService;
import shop.product.service.ISpecificationService;
import shop.product.service.ISpecificationValueService;
import shop.store.pojo.MemberShip;
import shop.store.pojo.ShopInfo;
import shop.store.service.IMemberShipService;
import shop.store.service.IShopInfoService;
import shop.tags.pojo.ShopProductTradeSituationTag;
import shop.tags.pojo.ShopSituationTag;
import shop.tags.pojo.ShopTradeTag;
import shop.tags.service.IShopProductTradeSituationTagService;
import shop.tags.service.IShopSituationTagService;
import shop.tags.service.IShopTradeSituationTagService;
import shop.tags.service.IShopTradeTagService;
import util.action.BaseAction;
import util.other.Escape;
import util.other.Utils;
import basic.pojo.BasicArea;
import basic.service.IAreaService;

/**
 * ProductsDetailAction - 前台展示商品详细信息Action类
 * 
 * @Author
 */
@SuppressWarnings("serial")
public class ProductsDetailAction extends BaseAction {
	private String customerId;
	/** 商品信息Service **/
	private IProductInfoService productInfoService;
	/** 商品分类Service **/
	private IProductTypeService productTypeService;;
	/** 商品图片Service **/
	private IProductImgService productImgService;
	/** 店铺信息Service **/
	private IShopInfoService shopInfoService;
	/** 商品收藏中间表Service **/
	private ICustomerCollectProductService customerCollectProductService;
	/** 商品规格Service **/
	private ISpecificationService specificationService;
	/** 商品规格值service **/
	private ISpecificationValueService specificationValueService;
	/** 商品和规格中间表Service **/
	@SuppressWarnings("unused")
	private IProductSpecificationService productSpecificationService;
	/** 商品和规格值中间表Service **/
	private IProductSpecificationValueService productSpecificationValueService;
	/** 订单Service **/
	private IOrdersService ordersService;
	/** 订单明细Service **/
	private IOrdersListService ordersListService;
	/** 买家策略Service **/
	private IBuyersRecordService buyersRecordService;
	/** 商品品牌 **/
	private IBrandService brandService;
	/** 商品实体类 **/
	private ProductInfo productInfo;
	/** 商品图片实体类 **/
	private ProductImg productImg;
	/** 店铺实体类 **/
	private ShopInfo shopInfo;
	/** 全局变量商品ID **/
	private Integer productId;
	/** 商品分类层级集合 **/
	private List<ProductType> productTypeList = new ArrayList<ProductType>();
	/** 多表查询 **/
	@SuppressWarnings("rawtypes")
	private List<Map> mapList = new ArrayList<Map>();
	/** 折扣率 **/
	private Integer discount;
	/** 存放商品的规格和规格值 **/
	@SuppressWarnings("rawtypes")
	private Map map = new LinkedHashMap();
	/** 商品图片List **/
	private List<ProductImg> productImgList = new ArrayList<ProductImg>();
	// 解析商品关键字
	private List<String> productTagList = new ArrayList<String>();
	/** buyPrice是否为会员价 **/
	private String buyPriceCustomer = "0";
	/** 会员价格 **/
	private BigDecimal customerPrice;
	/**
	 * 商品参数
	 */
	private JSONObject joProductPara;
	/**
	 * 商品属性
	 */
	private JSONArray jaattrList;
	private JSONArray jaListProductAttr;
	/** 属性list **/
	private List<ProductAttribute> listProductAttr = new ArrayList<ProductAttribute>();
	/** 属性service **/
	@SuppressWarnings("unused")
	private IProductAttributeService productAttributeService;
	/** 评价Service **/
	private IEvaluateGoodsService evaluateGoodsService;
	/** 评价总数 **/
	private Integer sum;
	/** 积分等级和百分比 **/
	private Integer grade;
	/** 图片循环次数 **/
	private Integer photoNum = 5;
	// 获取库存参数
	private String sids;
	/** 分类名称 **/
	private String prodTypeNames;
	/** 商品所属组 **/
	private Integer goods;
	/** 商品评价分类参数 **/
	private String evaluateType;
	/** 选择的商品规格 **/
	private String params;
	/** 当前goods下的拥有的商品规格组 **/
	private String goodsSpecifications;
	/** 商品详情品牌 **/
	private Brand brand;
	/** 预计出货日 **/
	private Integer stockUpDate;
	/** 发货地点 **/
	private String deliveryAddress;
	/** 区域service **/
	private IAreaService areaService;
	/** 计算后的价格 **/
	private BigDecimal buyPrice;
	/** 会员等级 **/
	private Integer clevel;
	/** 会员等级折扣 **/
	private Integer cdiscount;
	/** 当前商品会员价格 **/
	private BigDecimal nowProductBuyPrice;
	/** QQ集合 **/
	private List<Map<String, Object>> qqList;
	/** 客服与店铺关系Service */
	private IShopCustomerServiceService shopCustomerServiceService;

	/** 适用行业service **/
	private IShopTradeTagService shopTradeTagService;
	/** 使用场合service **/
	private IShopSituationTagService shopSituationTagService;
	/** 适用行业与使用场合关联表service **/
	private IShopTradeSituationTagService shopTradeSituationTagService;
	/** 商品关联适用行业与使用场合service **/
	private IShopProductTradeSituationTagService shopProductTradeSituationTagService;
	/** 会员店铺Service **/
	private IMemberShipService memberShipService;
	/**
	 * 声明一个字符串 用于存储 存在商品的 规格值组合 该字符串用@红,S@的格式存储
	 * **/
	private String hasProductGGG = "@";

	/****************************** end *******************************************/
	/**
	 * 获取路径信息
	 * 
	 * @param path
	 *            路径
	 * @param productTypeId
	 *            分类ID
	 * @param categoryParams
	 *            主题馆分类ID
	 * @return pathInfo 处理后的路径 用于页面显示
	 * @author 琦瑞
	 */
	public StringBuffer getPathInfo(StringBuffer path, String productTypeId,
			String categoryParams) {
		// 读取当前分类
		ProductType pt = (ProductType) productTypeService
				.getObjectByParams(" where o.productTypeId = " + productTypeId);
		if (Utils.objectIsNotEmpty(pt)) {
			if (pt.getParentId() == 1) {
				return path;
			} else {
				if (!"2".equals(pt.getLoadType())) {
					path.insert(
							0,
							"&nbsp;&nbsp;&gt;&nbsp;&nbsp;<a href=\"/shopHome/gotoType.action?productTypeId="
									+ productTypeId
									+ "&categoryParams="
									+ categoryParams
									+ "\">"
									+ pt.getSortName()
									+ "</a>");
				} else {
					path.insert(0,
							"&nbsp;&nbsp;&gt;&nbsp;&nbsp;" + pt.getSortName());
				}
				path = getPathInfo(path, String.valueOf(pt.getParentId()),
						categoryParams);
			}
		}
		return path;
	}

	/**
	 * 根据商品的id查询商品,查询同类商品并展示四个
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String productInfoById() {
		Customer customer = (Customer) session.getAttribute("customer");
		// 获取预览参数
		String commodityPreview = request.getParameter("commodityPreview");
		// 定义展示商品信息列表条件语句
		String showProductInfoListStr = " and b.isPass=3 and a.isPutSale=2 and a.isPass=1 and b.isClose=0";
		if (commodityPreview != null && !"".equals(commodityPreview)) {
			request.setAttribute("commodityPreview", commodityPreview);
			showProductInfoListStr = "";// 如果是预览商品 则清空查询条件
		}
		// 获取前台传递的参数A
		String brandId = request.getParameter("brandId");
		String productTypeId = request.getParameter("productTypeId");
		String categoryParams = request.getParameter("categoryParams");
		// A1查询出第一个商品
		if (Utils.objectIsNotEmpty(productId)) {// 如果前台传过来商品ID了
												// 说明不是2-2-2-2.html或者2-2-2.html格式
			// 此类情况的url格式为 /productInfo/2511.html
			productInfo = (ProductInfo) productInfoService
					.getObjectByParams(" where o.productId = " + productId);
			brandId = String.valueOf(productInfo.getBrandId());// 读取品牌ID
			productTypeId = String.valueOf(productInfo.getProductTypeId());// 读取商品分类ID
		} else {// 路径符合2-2-2-2.html或者2-2-2.html格式
				// 此类情况的url格式为/productInfoPage/4-5-2.html或者/productInfoPage/4-5-2-2510.html
			List<ProductInfo> firstProductInfo = null;
			if (Utils.objectIsNotEmpty(productTypeId)) {
				firstProductInfo = productInfoService.findSome(0, 1,
						" where o.productTypeId=" + productTypeId
								+ " and o.brandId=" + brandId);
			} else {
				firstProductInfo = productInfoService.findSome(0, 1,
						" where o.brandId=" + brandId);
			}
			if (firstProductInfo != null && firstProductInfo.size() > 0) {
				productInfo = firstProductInfo.get(0);// 商品赋值
				productId = productInfo.getProductId();// 商品ID赋值
				productTypeId = String.valueOf(productInfo.getProductTypeId());
			}
		}
		request.setAttribute("brandId", brandId);
		request.setAttribute("productTypeId", productTypeId);
		request.setAttribute("categoryParams", categoryParams);
		// A2查询出一组商品
		List<ProductInfo> productInfoListByBrandType = productInfoService
				.findObjects(" where o.productTypeId=" + productTypeId
						+ " and o.isShow =1 and o.isPass=1 and o.isPutSale=2");
		request.setAttribute("productInfoListByBrandType",
				productInfoListByBrandType);
		// A3查询出当前分类名称
		ProductType pt = (ProductType) productTypeService
				.getObjectByParams(" where o.productTypeId = " + productTypeId);
		if (Utils.objectIsNotEmpty(pt)) {
			request.setAttribute("ptName", pt.getSortName());
		}
		try {
			if (productInfo != null) {
				// 计算当前商品的市场价折扣
				getCustomerProductPrice(customer, productInfo);
				if (customer != null && customer.getType() != 2) {
					nowProductBuyPrice = customerPrice;
					request.setAttribute("priceDiscount", discount);
					request.setAttribute("priceDiscountOld", cdiscount);
					// 会员折扣
					MemberShip memberShip = (MemberShip) memberShipService
							.getObjectByParams(" where o.state=2 and o.customerId="
									+ customer.getCustomerId()
									+ " and o.shopInfoId="
									+ productInfo.getShopInfoId());
					if (null != memberShip) {
						BigDecimal memDiscount = memberShip.getDiscount();
						request.setAttribute("memDiscount", memDiscount);
					}
				} else {
					nowProductBuyPrice = buyPrice;
					request.setAttribute("priceDiscount", cdiscount);
				}
				// 判断当前操作商品是否为热卖商品
				String nowIsHost = isHotProduct(productInfo.getProductId());
				request.setAttribute("nowIsHost", nowIsHost);
				// 根据当前商品查询此组商品选择的所有规格
				List<ProductInfo> prodList = productInfoService.findObjects(
						null,
						" where o.isPass=1 and o.isPutSale=2 and o.goods='"
								+ productInfo.getGoods()
								+ "' order by o.productId desc ");
				StringBuffer sb = new StringBuffer();
				if (prodList != null && prodList.size() > 0) {
					for (ProductInfo product : prodList) {
						sb.append(product.getProductId()).append(",");
					}
				} else {
					sb.append(productInfo.getProductId()).append(",");
				}
				if (StringUtils.isNotEmpty(sb.toString())) {
					sb.deleteCharAt(sb.lastIndexOf(","));
				}
				if (StringUtils.isNotEmpty(sb.toString())) {
					List<Integer> pSVList = (List<Integer>) productSpecificationValueService
							.findUnDistinctList("specificationId",
									" where o.productId in(" + sb.toString()
											+ ")");
					if (pSVList != null && pSVList.size() > 0) {
						// 定义两个字符串 用来存储规格值
						String arrayStr1 = "";// 规格1
						String arrayStr2 = "";// 规格2
						int i = 0;
						for (Integer pSV : pSVList) {
							Specification specification = (Specification) specificationService
									.getObjectByParams(" where o.specificationId="
											+ pSV);
							// List<SpecificationValue> specificationValueList =
							// specificationValueService.findObjects(" where o.specificationId="+pSV);
							// 排除商品规格组排重
							List<Integer> productSpecificationValueList = (List<Integer>) productSpecificationValueService
									.findUnDistinctList("specificationValueId",
											" where o.specificationId=" + pSV
													+ " and o.productId in("
													+ sb.toString() + ")");
							// List<SpecificationValue>
							// specificationValueList=new
							// ArrayList<SpecificationValue>();
							List<GgImage> giList = new ArrayList<GgImage>();// 存储图片规格值
																			// 以及图片数据
							List<GgImage> giList2 = new ArrayList<GgImage>();// 存储规格值
																				// 文本类型的
							for (Integer specificationValueId : productSpecificationValueList) {// 查询规格值数据
								SpecificationValue specificationValue = (SpecificationValue) specificationValueService
										.getObjectById(String
												.valueOf(specificationValueId));
								if (i == 0) {
									arrayStr1 += specificationValue.getName()
											+ ",";
								} else {
									arrayStr2 += specificationValue.getName()
											+ ",";
								}
								// 通过规格 和 规格值 找 商品图片
								List<ProductSpecificationValue> psvList = productSpecificationValueService
										.findSome(
												0,
												1,
												" where o.specificationValueId="
														+ specificationValueId
														+ " and o.specificationId="
														+ pSV);
								ProductSpecificationValue psvObj = psvList
										.get(0);
								ProductInfo productInfo = (ProductInfo) productInfoService
										.getObjectByParams(" where o.productId = "
												+ psvObj.getProductId());// 通过商品ID查询商品
								if (specification.getType() != null
										&& specification.getType() == 2) {// 图片类型的规格值
									if (Utils.objectIsNotEmpty(psvList)) {
										GgImage gi = new GgImage();
										gi.setImage(productInfo.getLogoImg());// 图片路径
										gi.setSpecificationValue(specificationValue);// 规格信息
										gi.setProductId(String
												.valueOf(productInfo
														.getProductId()));// 商品ID
										giList.add(gi);
									}
								} else {
									GgImage gi = new GgImage();
									gi.setSpecificationValue(specificationValue);// 规格信息
									gi.setProductId(String.valueOf(productInfo
											.getProductId()));// 商品ID
									giList2.add(gi);
								}

							}
							Map<String, Object> mapObj = new HashMap<String, Object>();
							mapObj.put("specificationValueList", giList2);// 规格值集合
							mapObj.put("specificationValueListImage", giList);// 规格值集合图片
							map.put(specification, mapObj);
							i++;
						}
						// 判断规格组拼的数据是否存在商品 如果存在 则将其规格值组拼数据存起来
						if (!"".equals(arrayStr1)) {
							arrayStr1 = arrayStr1.substring(0,
									arrayStr1.lastIndexOf(","));
						}
						if (!"".equals(arrayStr2)) {
							arrayStr2 = arrayStr2.substring(0,
									arrayStr2.lastIndexOf(","));
						}
						String[] split = arrayStr1.split(",");
						String[] split2 = arrayStr2.split(",");
						if (split != null && split.length > 0) {
							for (String s1 : split) {
								if (split2 != null && split2.length > 0) {
									for (String s2 : split2) {
										String hql = "where 1=1";
										if (Utils.objectIsNotEmpty(s2)) {
											hql += " and o.productFullName like '%[%"
													+ s1 + "," + s2 + "%]%'";
										} else {// 只有一种规格的情况
											hql += " and o.productFullName like '%[%"
													+ s1 + "%]%'";
										}
										hql += " and o.goods="
												+ productInfo.getGoods();
										Integer count = productInfoService
												.getCount(hql);
										if (count > 0) {// 此种组合存在商品
											if (Utils.objectIsNotEmpty(s2)) {
												hasProductGGG += s1 + "," + s2
														+ "@";
											} else {
												hasProductGGG += s1 + "@";
											}
										}
									}
								}
							}
						}
					}
				}
				// 获取商品所在店铺的星星等级,好评百分比,评价总数
				// 根据店铺的ID查询出所有的商品并计算该店铺的积分等级
				List<EvaluateGoods> evaluateGoodsList = evaluateGoodsService
						.findObjects(" where o.shopInfoId = "
								+ productInfo.getShopInfoId());
				// 判断查询出来的集合是否为空
				if (Utils.collectionIsNotEmpty(evaluateGoodsList)) {
					// 定义一个Integer用来保存好评的数量
					Integer dividend = 0;
					sum = evaluateGoodsList.size();
					for (EvaluateGoods evaluateGoods : evaluateGoodsList) {
						Integer level = evaluateGoods.getLevel();
						if (level == 1) {
							dividend++;
						}
					}
					// 计算好评百分比
					grade = (dividend * 100) / sum;
					// 给前台判定图片数量
					if (grade <= 20) {
						photoNum = 0;
					}
					if (grade > 20 && grade <= 40) {
						photoNum = 1;
					}
					if (grade > 40 && grade <= 60) {
						photoNum = 2;
					}
					if (grade > 60 && grade <= 80) {
						photoNum = 3;
					}
					if (grade > 80) {
						photoNum = 4;
					}
				} else {
					grade = 100;
				}
				// 取商品的属性
				String sql = "select a.productId as productId, c.attrValueName as attrValueName,d.name as name from ProductInfo a,ProductAttrIndex b,AttributeValue c,ProductAttribute d where a.productId = b.productId and b.productAttrId = d.productAttrId and b.attrValueId = c.attrValueId and a.productId="
						+ productInfo.getProductId();
				List<Map> attrList = productInfoService.findListMapByHql(sql);
				jaattrList = JSONArray.fromObject(attrList);
				// 解析商品关键字
				String productTag = productInfo.getProductTag();
				if (!StringUtils.isEmpty(productTag)) {
					if (!StringUtils.isEmpty(productTag)) {
						String[] split = productTag.split(",");
						productTagList = Arrays.asList(split);
					}
				}
				stockUpDate = productInfo.getStockUpDate();
				// 计算前台显示商品折扣
				// 获取数据字典会员等级与折扣比
				// 查询下方5个推荐商品
				String hql = "SELECT o.salesPrice as salesPrice, o.productFullName as productFullName, o.productId as productId, o.shopInfoId as shopInfoId, s.shopName as shopName ,o.logoImg as logoImg FROM ProductInfo o,ShopInfo s WHERE o.shopInfoId=s.shopInfoId and o.productTypeId ="
						+ productInfo.getProductTypeId()
						+ " and o.isPass=1 and o.isPutSale=2 and o.isRecommend=1 order by o.putSaleDate desc";
				pageHelper.setPageSize(5);
				mapList = productInfoService.findListMapPage(hql, pageHelper);
				// 查询商品详情图片
				shopInfo = (ShopInfo) shopInfoService
						.getObjectByParams("where o.shopInfoId="
								+ productInfo.getShopInfoId());
				/** 查询QQ的sql语句 */
				String qqSql = "select b.trueName as trueName,b.nikeName as nikeName,b.qq as qq from shop_customerservice b ,shop_shop_customerservice c where b.useState=1 and c.customerServiceId=b.customerServiceId and c.customerId="
						+ shopInfo.getCustomerId();
				// 查询qq
				qqList = shopCustomerServiceService.findListMapBySql(qqSql);
				productImgList = productImgService
						.findObjects(" where o.productId ="
								+ productInfo.getProductId()
								+ " order by o.orders asc");
				// 获取商品导航当前位置
				String pathInfo = String.valueOf(getPathInfo(
						new StringBuffer(), productTypeId, categoryParams));
				request.setAttribute("pathInfo", pathInfo.substring(
						"&nbsp;&nbsp;&gt;".length(), pathInfo.length()));
				// 获取当前商品的品牌的信息
				brand = (Brand) brandService
						.getObjectByParams(" where o.brandId="
								+ productInfo.getBrandId());
				// 查询商品标签信息
				List<ShopProductTradeSituationTag> shopProductTradeSituationTagList = shopProductTradeSituationTagService
						.findObjects(
								null,
								" where o.productId="
										+ productInfo.getProductId()
										+ " group by o.ttId");
				if (shopProductTradeSituationTagList != null
						&& shopProductTradeSituationTagList.size() > 0) {
					String ids = "";// 定义存储适用行业标签ID的字符串组
					for (ShopProductTradeSituationTag sptst : shopProductTradeSituationTagList) {
						ids += "," + sptst.getTtId();
					}
					if (!"".equals(ids)) {
						ids = ids.substring(1, ids.length());
					} else {
						ids = "0";
					}
					// 1.适用行业标签
					List<ShopTradeTag> shopTradeTagList = shopTradeTagService
							.findObjects(null, " where o.ttId in ( " + ids
									+ " ) and o.useState=1");
					if (shopTradeTagList != null && shopTradeTagList.size() > 0) {
						request.setAttribute("shopTradeTagList",
								shopTradeTagList);
					}
					// 2.使用场合标签
					List<ShopProductTradeSituationTag> shopProductTradeSituationTagList2 = shopProductTradeSituationTagService
							.findObjects(null, " where o.productId="
									+ productInfo.getProductId()
									+ " group by o.stId");
					if (shopProductTradeSituationTagList2 != null
							&& shopProductTradeSituationTagList2.size() > 0) {
						String ids2 = "";// 定义存储适用行业标签ID的字符串组
						for (ShopProductTradeSituationTag sptst2 : shopProductTradeSituationTagList2) {
							ids2 += "," + sptst2.getStId();
						}
						if (!"".equals(ids2)) {
							ids2 = ids2.substring(1, ids2.length());
						} else {
							ids2 = "0";
						}
						List<ShopSituationTag> shopSituationTagList = shopSituationTagService
								.findObjects(null, " where o.stId in ( " + ids2
										+ " ) and o.useState=1");
						if (shopSituationTagList != null
								&& shopSituationTagList.size() > 0) {
							request.setAttribute("shopSituationTagList",
									shopSituationTagList);
						}
					}

				}
				// 取商品的发货地址
				if (productInfo.getDeliveryAddressCities() != null) {
					BasicArea adress = (BasicArea) areaService
							.getObjectById(String.valueOf(productInfo
									.getDeliveryAddressCities()));
					deliveryAddress = adress.getFullName();
				}
				// 取商品的参数
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig
						.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
				joProductPara = JSONObject.fromObject(productInfo, jsonConfig);
			}
		} catch (Exception e) {
			// e.printStackTrace();
			// System.out.println("因旧数据规格不全造成异常。");
		}
		// QRCode.createQRPng("", "", fileUrlConfig, null, "");
		return SUCCESS;
	}

	/**
	 * 根据商品的id查询商品
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String phoneProductInfoById() {
		session.setAttribute("customerId", customerId);
		Integer haoPingCount = evaluateGoodsService
				.getCount(" where o.productId=" + productId
						+ " and o.level =1 and o.evaluateState=0 ");
		Integer zhongPingCount = evaluateGoodsService
				.getCount(" where o.productId=" + productId
						+ " and o.level =0 and o.evaluateState=0 ");
		Integer chaPingCount = evaluateGoodsService
				.getCount(" where o.productId=" + productId
						+ " and o.level=-1 and o.evaluateState=0 ");
		request.setAttribute("haoPingCount", haoPingCount);
		request.setAttribute("zhongPingCount", zhongPingCount);
		request.setAttribute("chaPingCount", chaPingCount);
		if (StringUtils.isNotEmpty(evaluateType)) {
			String hql_count = "select count(cr.evaluateId) from Customer c,EvaluateGoods cr"
					+ " where cr.productId="
					+ productId
					+ " and cr.appraiserId=c.customerId  and cr.evaluateState=0";
			String hql_list = "select c.loginName as loginName,c.photoUrl as photoUrl,cr.appraiserId as customerId,"
					+ "cr.productId as productId,cr.level as level,cr.content as content,cr.isAnonymous as isAnonymous,cr.explain as explain,"
					+ "cr.evaluateState as state,cr.bothState as backState,cr.createTime as createTime from Customer c,EvaluateGoods cr"
					+ " where cr.productId="
					+ productId
					+ " and cr.appraiserId=c.customerId and cr.evaluateState=0 ";
			if ("all".equals(evaluateType)) {
				hql_count += "  and cr.level is not null";
				hql_list += "  and cr.level is not null ";
			} else if ("good".equals(evaluateType)) {
				hql_count += "  and cr.level =1";
				hql_list += "  and cr.level =1 ";
			} else if ("medium".equals(evaluateType)) {
				hql_count += "  and cr.level =0";
				hql_list += "  and cr.level =0 ";
			} else {
				hql_count += "  and cr.level=-1";
				hql_list += "  and cr.level=-1 ";
			}
			hql_list += "order by cr.createTime desc,cr.level desc ";
			Integer totalRecordCount = evaluateGoodsService
					.getCountByHQL(hql_count);
			pageHelper.setPageInfo(5, totalRecordCount, currentPage);
			List<Map> cusRevList = evaluateGoodsService.findListMapPage(
					hql_list, pageHelper);
			request.setAttribute("cusRevList", cusRevList);
		}
		Customer customer = (Customer) session.getAttribute("customer");
		// 获取预览参数
		String commodityPreview = request.getParameter("commodityPreview");
		// 定义展示商品信息列表条件语句
		String showProductInfoListStr = " and b.isPass=3 and a.isPutSale=2 and a.isPass=1 and b.isClose=0";
		if (commodityPreview != null && !"".equals(commodityPreview)) {
			request.setAttribute("commodityPreview", commodityPreview);
			showProductInfoListStr = "";// 如果是预览商品 则清空查询条件
		}
		// 获取前台传递的参数A
		String brandId = request.getParameter("brandId");
		String productTypeId = request.getParameter("productTypeId");
		String categoryParams = request.getParameter("categoryParams");
		// A1查询出第一个商品
		if (Utils.objectIsNotEmpty(productId)) {// 如果前台传过来商品ID了
												// 说明不是2-2-2-2.html或者2-2-2.html格式
			// 此类情况的url格式为 /productInfo/2511.html
			productInfo = (ProductInfo) productInfoService
					.getObjectByParams(" where o.productId = " + productId);
			String productFullName = productInfo.getProductFullName();
			if (productFullName.endsWith("]")) {
				String descrption = productFullName.substring(
						productFullName.indexOf('[') + 1,
						productFullName.indexOf(']'));
				productInfo.setDescrible(descrption);
			}
			brandId = String.valueOf(productInfo.getBrandId());// 读取品牌ID
			productTypeId = String.valueOf(productInfo.getProductTypeId());// 读取商品分类ID
		} else {// 路径符合2-2-2-2.html或者2-2-2.html格式
				// 此类情况的url格式为/productInfoPage/4-5-2.html或者/productInfoPage/4-5-2-2510.html
			List<ProductInfo> firstProductInfo = null;
			if (Utils.objectIsNotEmpty(productTypeId)) {
				firstProductInfo = productInfoService.findSome(0, 1,
						" where o.productTypeId=" + productTypeId
								+ " and o.brandId=" + brandId);
			} else {
				firstProductInfo = productInfoService.findSome(0, 1,
						" where o.brandId=" + brandId);
			}
			String productFullName = productInfo.getProductFullName();
			if (productFullName.endsWith("]")) {
				String descrption = productFullName.substring(
						productFullName.indexOf('[') + 1,
						productFullName.indexOf(']'));
				productInfo.setDescrible(descrption);
			}
			if (firstProductInfo != null && firstProductInfo.size() > 0) {
				productInfo = firstProductInfo.get(0);// 商品赋值
				productId = productInfo.getProductId();// 商品ID赋值
				productTypeId = String.valueOf(productInfo.getProductTypeId());
			}
		}
		request.setAttribute("brandId", brandId);
		request.setAttribute("productTypeId", productTypeId);
		request.setAttribute("categoryParams", categoryParams);
		// A2查询出一组商品
		List<ProductInfo> productInfoListByBrandType = productInfoService
				.findObjects(" where o.productTypeId=" + productTypeId
						+ " and o.isShow =1 and o.isPass=1 and o.isPutSale=2");
		request.setAttribute("productInfoListByBrandType",
				productInfoListByBrandType);
		// A3查询出当前分类名称
		ProductType pt = (ProductType) productTypeService
				.getObjectByParams(" where o.productTypeId = " + productTypeId);
		if (Utils.objectIsNotEmpty(pt)) {
			request.setAttribute("ptName", pt.getSortName());
		}
		try {
			if (productInfo != null) {
				// 计算当前商品的市场价折扣
				getCustomerProductPrice(customer, productInfo);
				if (customer != null && customer.getType() != 2) {
					nowProductBuyPrice = customerPrice;
					request.setAttribute("priceDiscount", discount);
					request.setAttribute("priceDiscountOld", cdiscount);
					// 会员折扣
					MemberShip memberShip = (MemberShip) memberShipService
							.getObjectByParams(" where o.state=2 and o.customerId="
									+ customer.getCustomerId()
									+ " and o.shopInfoId="
									+ productInfo.getShopInfoId());
					if (null != memberShip) {
						BigDecimal memDiscount = memberShip.getDiscount();
						request.setAttribute("memDiscount", memDiscount);
					}
				} else {
					nowProductBuyPrice = buyPrice;
					request.setAttribute("priceDiscount", cdiscount);
				}
				// 判断当前操作商品是否为热卖商品
				String nowIsHost = isHotProduct(productInfo.getProductId());
				request.setAttribute("nowIsHost", nowIsHost);
				// 根据当前商品查询此组商品选择的所有规格
				List<ProductInfo> prodList = productInfoService.findObjects(
						null,
						" where o.isPass=1 and o.isPutSale=2 and o.goods='"
								+ productInfo.getGoods()
								+ "' order by o.productId desc ");
				StringBuffer sb = new StringBuffer();
				if (prodList != null && prodList.size() > 0) {
					for (ProductInfo product : prodList) {
						sb.append(product.getProductId()).append(",");
					}
				} else {
					sb.append(productInfo.getProductId()).append(",");
				}
				if (StringUtils.isNotEmpty(sb.toString())) {
					sb.deleteCharAt(sb.lastIndexOf(","));
				}
				if (StringUtils.isNotEmpty(sb.toString())) {
					List<Integer> pSVList = (List<Integer>) productSpecificationValueService
							.findUnDistinctList("specificationId",
									" where o.productId in(" + sb.toString()
											+ ")");
					if (pSVList != null && pSVList.size() > 0) {
						// 定义两个字符串 用来存储规格值
						String arrayStr1 = "";// 规格1
						String arrayStr2 = "";// 规格2
						int i = 0;
						for (Integer pSV : pSVList) {
							Specification specification = (Specification) specificationService
									.getObjectByParams(" where o.specificationId="
											+ pSV);
							// List<SpecificationValue> specificationValueList =
							// specificationValueService.findObjects(" where o.specificationId="+pSV);
							// 排除商品规格组排重
							List<Integer> productSpecificationValueList = (List<Integer>) productSpecificationValueService
									.findUnDistinctList("specificationValueId",
											" where o.specificationId=" + pSV
													+ " and o.productId in("
													+ sb.toString() + ")");
							// List<SpecificationValue>
							// specificationValueList=new
							// ArrayList<SpecificationValue>();
							List<GgImage> giList = new ArrayList<GgImage>();// 存储图片规格值
																			// 以及图片数据
							List<GgImage> giList2 = new ArrayList<GgImage>();// 存储规格值
																				// 文本类型的
							for (Integer specificationValueId : productSpecificationValueList) {// 查询规格值数据
								SpecificationValue specificationValue = (SpecificationValue) specificationValueService
										.getObjectById(String
												.valueOf(specificationValueId));
								if (i == 0) {
									arrayStr1 += specificationValue.getName()
											+ ",";
								} else {
									arrayStr2 += specificationValue.getName()
											+ ",";
								}
								// 通过规格 和 规格值 找 商品图片
								List<ProductSpecificationValue> psvList = productSpecificationValueService
										.findSome(
												0,
												1,
												" where o.specificationValueId="
														+ specificationValueId
														+ " and o.specificationId="
														+ pSV);
								ProductSpecificationValue psvObj = psvList
										.get(0);
								ProductInfo productInfo = (ProductInfo) productInfoService
										.getObjectByParams(" where o.productId = "
												+ psvObj.getProductId());// 通过商品ID查询商品
								if (specification.getType() != null
										&& specification.getType() == 2) {// 图片类型的规格值
									if (Utils.objectIsNotEmpty(psvList)) {
										GgImage gi = new GgImage();
										gi.setImage(productInfo.getLogoImg());// 图片路径
										gi.setSpecificationValue(specificationValue);// 规格信息
										gi.setProductId(String
												.valueOf(productInfo
														.getProductId()));// 商品ID
										giList.add(gi);
									}
								} else {
									GgImage gi = new GgImage();
									gi.setSpecificationValue(specificationValue);// 规格信息
									gi.setProductId(String.valueOf(productInfo
											.getProductId()));// 商品ID
									giList2.add(gi);
								}

							}
							Map<String, Object> mapObj = new HashMap<String, Object>();
							mapObj.put("specificationValueList", giList2);// 规格值集合
							mapObj.put("specificationValueListImage", giList);// 规格值集合图片
							map.put(specification, mapObj);
							i++;
						}
						// 判断规格组拼的数据是否存在商品 如果存在 则将其规格值组拼数据存起来
						if (!"".equals(arrayStr1)) {
							arrayStr1 = arrayStr1.substring(0,
									arrayStr1.lastIndexOf(","));
						}
						if (!"".equals(arrayStr2)) {
							arrayStr2 = arrayStr2.substring(0,
									arrayStr2.lastIndexOf(","));
						}
						String[] split = arrayStr1.split(",");
						String[] split2 = arrayStr2.split(",");
						if (split != null && split.length > 0) {
							for (String s1 : split) {
								if (split2 != null && split2.length > 0) {
									for (String s2 : split2) {
										String hql = "where 1=1";
										if (Utils.objectIsNotEmpty(s2)) {
											hql += " and o.productFullName like '%[%"
													+ s1 + "," + s2 + "%]%'";
										} else {// 只有一种规格的情况
											hql += " and o.productFullName like '%[%"
													+ s1 + "%]%'";
										}
										hql += " and o.goods="
												+ productInfo.getGoods();
										Integer count = productInfoService
												.getCount(hql);
										if (count > 0) {// 此种组合存在商品
											if (Utils.objectIsNotEmpty(s2)) {
												hasProductGGG += s1 + "," + s2
														+ "@";
											} else {
												hasProductGGG += s1 + "@";
											}
										}
									}
								}
							}
						}
					}
				}
				// 获取商品所在店铺的星星等级,好评百分比,评价总数
				// 根据店铺的ID查询出所有的商品并计算该店铺的积分等级
				List<EvaluateGoods> evaluateGoodsList = evaluateGoodsService
						.findObjects(" where o.shopInfoId = "
								+ productInfo.getShopInfoId());
				// 判断查询出来的集合是否为空
				if (Utils.collectionIsNotEmpty(evaluateGoodsList)) {
					// 定义一个Integer用来保存好评的数量
					Integer dividend = 0;
					sum = evaluateGoodsList.size();
					for (EvaluateGoods evaluateGoods : evaluateGoodsList) {
						Integer level = evaluateGoods.getLevel();
						if (level == 1) {
							dividend++;
						}
					}
					// 计算好评百分比
					grade = (dividend * 100) / sum;
					// 给前台判定图片数量
					if (grade <= 20) {
						photoNum = 0;
					}
					if (grade > 20 && grade <= 40) {
						photoNum = 1;
					}
					if (grade > 40 && grade <= 60) {
						photoNum = 2;
					}
					if (grade > 60 && grade <= 80) {
						photoNum = 3;
					}
					if (grade > 80) {
						photoNum = 4;
					}
				} else {
					grade = 100;
				}
				// 取商品的属性
				String sql = "select a.productId as productId, c.attrValueName as attrValueName,d.name as name from ProductInfo a,ProductAttrIndex b,AttributeValue c,ProductAttribute d where a.productId = b.productId and b.productAttrId = d.productAttrId and b.attrValueId = c.attrValueId and a.productId="
						+ productInfo.getProductId();
				List<Map> attrList = productInfoService.findListMapByHql(sql);
				jaattrList = JSONArray.fromObject(attrList);
				// 解析商品关键字
				String productTag = productInfo.getProductTag();
				if (!StringUtils.isEmpty(productTag)) {
					if (!StringUtils.isEmpty(productTag)) {
						String[] split = productTag.split(",");
						productTagList = Arrays.asList(split);
					}
				}
				stockUpDate = productInfo.getStockUpDate();
				// 计算前台显示商品折扣
				// 获取数据字典会员等级与折扣比
				// 查询下方5个推荐商品
				String hql = "SELECT o.salesPrice as salesPrice, o.productFullName as productFullName, o.productId as productId, o.shopInfoId as shopInfoId, s.shopName as shopName ,o.logoImg as logoImg FROM ProductInfo o,ShopInfo s WHERE o.shopInfoId=s.shopInfoId and o.productTypeId ="
						+ productInfo.getProductTypeId()
						+ " and o.isPass=1 and o.isPutSale=2 and o.isRecommend=1 order by o.putSaleDate desc";
				pageHelper.setPageSize(5);
				mapList = productInfoService.findListMapPage(hql, pageHelper);
				// 查询商品详情图片
				shopInfo = (ShopInfo) shopInfoService
						.getObjectByParams("where o.shopInfoId="
								+ productInfo.getShopInfoId());
				/** 查询QQ的sql语句 */
				String qqSql = "select b.trueName as trueName,b.nikeName as nikeName,b.qq as qq from shop_customerservice b ,shop_shop_customerservice c where b.useState=1 and c.customerServiceId=b.customerServiceId and c.customerId="
						+ shopInfo.getCustomerId();
				// 查询qq
				qqList = shopCustomerServiceService.findListMapBySql(qqSql);
				productImgList = productImgService
						.findObjects(" where o.productId ="
								+ productInfo.getProductId()
								+ " order by o.orders asc");
				// 获取商品导航当前位置
				String pathInfo = String.valueOf(getPathInfo(
						new StringBuffer(), productTypeId, categoryParams));
				request.setAttribute("pathInfo", pathInfo.substring(
						"&nbsp;&nbsp;&gt;".length(), pathInfo.length()));
				// 获取当前商品的品牌的信息
				brand = (Brand) brandService
						.getObjectByParams(" where o.brandId="
								+ productInfo.getBrandId());
				// 查询商品标签信息
				List<ShopProductTradeSituationTag> shopProductTradeSituationTagList = shopProductTradeSituationTagService
						.findObjects(
								null,
								" where o.productId="
										+ productInfo.getProductId()
										+ " group by o.ttId");
				if (shopProductTradeSituationTagList != null
						&& shopProductTradeSituationTagList.size() > 0) {
					String ids = "";// 定义存储适用行业标签ID的字符串组
					for (ShopProductTradeSituationTag sptst : shopProductTradeSituationTagList) {
						ids += "," + sptst.getTtId();
					}
					if (!"".equals(ids)) {
						ids = ids.substring(1, ids.length());
					} else {
						ids = "0";
					}
					// 1.适用行业标签
					List<ShopTradeTag> shopTradeTagList = shopTradeTagService
							.findObjects(null, " where o.ttId in ( " + ids
									+ " ) and o.useState=1");
					if (shopTradeTagList != null && shopTradeTagList.size() > 0) {
						request.setAttribute("shopTradeTagList",
								shopTradeTagList);
					}
					// 2.使用场合标签
					List<ShopProductTradeSituationTag> shopProductTradeSituationTagList2 = shopProductTradeSituationTagService
							.findObjects(null, " where o.productId="
									+ productInfo.getProductId()
									+ " group by o.stId");
					if (shopProductTradeSituationTagList2 != null
							&& shopProductTradeSituationTagList2.size() > 0) {
						String ids2 = "";// 定义存储适用行业标签ID的字符串组
						for (ShopProductTradeSituationTag sptst2 : shopProductTradeSituationTagList2) {
							ids2 += "," + sptst2.getStId();
						}
						if (!"".equals(ids2)) {
							ids2 = ids2.substring(1, ids2.length());
						} else {
							ids2 = "0";
						}
						List<ShopSituationTag> shopSituationTagList = shopSituationTagService
								.findObjects(null, " where o.stId in ( " + ids2
										+ " ) and o.useState=1");
						if (shopSituationTagList != null
								&& shopSituationTagList.size() > 0) {
							request.setAttribute("shopSituationTagList",
									shopSituationTagList);
						}
					}

				}
				// 取商品的发货地址
				if (productInfo.getDeliveryAddressCities() != null) {
					BasicArea adress = (BasicArea) areaService
							.getObjectById(String.valueOf(productInfo
									.getDeliveryAddressCities()));
					deliveryAddress = adress.getFullName();
				}

				// 取商品的参数
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig
						.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
				joProductPara = JSONObject.fromObject(productInfo, jsonConfig);
				if (customerId != null) {
					List<CustomerCollectProduct> findObjects = customerCollectProductService
							.findObjects(" where productId=" + productId
									+ " and customerId=" + customerId);
					if(findObjects.size()<1){
						session.setAttribute("collect", false);
					}else{
						session.setAttribute("collect", true);
					}
				}
				
				//取评价信息
				haoPingCount = evaluateGoodsService
						.getCount(" where o.productId=" + productId
								+ " and o.level =1 and o.evaluateState=0 ");
				zhongPingCount = evaluateGoodsService
						.getCount(" where o.productId=" + productId
								+ " and o.level =0 and o.evaluateState=0 ");
				chaPingCount = evaluateGoodsService
						.getCount(" where o.productId=" + productId
								+ " and o.level=-1 and o.evaluateState=0 ");
				request.setAttribute("haoPingCount", haoPingCount);
				request.setAttribute("zhongPingCount", zhongPingCount);
				request.setAttribute("chaPingCount", chaPingCount);
				
				//取所有评价
				evaluateType="all";
				String hql_count = "select count(cr.evaluateId) from Customer c,EvaluateGoods cr"
						+ " where cr.productId="
						+ productId
						+ " and cr.appraiserId=c.customerId  and cr.evaluateState=0";
				String hql_list = "select c.loginName as loginName,c.photoUrl as photoUrl,cr.appraiserId as customerId,"
						+ "cr.productId as productId,cr.level as level,cr.content as content,cr.isAnonymous as isAnonymous,cr.explain as explain,"
						+ "cr.evaluateState as state,cr.bothState as backState,cr.createTime as createTime from Customer c,EvaluateGoods cr"
						+ " where cr.productId="
						+ productId
						+ " and cr.appraiserId=c.customerId and cr.evaluateState=0 ";
				if ("all".equals(evaluateType)) {
					hql_count += "  and cr.level is not null";
					hql_list += "  and cr.level is not null ";
				} else if ("good".equals(evaluateType)) {
					hql_count += "  and cr.level =1";
					hql_list += "  and cr.level =1 ";
				} else if ("medium".equals(evaluateType)) {
					hql_count += "  and cr.level =0";
					hql_list += "  and cr.level =0 ";
				} else {
					hql_count += "  and cr.level=-1";
					hql_list += "  and cr.level=-1 ";
				}
				hql_list += "order by cr.createTime desc,cr.level desc ";
				Integer totalRecordCount = evaluateGoodsService
						.getCountByHQL(hql_count);
				pageHelper.setPageInfo(5, totalRecordCount, currentPage);
				List<Map> cusRevList = evaluateGoodsService.findListMapPage(
						hql_list, pageHelper);
				request.setAttribute("cusRevList", cusRevList);
			}
		} catch (Exception e) {
			// e.printStackTrace();
			// System.out.println("因旧数据规格不全造成异常。");
		}
		// QRCode.createQRPng("", "", fileUrlConfig, null, "");
		return SUCCESS;
	}

	/**
	 * 商品图片规格内部类
	 * 
	 * @author 琦瑞
	 */
	public class GgImage {
		/** 图片url **/
		private String image;
		/** 规格值对象 **/
		private SpecificationValue specificationValue;
		/** 商品ID **/
		private String productId;

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public SpecificationValue getSpecificationValue() {
			return specificationValue;
		}

		public void setSpecificationValue(SpecificationValue specificationValue) {
			this.specificationValue = specificationValue;
		}

		public String getProductId() {
			return productId;
		}

		public void setProductId(String productId) {
			this.productId = productId;
		}
	}

	/**
	 * 计算商品会员价
	 */
	public void getCustomerProductPrice(Customer customer,
			ProductInfo productInfo) {
		Integer rebate = 0;
		// 计算普通的折扣率
		if (productInfo.getMarketPrice().subtract(productInfo.getSalesPrice())
				.compareTo(new BigDecimal(0)) > 0) {
			BigDecimal discount = productInfo.getMarketPrice().subtract(
					productInfo.getSalesPrice());// 原价与售价的差价
			discount = discount.divide(productInfo.getMarketPrice(), 2,
					BigDecimal.ROUND_HALF_EVEN);// 0.几的比率
			BigDecimal sp = productInfo.getSalesPrice();// 售价
			discount = discount.multiply(new BigDecimal(100));
			// 将BigDecimal 转换成Integer
			String str = String.valueOf(discount);
			// 判断str是否包含“.”
			if (str.indexOf(".") > 0) {
				str = str.substring(0, str.lastIndexOf("."));
			}
			cdiscount = Integer.parseInt(str);// 相对于原价的折扣
			rebate = Integer.parseInt(str);// 相对于原价的折扣
		} else {
			cdiscount = 0;
		}
		buyPrice = productInfo.getSalesPrice();// 售价

		// 判断会员是否登陆
		if (customer != null && customer.getCustomerId() != null) {// 如果登陆则计算相应的数据
			String sql = "select br.levelDiscountValue as levelDiscountValue, br.buyersLevel as buyersLevel from BuyersRecord br "
					+ "where br.customerId="
					+ customer.getCustomerId()
					+ "order by br.optionDate desc";
			List<Map> listbs = buyersRecordService.findListMapByHql(sql);
			if (productInfo.getMarketPrice() != null) {
				if (productInfo.getMarketPrice().compareTo(new BigDecimal(0)) > 0) {// 将0与市场价格比较
					if (listbs != null && listbs.size() > 0) {
						Map bs = listbs.get(0);
						Object object = bs.get("levelDiscountValue");
						Integer buyersLevel = Integer.valueOf(bs.get(
								"buyersLevel").toString());
						clevel = buyersLevel;
						if (object != null) {
							// 会员等级不等于1
							if (buyersLevel >= 3) {
								BigDecimal bilv = new BigDecimal(
										String.valueOf(object));
								bilv = bilv.divide(new BigDecimal(100), 2,
										BigDecimal.ROUND_HALF_EVEN);// 0.几的比率
								BigDecimal discount = bilv.multiply(productInfo
										.getSalesPrice());// 优惠价
								discount = productInfo.getSalesPrice()
										.subtract(discount);// 实际售价
								// 将BigDecimal 转换成Integer
								String str = String.valueOf(discount);
								String bilv1 = String.valueOf(bilv);
								// 判断str是否包含“.”
								if (str.indexOf(".") > 0
										&& bilv1.indexOf(".") > 0) {
									str = str
											.substring(0, str.lastIndexOf("."));
									customerPrice = discount.divide(
											new BigDecimal(1), 2,
											BigDecimal.ROUND_HALF_EVEN);
								}
								rebate = Integer
										.valueOf(String.valueOf(object));
								buyPriceCustomer = "1";
							} else {
								customerPrice = productInfo.getSalesPrice();// 售价
							}
						}
					}
				} else {
					customerPrice = productInfo.getSalesPrice();// 售价
				}
			}
			discount = rebate;

		}
	}

	/**
	 * 判断商品是否为热卖商品
	 * 
	 * @param productId
	 *            商品id
	 * @return 0:不是热卖 1:是热卖
	 */
	public String isHotProduct(Integer productId) {
		if (Utils.objectIsNotEmpty(productId)) {
			String hotSaleDay = String.valueOf(shopConfig.get("hotSaleDay"));// 热卖天数
			String hotSaleNumber = String.valueOf(shopConfig
					.get("hotSaleNumber"));// 热卖商品个数
			if (Utils.objectIsNotEmpty(hotSaleDay)
					&& Utils.objectIsNotEmpty(hotSaleNumber)) {
				Date endTime = new Date();// 当前时间 即结束时间
				// 计算查询订单时间范围
				Calendar cd = Calendar.getInstance();
				cd.setTime(endTime);
				cd.add(Calendar.MONDAY, -Integer.parseInt(hotSaleDay));// 减去热卖天数
				Date startTime = cd.getTime();// 开始时间
				// 通过商品id查询订单
				String hql = "select  count(a.ordersId) as count from Orders a , OrdersList b where UNIX_TIMESTAMP(a.createTime) >= UNIX_TIMESTAMP('"
						+ startTime
						+ "') and a.ordersId=b.ordersId and b.productId="
						+ productId;
				List<Map> listMap = ordersService.findListMapByHql(hql);
				if (Utils.objectIsNotEmpty(listMap)) {
					Integer count = Integer.parseInt(String.valueOf(listMap
							.get(0).get("count")));
					if (count.compareTo(Integer.parseInt(hotSaleNumber)) >= 0) {
						return "1";
					}
				}
			} else {
				return "0";
			}
		}
		return "0";
	}

	/**
	 * 保存商品收藏信息 注释部分为之前使用异步方式收藏商品，由于拦截器无法跳出异步，改成同步方式操作
	 */
	public String saveCustomerCollectProduct() throws IOException {
		Customer customer = (Customer) session.getAttribute("customer");
		if (null != customer) {
			Object obj = customerCollectProductService
					.getObjectByParams("where o.customerId = "
							+ customer.getCustomerId() + " and o.productId = "
							+ productId);
			// 判定是否需要添加收藏，并实行操作
			if (obj == null) {
				CustomerCollectProduct customerCollectProduct = new CustomerCollectProduct();
				productInfo = (ProductInfo) productInfoService
						.getObjectById(String.valueOf(productId));
				customerCollectProduct.setCustomerId(customer.getCustomerId());
				customerCollectProduct.setProductId(Integer.valueOf(productId));
				customerCollectProduct.setProductName(productInfo
						.getProductName());
				customerCollectProductService
						.saveOrUpdateObject(customerCollectProduct);
			} else {
			}
		}
		return SUCCESS;
	}

	/**
	 * 获取当前分类名称（无级限）
	 */
	public void getProductTypeName(Integer prodTypeId) {
		String path = request.getContextPath();
		// 获取当前的对象
		ProductType pt = (ProductType) productTypeService
				.getObjectByParams(" where o.productTypeId=" + prodTypeId);
		if (pt != null) {
			// 加上超链接
			String aLabel = "<a href='"
					+ path
					+ "/frontBrands/getBrandsAndProductInfos.action?productTypeId="
					+ pt.getProductTypeId() + "&level=" + pt.getLevel()
					+ "&orderBy=normal'>" + pt.getSortName() + "</a>";
			if (StringUtils.isNotEmpty(prodTypeNames)) {
				prodTypeNames = aLabel + "&nbsp;&gt;&nbsp;" + prodTypeNames;
			} else {
				prodTypeNames = aLabel;
			}
		}
		// 递归
		if (pt != null && pt.getParentId() != 1) {
			getProductTypeName(pt.getParentId());
		}
	}

	/**
	 * 查询商品评价
	 */
	public String evaluateProductList() {
		Integer haoPingCount = evaluateGoodsService
				.getCount(" where o.productId=" + productId
						+ " and o.level =1 and o.evaluateState=0 ");
		Integer zhongPingCount = evaluateGoodsService
				.getCount(" where o.productId=" + productId
						+ " and o.level =0 and o.evaluateState=0 ");
		Integer chaPingCount = evaluateGoodsService
				.getCount(" where o.productId=" + productId
						+ " and o.level=-1 and o.evaluateState=0 ");
		request.setAttribute("haoPingCount", haoPingCount);
		request.setAttribute("zhongPingCount", zhongPingCount);
		request.setAttribute("chaPingCount", chaPingCount);
		return SUCCESS;
	}

	/**
	 * 查询商品评价分类列表
	 */
	@SuppressWarnings("rawtypes")
	public String evaluateTypeList() {
		if (StringUtils.isNotEmpty(evaluateType)) {
			String hql_count = "select count(cr.evaluateId) from Customer c,EvaluateGoods cr"
					+ " where cr.productId="
					+ productId
					+ " and cr.appraiserId=c.customerId  and cr.evaluateState=0";
			String hql_list = "select c.loginName as loginName,c.photoUrl as photoUrl,cr.appraiserId as customerId,"
					+ "cr.productId as productId,cr.level as level,cr.content as content,cr.isAnonymous as isAnonymous,cr.explain as explain,"
					+ "cr.evaluateState as state,cr.bothState as backState,cr.createTime as createTime from Customer c,EvaluateGoods cr"
					+ " where cr.productId="
					+ productId
					+ " and cr.appraiserId=c.customerId and cr.evaluateState=0 ";
			if ("all".equals(evaluateType)) {
				hql_count += "  and cr.level is not null";
				hql_list += "  and cr.level is not null ";
			} else if ("good".equals(evaluateType)) {
				hql_count += "  and cr.level =1";
				hql_list += "  and cr.level =1 ";
			} else if ("medium".equals(evaluateType)) {
				hql_count += "  and cr.level =0";
				hql_list += "  and cr.level =0 ";
			} else {
				hql_count += "  and cr.level=-1";
				hql_list += "  and cr.level=-1 ";
			}
			hql_list += "order by cr.createTime desc,cr.level desc ";
			Integer totalRecordCount = evaluateGoodsService
					.getCountByHQL(hql_count);
			pageHelper.setPageInfo(5, totalRecordCount, currentPage);
			List<Map> cusRevList = evaluateGoodsService.findListMapPage(
					hql_list, pageHelper);
			request.setAttribute("cusRevList", cusRevList);
		}
		return SUCCESS;
	}

	/**
	 * 根据规格查找商品
	 */
	public void selectSpecification() {
		try {
			String hql = "where 1=1";
			if (StringUtils.isNotEmpty(params)) {
				params = Escape.unescape(params);
				String[] param = params.split(",");
				for (int i = 0; i < param.length; i++) {
					hql += " and o.productFullName like '%[%" + param[i]
							+ "%]%'";
				}
			}
			hql += " and o.goods=" + goods;
			ProductInfo product = (ProductInfo) productInfoService
					.getObjectByParams(hql);
			JSONObject jo = JSONObject.fromObject(product);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ProductInfo getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(ProductInfo productInfo) {
		this.productInfo = productInfo;
	}

	public ProductImg getProductImg() {
		return productImg;
	}

	public void setProductImg(ProductImg productImg) {
		this.productImg = productImg;
	}

	public ShopInfo getShopInfo() {
		return shopInfo;
	}

	public void setShopInfo(ShopInfo shopInfo) {
		this.shopInfo = shopInfo;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	@SuppressWarnings({ "rawtypes" })
	public List<Map> getMapList() {
		return mapList;
	}

	@SuppressWarnings("rawtypes")
	public void setMapList(List<Map> mapList) {
		this.mapList = mapList;
	}

	public void setProductInfoService(IProductInfoService productInfoService) {
		this.productInfoService = productInfoService;
	}

	public void setProductImgService(IProductImgService productImgService) {
		this.productImgService = productImgService;
	}

	public void setShopInfoService(IShopInfoService shopInfoService) {
		this.shopInfoService = shopInfoService;
	}

	public void setCustomerCollectProductService(
			ICustomerCollectProductService customerCollectProductService) {
		this.customerCollectProductService = customerCollectProductService;
	}

	public void setProductTypeService(IProductTypeService productTypeService) {
		this.productTypeService = productTypeService;
	}

	public List<ProductType> getProductTypeList() {
		return productTypeList;
	}

	public void setProductTypeList(List<ProductType> productTypeList) {
		this.productTypeList = productTypeList;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public void setSpecificationService(
			ISpecificationService specificationService) {
		this.specificationService = specificationService;
	}

	public void setSpecificationValueService(
			ISpecificationValueService specificationValueService) {
		this.specificationValueService = specificationValueService;
	}

	public void setProductSpecificationService(
			IProductSpecificationService productSpecificationService) {
		this.productSpecificationService = productSpecificationService;
	}

	public void setProductSpecificationValueService(
			IProductSpecificationValueService productSpecificationValueService) {
		this.productSpecificationValueService = productSpecificationValueService;
	}

	@SuppressWarnings("rawtypes")
	public Map getMap() {
		return map;
	}

	@SuppressWarnings("rawtypes")
	public void setMap(Map map) {
		this.map = map;
	}

	public List<ProductImg> getProductImgList() {
		return productImgList;
	}

	public void setProductImgList(List<ProductImg> productImgList) {
		this.productImgList = productImgList;
	}

	public List<String> getProductTagList() {
		return productTagList;
	}

	public void setProductTagList(List<String> productTagList) {
		this.productTagList = productTagList;
	}

	public JSONObject getJoProductPara() {
		return joProductPara;
	}

	public void setJoProductPara(JSONObject joProductPara) {
		this.joProductPara = joProductPara;
	}

	public JSONArray getJaListProductAttr() {
		return jaListProductAttr;
	}

	public void setJaListProductAttr(JSONArray jaListProductAttr) {
		this.jaListProductAttr = jaListProductAttr;
	}

	public List<ProductAttribute> getListProductAttr() {
		return listProductAttr;
	}

	public void setListProductAttr(List<ProductAttribute> listProductAttr) {
		this.listProductAttr = listProductAttr;
	}

	public void setProductAttributeService(
			IProductAttributeService productAttributeService) {
		this.productAttributeService = productAttributeService;
	}

	public void setEvaluateGoodsService(
			IEvaluateGoodsService evaluateGoodsService) {
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

	public String getSids() {
		return sids;
	}

	public void setSids(String sids) {
		this.sids = sids;
	}

	public String getProdTypeNames() {
		return prodTypeNames;
	}

	public void setProdTypeNames(String prodTypeNames) {
		this.prodTypeNames = prodTypeNames;
	}

	public String getEvaluateType() {
		return evaluateType;
	}

	public void setEvaluateType(String evaluateType) {
		this.evaluateType = evaluateType;
	}

	public Integer getGoods() {
		return goods;
	}

	public void setGoods(Integer goods) {
		this.goods = goods;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getGoodsSpecifications() {
		return goodsSpecifications;
	}

	public void setGoodsSpecifications(String goodsSpecifications) {
		this.goodsSpecifications = goodsSpecifications;
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

	public void setBuyersRecordService(IBuyersRecordService buyersRecordService) {
		this.buyersRecordService = buyersRecordService;
	}

	public BigDecimal getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(BigDecimal buyPrice) {
		this.buyPrice = buyPrice;
	}

	public Integer getStockUpDate() {
		return stockUpDate;
	}

	public void setStockUpDate(Integer stockUpDate) {
		this.stockUpDate = stockUpDate;
	}

	public Integer getClevel() {
		return clevel;
	}

	public void setClevel(Integer clevel) {
		this.clevel = clevel;
	}

	public Integer getCdiscount() {
		return cdiscount;
	}

	public void setCdiscount(Integer cdiscount) {
		this.cdiscount = cdiscount;
	}

	public void setOrdersService(IOrdersService ordersService) {
		this.ordersService = ordersService;
	}

	public void setOrdersListService(IOrdersListService ordersListService) {
		this.ordersListService = ordersListService;
	}

	public String getBuyPriceCustomer() {
		return buyPriceCustomer;
	}

	public void setBuyPriceCustomer(String buyPriceCustomer) {
		this.buyPriceCustomer = buyPriceCustomer;
	}

	public BigDecimal getNowProductBuyPrice() {
		return nowProductBuyPrice;
	}

	public void setNowProductBuyPrice(BigDecimal nowProductBuyPrice) {
		this.nowProductBuyPrice = nowProductBuyPrice;
	}

	public void setShopSituationTagService(
			IShopSituationTagService shopSituationTagService) {
		this.shopSituationTagService = shopSituationTagService;
	}

	public void setShopTradeSituationTagService(
			IShopTradeSituationTagService shopTradeSituationTagService) {
		this.shopTradeSituationTagService = shopTradeSituationTagService;
	}

	public void setShopProductTradeSituationTagService(
			IShopProductTradeSituationTagService shopProductTradeSituationTagService) {
		this.shopProductTradeSituationTagService = shopProductTradeSituationTagService;
	}

	public void setShopTradeTagService(IShopTradeTagService shopTradeTagService) {
		this.shopTradeTagService = shopTradeTagService;
	}

	public BigDecimal getCustomerPrice() {
		return customerPrice;
	}

	public void setCustomerPrice(BigDecimal customerPrice) {
		this.customerPrice = customerPrice;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public void setAreaService(IAreaService areaService) {
		this.areaService = areaService;
	}

	public JSONArray getJaattrList() {
		return jaattrList;
	}

	public void setJaattrList(JSONArray jaattrList) {
		this.jaattrList = jaattrList;
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

	public void setMemberShipService(IMemberShipService memberShipService) {
		this.memberShipService = memberShipService;
	}

	public String getHasProductGGG() {
		return hasProductGGG;
	}

	public void setHasProductGGG(String hasProductGGG) {
		this.hasProductGGG = hasProductGGG;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

}
