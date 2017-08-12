package shop.order.pojo;
import java.io.Serializable;
import java.util.Date;

import util.pojo.BaseEntity;
/**
 * 订单操作日志bean
 * @author 张攀攀
 *
 */
public class OrdersOPLog extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 8418159682138970816L;
	/**
	 * 订单操作日志ID
	 */
	private Integer ordersOPLogId;
	/**
	 * 订单ID
	 */
	private Integer ordersId;
	/**
	 * 订单号
	 */
	private String ordersNo;
	/**
	 * 订单操作人ID
	 */
	private Integer ooperatorId;
	/**
	 * 订单操作人
	 */
	private String operatorName;
	/**
	 * 订单操作人来源
	 * (1、后台用户；2、后台系统；3、前台顾客；)
	 */
	private String ooperatorSource;
	/**
	 * 订单操作时间
	 */
	private Date operatorTime;
	/**
	 * 订单操作状态
	 * (1、未处理；
	 * 2、订单留言(客户、客服订单留言状态)；
	 * 3、修改订单；
	 * 4、订单确认；
	 * 5、收款确认；
	 * 6、正在配货；
	 * 7、已经发货；
	 * 8、收货确认；
	 * 9、确认作废；
	 * 10、锁定订单；
	 * 11、解锁订单)
	 */
	private Integer ordersOperateState;
	/**
	 * 订单操作备注
	 */
	private String comments;
	/**
	 * 订单留言（客户、客服都可留言）
	 */
	private String ordersMsg;
	public Integer getOrdersOPLogId() {
		return ordersOPLogId;
	}
	public void setOrdersOPLogId(Integer ordersOPLogId) {
		this.ordersOPLogId = ordersOPLogId;
	}
	public Integer getOrdersId() {
		return ordersId;
	}
	public void setOrdersId(Integer ordersId) {
		this.ordersId = ordersId;
	}
	public String getOrdersNo() {
		return ordersNo;
	}
	public void setOrdersNo(String ordersNo) {
		this.ordersNo = ordersNo;
	}
	public Integer getOoperatorId() {
		return ooperatorId;
	}
	public void setOoperatorId(Integer ooperatorId) {
		this.ooperatorId = ooperatorId;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getOoperatorSource() {
		return ooperatorSource;
	}
	public void setOoperatorSource(String ooperatorSource) {
		this.ooperatorSource = ooperatorSource;
	}
	public Date getOperatorTime() {
		return operatorTime;
	}
	public void setOperatorTime(Date operatorTime) {
		this.operatorTime = operatorTime;
	}
	public Integer getOrdersOperateState() {
		return ordersOperateState;
	}
	public void setOrdersOperateState(Integer ordersOperateState) {
		this.ordersOperateState = ordersOperateState;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getOrdersMsg() {
		return ordersMsg;
	}
	public void setOrdersMsg(String ordersMsg) {
		this.ordersMsg = ordersMsg;
	}
}
