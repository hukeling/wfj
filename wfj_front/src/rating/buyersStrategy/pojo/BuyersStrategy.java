package rating.buyersStrategy.pojo;
import java.io.Serializable;
import java.math.BigDecimal;

import util.pojo.BaseEntity;
/**
 * 买家等级策略实体
 * @author wsy
 *
 */
public class BuyersStrategy extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 3285365294786716497L;
	private Integer buyersLevelId;//买家等级策略ID
	private Integer buyersLevel;//买家等级
	private String buyerRank;//买家头衔
	private String levelIcon;//等级图标
	private Integer minRefValue;//最小参考值
	private Integer maxRefValue;//最大参考值
	private Integer levelDiscountValue;//等级折扣值
	private BigDecimal lineOfCredit;//授信额度
	private Integer creditDate;//授信期限
	public Integer getBuyersLevelId() {
		return buyersLevelId;
	}
	public void setBuyersLevelId(Integer buyersLevelId) {
		this.buyersLevelId = buyersLevelId;
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
}
