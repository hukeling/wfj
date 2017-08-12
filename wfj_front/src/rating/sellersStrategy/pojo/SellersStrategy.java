package rating.sellersStrategy.pojo;
import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * 卖家等级策略实体
 * @author wsy
 *
 */
public class SellersStrategy extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 5228479250636830150L;
	private Integer sellersLevelId;//卖家等级策略ID
	private Integer sellersLevel;//卖家等级
	private String sellersRank;//卖家头衔
	private String levelIcon;//等级图标
	private Integer minRefValue;//最小参考值
	private Integer maxRefValue;//最大参考值
	private Integer levelDiscountValue;//等级折扣值
	public Integer getSellersLevelId() {
		return sellersLevelId;
	}
	public void setSellersLevelId(Integer sellersLevelId) {
		this.sellersLevelId = sellersLevelId;
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
