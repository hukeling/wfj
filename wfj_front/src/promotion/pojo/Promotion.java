package promotion.pojo;
import java.io.Serializable;
import java.util.Date;

import util.pojo.BaseEntity;
/**
 * 促销活动
 * @author ss
 *
 */
public class Promotion extends BaseEntity implements Serializable{
	private static final long serialVersionUID = -8374378128498102021L;
	/**
	 * 促销活动ID
	 */
	private Integer promotionId;
	/**
	 * 主题
	 */
	private String subject;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 活动状态
	 */
	private Integer state;
	/**
	 * 大海报
	 */
	private String bigCard;
	/**
	 * 小海报
	 */
	private String smallCard;
	public Integer getPromotionId() {
		return promotionId;
	}
	public void setPromotionId(Integer promotionId) {
		this.promotionId = promotionId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getBigCard() {
		return bigCard;
	}
	public void setBigCard(String bigCard) {
		this.bigCard = bigCard;
	}
	public String getSmallCard() {
		return smallCard;
	}
	public void setSmallCard(String smallCard) {
		this.smallCard = smallCard;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
}
