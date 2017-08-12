package shop.front.bean;
import java.math.BigDecimal;
/**
 * CustomerHomeBean - 用户中心的bean 
 */
public class CustomerHomeBean {
	/**未付款订单数量**/
	private Integer unPayCount;
	/**取消订单数量**/
	private Integer cancelCount;
	/**完成订单数量**/
	private Integer completeCount;
	/**账号余额**/
	private BigDecimal balance;
	/**用户积分**/
	private Integer integral;
	public Integer getUnPayCount() {
		return unPayCount;
	}
	public void setUnPayCount(Integer unPayCount) {
		this.unPayCount = unPayCount;
	}
	public Integer getCancelCount() {
		return cancelCount;
	}
	public void setCancelCount(Integer cancelCount) {
		this.cancelCount = cancelCount;
	}
	public Integer getCompleteCount() {
		return completeCount;
	}
	public void setCompleteCount(Integer completeCount) {
		this.completeCount = completeCount;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public Integer getIntegral() {
		return integral;
	}
	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
}
