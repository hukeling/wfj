package shop.mailSubscribe.pojo;
import java.util.Date;

import util.pojo.BaseEntity;
/**
 * 邮件订阅类
 * @author lqn
 *
 */
public class MailSubscribe extends BaseEntity{
	private static final long serialVersionUID = 7921764722813975329L;
	/**
	 * 文章ID
	 */
	private Integer articleId;
	/**
	 * 文章标题
	 */
	private String title;
	/**
	 * 文章摘要
	 */
	private String brief;
	/**
	 * 文章内容
	 */
	private String content;
	/**
	 * 文章图片地址     备用
	 */
	private String imgUrl;
	/**
	 * 是否发送
	 */
	private Integer isSend;
	/**
	 * 发布人
	 */
	private String publishUser;
	/**
	 * 修改人
	 */
	private String modifyUser;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	public Integer getArticleId() {
		return articleId;
	}
	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public Integer getIsSend() {
		return isSend;
	}
	public void setIsSend(Integer isSend) {
		this.isSend = isSend;
	}
	public String getPublishUser() {
		return publishUser;
	}
	public void setPublishUser(String publishUser) {
		this.publishUser = publishUser;
	}
	public String getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
