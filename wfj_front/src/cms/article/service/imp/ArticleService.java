package cms.article.service.imp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import util.service.BaseService;
import cms.article.dao.IArticleDao;
import cms.article.pojo.CmsArticle;
import cms.article.service.IArticleService;
import cms.attachement.pojo.ArticleAttachment;
import cms.attachement.service.IArticleAttachmentService;
/**
 *  文章service
 * 
 */
public class ArticleService extends BaseService<CmsArticle> implements IArticleService {
	private  IArticleDao articleDao;
    private IArticleAttachmentService articleAttachmentService;
	public void setArticleDao(IArticleDao articleDao) {
		this.baseDao=this.articleDao = articleDao;
	}
    public void setArticleAttachmentService(IArticleAttachmentService articleAttachmentService) {
		this.articleAttachmentService = articleAttachmentService;
	}
    /**
	 * 添加
	 * 
	 * @param cmsArticle,attUrls
	 *            
	 * @return CmsArticle
	 */
	@SuppressWarnings("unchecked")
	public CmsArticle saveOrUpdateArticleAndAtt(CmsArticle cmsArticle,String attUrls) {
		 //1 保存文章 
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		if(cmsArticle.getArticleId()==null){
			//如果是第一次添加就加创建时间 
			cmsArticle.setCreateTime(dateString);
		}
		cmsArticle.setUpdateTime(dateString);
		 //默认审核状态为 0 ,未审核 
		 //Short isPass=0;
		// cmsArticle.setIsPass(isPass);
		 //设置图片的真实名称  imgUrl=cms/image/e2f95641-e4b8-4816-9393-57ed5bca1aef_2.jpg
		 if(cmsArticle.getImgUrl()!=null &&!"".equals(cmsArticle.getImgUrl())){
			 String imgUrl = cmsArticle.getImgUrl();
			 String imgTrueName =imgUrl.substring(imgUrl.lastIndexOf("_")+1);
			 cmsArticle.setImgTrueName(imgTrueName);
		 }
		 CmsArticle cmsArticle2 = (CmsArticle)articleDao.saveOrUpdateObject(cmsArticle);
		 //2 添加或更新 附件内容 
		 //如果 附件存在的话 保存附件 .atturls="0",附件表不保存任何数据  
		 if(!"0".equals(attUrls)){
			 Integer articleId = cmsArticle2.getArticleId();
			 String articleType = cmsArticle2.getArticleType();
			 //2.1 删除附件
			 List<ArticleAttachment> attList = articleAttachmentService.findObjects(null," where 1=1 and o.articleId="+articleId);
			 if(attList.size()!=0){
				 articleAttachmentService.deleteByParams(" where 1=1 and o.articleId="+articleId);
			 }
			 //2.2 添加附件
			 String[] attUrlArr = attUrls.split(",");
			 // splitStr 是分隔符 
			 //String[] attUrlArr = attUrls.split('"'+splitStr+'"');
			 for(String attUrl:attUrlArr){
				 ArticleAttachment articleAttachment = new ArticleAttachment();
				 articleAttachment.setAttUrl(attUrl);
				 if(attUrl.indexOf("@")!=-1){
					 String attName=attUrl.substring(attUrl.lastIndexOf("@")+1);
					 articleAttachment.setAttName(attName); 
				 }
				 articleAttachment.setArticleId(articleId);
				 articleAttachment.setAttType(articleType);
				 articleAttachment.setCreateTime(dateString);
				 articleAttachmentService.saveOrUpdateObject(articleAttachment);
			 }
		 }
		return cmsArticle2;
	}
	/**
	 * 更新审核状态
	 * 
	 * @param articleId, isPass
	 *            
	 * @return CmsArticle
	 */
	public CmsArticle updateIsPass(String articleId, String isPass) {
		CmsArticle cmsArticle = (CmsArticle)articleDao.getObjectById(articleId);
		cmsArticle.setIsPass(Short.parseShort(isPass));
		CmsArticle cmsArticle2 = (CmsArticle)articleDao.saveOrUpdateObject(cmsArticle);
		return cmsArticle2;
	}
	/**
	 * 删除 文章 及 附件 
	 * 
	 * @param ids
	 *            
	 * @return Boolean
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	public Boolean deleteArticleAndAttByIds(String ids) {
		String[] idsArr = ids.split(",");
		boolean manflag=false;
		boolean flag2=false;
		for(String articleId:idsArr){
			//删除附件表的数据
			List articleAttachList = articleAttachmentService.findObjects(null, " where 1=1 and o.articleId="+articleId);
			boolean flag1=false;
			if(articleAttachList!=null){
				flag1 = articleAttachmentService.deleteObjectsByIds("articleId", articleId);
			} 
			//删除主表的数据 
			flag2 = deleteObjectsByIds("articleId", articleId);
		}
		return flag2;
	}
}
