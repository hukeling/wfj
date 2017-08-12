package shop.prosceniumInstall.action;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import promotion.service.IPlatformPromotionService;
import shop.front.bean.BottomNewsModuleBean;
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
import shop.product.pojo.ProductType;
import util.action.BaseAction;
import cms.article.pojo.CmsArticle;
import cms.article.service.IArticleService;
/**
 * BuildFreeMarkAction - 更新首页静态页面类
 */
@SuppressWarnings("serial")
public class BuildFreeMarkAction extends BaseAction{
	/**
	 * 模版的文件名称
	 */
	public static final String INDEX_PATH_PATH = "/index.ftl";
	/**
	 * 生成的静态页的文件名称
	 */
	public static final String OUT_INDEX_PATH = "/index.html";
	/**
	 * log加载日志文件
	 */
	private static final Log log = LogFactory.getLog(BuildFreeMarkAction.class);
	private IShopHomeMiddleCategoryService shopHomeMiddleCategoryService;//中间部分分类Service
	private IShopHomeMiddleCategoryBilateralService shopHomeMiddleCategoryBilateralService;//中间部分两侧维护Service
	private IShopHomeMiddleCategoryTABService shopHomeMiddleCategoryTABService;//中间部分tab维护Service
	private IPlatformPromotionService promotionService;//促销信息
	private IHomeLBTService homeLBTService;//首页大轮播图Service
	private IHomeAdvertisementService homeAdvertisementService;//首页广告位Service
	private IHomeParticularlyTabService homeParticularlyTabService;//首页特别商品（Tab部分）Service
	private IArticleService articleService;//天天快报
	/**
	 * 跳转到生成静态页的页面
	 */
	public String gotoBuildTaskPage(){
		return SUCCESS;
	}
	/**
	 * 构建静态页，手动初始化
	 */
	@SuppressWarnings("unchecked")
	public void build(){
		//得到FreekMarker的配置信息
		ServletContext context = util.other.FreeMarker.servletContext();
		Map<String,Object> params = new HashMap<String, Object>();
		//调用公共部分方法
		publicSector(params,context);
		//获取生成静态页面文件的全路径
		StringBuffer out_path =new StringBuffer(context.getRealPath("/")).append(OUT_INDEX_PATH);
		FileOutputStream fileOutputStream=null;
		OutputStreamWriter out  =null;
		try {
			//实例化文件输出流
			fileOutputStream = new FileOutputStream(out_path.toString());
			//实例化字节流，把字符流转换成字节流	
			out = new OutputStreamWriter(fileOutputStream,"UTF-8");
			//调用生成静态页的方法，succss表示生成成功，fail表示生成失败
			Boolean isSuccess = util.other.FreeMarker.process(INDEX_PATH_PATH, params, out);
			//System.out.println("构建静态页，手动初始化是否成功 : " + isSuccess);
			JSONObject jo = new JSONObject();
			jo.accumulate("isSuccess", isSuccess + "");
			response.setContentType("text/html;charset=utf-8");
			PrintWriter ot = response.getWriter();
			ot.println(jo.toString());
			ot.flush();
			ot.close();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if (out != null) {
				try {
					//刷新输出流
					out.flush();
					//关闭输出流
					out.close();
				} catch (IOException e) {
				}
				if (fileOutputStream != null) {
					try {
						//刷新输出流
						fileOutputStream.flush();
						//关闭输出流
						fileOutputStream.close();
					} catch (IOException e) {
					}
				}
			}
		}
		log.info("----- start buiding index page end -----");
	}
	/**
	 * 初始化构建静态化页面
	 */
	public void initFreemarkbuild(){
		//得到FreekMarker的配置信息
		ServletContext context = util.other.FreeMarker.servletContext();
		Map<String,Object> params = new HashMap<String, Object>();
		//调用公共部分方法
		publicSector(params,context);
		//获取生成静态页面文件的全路径
		StringBuffer out_path =new StringBuffer(context.getRealPath("/")).append(OUT_INDEX_PATH);
		FileOutputStream fileOutputStream=null;
		OutputStreamWriter out  =null;
		try {
			//实例化文件输出流
			fileOutputStream = new FileOutputStream(out_path.toString());
			//实例化字节流，把字符流转换成字节流	
			out = new OutputStreamWriter(fileOutputStream,"UTF-8");
			//调用生成静态页的方法，succss表示生成成功，fail表示生成失败
			Boolean isSuccess = util.other.FreeMarker.process(INDEX_PATH_PATH, params, out);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if (fileOutputStream != null) {
				try {
					//刷新输出流
					fileOutputStream.flush();
					//关闭输出流
					fileOutputStream.close();
				} catch (IOException e) {
				}
			}
			log.info("----- start buiding index page end -----");
		}
	}
	/**
	 * 手动构建与初始化的公共部分
	 */
	@SuppressWarnings("unchecked")
	public void publicSector(Map<String,Object> params,ServletContext context){
		log.info("----- start buiding index page start -----");
		Map fileUrlConfig = BaseAction.getFileUrlConfig();
		params.put("contextPath", context.getContextPath());
		params.put("fileUrlConfig", fileUrlConfig);
		params.put("sessionTest", session);
		/**************************数据查询   whb******************************************************/
		//首页大轮播图List
		List<HomeLBT> homeLBTList = (List<HomeLBT>) homeLBTService.findListSpecifiedNumber(null, 0, 5, "  where o.isShow=1 order by o.sortCode asc ");
		//首页广告位
		List<HomeAdvertisement> homeAdvertisementList = (List<HomeAdvertisement>) homeAdvertisementService.findListSpecifiedNumber(null, 0, 6, " where o.isShow=1 order by o.showLocation ");
		//首页特别商品tab
		List<HomeParticularlyTab> homeParticularlyTabList1 = (List<HomeParticularlyTab>) homeParticularlyTabService.findListSpecifiedNumber(null, 0, 5, " where o.isShow=1 and o.showLocation=1 order by o.sortCode asc, o.tabProductId desc");//标签1
		List<HomeParticularlyTab> homeParticularlyTabList2 = (List<HomeParticularlyTab>) homeParticularlyTabService.findListSpecifiedNumber(null, 0, 5, " where o.isShow=1 and o.showLocation=2 order by o.sortCode asc, o.tabProductId desc");//标签2
		List<HomeParticularlyTab> homeParticularlyTabList3 = (List<HomeParticularlyTab>) homeParticularlyTabService.findListSpecifiedNumber(null, 0, 5, " where o.isShow=1 and o.showLocation=3 order by o.sortCode asc, o.tabProductId desc");//标签3
		List<HomeParticularlyTab> homeParticularlyTabList4 = (List<HomeParticularlyTab>) homeParticularlyTabService.findListSpecifiedNumber(null, 0, 5, " where o.isShow=1 and o.showLocation=4 order by o.sortCode asc, o.tabProductId desc");//标签4
		List<HomeParticularlyTab> homeParticularlyTabList5 = (List<HomeParticularlyTab>) homeParticularlyTabService.findListSpecifiedNumber(null, 0, 5, " where o.isShow=1 and o.showLocation=5 order by o.sortCode asc, o.tabProductId desc");//标签5
		//天天快报
		List<CmsArticle> articleList = (List<CmsArticle>) articleService.findListSpecifiedNumber(null, 0, 8, " where o.isEssence=1 and o.isShow=1 order by o.articleId desc ");
		//促销信息
		//List<Promotion> promotionList = (List<Promotion>) promotionService.findListSpecifiedNumber(null, 0, 6, " where o.isPass=1 order by o.promotionId desc ");
		//查询首页底部文章信息
		List<BottomNewsModuleBean> bottomNewsModuleBeans=(List<BottomNewsModuleBean>) servletContext.getAttribute("bottomNewsModuleBeans");
		/**************************数据查询   mqr******************************************************/
		//listMap用于前台首页中间部分分类展示
		List<Map<String,Object>> listMap=new ArrayList<Map<String,Object>>();
		//查询首页中间部分分类信息：一级分类
		/*从数据字典中读取分类个数*/
		Map<String,List<HomeKeyBook>> homeKeyBookMap = (Map<String, List<HomeKeyBook>>) servletContext.getAttribute("homekeybook");
		List<ShopHomeMiddleCategory> shopHomeMiddleCategoryList = (List<ShopHomeMiddleCategory>) shopHomeMiddleCategoryService.findListSpecifiedNumber(null, 0, Integer.parseInt(homeKeyBookMap.get("middleCategoryNum").get(0).getValue()), " where o.parentId=0 and o.isShow=1 order by o.sortCode");
		if(shopHomeMiddleCategoryList!=null&&shopHomeMiddleCategoryList.size()>0){
			for(ShopHomeMiddleCategory shmc:shopHomeMiddleCategoryList){
				//封装categoryMiddleMap用于前台首页中间部分分类展示
				Map<String,Object> categoryMiddleMap=new HashMap<String, Object>();
				categoryMiddleMap.put("yiJCategory", shmc);
				//查询一级分类下的数据：二级小分类
				List<ShopHomeMiddleCategoryBilateral> shopHomeMiddleCategoryBilateralList = (List<ShopHomeMiddleCategoryBilateral>) shopHomeMiddleCategoryBilateralService.findListSpecifiedNumber(null, 0, 8, " where o.categoryId="+shmc.getCategoryId()+" and o.isShow=1 and o.type=1 order by o.sortCode");
				categoryMiddleMap.put("secCategory", shopHomeMiddleCategoryBilateralList);
				//查询一级分类下的数据：促销商品
				List<ShopHomeMiddleCategoryBilateral> shopHomeMiddleCategoryBilateralListProduct = (List<ShopHomeMiddleCategoryBilateral>) shopHomeMiddleCategoryBilateralService.findListSpecifiedNumber(null, 0, 1, " where o.categoryId="+shmc.getCategoryId()+" and o.isShow=1 and o.type=2 order by o.sortCode");
				if(shopHomeMiddleCategoryBilateralListProduct!=null&&shopHomeMiddleCategoryBilateralListProduct.size()>0){
					categoryMiddleMap.put("promotionProduct", shopHomeMiddleCategoryBilateralListProduct.get(0));
				}
				//查询热销排行数据
				List<ShopHomeMiddleCategoryBilateral> shopHomeMiddleCategoryBilateralListSort = (List<ShopHomeMiddleCategoryBilateral>) shopHomeMiddleCategoryBilateralService.findListSpecifiedNumber(null, 0, 6, " where o.categoryId="+shmc.getCategoryId()+" and o.isShow=1 and o.type=3 order by o.sortCode");
				categoryMiddleMap.put("promotionProductSort", shopHomeMiddleCategoryBilateralListSort);
				//根据一级分类id查询二级分类 用于tab展示
				List<ShopHomeMiddleCategory> shopHomeMiddleCategoryList2=(List<ShopHomeMiddleCategory>) shopHomeMiddleCategoryService.findListSpecifiedNumber(null, 0, 5, " where o.parentId="+shmc.getCategoryId()+" and o.isShow=1 order by o.sortCode");
				List<Map<String,Object>> listMapTab=new ArrayList<Map<String,Object>>();
				if(shopHomeMiddleCategoryList2!=null&&shopHomeMiddleCategoryList2.size()>0){
					for(ShopHomeMiddleCategory sc:shopHomeMiddleCategoryList2){
						Map<String,Object> middleMap=new HashMap<String, Object>();
						//根据二级分类的id 查询商品或促销 以及小轮播图信息
						//商品或促销 imageType=1
						List<ShopHomeMiddleCategoryTAB> tabProductList = (List<ShopHomeMiddleCategoryTAB>) shopHomeMiddleCategoryTABService.findListSpecifiedNumber(null, 0, 7, " where o.categoryId="+sc.getCategoryId()+" and o.isShow=1 and o.imageType=1");
						middleMap.put("tabProductList", tabProductList);
						//小轮播图 imageType=2
						List<ShopHomeMiddleCategoryTAB> lbtList = (List<ShopHomeMiddleCategoryTAB>) shopHomeMiddleCategoryTABService.findListSpecifiedNumber(null, 0, 3, " where o.categoryId="+sc.getCategoryId()+" and o.isShow=1 and o.imageType=2");
						middleMap.put("lbtList", lbtList);
						middleMap.put("tabName", sc.getCategoryName());
						middleMap.put("link", sc.getLink());
						listMapTab.add(middleMap);
					};
					categoryMiddleMap.put("listMapTab", listMapTab);
				}
				listMap.add(categoryMiddleMap);//添加到最外层
			}
		}
		//查询首页全部分类信息
		 Map categoryMap = (Map) servletContext.getAttribute("categoryMap");
		 List<ProductType> categoryList = new ArrayList<ProductType>();
		 categoryList = (List<ProductType>) context.getAttribute("categoryList");
		/**************************参数存储   whb******************************************************/
		params.put("homeLBTList", homeLBTList);//大轮播图
		params.put("homeAdvertisementList", homeAdvertisementList);//广告位
		params.put("homeParticularlyTabList1", homeParticularlyTabList1);//特别商品标签1
		params.put("homeParticularlyTabList2", homeParticularlyTabList2);//特别商品标签2
		params.put("homeParticularlyTabList3", homeParticularlyTabList3);//特别商品标签3
		params.put("homeParticularlyTabList4", homeParticularlyTabList4);//特别商品标签4
		params.put("homeParticularlyTabList5", homeParticularlyTabList5);//特别商品标签5
		params.put("articleList", articleList);//天天快报
		//params.put("promotionList", promotionList);//促销信息
		params.put("bottomNewsModuleBeans", bottomNewsModuleBeans);//首页底部新闻信息
		/**************************参数存储   mqr******************************************************/	
		params.put("listMap", listMap);//首页中间分类部分
		params.put("categoryMap", categoryMap);//首页全部分类
		params.put("categoryList", categoryList);
		params.put("homekeybook",context.getAttribute("homekeybook") );
	}
	//setter getter
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
	public void setPromotionService(IPlatformPromotionService promotionService) {
		this.promotionService = promotionService;
	}
}
