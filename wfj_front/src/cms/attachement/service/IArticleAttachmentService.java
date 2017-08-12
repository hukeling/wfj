package cms.attachement.service;
import java.util.List;

import util.service.IBaseService;
import cms.attachement.pojo.ArticleAttachment;
/**
 *  文章附件service接口
 * 
 */
public interface IArticleAttachmentService extends IBaseService<ArticleAttachment> {
	/**
	 * 得到附件对象 列表 
	 * 
	 * @param articleId
	 *            
	 * @return List<ArticleAttachment>
	 */
	public List<ArticleAttachment> getAttrByArticleId(String id);
	/**
	 * 刪除附件
	 * 
	 * @param whereCondition
	 *            
	 * @return 
	 */
	public void deleteByParams(String string);
}
