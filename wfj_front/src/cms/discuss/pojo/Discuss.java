package cms.discuss.pojo;
import util.pojo.BaseEntity;
/**
 * CmsDiscuss：评价表
 */
public class Discuss extends BaseEntity   implements java.io.Serializable {
	private static final long serialVersionUID = -7017971698619725567L;
	//评价id
	private Integer discussId;
	//对应文章id
	private Integer articleId;
	//用户id
	private Integer usersId;
	//用户名，或游客
	private String userName;
	//评价内容
	private String content;
	//是否显示
	private Short isShow;
	public Integer getDiscussId() {
		return discussId;
	}
	public void setDiscussId(Integer discussId) {
		this.discussId = discussId;
	}
	public Integer getArticleId() {
		return articleId;
	}
	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}
	public Integer getUsersId() {
		return usersId;
	}
	public void setUsersId(Integer usersId) {
		this.usersId = usersId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Short getIsShow() {
		return isShow;
	}
	public void setIsShow(Short isShow) {
		this.isShow = isShow;
	}
}