package shop.information.pojo;
import java.io.Serializable;
import java.util.Date;

import util.pojo.BaseEntity;
/**
 * 资讯评论
 * @author mqr
 *
 */
public class InformationComment extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1693235566968142980L;
	/**
	 * ID
	 */
	private Integer discussId;
	/**
	 * 文章ID
	 */
	private Integer articleId;
	/**
	 * 评论人ID
	 */
	private Integer customerId;
	/**
	 * 评论内容
	 */
	private String content;
	/**
	 * 审核状态
	 */
	private Integer state;
	/**
	 * 评论发布IP
	 */
	private String ip;
	/**
	 * 发表评论时间
	 */
	private Date createTime;
	//setter getter
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
