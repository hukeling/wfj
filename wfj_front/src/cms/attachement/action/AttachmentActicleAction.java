package cms.attachement.action;
import util.action.BaseAction;
import cms.attachement.service.IArticleAttachmentService;
/**
 *  
 * 附件action
 */
@SuppressWarnings("serial")
public class AttachmentActicleAction extends BaseAction {
	private IArticleAttachmentService articleAttachmentService;
	private String deleteURL;
	//图片和文档上传的url 
	private String attUrls;
	private String ids;
	private String id;
	/**
	 * 删除附件 
	 * 
	 */
	public void deleteAtt() throws Exception {
		//现在得到的是多个，所以用ids ，重新查询方法 
		articleAttachmentService.deleteByParams("where 1=1 and o.attachmentId="+id);
	}
	/**
	 * 得到id
	 * 
	 */
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setArticleAttachmentService(IArticleAttachmentService articleAttachmentService) {
		this.articleAttachmentService = articleAttachmentService;
	}
	public String getDeleteURL() {
		return deleteURL;
	}
	public void setDeleteURL(String deleteURL) {
		this.deleteURL = deleteURL;
	}
	public String getAttUrls() {
		return attUrls;
	}
	public void setAttUrls(String attUrls) {
		this.attUrls = attUrls;
	}
}
