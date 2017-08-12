package phone.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;

import org.springframework.stereotype.Controller;

import promotion.service.IPlatformPromotionService;
import shop.customer.pojo.Customer;
import shop.front.bean.AllProductTypeBean;
import shop.front.shoppingOnLine.service.IShoppingCartService;
import shop.homeIndex.pojo.HomeKeyBook;
import shop.homeIndex.pojo.HomeLBT;
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
import shop.product.service.IBrandService;
import shop.product.service.IBrandTypeService;
import shop.product.service.IProductInfoService;
import shop.product.service.IProductTypeService;
import util.action.BaseAction;
import cms.article.service.IArticleService;

@Controller
@SuppressWarnings("serial")
public class PhoneHomeAction extends BaseAction {
	@Resource
	private IHomeLBTService homeLBTService;// 首页轮播图
	@Resource
	private IHomeAdvertisementService homeAdvertisementService;// 首页广告位
	@Resource
	private IHomeParticularlyTabService homeParticularlyTabService;// 首页特别商品
	@Resource
	private IArticleService articleService;// 天天快报
	@Resource
	private IPlatformPromotionService promotionService;// 促销信息
	@Resource
	private IShopHomeMiddleCategoryService shopHomeMiddleCategoryService;// 中间部分以及分类
	@Resource
	private IShopHomeMiddleCategoryBilateralService shopHomeMiddleCategoryBilateralService;// 一级分类下的数据：促销商品
	@Resource
	private IShopHomeMiddleCategoryTABService shopHomeMiddleCategoryTABService;// 小轮播图
	@Resource
	private IShoppingCartService shoppingCartService;// 购物车
	@Resource
	private IProductInfoService productInfoService;
	/** 品牌service **/
	@Resource
	private IBrandService brandService;
	/** 分类品牌service **/
	@Resource
	private IBrandTypeService brandTypeService;
	private String txt;// 易采快报搜索条件
	/** 商品分类ID **/
	private String productTypeId;
	/** 商品分类Service **/
	@Resource
	private IProductTypeService productTypeService;
	/** 主题馆参数 **/
	private String categoryParams;
	/** 登录是否登录标识 */
	private String customerId;

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
							// String url = start + "phoneProductInfo" + end;
							String url = "http://192.168.10.7:8080/wfj_front/"
									+ "phoneProductInfo" + end;
							shopHomeMiddleCategoryTAB.setLink(url);
						}
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
		session.setAttribute("brandList", brandList);// 首页品牌推荐
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

}
