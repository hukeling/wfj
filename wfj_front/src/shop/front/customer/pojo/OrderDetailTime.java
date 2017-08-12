package shop.front.customer.pojo;
import java.util.Date;
/**
 * OrderDetailTime - 查看订单时的状态时间
 */
public class OrderDetailTime {
	/**生成订单时间**/
	private Date createTime;
	/**支付时间**/
	private Date payTime;
	/**发货时间**/
	private Date deliveryTime;
	/**收货时间**/
	private Date receivingTime ;
	/**物流信息**/
	private String logisticsInfo;
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public Date getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public Date getReceivingTime() {
		return receivingTime;
	}
	public void setReceivingTime(Date receivingTime) {
		this.receivingTime = receivingTime;
	}
	public String getLogisticsInfo() {
		return logisticsInfo;
	}
	public void setLogisticsInfo(String logisticsInfo) {
		this.logisticsInfo = logisticsInfo;
	}
}
