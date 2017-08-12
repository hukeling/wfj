package cms.friendLink.pojo;
import util.pojo.BaseEntity;
/**
 * CmsFriendlink：友情链接表
 */
public class Friendlink  extends BaseEntity  implements java.io.Serializable {
	private static final long serialVersionUID = -8378526264026027042L;
	//友情链接id
	private Integer id;
	//链接名称
	private String linkName;
	//访问地址
	private String url;
	//友情图片
	private String imgUrl;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
}