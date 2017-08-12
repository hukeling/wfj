package shop.front.home.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import promotion.pojo.Promotion;
import promotion.service.IPlatformPromotionService;
import shop.customer.pojo.Customer;
import shop.front.bean.AllProductTypeBean;
import shop.front.bean.BottomNewsModuleBean;
import shop.front.shoppingOnLine.service.IShoppingCartService;
import shop.homeIndex.pojo.HomeAdvertisement;
import shop.homeIndex.pojo.HomeKeyBook;
import shop.homeIndex.pojo.HomeLBT;
import shop.homeIndex.pojo.HomeParticularlyTab;
import shop.homeIndex.pojo.ShopHomeMiddleCategory;
import shop.homeIndex.pojo.ShopHomeMiddleCategoryBilateral;
import shop.homeIndex.pojo.ShopHomeMiddleCategoryTAB;
import shop.homeIndex.service.IHomeAdvertisementService;
import shop.homeIndex.service.IHomeLBTService;
import shop.homeIndex.service.IHomeParticularlyTabService;
import shop.homeIndex.service.IShopHomeMiddleCategoryBilateralService;
import shop.homeIndex.service.IShopHomeMiddleCategoryService;
import shop.homeIndex.service.IShopHomeMiddleCategoryTABService;
import shop.product.pojo.Brand;
import shop.product.pojo.ProductType;
import shop.product.service.IBrandService;
import shop.product.service.IBrandTypeService;
import shop.product.service.IProductInfoService;
import shop.product.service.IProductTypeService;
import util.action.BaseAction;
import util.other.Utils;
import cms.article.pojo.CmsArticle;
import cms.article.service.IArticleService;

/**
 * HomeAction - 前台首页Action
 */
@SuppressWarnings("serial")
public class HomeAction extends BaseAction {
	private IHomeLBTService homeLBTService;// 首页轮播图
	private IHomeAdvertisementService homeAdvertisementService;// 首页广告位
	private IHomeParticularlyTabService homeParticularlyTabService;// 首页特别商品
	private IArticleService articleService;// 天天快报
	private IPlatformPromotionService promotionService;// 促销信息
	private IShopHomeMiddleCategoryService shopHomeMiddleCategoryService;// 中间部分以及分类
	private IShopHomeMiddleCategoryBilateralService shopHomeMiddleCategoryBilateralService;// 一级分类下的数据：促销商品
	private IShopHomeMiddleCategoryTABService shopHomeMiddleCategoryTABService;// 小轮播图
	private IShoppingCartService shoppingCartService;// 购物车
	private IProductInfoService productInfoService;
	/** 品牌service **/
	private IBrandService brandService;
	/** 分类品牌service **/
	private IBrandTypeService brandTypeService;
	private String txt;// 易采快报搜索条件
	/** 商品分类ID **/
	private String productTypeId;
	/** 商品分类Service **/
	private IProductTypeService productTypeService;
	/** 主题馆参数 **/
	private String categoryParams;
	/** 登录是否登录标识 */
	private String customerId;

	/**
	 * 跳转到分类列表页
	 * 
	 * @author 琦瑞
	 */
	public String gotoType() {
		// 根据分类ID查询品牌数据
		String hql = "select a.brandId as brandId , b.brandBigImageUrl as brandBigImageUrl,b.brandImageUrl as brandImageUrl,b.brandName as brandName from BrandType a,Brand b where a.productTypeId = "
				+ productTypeId
				+ " and a.brandId = b.brandId order by b.sortCode";
		String hqlCount = "select count(a.brandId) as count from BrandType a,Brand b where a.productTypeId = "
				+ productTypeId
				+ " and a.brandId = b.brandId order by b.sortCode";
		int totalRecordCount = brandTypeService.getMultilistCount(hqlCount);
		pageHelper.setPageInfo(30, totalRecordCount, currentPage);
		List<Map> brandList = brandTypeService.findListMapPage(hql, pageHelper);
		request.setAttribute("brandList", brandList);
		// 获取路径信息
		String pathInfo = String.valueOf(getPathInfo(new StringBuffer(),
				productTypeId, categoryParams));
		request.setAttribute(
				"pathInfo",
				pathInfo.substring("&nbsp;&nbsp;&gt;".length(),
						pathInfo.length()));
		return SUCCESS;
	}

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

	// 跳转到phoneHome页面
	@SuppressWarnings("unchecked")
	public String gotoPhoneHomePage() {
		// log.info("----- start buiding index page start -----");
		Map<String, Object> params = new HashMap<String, Object>();
		Map fileUrlConfig = BaseAction.getFileUrlConfig();
		// params.put("contextPath", context.getContextPath());
		request.setAttribute("fileUrlConfig", fileUrlConfig);
		// params.put("sessionTest", session);
		/************************** 数据查询 whb ******************************************************/
		// 首页大轮播图List
		List<HomeLBT> homeLBTList = (List<HomeLBT>) homeLBTService
				.findListSpecifiedNumber(null, 0, 5,
						"  where o.isShow=1 order by o.sortCode asc ");
		// 首页广告位
		List<HomeAdvertisement> homeAdvertisementList = (List<HomeAdvertisement>) homeAdvertisementService
				.findListSpecifiedNumber(null, 0, 8,
						" where o.isShow=1 order by o.showLocation ");
		// 首页小轮播图广告位
		List<HomeAdvertisement> homeLittleAdvertisementList = (List<HomeAdvertisement>) homeAdvertisementService
				.findListSpecifiedNumber(null, 0, 10,
						" where o.isShow=1 and o.showLocation=9 order by o.sortCode ");
		// 首页新闻上方广告位
		List<HomeAdvertisement> homeNewsAdvertisementList = (List<HomeAdvertisement>) homeAdvertisementService
				.findListSpecifiedNumber(null, 0, 4,
						" where o.isShow=1 and o.showLocation=5 order by o.sortCode ");
		// 首页底部广告位
		List<HomeAdvertisement> homeBottomAdvertisementList = (List<HomeAdvertisement>) homeAdvertisementService
				.findListSpecifiedNumber(null, 0, 2,
						" where o.isShow=1 and o.showLocation=6 order by o.sortCode ");
		// 首页特别商品tab
		List<HomeParticularlyTab> homeParticularlyTabList1 = (List<HomeParticularlyTab>) homeParticularlyTabService
				.findListSpecifiedNumber(
						null,
						0,
						5,
						" where o.isShow=1 and o.showLocation=1 order by o.sortCode asc, o.tabProductId desc ");// 标签1
		List<HomeParticularlyTab> homeParticularlyTabList2 = (List<HomeParticularlyTab>) homeParticularlyTabService
				.findListSpecifiedNumber(
						null,
						0,
						5,
						" where o.isShow=1 and o.showLocation=2 order by o.sortCode asc, o.tabProductId desc");// 标签2
		List<HomeParticularlyTab> homeParticularlyTabList3 = (List<HomeParticularlyTab>) homeParticularlyTabService
				.findListSpecifiedNumber(
						null,
						0,
						5,
						" where o.isShow=1 and o.showLocation=3 order by o.sortCode asc, o.tabProductId desc");// 标签3
		List<HomeParticularlyTab> homeParticularlyTabList4 = (List<HomeParticularlyTab>) homeParticularlyTabService
				.findListSpecifiedNumber(
						null,
						0,
						5,
						" where o.isShow=1 and o.showLocation=4 order by o.sortCode asc, o.tabProductId desc");// 标签4
		List<HomeParticularlyTab> homeParticularlyTabList5 = (List<HomeParticularlyTab>) homeParticularlyTabService
				.findListSpecifiedNumber(
						null,
						0,
						5,
						" where o.isShow=1 and o.showLocation=5 order by o.sortCode asc, o.tabProductId desc");// 标签5
		// 天天快报
		List<CmsArticle> articleList = (List<CmsArticle>) articleService
				.findListSpecifiedNumber(null, 0, 9,
						" where o.isShow=1 and o.categoryId=112 order by o.articleId desc ");
		// 促销信息
		List<Promotion> promotionList = (List<Promotion>) promotionService
				.findListSpecifiedNumber(null, 0, 6,
						" where o.isPass=1 order by o.promotionId desc ");
		// 查询首页底部文章信息
		List<BottomNewsModuleBean> bottomNewsModuleBeans = (List<BottomNewsModuleBean>) servletContext
				.getAttribute("bottomNewsModuleBeans");
		// 底部链接
		List<CmsArticle> articleLinkList = articleService
				.findObjects(" where o.categoryId=99 order by o.sortCode asc");
		// 品牌推荐 查询条件 1显示 2推荐
		List<Brand> brandList = brandService
				.findSome(0, 16,
						" where o.isShow = 1 and o.isRecommend = 1 order by o.sortCode");

		/************************** 数据查询 mqr ******************************************************/
		// listMap用于前台首页中间部分分类展示
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		// 查询首页中间部分分类信息：一级分类
		/* 从数据字典中读取分类个数 */
		Map<String, List<HomeKeyBook>> homeKeyBookMap = (Map<String, List<HomeKeyBook>>) servletContext
				.getAttribute("homekeybook");
		List<ShopHomeMiddleCategory> shopHomeMiddleCategoryList = (List<ShopHomeMiddleCategory>) shopHomeMiddleCategoryService
				.findListSpecifiedNumber(
						null,
						0,
						Integer.parseInt(homeKeyBookMap
								.get("middleCategoryNum").get(0).getValue()),
						" where o.parentId=0 and o.isShow=1 order by o.sortCode");
		if (shopHomeMiddleCategoryList != null
				&& shopHomeMiddleCategoryList.size() > 0) {
			for (ShopHomeMiddleCategory shmc : shopHomeMiddleCategoryList) {
				// 封装categoryMiddleMap用于前台首页中间部分分类展示
				Map<String, Object> categoryMiddleMap = new HashMap<String, Object>();
				categoryMiddleMap.put("yiJCategory", shmc);
				// 查询一级分类下的数据：二级小分类
				List<ShopHomeMiddleCategoryBilateral> shopHomeMiddleCategoryBilateralList = (List<ShopHomeMiddleCategoryBilateral>) shopHomeMiddleCategoryBilateralService
						.findListSpecifiedNumber(
								null,
								0,
								8,
								" where o.categoryId="
										+ shmc.getCategoryId()
										+ " and o.isShow=1 and o.type=1 order by o.sortCode");
				categoryMiddleMap.put("secCategory",
						shopHomeMiddleCategoryBilateralList);
				// 查询一级分类下的数据：促销商品
				List<ShopHomeMiddleCategoryBilateral> shopHomeMiddleCategoryBilateralListProduct = (List<ShopHomeMiddleCategoryBilateral>) shopHomeMiddleCategoryBilateralService
						.findListSpecifiedNumber(
								null,
								0,
								8,
								" where o.categoryId="
										+ shmc.getCategoryId()
										+ " and o.isShow=1 and o.type=2 order by o.sortCode");
				if (shopHomeMiddleCategoryBilateralListProduct != null
						&& shopHomeMiddleCategoryBilateralListProduct.size() > 0) {
					categoryMiddleMap.put("promotionProductList",
							shopHomeMiddleCategoryBilateralListProduct);
				}
				// 查询热销排行数据
				List<ShopHomeMiddleCategoryBilateral> shopHomeMiddleCategoryBilateralListSort = (List<ShopHomeMiddleCategoryBilateral>) shopHomeMiddleCategoryBilateralService
						.findListSpecifiedNumber(
								null,
								0,
								7,
								" where o.categoryId="
										+ shmc.getCategoryId()
										+ " and o.isShow=1 and o.type=3 order by o.sortCode");
				categoryMiddleMap.put("promotionProductSort",
						shopHomeMiddleCategoryBilateralListSort);
				// 根据一级分类id查询二级分类 用于tab展示
				List<ShopHomeMiddleCategory> shopHomeMiddleCategoryList2 = (List<ShopHomeMiddleCategory>) shopHomeMiddleCategoryService
						.findListSpecifiedNumber(null, 0, 5,
								" where o.parentId=" + shmc.getCategoryId()
										+ " and o.isShow=1 order by o.sortCode");
				List<Map<String, Object>> listMapTab = new ArrayList<Map<String, Object>>();
				if (shopHomeMiddleCategoryList2 != null
						&& shopHomeMiddleCategoryList2.size() > 0) {
					for (ShopHomeMiddleCategory sc : shopHomeMiddleCategoryList2) {
						Map<String, Object> middleMap = new HashMap<String, Object>();
						// 根据二级分类的id 查询商品或促销 以及小轮播图信息
						// 商品或促销 imageType=1
						List<ShopHomeMiddleCategoryTAB> tabProductList = (List<ShopHomeMiddleCategoryTAB>) shopHomeMiddleCategoryTABService
								.findListSpecifiedNumber(
										null,
										0,
										1,
										" where o.categoryId="
												+ sc.getCategoryId()
												+ " and o.isShow=1 and o.imageType=1 order by o.sortCode asc ,categoryTabId desc");
						middleMap.put("tabProductList", tabProductList);
						for (ShopHomeMiddleCategoryTAB shopHomeMiddleCategoryTAB : tabProductList) {
							String end = shopHomeMiddleCategoryTAB.getLink()
									.substring(
											shopHomeMiddleCategoryTAB.getLink()
													.lastIndexOf("/"),
											shopHomeMiddleCategoryTAB.getLink()
													.length());
							String start = this.fileUrlConfig.get("commonUrl")
									.toString();
							//String url = start + "phoneProductInfo" + end;
							String url="http://192.168.1.118:8080/wfj_front/" + "phoneProductInfo" + end;
							shopHomeMiddleCategoryTAB.setLink(url);
						}
						// 小轮播图 imageType=2
						/*
						 * List<ShopHomeMiddleCategoryTAB> lbtList =
						 * (List<ShopHomeMiddleCategoryTAB>)
						 * shopHomeMiddleCategoryTABService
						 * .findListSpecifiedNumber(null, 0, 3,
						 * " where o.categoryId="
						 * +sc.getCategoryId()+" and o.isShow=1 and o.imageType=2"
						 * ); middleMap.put("lbtList", lbtList);
						 */
						middleMap.put("tabName", sc.getCategoryName());
						middleMap.put("link", sc.getLink());
						listMapTab.add(middleMap);
					}
					categoryMiddleMap.put("listMapTab", listMapTab);
				}
				listMap.add(categoryMiddleMap);// 添加到最外层
			}
		}
		// 查询首页全部分类信息
		List<AllProductTypeBean> productTypeBeanList = (List<AllProductTypeBean>) servletContext
				.getAttribute("productTypeBeanList");

		// 精品推荐----美食类
		/*
		 * String hql =
		 * "from ProductInfo where parentId in (64,1451,1452,1453,1454,1455,1456,1458)"
		 * ; List<ProductInfo> phoneBoutiqueList = productInfoService
		 * .findObjectsByHQL(hql); session.setAttribute("phoneBoutiqueList",
		 * phoneBoutiqueList);
		 */
		/************************** 参数存储 whb ******************************************************/
		request.setAttribute("homeLBTList", homeLBTList);// 大轮播图
		request.setAttribute("homeLittleAdvertisementList",
				homeLittleAdvertisementList);// 首页小轮播图广告位
		request.setAttribute("homeAdvertisementList", homeAdvertisementList);// 广告位
		request.setAttribute("homeNewsAdvertisementList",
				homeNewsAdvertisementList);// 首页新闻上方广告位
		request.setAttribute("homeBottomAdvertisementList",
				homeBottomAdvertisementList);// 首页底部广告位
		request.setAttribute("homeParticularlyTabList1",
				homeParticularlyTabList1);// 特别商品标签1
		request.setAttribute("homeParticularlyTabList2",
				homeParticularlyTabList2);// 特别商品标签2
		request.setAttribute("homeParticularlyTabList3",
				homeParticularlyTabList3);// 特别商品标签3
		request.setAttribute("homeParticularlyTabList4",
				homeParticularlyTabList4);// 特别商品标签4
		request.setAttribute("homeParticularlyTabList5",
				homeParticularlyTabList5);// 特别商品标签5
		request.setAttribute("articleList", articleList);// 天天快报
		// request.setAttribute("promotionList", promotionList);//促销信息
		request.setAttribute("bottomNewsModuleBeans", bottomNewsModuleBeans);// 首页底部新闻信息
		request.setAttribute("bottomNewsLink", articleLinkList);// 首页底部新闻链接信息
		session.setAttribute("brandList", brandList);// 首页品牌推荐
		session.setAttribute("promotionList", promotionList);
		/************************** 参数存储 mqr ******************************************************/
		request.setAttribute("listMap", listMap);// 首页中间分类部分
		request.setAttribute("productTypeBeanList", productTypeBeanList);// 首页全部分类
		// 查询购物车相关信息
		Customer customer = (Customer) session.getAttribute("customer");
		if (customer != null) {
			session.setAttribute("customerId", customer.getCustomerId());
		} else if (customerId != null) {
			session.setAttribute("customerId", customerId);
		}
		Cookie[] cookie = request.getCookies();
		Map cartCountMap = new HashMap<Object, Object>();
		Map cartMap = shoppingCartService.gotoShoppingCart(customer, cookie,
				cartCountMap);
		session.setAttribute("cartMap", cartMap);
		session.setAttribute("cartCountMap",
				String.valueOf(cartCountMap.get("count")));
		return SUCCESS;
	}

	// 跳转到phoneHome页面
	@SuppressWarnings("unchecked")
	public String gotoTestPage() {
		// log.info("----- start buiding index page start -----");
		Map<String, Object> params = new HashMap<String, Object>();
		Map fileUrlConfig = BaseAction.getFileUrlConfig();
		// params.put("contextPath", context.getContextPath());
		request.setAttribute("fileUrlConfig", fileUrlConfig);
		// params.put("sessionTest", session);
		/************************** 数据查询 whb ******************************************************/
		// 首页大轮播图List
		List<HomeLBT> homeLBTList = (List<HomeLBT>) homeLBTService
				.findListSpecifiedNumber(null, 0, 5,
						"  where o.isShow=1 order by o.sortCode asc ");
		// 首页广告位
		List<HomeAdvertisement> homeAdvertisementList = (List<HomeAdvertisement>) homeAdvertisementService
				.findListSpecifiedNumber(null, 0, 8,
						" where o.isShow=1 order by o.showLocation ");
		// 首页小轮播图广告位
		List<HomeAdvertisement> homeLittleAdvertisementList = (List<HomeAdvertisement>) homeAdvertisementService
				.findListSpecifiedNumber(null, 0, 10,
						" where o.isShow=1 and o.showLocation=9 order by o.sortCode ");
		// 首页新闻上方广告位
		List<HomeAdvertisement> homeNewsAdvertisementList = (List<HomeAdvertisement>) homeAdvertisementService
				.findListSpecifiedNumber(null, 0, 4,
						" where o.isShow=1 and o.showLocation=5 order by o.sortCode ");
		// 首页底部广告位
		List<HomeAdvertisement> homeBottomAdvertisementList = (List<HomeAdvertisement>) homeAdvertisementService
				.findListSpecifiedNumber(null, 0, 2,
						" where o.isShow=1 and o.showLocation=6 order by o.sortCode ");
		// 首页特别商品tab
		List<HomeParticularlyTab> homeParticularlyTabList1 = (List<HomeParticularlyTab>) homeParticularlyTabService
				.findListSpecifiedNumber(
						null,
						0,
						5,
						" where o.isShow=1 and o.showLocation=1 order by o.sortCode asc, o.tabProductId desc ");// 标签1
		List<HomeParticularlyTab> homeParticularlyTabList2 = (List<HomeParticularlyTab>) homeParticularlyTabService
				.findListSpecifiedNumber(
						null,
						0,
						5,
						" where o.isShow=1 and o.showLocation=2 order by o.sortCode asc, o.tabProductId desc");// 标签2
		List<HomeParticularlyTab> homeParticularlyTabList3 = (List<HomeParticularlyTab>) homeParticularlyTabService
				.findListSpecifiedNumber(
						null,
						0,
						5,
						" where o.isShow=1 and o.showLocation=3 order by o.sortCode asc, o.tabProductId desc");// 标签3
		List<HomeParticularlyTab> homeParticularlyTabList4 = (List<HomeParticularlyTab>) homeParticularlyTabService
				.findListSpecifiedNumber(
						null,
						0,
						5,
						" where o.isShow=1 and o.showLocation=4 order by o.sortCode asc, o.tabProductId desc");// 标签4
		List<HomeParticularlyTab> homeParticularlyTabList5 = (List<HomeParticularlyTab>) homeParticularlyTabService
				.findListSpecifiedNumber(
						null,
						0,
						5,
						" where o.isShow=1 and o.showLocation=5 order by o.sortCode asc, o.tabProductId desc");// 标签5
		// 天天快报
		List<CmsArticle> articleList = (List<CmsArticle>) articleService
				.findListSpecifiedNumber(null, 0, 9,
						" where o.isShow=1 and o.categoryId=112 order by o.articleId desc ");
		// 促销信息
		// List<Promotion> promotionList = (List<Promotion>)
		// promotionService.findListSpecifiedNumber(null, 0, 6,
		// " where o.isPass=1 order by o.promotionId desc ");
		// 查询首页底部文章信息
		List<BottomNewsModuleBean> bottomNewsModuleBeans = (List<BottomNewsModuleBean>) servletContext
				.getAttribute("bottomNewsModuleBeans");
		// 底部链接
		List<CmsArticle> articleLinkList = articleService
				.findObjects(" where o.categoryId=99 order by o.sortCode asc");
		// 品牌推荐 查询条件 1显示 2推荐
		List<Brand> brandList = brandService
				.findSome(0, 16,
						" where o.isShow = 1 and o.isRecommend = 1 order by o.sortCode");

		/************************** 数据查询 mqr ******************************************************/
		// listMap用于前台首页中间部分分类展示
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		// 查询首页中间部分分类信息：一级分类
		/* 从数据字典中读取分类个数 */
		Map<String, List<HomeKeyBook>> homeKeyBookMap = (Map<String, List<HomeKeyBook>>) servletContext
				.getAttribute("homekeybook");
		List<ShopHomeMiddleCategory> shopHomeMiddleCategoryList = (List<ShopHomeMiddleCategory>) shopHomeMiddleCategoryService
				.findListSpecifiedNumber(
						null,
						0,
						Integer.parseInt(homeKeyBookMap
								.get("middleCategoryNum").get(0).getValue()),
						" where o.parentId=0 and o.isShow=1 order by o.sortCode");
		if (shopHomeMiddleCategoryList != null
				&& shopHomeMiddleCategoryList.size() > 0) {
			for (ShopHomeMiddleCategory shmc : shopHomeMiddleCategoryList) {
				// 封装categoryMiddleMap用于前台首页中间部分分类展示
				Map<String, Object> categoryMiddleMap = new HashMap<String, Object>();
				categoryMiddleMap.put("yiJCategory", shmc);
				// 查询一级分类下的数据：二级小分类
				List<ShopHomeMiddleCategoryBilateral> shopHomeMiddleCategoryBilateralList = (List<ShopHomeMiddleCategoryBilateral>) shopHomeMiddleCategoryBilateralService
						.findListSpecifiedNumber(
								null,
								0,
								8,
								" where o.categoryId="
										+ shmc.getCategoryId()
										+ " and o.isShow=1 and o.type=1 order by o.sortCode");
				categoryMiddleMap.put("secCategory",
						shopHomeMiddleCategoryBilateralList);
				// 查询一级分类下的数据：促销商品
				List<ShopHomeMiddleCategoryBilateral> shopHomeMiddleCategoryBilateralListProduct = (List<ShopHomeMiddleCategoryBilateral>) shopHomeMiddleCategoryBilateralService
						.findListSpecifiedNumber(
								null,
								0,
								8,
								" where o.categoryId="
										+ shmc.getCategoryId()
										+ " and o.isShow=1 and o.type=2 order by o.sortCode");
				if (shopHomeMiddleCategoryBilateralListProduct != null
						&& shopHomeMiddleCategoryBilateralListProduct.size() > 0) {
					categoryMiddleMap.put("promotionProductList",
							shopHomeMiddleCategoryBilateralListProduct);
				}
				// 查询热销排行数据
				List<ShopHomeMiddleCategoryBilateral> shopHomeMiddleCategoryBilateralListSort = (List<ShopHomeMiddleCategoryBilateral>) shopHomeMiddleCategoryBilateralService
						.findListSpecifiedNumber(
								null,
								0,
								7,
								" where o.categoryId="
										+ shmc.getCategoryId()
										+ " and o.isShow=1 and o.type=3 order by o.sortCode");
				categoryMiddleMap.put("promotionProductSort",
						shopHomeMiddleCategoryBilateralListSort);
				// 根据一级分类id查询二级分类 用于tab展示
				List<ShopHomeMiddleCategory> shopHomeMiddleCategoryList2 = (List<ShopHomeMiddleCategory>) shopHomeMiddleCategoryService
						.findListSpecifiedNumber(null, 0, 5,
								" where o.parentId=" + shmc.getCategoryId()
										+ " and o.isShow=1 order by o.sortCode");
				List<Map<String, Object>> listMapTab = new ArrayList<Map<String, Object>>();
				if (shopHomeMiddleCategoryList2 != null
						&& shopHomeMiddleCategoryList2.size() > 0) {
					for (ShopHomeMiddleCategory sc : shopHomeMiddleCategoryList2) {
						Map<String, Object> middleMap = new HashMap<String, Object>();
						// 根据二级分类的id 查询商品或促销 以及小轮播图信息
						// 商品或促销 imageType=1
						List<ShopHomeMiddleCategoryTAB> tabProductList = (List<ShopHomeMiddleCategoryTAB>) shopHomeMiddleCategoryTABService
								.findListSpecifiedNumber(
										null,
										0,
										10,
										" where o.categoryId="
												+ sc.getCategoryId()
												+ " and o.isShow=1 and o.imageType=1 order by o.sortCode asc ,categoryTabId desc");
						middleMap.put("tabProductList", tabProductList);
						// 小轮播图 imageType=2
						/*
						 * List<ShopHomeMiddleCategoryTAB> lbtList =
						 * (List<ShopHomeMiddleCategoryTAB>)
						 * shopHomeMiddleCategoryTABService
						 * .findListSpecifiedNumber(null, 0, 3,
						 * " where o.categoryId="
						 * +sc.getCategoryId()+" and o.isShow=1 and o.imageType=2"
						 * ); middleMap.put("lbtList", lbtList);
						 */
						middleMap.put("tabName", sc.getCategoryName());
						middleMap.put("link", sc.getLink());
						listMapTab.add(middleMap);
					}
					;
					categoryMiddleMap.put("listMapTab", listMapTab);
				}
				listMap.add(categoryMiddleMap);// 添加到最外层
			}
		}
		// 查询首页全部分类信息
		List<AllProductTypeBean> productTypeBeanList = (List<AllProductTypeBean>) servletContext
				.getAttribute("productTypeBeanList");

		/************************** 参数存储 whb ******************************************************/
		request.setAttribute("homeLBTList", homeLBTList);// 大轮播图
		request.setAttribute("homeLittleAdvertisementList",
				homeLittleAdvertisementList);// 首页小轮播图广告位
		request.setAttribute("homeAdvertisementList", homeAdvertisementList);// 广告位
		request.setAttribute("homeNewsAdvertisementList",
				homeNewsAdvertisementList);// 首页新闻上方广告位
		request.setAttribute("homeBottomAdvertisementList",
				homeBottomAdvertisementList);// 首页底部广告位
		request.setAttribute("homeParticularlyTabList1",
				homeParticularlyTabList1);// 特别商品标签1
		request.setAttribute("homeParticularlyTabList2",
				homeParticularlyTabList2);// 特别商品标签2
		request.setAttribute("homeParticularlyTabList3",
				homeParticularlyTabList3);// 特别商品标签3
		request.setAttribute("homeParticularlyTabList4",
				homeParticularlyTabList4);// 特别商品标签4
		request.setAttribute("homeParticularlyTabList5",
				homeParticularlyTabList5);// 特别商品标签5
		request.setAttribute("articleList", articleList);// 天天快报
		// request.setAttribute("promotionList", promotionList);//促销信息
		request.setAttribute("bottomNewsModuleBeans", bottomNewsModuleBeans);// 首页底部新闻信息
		request.setAttribute("bottomNewsLink", articleLinkList);// 首页底部新闻链接信息
		request.setAttribute("brandList", brandList);// 首页品牌推荐
		/************************** 参数存储 mqr ******************************************************/
		request.setAttribute("listMap", listMap);// 首页中间分类部分
		request.setAttribute("productTypeBeanList", productTypeBeanList);// 首页全部分类
		// 查询购物车相关信息
		Customer customer = (Customer) session.getAttribute("customer");
		Cookie[] cookie = request.getCookies();
		Map cartCountMap = new HashMap<Object, Object>();
		Map cartMap = shoppingCartService.gotoShoppingCart(customer, cookie,
				cartCountMap);
		session.setAttribute("cartMap", cartMap);
		session.setAttribute("cartCountMap",
				String.valueOf(cartCountMap.get("count")));
		return SUCCESS;
	}

	// 跳转到Home页面
	@SuppressWarnings("unchecked")
	public String gotoHomePage() {
		// log.info("----- start buiding index page start -----");
		Map<String, Object> params = new HashMap<String, Object>();
		Map fileUrlConfig = BaseAction.getFileUrlConfig();
		// params.put("contextPath", context.getContextPath());
		request.setAttribute("fileUrlConfig", fileUrlConfig);
		// params.put("sessionTest", session);
		/************************** 数据查询 whb ******************************************************/
		// 首页大轮播图List
		List<HomeLBT> homeLBTList = (List<HomeLBT>) homeLBTService
				.findListSpecifiedNumber(null, 0, 5,
						"  where o.isShow=1 order by o.sortCode asc ");
		// 首页广告位
		List<HomeAdvertisement> homeAdvertisementList = (List<HomeAdvertisement>) homeAdvertisementService
				.findListSpecifiedNumber(null, 0, 8,
						" where o.isShow=1 order by o.showLocation ");
		// 首页小轮播图广告位
		List<HomeAdvertisement> homeLittleAdvertisementList = (List<HomeAdvertisement>) homeAdvertisementService
				.findListSpecifiedNumber(null, 0, 10,
						" where o.isShow=1 and o.showLocation=9 order by o.sortCode ");
		// 首页新闻上方广告位
		List<HomeAdvertisement> homeNewsAdvertisementList = (List<HomeAdvertisement>) homeAdvertisementService
				.findListSpecifiedNumber(null, 0, 4,
						" where o.isShow=1 and o.showLocation=5 order by o.sortCode ");
		// 首页底部广告位
		List<HomeAdvertisement> homeBottomAdvertisementList = (List<HomeAdvertisement>) homeAdvertisementService
				.findListSpecifiedNumber(null, 0, 2,
						" where o.isShow=1 and o.showLocation=6 order by o.sortCode ");
		// 首页特别商品tab
		List<HomeParticularlyTab> homeParticularlyTabList1 = (List<HomeParticularlyTab>) homeParticularlyTabService
				.findListSpecifiedNumber(
						null,
						0,
						5,
						" where o.isShow=1 and o.showLocation=1 order by o.sortCode asc, o.tabProductId desc ");// 标签1
		List<HomeParticularlyTab> homeParticularlyTabList2 = (List<HomeParticularlyTab>) homeParticularlyTabService
				.findListSpecifiedNumber(
						null,
						0,
						5,
						" where o.isShow=1 and o.showLocation=2 order by o.sortCode asc, o.tabProductId desc");// 标签2
		List<HomeParticularlyTab> homeParticularlyTabList3 = (List<HomeParticularlyTab>) homeParticularlyTabService
				.findListSpecifiedNumber(
						null,
						0,
						5,
						" where o.isShow=1 and o.showLocation=3 order by o.sortCode asc, o.tabProductId desc");// 标签3
		List<HomeParticularlyTab> homeParticularlyTabList4 = (List<HomeParticularlyTab>) homeParticularlyTabService
				.findListSpecifiedNumber(
						null,
						0,
						5,
						" where o.isShow=1 and o.showLocation=4 order by o.sortCode asc, o.tabProductId desc");// 标签4
		List<HomeParticularlyTab> homeParticularlyTabList5 = (List<HomeParticularlyTab>) homeParticularlyTabService
				.findListSpecifiedNumber(
						null,
						0,
						5,
						" where o.isShow=1 and o.showLocation=5 order by o.sortCode asc, o.tabProductId desc");// 标签5
		// 天天快报
		List<CmsArticle> articleList = (List<CmsArticle>) articleService
				.findListSpecifiedNumber(null, 0, 9,
						" where o.isShow=1 and o.categoryId=112 order by o.articleId desc ");
		// 促销信息
		// List<Promotion> promotionList = (List<Promotion>)
		// promotionService.findListSpecifiedNumber(null, 0, 6,
		// " where o.isPass=1 order by o.promotionId desc ");
		// 查询首页底部文章信息
		List<BottomNewsModuleBean> bottomNewsModuleBeans = (List<BottomNewsModuleBean>) servletContext
				.getAttribute("bottomNewsModuleBeans");
		// 底部链接
		List<CmsArticle> articleLinkList = articleService
				.findObjects(" where 1=1 order by o.sortCode asc");
		// 品牌推荐 查询条件 1显示 2推荐
		List<Brand> brandList = brandService
				.findSome(0, 16,
						" where o.isShow = 1 and o.isRecommend = 1 order by o.sortCode");

		/************************** 数据查询 mqr ******************************************************/
		// listMap用于前台首页中间部分分类展示
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		// 查询首页中间部分分类信息：一级分类
		/* 从数据字典中读取分类个数 */
		Map<String, List<HomeKeyBook>> homeKeyBookMap = (Map<String, List<HomeKeyBook>>) servletContext
				.getAttribute("homekeybook");
		List<ShopHomeMiddleCategory> shopHomeMiddleCategoryList = (List<ShopHomeMiddleCategory>) shopHomeMiddleCategoryService
				.findListSpecifiedNumber(
						null,
						0,
						Integer.parseInt(homeKeyBookMap
								.get("middleCategoryNum").get(0).getValue()),
						" where o.parentId=0 and o.isShow=1 order by o.sortCode");
		if (shopHomeMiddleCategoryList != null
				&& shopHomeMiddleCategoryList.size() > 0) {
			for (ShopHomeMiddleCategory shmc : shopHomeMiddleCategoryList) {
				// 封装categoryMiddleMap用于前台首页中间部分分类展示
				Map<String, Object> categoryMiddleMap = new HashMap<String, Object>();
				categoryMiddleMap.put("yiJCategory", shmc);
				// 查询一级分类下的数据：二级小分类
				List<ShopHomeMiddleCategoryBilateral> shopHomeMiddleCategoryBilateralList = (List<ShopHomeMiddleCategoryBilateral>) shopHomeMiddleCategoryBilateralService
						.findListSpecifiedNumber(
								null,
								0,
								8,
								" where o.categoryId="
										+ shmc.getCategoryId()
										+ " and o.isShow=1 and o.type=1 order by o.sortCode");
				categoryMiddleMap.put("secCategory",
						shopHomeMiddleCategoryBilateralList);
				// 查询一级分类下的数据：促销商品
				List<ShopHomeMiddleCategoryBilateral> shopHomeMiddleCategoryBilateralListProduct = (List<ShopHomeMiddleCategoryBilateral>) shopHomeMiddleCategoryBilateralService
						.findListSpecifiedNumber(
								null,
								0,
								8,
								" where o.categoryId="
										+ shmc.getCategoryId()
										+ " and o.isShow=1 and o.type=2 order by o.sortCode");
				if (shopHomeMiddleCategoryBilateralListProduct != null
						&& shopHomeMiddleCategoryBilateralListProduct.size() > 0) {
					categoryMiddleMap.put("promotionProductList",
							shopHomeMiddleCategoryBilateralListProduct);
				}
				// 查询热销排行数据
				List<ShopHomeMiddleCategoryBilateral> shopHomeMiddleCategoryBilateralListSort = (List<ShopHomeMiddleCategoryBilateral>) shopHomeMiddleCategoryBilateralService
						.findListSpecifiedNumber(
								null,
								0,
								7,
								" where o.categoryId="
										+ shmc.getCategoryId()
										+ " and o.isShow=1 and o.type=3 order by o.sortCode");
				categoryMiddleMap.put("promotionProductSort",
						shopHomeMiddleCategoryBilateralListSort);
				// 根据一级分类id查询二级分类 用于tab展示
				List<ShopHomeMiddleCategory> shopHomeMiddleCategoryList2 = (List<ShopHomeMiddleCategory>) shopHomeMiddleCategoryService
						.findListSpecifiedNumber(null, 0, 5,
								" where o.parentId=" + shmc.getCategoryId()
										+ " and o.isShow=1 order by o.sortCode");
				List<Map<String, Object>> listMapTab = new ArrayList<Map<String, Object>>();
				if (shopHomeMiddleCategoryList2 != null
						&& shopHomeMiddleCategoryList2.size() > 0) {
					for (ShopHomeMiddleCategory sc : shopHomeMiddleCategoryList2) {
						Map<String, Object> middleMap = new HashMap<String, Object>();
						// 根据二级分类的id 查询商品或促销 以及小轮播图信息
						// 商品或促销 imageType=1
						List<ShopHomeMiddleCategoryTAB> tabProductList = (List<ShopHomeMiddleCategoryTAB>) shopHomeMiddleCategoryTABService
								.findListSpecifiedNumber(
										null,
										0,
										10,
										" where o.categoryId="
												+ sc.getCategoryId()
												+ " and o.isShow=1 and o.imageType=1 order by o.sortCode asc ,categoryTabId desc");
						middleMap.put("tabProductList", tabProductList);
						// 小轮播图 imageType=2
						/*
						 * List<ShopHomeMiddleCategoryTAB> lbtList =
						 * (List<ShopHomeMiddleCategoryTAB>)
						 * shopHomeMiddleCategoryTABService
						 * .findListSpecifiedNumber(null, 0, 3,
						 * " where o.categoryId="
						 * +sc.getCategoryId()+" and o.isShow=1 and o.imageType=2"
						 * ); middleMap.put("lbtList", lbtList);
						 */
						middleMap.put("tabName", sc.getCategoryName());
						 String link = sc.getLink().replaceAll("101.200.182.119", "192.168.1.118");
						middleMap.put("link", link);
						listMapTab.add(middleMap);
					}
					;
					categoryMiddleMap.put("listMapTab", listMapTab);
				}
				listMap.add(categoryMiddleMap);// 添加到最外层
			}
		}
		// 查询首页全部分类信息
		List<AllProductTypeBean> productTypeBeanList = (List<AllProductTypeBean>) servletContext
				.getAttribute("productTypeBeanList");

		/************************** 参数存储 whb ******************************************************/
		request.setAttribute("homeLBTList", homeLBTList);// 大轮播图
		request.setAttribute("homeLittleAdvertisementList",
				homeLittleAdvertisementList);// 首页小轮播图广告位
		request.setAttribute("homeAdvertisementList", homeAdvertisementList);// 广告位
		request.setAttribute("homeNewsAdvertisementList",
				homeNewsAdvertisementList);// 首页新闻上方广告位
		request.setAttribute("homeBottomAdvertisementList",
				homeBottomAdvertisementList);// 首页底部广告位
		request.setAttribute("homeParticularlyTabList1",
				homeParticularlyTabList1);// 特别商品标签1
		request.setAttribute("homeParticularlyTabList2",
				homeParticularlyTabList2);// 特别商品标签2
		request.setAttribute("homeParticularlyTabList3",
				homeParticularlyTabList3);// 特别商品标签3
		request.setAttribute("homeParticularlyTabList4",
				homeParticularlyTabList4);// 特别商品标签4
		request.setAttribute("homeParticularlyTabList5",
				homeParticularlyTabList5);// 特别商品标签5
		request.setAttribute("articleList", articleList);// 天天快报
		// request.setAttribute("promotionList", promotionList);//促销信息
		request.setAttribute("bottomNewsModuleBeans", bottomNewsModuleBeans);// 首页底部新闻信息
		request.setAttribute("bottomNewsLink", articleLinkList);// 首页底部新闻链接信息
		request.setAttribute("brandList", brandList);// 首页品牌推荐
		/************************** 参数存储 mqr ******************************************************/
		request.setAttribute("listMap", listMap);// 首页中间分类部分
		request.setAttribute("productTypeBeanList", productTypeBeanList);// 首页全部分类
		// 查询购物车相关信息
		Customer customer = (Customer) session.getAttribute("customer");
		Cookie[] cookie = request.getCookies();
		Map cartCountMap = new HashMap<Object, Object>();
		Map cartMap = shoppingCartService.gotoShoppingCart(customer, cookie,
				cartCountMap);
		session.setAttribute("cartMap", cartMap);
		session.setAttribute("cartCountMap",
				String.valueOf(cartCountMap.get("count")));
		return SUCCESS;
	}

	/**
	 * 易采快报的更多链接
	 */
	public String moreNews() {
		// 查询30易采快报数据
		List<CmsArticle> articleList = null;
		if (txt == null || txt == "" || txt == "null") {
			int totalRecordCount = articleService
					.getCount("where o.isShow=1 and o.categoryId=112 order by o.articleId desc ");
			pageHelper.setPageInfo(30, totalRecordCount, currentPage);
			articleList = (List<CmsArticle>) articleService
					.findListByPageHelper(null, pageHelper,
							" where o.isShow=1 and o.categoryId=112 order by o.articleId desc ");
		} else {
			int totalRecordCount = articleService
					.getCount("where o.isShow=1 and o.categoryId=112 and o.title like '%"
							+ txt + "%' order by o.articleId desc ");
			pageHelper.setPageInfo(30, totalRecordCount, currentPage);
			articleList = (List<CmsArticle>) articleService
					.findListByPageHelper(null, pageHelper,
							" where o.isShow=1 and o.categoryId=112 and o.title like '%"
									+ txt + "%' order by o.articleId desc ");
		}
		request.setAttribute("txt", txt);
		request.setAttribute("moreNews", articleList);
		return SUCCESS;
	}

	public String companyIntroduce() {
		return SUCCESS;
	}

	public void getParams() throws IOException {
		Customer customer = (Customer) this.session.getAttribute("customer");
		PrintWriter out = response.getWriter();
		String name = "";
		if (customer != null) {
			name = customer.getLoginName();
		}
		out.print(String.format("{\"username\":\"%s\"}", name));
		out.flush();
		out.close();
	}

	public void setHomeLBTService(IHomeLBTService homeLBTService) {
		this.homeLBTService = homeLBTService;
	}

	public void setHomeAdvertisementService(
			IHomeAdvertisementService homeAdvertisementService) {
		this.homeAdvertisementService = homeAdvertisementService;
	}

	public void setHomeParticularlyTabService(
			IHomeParticularlyTabService homeParticularlyTabService) {
		this.homeParticularlyTabService = homeParticularlyTabService;
	}

	public void setArticleService(IArticleService articleService) {
		this.articleService = articleService;
	}

	public void setShopHomeMiddleCategoryService(
			IShopHomeMiddleCategoryService shopHomeMiddleCategoryService) {
		this.shopHomeMiddleCategoryService = shopHomeMiddleCategoryService;
	}

	public void setShopHomeMiddleCategoryBilateralService(
			IShopHomeMiddleCategoryBilateralService shopHomeMiddleCategoryBilateralService) {
		this.shopHomeMiddleCategoryBilateralService = shopHomeMiddleCategoryBilateralService;
	}

	public void setShopHomeMiddleCategoryTABService(
			IShopHomeMiddleCategoryTABService shopHomeMiddleCategoryTABService) {
		this.shopHomeMiddleCategoryTABService = shopHomeMiddleCategoryTABService;
	}

	public void setPromotionService(IPlatformPromotionService promotionService) {
		this.promotionService = promotionService;
	}

	public void setShoppingCartService(IShoppingCartService shoppingCartService) {
		this.shoppingCartService = shoppingCartService;
	}

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}

	public void setBrandService(IBrandService brandService) {
		this.brandService = brandService;
	}

	public String getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(String productTypeId) {
		this.productTypeId = productTypeId;
	}

	public String getCategoryParams() {
		return categoryParams;
	}

	public void setCategoryParams(String categoryParams) {
		this.categoryParams = categoryParams;
	}

	public void setBrandTypeService(IBrandTypeService brandTypeService) {
		this.brandTypeService = brandTypeService;
	}

	public void setProductTypeService(IProductTypeService productTypeService) {
		this.productTypeService = productTypeService;
	}

	public void setProductInfoService(IProductInfoService productInfoService) {
		this.productInfoService = productInfoService;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

}
