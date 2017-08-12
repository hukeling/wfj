package shop.front.help.action;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import shop.front.bean.BottomNewsModuleBean;
import util.action.BaseAction;
import util.other.Escape;
import cms.article.pojo.CmsArticle;
import cms.article.service.IArticleService;
import cms.category.pojo.CmsCategory;
import cms.category.service.ICategoryService;
/**
 * CouponAction - 帮助天地文章Action
 * @author XHT
 */
@SuppressWarnings("serial")
public class HelpFrontAction extends BaseAction{
	/** 文章Service **/
	private IArticleService articleService;
	/** 帮助天地文章集合(左边展示) **/
	private List<CmsArticle> articleList = new ArrayList<CmsArticle>();
	/** 文章主键ID **/
	private String articleId;
	/** 帮助天地文章展示 **/
	private CmsArticle cmsArticle;
	/** 文章模糊查询 **/
	private String str;
	/** 模糊查询文章结果 **/
	@SuppressWarnings("rawtypes")
	private List<Map> cmsArticleList = new ArrayList<Map>();
	/** 文章分类对象 **/
	private CmsCategory cmsCategory;
	/** 文章分类Service **/
	private ICategoryService categoryService;
	/** 文章分类ID **/
	private String categoryId;
	/**
	 * 跳转交流天地页面
	 */
	public String gotoHelpFront(){
		//查询出最新的一篇文章
		pageHelper.setPageInfo(1, 1, 1);
		articleList = articleService.findListByPageHelper(new String[]{"articleId","categoryId"}, pageHelper, " where o.isShow = 1 and o.categoryId = 98 order by o.articleId desc ");
		if(articleList!=null&&articleList.size()>0){
			cmsArticle=articleList.get(0);
		}
		return SUCCESS;
	}
	/**
	 * 跳转交流天地文章展示页面
	 */
	public String gotoHelpFrontList(){
		cmsArticle = (CmsArticle) articleService.getObjectById(articleId);
		cmsCategory = (CmsCategory) categoryService.getObjectByParams("where o.categoryId = "+cmsArticle.getCategoryId());
		pageHelper.setPageInfo(20, 20, 1);//现不做分页 只是用于查询20条数据 后期需要修改
		articleList = articleService.findListByPageHelper(null, pageHelper, " where o.isShow = 1 and o.categoryId = "+cmsArticle.getCategoryId()+" order by o.articleId desc");
		return SUCCESS;
	}
	/**
	 * 根据标题模糊查询文章并展示到页面
	 */
	public String gotoSomeHelpFrontList(){
		if(StringUtils.isNotEmpty(categoryId)){
			cmsCategory = (CmsCategory) categoryService.getObjectByParams("where o.categoryId = "+categoryId);
			articleList = articleService.findObjects(" where o.isShow = 1 and o.categoryId = "+categoryId);
		}
		//对搜索内容进行解码
		String replaceAll = str.replaceAll("_", "%");
		str = Escape.unescape((replaceAll.trim()));
		String hql = "SELECT o.title as title,o.updateTime as updateTime, o.brief as brief,o.articleId as articleId FROM CmsArticle o WHERE o.isShow = 1 and o.title like "+"'%"+str+"%'";
		cmsArticleList = articleService.findListMapByHql(hql);
		return SUCCESS;
	}
	/**
	 * 底部文章直接跳转到文章详情展示页面
	 */
	public String gotoFooterArticleList(){
		//添加传过来的文章分类ID,提供给页面展示
		cmsArticle = (CmsArticle) articleService.getObjectById(articleId);
		if(cmsArticle!=null){
			cmsCategory = (CmsCategory) categoryService.getObjectByParams("where o.categoryId = "+cmsArticle.getCategoryId());
			articleList = articleService.findObjects(" where o.isShow = 1 and o.categoryId = "+cmsArticle.getCategoryId());
		}
		return SUCCESS;
	}
	/**
	 * 更新初始化商城底部新闻
	 * @param 此方法属于跨域操作（前后台端口不一致）后期需要使用memcache缓存技术做调整
	 */
	public void updateInServletContextArticle(){
		//底部信息分类
		List<CmsCategory> cmsCategorys = categoryService.findObjects(null, " where o.parentId=92 order by o.categoryId asc");
		List<BottomNewsModuleBean> bottomNewsModuleBeans = new ArrayList<BottomNewsModuleBean>();
		for(CmsCategory cc : cmsCategorys){
			BottomNewsModuleBean bnmb = new BottomNewsModuleBean();
			bnmb.setNewsTypeName(cc.getCategoryName());
			String hql = "select ca.articleId as articleId,ca.categoryId as categoryId,ca.title as title,ca.outUrl as outUrl from CmsArticle ca "+
						" where ca.isShow=1 and ca.categoryId="+cc.getCategoryId()+" order by ca.sortCode asc";
			List<Map> articleList = articleService.findListMapByHql(hql);
			bnmb.setNewsContentList(articleList);
			bottomNewsModuleBeans.add(bnmb);
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
	}
	public void setArticleService(IArticleService articleService) {
		this.articleService = articleService;
	}
	public List<CmsArticle> getArticleList() {
		return articleList;
	}
	public void setArticleList(List<CmsArticle> articleList) {
		this.articleList = articleList;
	}
	public String getArticleId() {
		return articleId;
	}
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
	public CmsArticle getCmsArticle() {
		return cmsArticle;
	}
	public void setCmsArticle(CmsArticle cmsArticle) {
		this.cmsArticle = cmsArticle;
	}
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	@SuppressWarnings("unchecked")
	public List<Map> getCmsArticleList() {
		return cmsArticleList;
	}
	@SuppressWarnings("unchecked")
	public void setCmsArticleList(List<Map> cmsArticleList) {
		this.cmsArticleList = cmsArticleList;
	}
	public CmsCategory getCmsCategory() {
		return cmsCategory;
	}
	public void setCmsCategory(CmsCategory cmsCategory) {
		this.cmsCategory = cmsCategory;
	}
	public void setCategoryService(ICategoryService categoryService) {
		this.categoryService = categoryService;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
}
