package shop.customer.pojo;
import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * 会员等级实体类
 * @author ss
 *
 */
public class CustomerCredit extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 3007290839164795074L;
	/**
	 * 会员等级ID
	 */
	private Integer customerCreditId;
	/**
	 * 能力值
	 */
	private Integer capacityValue;
	/**
	 * 买家头衔
	 */
	private String buyerRank;
	/**
	 * 买家图标
	 */
	private String buyerIcon;
	/**
	 * 最小值
	 */
	private Integer minRefValue;
	/**
	 * 最大值
	 */
	private Integer maxRefValue;
	public Integer getCustomerCreditId() {
		return customerCreditId;
	}
	public void setCustomerCreditId(Integer customerCreditId) {
		this.customerCreditId = customerCreditId;
	}
	public Integer getCapacityValue() {
		return capacityValue;
	}
	public void setCapacityValue(Integer capacityValue) {
		this.capacityValue = capacityValue;
	}
	public String getBuyerRank() {
		return buyerRank;
	}
	public void setBuyerRank(String buyerRank) {
		this.buyerRank = buyerRank;
	}
	public String getBuyerIcon() {
		return buyerIcon;
	}
	public void setBuyerIcon(String buyerIcon) {
		this.buyerIcon = buyerIcon;
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
}
