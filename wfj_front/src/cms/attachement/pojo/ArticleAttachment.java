package cms.attachement.pojo;
import util.pojo.BaseEntity;
/**
 * CmsArticleAttachment：附件表
 */
public class ArticleAttachment extends BaseEntity  implements java.io.Serializable {
	private static final long serialVersionUID = -4203040269671800977L;
	//附件id
	private Integer attachmentId;
	//文章id
	private Integer articleId;
	//附件url
	private String attUrl;
	//附件名称
	private String attName;
	//创建时间
	private String createTime;
	//消耗积分
	private Integer reduceIntegral;
	//附件类型
	private String attType;
	public Integer getAttachmentId() {
		return attachmentId;
	}
	public void setAttachmentId(Integer attachmentId) {
		this.attachmentId = attachmentId;
	}
	public String getAttUrl() {
		return attUrl;
	}
	public void setAttUrl(String attUrl) {
		this.attUrl = attUrl;
	}
	public String getAttName() {
		return attName;
	}
	public void setAttName(String attName) {
		this.attName = attName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Integer getReduceIntegral() {
		return reduceIntegral;
	}
	public void setReduceIntegral(Integer reduceIntegral) {
		this.reduceIntegral = reduceIntegral;
	}
	public Integer getArticleId() {
		return articleId;
	}
	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}
	public String getAttType() {
		return attType;
	}
	public void setAttType(String attType) {
		this.attType = attType;
	}
}