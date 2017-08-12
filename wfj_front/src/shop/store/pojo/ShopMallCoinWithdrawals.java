package shop.store.pojo;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import util.pojo.BaseEntity;
/**
 * ShopMallCoinWithdrawals - 供应商商城币提现
 * @author wy
 */
public class ShopMallCoinWithdrawals extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1833412496296023406L;
	/**
	 * 收支明细ID
	 */
	private Integer detailId;
	/**
	 * 付款人
	 */
	private String payerName;
	/**
	 * 付款人ID
	 */
	private Integer payerId;
	/**
	 * 收款人
	 */
	private String payeeName;
	/**
	 * 当前提现比例
	 */
	private String withdrawBL;
	/**
	 * 收款人ID
	 */
	private Integer payeeId;
	/**
	 * 持卡人
	 */
	private String cardHolder;
	/**
	 * 银行卡号
	 */
	private String cardNumber;
	/**
	 * 预留手机号
	 */
	private String phone;
	/**
	 * 流水号
	 */
	private String serialNumber;
	/**
	 * 交易时间
	 */
	private Date tradeTime;
	/**
	 * 收支类型（交易类型）
	 */
	private Integer incomeExpensesType;
	/**
	 * 交易金额
	 */
	private BigDecimal transactionAmount;
	/**
	 * 收入金额
	 */
	private BigDecimal income;
	/**
	 * 支出金额
	 */
	private BigDecimal outlay;
	/**
	 * 备注
	 */
	private String remarks;
	/**
	 * 用户留言
	 */
	private String customerMessage;
	/**
	 * 客服留言
	 */
	private String serverMessage;
	/**
	 * 交易状态
	 */
	private Integer state;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 审核人
	 */
	private String reviewer;
	/**
	 * 审核时间
	 */
	private Date reviewerDate;
	public Integer getDetailId() {
		return detailId;
	}
	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}
	public Integer getPayeeId() {
		return payeeId;
	}
	public void setPayeeId(Integer payeeId) {
		this.payeeId = payeeId;
	}
	public String getPayeeName() {
		return payeeName;
	}
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}
	public Integer getPayerId() {
		return payerId;
	}
	public void setPayerId(Integer payerId) {
		this.payerId = payerId;
	}
	public String getPayerName() {
		return payerName;
	}
	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public Date getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}
	public Integer getIncomeExpensesType() {
		return incomeExpensesType;
	}
	public void setIncomeExpensesType(Integer incomeExpensesType) {
		this.incomeExpensesType = incomeExpensesType;
	}
	public BigDecimal getIncome() {
		return income;
	}
	public void setIncome(BigDecimal income) {
		this.income = income;
	}
	public BigDecimal getOutlay() {
		return outlay;
	}
	public void setOutlay(BigDecimal outlay) {
		this.outlay = outlay;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getCardHolder() {
		return cardHolder;
	}
	public void setCardHolder(String cardHolder) {
		this.cardHolder = cardHolder;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public String getCustomerMessage() {
		return customerMessage;
	}
	public void setCustomerMessage(String customerMessage) {
		this.customerMessage = customerMessage;
	}
	public String getServerMessage() {
		return serverMessage;
	}
	public void setServerMessage(String serverMessage) {
		this.serverMessage = serverMessage;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getWithdrawBL() {
		return withdrawBL;
	}
	public void setWithdrawBL(String withdrawBL) {
		this.withdrawBL = withdrawBL;
	}
	public String getReviewer() {
		return reviewer;
	}
	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}
	public Date getReviewerDate() {
		return reviewerDate;
	}
	public void setReviewerDate(Date reviewerDate) {
		this.reviewerDate = reviewerDate;
	}
}
