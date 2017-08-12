package shop.front.store.pojo;
/**
 * OrdersHomeStatus - 店铺首页中显示的几种状态的数量
 */
public class OrdersHomeStatus {
	/**评价订单数量**/
	private Integer complaintNum;
	/**退货订单数量**/
	private Integer refundNum;
	/**参加活动的数量，点击查看活动参与列表数量**/
	private Integer activityNum;
	/**等待发货的的订单数量**/
	private Integer waitingNum;
	/**未上架产品数量**/
	private Integer OffShelfNum;
	/**已上架的产品数量**/
	private Integer OnShelfNum;
	public Integer getComplaintNum() {
		return complaintNum;
	}
	public void setComplaintNum(Integer complaintNum) {
		this.complaintNum = complaintNum;
	}
	public Integer getRefundNum() {
		return refundNum;
	}
	public void setRefundNum(Integer refundNum) {
		this.refundNum = refundNum;
	}
	public Integer getActivityNum() {
		return activityNum;
	}
	public void setActivityNum(Integer activityNum) {
		this.activityNum = activityNum;
	}
	public Integer getWaitingNum() {
		return waitingNum;
	}
	public void setWaitingNum(Integer waitingNum) {
		this.waitingNum = waitingNum;
	}
	public Integer getOffShelfNum() {
		return OffShelfNum;
	}
	public void setOffShelfNum(Integer offShelfNum) {
		OffShelfNum = offShelfNum;
	}
	public Integer getOnShelfNum() {
		return OnShelfNum;
	}
	public void setOnShelfNum(Integer onShelfNum) {
		OnShelfNum = onShelfNum;
	}
}
