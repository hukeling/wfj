package rating.sellersRecord.pojo;
import java.io.Serializable;
import java.util.Date;

import util.pojo.BaseEntity;
/**
 * 卖家等级升迁记录实体
 * @author wsy
 *
 */
public class SellersRecord extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -478103446916960331L;
	private Integer ratingRecordId;//卖家等级升迁记录ID
	private Integer customerId;//客户ID
	private Integer sellersLevel;//卖家等级
	private String sellersRank;//卖家头衔
	private String levelIcon;//卖家图标
	private String remark;//备注
	private Date optionDate;//操作时间
	private String operator;//操作人
	public Integer getRatingRecordId() {
		return ratingRecordId;
	}
	public void setRatingRecordId(Integer ratingRecordId) {
		this.ratingRecordId = ratingRecordId;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Integer getSellersLevel() {
		return sellersLevel;
	}
	public void setSellersLevel(Integer sellersLevel) {
		this.sellersLevel = sellersLevel;
	}
	public String getSellersRank() {
		return sellersRank;
	}
	public void setSellersRank(String sellersRank) {
		this.sellersRank = sellersRank;
	}
	public String getLevelIcon() {
		return levelIcon;
	}
	public void setLevelIcon(String levelIcon) {
		this.levelIcon = levelIcon;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getOptionDate() {
		return optionDate;
	}
	public void setOptionDate(Date optionDate) {
		this.optionDate = optionDate;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
}
