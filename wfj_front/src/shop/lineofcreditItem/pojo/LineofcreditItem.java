package shop.lineofcreditItem.pojo;
import java.io.Serializable;
import java.math.BigDecimal;

import util.pojo.BaseEntity;
/**
 * 授信额度明细实体
 * @author wsy
 *
 */
public class LineofcreditItem extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 3499610762873882779L;
	private Integer lineOfCreditId;//授信额度明细Id
	private Integer customerId;//客户ID
	private Integer ordersId;//订单ID
	private String ordersNo;//订单号
	private String serialNumber;//流水号
	private Integer type;//类型
	private BigDecimal transactionNumber;//交易数量
	private BigDecimal remainingNumber;//交易金额
	private BigDecimal frozenNumber;//冻结数量
	private java.util.Date tradeTime;//金币交易时间
	private java.util.Date operatorTime;//操作时间
	private String remarks;//备注
	public Integer getLineOfCreditId() {
		return lineOfCreditId;
	}
	public void setLineOfCreditId(Integer lineOfCreditId) {
		this.lineOfCreditId = lineOfCreditId;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
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
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public BigDecimal getTransactionNumber() {
		return transactionNumber;
	}
	public void setTransactionNumber(BigDecimal transactionNumber) {
		this.transactionNumber = transactionNumber;
	}
	public BigDecimal getRemainingNumber() {
		return remainingNumber;
	}
	public void setRemainingNumber(BigDecimal remainingNumber) {
		this.remainingNumber = remainingNumber;
	}
	public BigDecimal getFrozenNumber() {
		return frozenNumber;
	}
	public void setFrozenNumber(BigDecimal frozenNumber) {
		this.frozenNumber = frozenNumber;
	}
	public java.util.Date getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(java.util.Date tradeTime) {
		this.tradeTime = tradeTime;
	}
	public java.util.Date getOperatorTime() {
		return operatorTime;
	}
	public void setOperatorTime(java.util.Date operatorTime) {
		this.operatorTime = operatorTime;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
