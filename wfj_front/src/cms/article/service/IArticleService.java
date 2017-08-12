package cms.article.service;
import util.service.IBaseService;
import cms.article.pojo.CmsArticle;
/**
 *  文章service接口
 * 
 */
public interface IArticleService extends IBaseService<CmsArticle> {
	 /**
	 * 添加
	 * 
	 * @param cmsArticle,attUrls
	 *            
	 * @return CmsArticle
	 */
	public CmsArticle saveOrUpdateArticleAndAtt(CmsArticle cmsArticle, String attUrls);
	/**
	 * 更新审核状态
	 * 
	 * @param articleId, isPass
	 *            
	 * @return CmsArticle
	 */
	public CmsArticle updateIsPass(String articleId, String isPass);
	/**
	 * 删除 文章 及 附件 
	 * 
	 * @param ids
	 *            
	 * @return Boolean
	 */
	public Boolean deleteArticleAndAttByIds(String ids);
}
