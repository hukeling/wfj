package cms.seo.pojo;
import util.pojo.BaseEntity;
/**
 * CmsSeo：seo表
 */
public class CmsSeo  extends BaseEntity  implements java.io.Serializable {
	private static final long serialVersionUID = -4247810332804196584L;
	//  seoid
	private Integer seoId;
	//  title
	private String title;
	//  keywords
	private String keywords;
	//  description
	private String description;
	public Integer getSeoId() {
		return seoId;
	}
	public void setSeoId(Integer seoId) {
		this.seoId = seoId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}