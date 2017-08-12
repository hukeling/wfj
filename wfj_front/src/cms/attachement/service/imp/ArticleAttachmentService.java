package cms.attachement.service.imp;
import java.util.List;

import util.service.BaseService;
import cms.attachement.dao.IArticleAttachmentDao;
import cms.attachement.pojo.ArticleAttachment;
import cms.attachement.service.IArticleAttachmentService;
/**
 *  文章附件service
 * 
 */
public class ArticleAttachmentService extends BaseService<ArticleAttachment> implements IArticleAttachmentService {
	 private IArticleAttachmentDao articleAttachmentDao;
	public void setArticleAttachmentDao(IArticleAttachmentDao articleAttachmentDao) {
		this.baseDao=this.articleAttachmentDao = articleAttachmentDao;
	}
	/**
	 * 得到附件对象 列表 
	 * 
	 * @param articleId
	 *            
	 * @return List<ArticleAttachment>
	 */
	@SuppressWarnings("unchecked")
	public List<ArticleAttachment> getAttrByArticleId(String articleId) {
		return articleAttachmentDao.findObjects(null,"where 1=1 and o.articleId="+articleId);
	}
	/**
	 * 刪除附件
	 * 
	 * @param whereCondition
	 *            
	 * @return 
	 */
	public void deleteByParams(String whereCondition) {
		articleAttachmentDao.deleteByParams(whereCondition);
	}
}
