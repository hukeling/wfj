package shop.customer.pojo;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import util.pojo.BaseEntity;
/**
 * 商城币
 * @author mf
 */
public class MallCoin extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 7386031930676838283L;
	/**商城币明细ID**/
	private Integer mallCoinId;
	/**订单id**/
	private Integer ordersId;
	/**订单号**/
	private String ordersNo;
	/**用户Id**/
	private Integer customerId;
	/**流水号**/
	private String serialNumber;
	/**类型**/
	private Integer type;
	/**交易数量**/
	private BigDecimal transactionNumber;
	/**剩余数量**/
	private BigDecimal remainingNumber;
	/**交易时间**/
	private Date tradeTime;
	/**操作时间**/
	private Date operatorTime;
	/**备注**/
	private String remarks;
	/**冻结数量**/
	private BigDecimal frozenNumber;
	/**当时分享比例**/
	private Integer proportion;
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
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
	public Date getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}
	public Date getOperatorTime() {
		return operatorTime;
	}
	public void setOperatorTime(Date operatorTime) {
		this.operatorTime = operatorTime;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	public BigDecimal getFrozenNumber() {
		return frozenNumber;
	}
	public void setFrozenNumber(BigDecimal frozenNumber) {
		this.frozenNumber = frozenNumber;
	}
	public Integer getProportion() {
		return proportion;
	}
	public void setProportion(Integer proportion) {
		this.proportion = proportion;
	}
	public Integer getMallCoinId() {
		return mallCoinId;
	}
	public void setMallCoinId(Integer mallCoinId) {
		this.mallCoinId = mallCoinId;
	}
}
