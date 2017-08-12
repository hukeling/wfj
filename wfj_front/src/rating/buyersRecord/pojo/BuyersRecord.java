package rating.buyersRecord.pojo;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import util.pojo.BaseEntity;
/**
 * 买家等级升迁记录实体
 * @author wsy
 *
 */
public class BuyersRecord extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -4103455483776875824L;
	private Integer ratingRecordId;//买家等级升迁记录ID
	private Integer customerId;//客户ID
	private Integer buyersLevel;//买家等级
	private String buyerRank;//买家头衔
	private String levelIcon;//等级图标
	private String remark;//备注
	private Date optionDate;//操作时间
	private String operator;//操作人
	private BigDecimal lineOfCredit;//授信额度
	private Integer creditDate;//授信期限
	private Integer minRefValue;//最小参考值
	private Integer maxRefValue;//最大参考值
	private Integer levelDiscountValue;//等级折扣值
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
	public Integer getBuyersLevel() {
		return buyersLevel;
	}
	public void setBuyersLevel(Integer buyersLevel) {
		this.buyersLevel = buyersLevel;
	}
	public String getBuyerRank() {
		return buyerRank;
	}
	public void setBuyerRank(String buyerRank) {
		this.buyerRank = buyerRank;
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
	public BigDecimal getLineOfCredit() {
		return lineOfCredit;
	}
	public void setLineOfCredit(BigDecimal lineOfCredit) {
		this.lineOfCredit = lineOfCredit;
	}
	public Integer getCreditDate() {
		return creditDate;
	}
	public void setCreditDate(Integer creditDate) {
		this.creditDate = creditDate;
	}
	public Integer getMinRefValue() {
		return minRefValue;
	}
	public void setMinRefValue(Integer minRefValue) {
		this.minRefValue = minRefValue;
	}
	public Integer getMaxRefValue() {
		return maxRefValue;
	}
	public void setMaxRefValue(Integer maxRefValue) {
		this.maxRefValue = maxRefValue;
	}
	public Integer getLevelDiscountValue() {
		return levelDiscountValue;
	}
	public void setLevelDiscountValue(Integer levelDiscountValue) {
		this.levelDiscountValue = levelDiscountValue;
	}
}
