package shop.order.pojo;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import util.pojo.BaseEntity;
/**
 * 订单bean
 * @author 张攀攀
 *
 */
public class Orders extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 438406933695362923L;
	/**
	 * 订单Id
	 */
	private Integer ordersId;
	/**
	 * 客户Id
	 */
	private Integer customerId;
	/**
	 * 采购员id
	 */
	private Integer sonaccountId;
	/**
	 * 店铺id
	 */
	private Integer shopInfoId;
	/**
	 * 采购员姓名
	 */
	private String buyerName;
	/**
	 * 总订单号
	 */
	private String totalOrdersNo;
	/**
	 * 订单号
	 */
	private String ordersNo;
	/**
	 * 支付交易号
	 */
	private String dealId;
	/**
	 * 订单生成时间
	 */
	private Date createTime;
	/**
	 * 订单修改时间
	 */
	private Date updateTime;
	/**
	 * 州省地区
	 */
	private String regionLocation;
	/**
	 * 城市
	 */
	private String city;
	/**
	 * 国家
	 */
	private String country;
	/**
	 * 详细地址
	 */
	private String address;
	/**
	 * 收获人姓名
	 */
	private String consignee;
	/**
	 * 电子邮件
	 */
	private String email;
	/**
	 * 邮政编码
	 */
	private String postcode;
	/**
	 * 最佳配送时间(
	 * 1.只工作日送货(双休日、假日不用送);
	 * 2.工作日、双休日与假日均可送货;
	 * 3.只双休日、假日送货(工作日不用送)
	 * )
	 */
	private Integer bestSendDate;
	/**
	 * 标志性建筑
	 */
	private String flagContractor;
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 手机
	 */
	private String mobilePhone;
	/**
	 * 配送方式
	 * 1、快递公司
	 * 2、同城快递
	 * 3、线下自提
	 */
	private Integer sendType;
	/**
	 * 支付方式
	 * (1、货到付款 2、支付宝 3、银行汇款 4.网银支付)
	 */
	private Integer payMode;
	/**
	 * 商品总金额
	 */
	private BigDecimal totalAmount;
	/**
	 * 最终支付金额
	 */
	private BigDecimal finalAmount;
	/**
	 * 运费
	 */
	private BigDecimal freight;
	/**
	 * 是否使用优惠卷
	 * 0、否,1、是
	 */
	private Integer isUseCoupon;
	/**
	 * 优惠券ID
	 */
	private Integer discountCouponId;
	/**
	 * 订单使用优惠券金额
	 */
	private BigDecimal orderCouponAmount;
	/**
	 * 订单附言
	 */
	private String comments;
	/**
	 * 购买人IP
	 */
	private String ip;
	/**
	 * 货币类型	
	 */
	private String currency;
	/**
	 * 订单状态
	 * (1、未处理(生成订单)；2、已付款；3、正在配货；4、已发货；5、已收货；6、订单取消；7、异常订单(退换货等、问题订单)；9、已评价；)
	 */
	private Integer ordersState;
	/**
	 * 缺货处理
	 * (1、等待所有商品备齐后再发；2、取消订单；3、与店主协商；)
	 */
	private Integer oosOperator;
	/**
	 * 锁定操作
	 * (0：未锁定；1：已锁定)
	 */
	private Integer isLocked;
	/**
	 * 订单备注（客服留言）
	 */
	private String ordersRemark;
	/**
	 * 订单赠送商城币总额（佣金分享）
	 */
	private BigDecimal virtualCoin;
	/**使用天海币数量**/
	private BigDecimal userCoin;
	/***天海币兑换金额**/
	private BigDecimal changeAmount;
	/**
	 * 订单赠送商城币总额
	 */
	private BigDecimal virtualCoinNumber;
	/**
	 * 已使用授信额度
	 */
	private BigDecimal useLineOfCredit;
	/**
	 * 发票企业名称
	 */
	private String companyNameForInvoice;
	/**
	 * 纳税人识别号
	 */
	private String taxpayerNumber;
	/**
	 * 发票地址
	 */
	private String addressForInvoice;
	/**
	 * 发票电话
	 */
	private String phoneForInvoice;
	/**
	 * 开户行
	 */
	private String openingBank;
	/**
	 * 账号
	 */
	private String bankAccountNumber;
	/**
	 * (买家)结算状态
	 */
	private Integer settlementStatus;
	/**
	 * (卖家)结算状态
	 */
	private Integer settlementStatusForSellers;
	/**
	 * 发票类型
	 */
	private Integer invoiceType;//1：不开发票;2：普通发票;3：增值税发票
	/**
	 * 发票内容
	 */
	private String invoiceInfo;
	/**
	 * 下单人类型
	 */
	private Integer customerType;
	/**
	 * 折扣比例
	 */
	private BigDecimal discount;
	/**
	 * 银行简码
	 */
	private String bankCode;
	/**
	 * 配送方式1:快递配送，2：线下自取
	 */
	/*private Integer distributionMode;*/
	/**
	 * 税费
	 */
	private BigDecimal taxation;
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public Integer getOrdersId() {
		return ordersId;
	}
	public void setOrdersId(Integer ordersId) {
		this.ordersId = ordersId;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getOrdersNo() {
		return ordersNo;
	}
	public void setOrdersNo(String ordersNo) {
		this.ordersNo = ordersNo;
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
	public String getRegionLocation() {
		return regionLocation;
	}
	public void setRegionLocation(String regionLocation) {
		this.regionLocation = regionLocation;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getFlagContractor() {
		return flagContractor;
	}
	public void setFlagContractor(String flagContractor) {
		this.flagContractor = flagContractor;
	}
	public Integer getSendType() {
		return sendType;
	}
	public void setSendType(Integer sendType) {
		this.sendType = sendType;
	}
	public Integer getPayMode() {
		return payMode;
	}
	public void setPayMode(Integer payMode) {
		this.payMode = payMode;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public BigDecimal getFinalAmount() {
		return finalAmount;
	}
	public void setFinalAmount(BigDecimal finalAmount) {
		this.finalAmount = finalAmount;
	}
	public BigDecimal getFreight() {
		return freight;
	}
	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}
	public Integer getIsUseCoupon() {
		return isUseCoupon;
	}
	public void setIsUseCoupon(Integer isUseCoupon) {
		this.isUseCoupon = isUseCoupon;
	}
	public Integer getDiscountCouponId() {
		return discountCouponId;
	}
	public void setDiscountCouponId(Integer discountCouponId) {
		this.discountCouponId = discountCouponId;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Integer getOosOperator() {
		return oosOperator;
	}
	public void setOosOperator(Integer oosOperator) {
		this.oosOperator = oosOperator;
	}
	public Integer getBestSendDate() {
		return bestSendDate;
	}
	public void setBestSendDate(Integer bestSendDate) {
		this.bestSendDate = bestSendDate;
	}
	public Integer getIsLocked() {
		return isLocked;
	}
	public void setIsLocked(Integer isLocked) {
		this.isLocked = isLocked;
	}
	public Integer getOrdersState() {
		return ordersState;
	}
	public void setOrdersState(Integer ordersState) {
		this.ordersState = ordersState;
	}
	public String getOrdersRemark() {
		return ordersRemark;
	}
	public void setOrdersRemark(String ordersRemark) {
		this.ordersRemark = ordersRemark;
	}
	public Integer getShopInfoId() {
		return shopInfoId;
	}
	public void setShopInfoId(Integer shopInfoId) {
		this.shopInfoId = shopInfoId;
	}
	public BigDecimal getVirtualCoin() {
		return virtualCoin;
	}
	public void setVirtualCoin(BigDecimal virtualCoin) {
		this.virtualCoin = virtualCoin;
	}
	public String getTotalOrdersNo() {
		return totalOrdersNo;
	}
	public void setTotalOrdersNo(String totalOrdersNo) {
		this.totalOrdersNo = totalOrdersNo;
	}
	public BigDecimal getUserCoin() {
		return userCoin;
	}
	public void setUserCoin(BigDecimal userCoin) {
		this.userCoin = userCoin;
	}
	public BigDecimal getChangeAmount() {
		return changeAmount;
	}
	public void setChangeAmount(BigDecimal changeAmount) {
		this.changeAmount = changeAmount;
	}
	public BigDecimal getUseLineOfCredit() {
		return useLineOfCredit;
	}
	public void setUseLineOfCredit(BigDecimal useLineOfCredit) {
		this.useLineOfCredit = useLineOfCredit;
	}
	public Integer getSonaccountId() {
		return sonaccountId;
	}
	public void setSonaccountId(Integer sonaccountId) {
		this.sonaccountId = sonaccountId;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public String getCompanyNameForInvoice() {
		return companyNameForInvoice;
	}
	public void setCompanyNameForInvoice(String companyNameForInvoice) {
		this.companyNameForInvoice = companyNameForInvoice;
	}
	public String getAddressForInvoice() {
		return addressForInvoice;
	}
	public void setAddressForInvoice(String addressForInvoice) {
		this.addressForInvoice = addressForInvoice;
	}
	public String getPhoneForInvoice() {
		return phoneForInvoice;
	}
	public void setPhoneForInvoice(String phoneForInvoice) {
		this.phoneForInvoice = phoneForInvoice;
	}
	public String getOpeningBank() {
		return openingBank;
	}
	public void setOpeningBank(String openingBank) {
		this.openingBank = openingBank;
	}
	public String getTaxpayerNumber() {
		return taxpayerNumber;
	}
	public void setTaxpayerNumber(String taxpayerNumber) {
		this.taxpayerNumber = taxpayerNumber;
	}
	public String getBankAccountNumber() {
		return bankAccountNumber;
	}
	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}
	public Integer getSettlementStatus() {
		return settlementStatus;
	}
	public void setSettlementStatus(Integer settlementStatus) {
		this.settlementStatus = settlementStatus;
	}
	public Integer getSettlementStatusForSellers() {
		return settlementStatusForSellers;
	}
	public void setSettlementStatusForSellers(Integer settlementStatusForSellers) {
		this.settlementStatusForSellers = settlementStatusForSellers;
	}
	public Integer getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(Integer invoiceType) {
		this.invoiceType = invoiceType;
	}
	public String getInvoiceInfo() {
		return invoiceInfo;
	}
	public void setInvoiceInfo(String invoiceInfo) {
		this.invoiceInfo = invoiceInfo;
	}
	public Integer getCustomerType() {
		return customerType;
	}
	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
	}
	public String getDealId() {
		return dealId;
	}
	public void setDealId(String dealId) {
		this.dealId = dealId;
	}
	public BigDecimal getVirtualCoinNumber() {
		return virtualCoinNumber;
	}
	public void setVirtualCoinNumber(BigDecimal virtualCoinNumber) {
		this.virtualCoinNumber = virtualCoinNumber;
	}
	public BigDecimal getDiscount() {
		return discount;
	}
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	public BigDecimal getOrderCouponAmount() {
		return orderCouponAmount;
	}
	public void setOrderCouponAmount(BigDecimal orderCouponAmount) {
		this.orderCouponAmount = orderCouponAmount;
	}
	/*public Integer getDistributionMode() {
		return distributionMode;
	}
	public void setDistributionMode(Integer distributionMode) {
		this.distributionMode = distributionMode;
	}*/
	public BigDecimal getTaxation() {
		return taxation;
	}
	public void setTaxation(BigDecimal taxation) {
		this.taxation = taxation;
	}
}
