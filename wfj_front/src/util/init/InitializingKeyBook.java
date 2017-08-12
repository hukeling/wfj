package util.init;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware;

import shop.front.bean.BottomNewsModuleBean;
import shop.homeIndex.pojo.HomeAdvertisement;
import shop.homeIndex.service.IHomeAdvertisementService;
import shop.product.pojo.Brand;
import shop.product.pojo.BrandType;
import shop.product.pojo.ProductType;
import shop.product.service.IBrandService;
import shop.product.service.IBrandTypeService;
import shop.product.service.IProductTypeService;
import util.other.FreeMarker;
import basic.pojo.KeyBook;
import basic.service.IKeyBookService;
import cms.article.pojo.CmsArticle;
import cms.article.service.IArticleService;
import cms.category.pojo.CmsCategory;
import cms.category.service.ICategoryService;
/**
 * 项目初始化加载数据字典
 */
@SuppressWarnings("unused")
public class InitializingKeyBook implements InitializingBean , ServletContextAware {
	//servlet 上下文
	private ServletContext servletContext;
	//keyBookService
	private IKeyBookService keyBookService;
	/** 底部文章集合 **/
	private IArticleService articleService;//引用文章Service
	private ICategoryService categoryService;//引用文章分类Service
	private IProductTypeService productTypeService;//商品分类Service
	private IBrandTypeService brandTypeService;//品牌和分类Service
	private IBrandService brandService;//品牌Service
	private Map categoryMap = new LinkedHashMap();//商品分类
	private Map categoryBrandMap = new LinkedHashMap();//商品分类下的品牌
	private List<ProductType> productTypeList = new ArrayList<ProductType>();//商品分类List
	/**
	 * 首页广告位Service
	 */
	private IHomeAdvertisementService homeAdvertisementService;
	
	/**
	 * 项目初始化加载数据字典
	 */
	@SuppressWarnings("unchecked")
	public void afterPropertiesSet() throws Exception {
		if (servletContext != null) {
			Map<String,List<KeyBook>> keybook = new HashMap<String,List<KeyBook>>();
			List<String> typeNameList = keyBookService.distinctType("type", "");//查找类型名称
			if(typeNameList!=null&&typeNameList.size()>0){
				for(String typeName : typeNameList){
					List<KeyBook> keyBookList = keyBookService.findObjects(" where o.type = '"+typeName+"' order by o.value asc");//根据类型名称查出对象集合
					keybook.put(typeName, keyBookList);
				}
			}
			servletContext.setAttribute("keybook", keybook);
			if (servletContext != null) {
				List<ProductType> categoryList = new ArrayList<ProductType>();
				//一级分类集合
				productTypeList = productTypeService.findObjects(" where o.isShow=1 and o.parentId=1 order by o.sortCode");
				if(productTypeList!=null&&productTypeList.size()>0){
					for(ProductType pt : productTypeList){
						Map seondMap  = new LinkedHashMap();
						//二级分类集合
						List<ProductType> secondProductTypeList = productTypeService.findObjects(" where o.parentId='"+pt.getProductTypeId()+"' and o.isShow=1 order by o.sortCode");
						pt.setChildProductType(secondProductTypeList);
						if(secondProductTypeList!=null&&secondProductTypeList.size()>0){
							for(ProductType spt : secondProductTypeList){
								List<Brand> brandList = new ArrayList<Brand>();
								Map thirdMap  = new LinkedHashMap();
								//三级分类集合
								List<ProductType> thirdProductTypeList = productTypeService.findObjects(" where o.parentId='"+spt.getProductTypeId()+"' and o.isShow=1");
								spt.setChildProductType(thirdProductTypeList);
								if(thirdProductTypeList!=null&&thirdProductTypeList.size()>0){
									for(ProductType tpt : thirdProductTypeList){
										//四级分类集合
										List<ProductType> fourProductTypeList = productTypeService.findObjects(" where o.parentId='"+tpt.getProductTypeId()+"' and o.isShow=1");
										tpt.setChildProductType(fourProductTypeList);
										thirdMap.put(tpt, fourProductTypeList);
									}
								}
								seondMap.put(spt, thirdMap);
								List<BrandType> brandTypeList = brandTypeService.findSome(0, 5, " where o.productTypeId='"+spt.getProductTypeId()+"'");
								if(brandTypeList!=null && brandTypeList.size()>0){
									for(BrandType bt : brandTypeList){
										Brand brand = (Brand) brandService.getObjectByParams(" where o.brandId='"+bt.getBrandId()+"' and o.isShow=1");
										if(brand!=null&&brand.getBrandId()!=null){
											brandList.add(brand);
										}
									}
								}
								categoryBrandMap.put(spt, brandList);
							}
							
						}
						
						categoryMap.put(pt, seondMap);
					}
					
				}
				//首页新闻上方广告位
				List<HomeAdvertisement> homeNewsAdvertisementList = (List<HomeAdvertisement>) homeAdvertisementService.findListSpecifiedNumber(null, 0, 4, " where o.isShow=1 and o.showLocation=5 order by o.sortCode ");
				servletContext.setAttribute("homeNewsAdvertisementList", homeNewsAdvertisementList);
				//首页底部广告位
				List<HomeAdvertisement> homeBottomAdvertisementList = (List<HomeAdvertisement>) homeAdvertisementService.findListSpecifiedNumber(null, 0, 2, " where o.isShow=1 and o.showLocation=6 order by o.sortCode ");
				servletContext.setAttribute("homeBottomAdvertisementList", homeBottomAdvertisementList);
				servletContext.setAttribute("categoryMap", categoryMap);
				servletContext.setAttribute("categoryList", productTypeList);
				servletContext.setAttribute("categoryBrandMap", categoryBrandMap);
			//底部新闻信息的加载
			initNews();
			FreeMarker.init(servletContext);
		}
	}
	}
	/**
	 * 项目底部新闻信息初始化加载
	 * */
	@SuppressWarnings("unchecked")
	private void initNews() {
		//底部信息分类
		List<CmsCategory> cmsCategorys = categoryService.findObjects(null, " where o.parentId=92 order by o.categoryId asc");
		List<BottomNewsModuleBean> bottomNewsModuleBeans = new ArrayList<BottomNewsModuleBean>();
		if(cmsCategorys!=null&&cmsCategorys.size()>0){
			for(CmsCategory cc : cmsCategorys){
				BottomNewsModuleBean bnmb = new BottomNewsModuleBean();
				bnmb.setNewsTypeName(cc.getCategoryName());
				String hql = "select ca.articleId as articleId,ca.categoryId as categoryId,ca.title as title,ca.outUrl as outUrl from CmsArticle ca "+
						" where ca.isShow=1 and ca.categoryId="+cc.getCategoryId()+" order by ca.sortCode asc";
				List<Map> articleList = articleService.findListMapByHql(hql);
				bnmb.setNewsContentList(articleList);
				bottomNewsModuleBeans.add(bnmb);
			}
			
		}
		servletContext.setAttribute("bottomNewsModuleBeans", bottomNewsModuleBeans);
		//底部信息上（分类id为：111）
		String footerUp = "select ca.articleId as articleId,ca.title as title from CmsArticle ca "+
				" where ca.categoryId=111 order by ca.sortCode asc";
		List<Map> footerUpArticleList = articleService.findListMapByHql(footerUp);
		servletContext.setAttribute("footerUpArticleList",footerUpArticleList);
		//底部信息下（分类id为：112）
		String footerDown = "select ca.articleId as articleId,ca.title as title from CmsArticle ca "+
				" where ca.categoryId=112 order by ca.sortCode asc";
		List<Map> footerDownArticleList = articleService.findListMapByHql(footerDown);
		servletContext.setAttribute("footerDownArticleList",footerDownArticleList);
		//头部信息（分类id为：110）
		String header = "select ca.articleId as articleId,ca.title as title,ca.imgUrl as imgUrl from CmsArticle ca "+
				" where ca.categoryId=110 order by ca.sortCode asc";
		List<Map> headerArticleList = articleService.findListMapByHql(header);
		servletContext.setAttribute("headerArticleList",headerArticleList);
		//底部链接
		List<CmsArticle> articleList = articleService.findObjects(" where o.categoryId=99 order by o.sortCode asc");
		servletContext.setAttribute("bottomNewsLink", articleList);
	}
	
	public IKeyBookService getKeyBookService() {
		return keyBookService;
	}
	public void setKeyBookService(IKeyBookService keyBookService) {
		this.keyBookService = keyBookService;
	}
	public ServletContext getServletContext() {
		return servletContext;
	}
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	public void setArticleService(IArticleService articleService) {
		this.articleService = articleService;
	}
	public void setCategoryService(ICategoryService categoryService) {
		this.categoryService = categoryService;
	}
	@SuppressWarnings("unchecked")
	public Map getCategoryMap() {
		return categoryMap;
	}
	@SuppressWarnings("unchecked")
	public void setCategoryMap(Map categoryMap) {
		this.categoryMap = categoryMap;
	}
	@SuppressWarnings("unchecked")
	public Map getCategoryBrandMap() {
		return categoryBrandMap;
	}
	@SuppressWarnings("unchecked")
	public void setCategoryBrandMap(Map categoryBrandMap) {
		this.categoryBrandMap = categoryBrandMap;
	}
	public List<ProductType> getProductTypeList() {
		return productTypeList;
	}
	public void setProductTypeList(List<ProductType> productTypeList) {
		this.productTypeList = productTypeList;
	}
	public void setProductTypeService(IProductTypeService productTypeService) {
		this.productTypeService = productTypeService;
	}
	public void setBrandTypeService(IBrandTypeService brandTypeService) {
		this.brandTypeService = brandTypeService;
	}
	public void setBrandService(IBrandService brandService) {
		this.brandService = brandService;
	}
	public void setHomeAdvertisementService(
			IHomeAdvertisementService homeAdvertisementService) {
		this.homeAdvertisementService = homeAdvertisementService;
	}
}