package shop.order.pojo;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import util.pojo.BaseEntity;
/**
 * 订单明细bean
 * 
 * @author 张攀攀
 * 
 */
public class OrdersList extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 8418159682138970816L;
	/**
	 * 订单明细ID
	 */
	private Integer orderDetailId;
	/**
	 * 订单ID
	 */
	private Integer ordersId;
	/**
	 * 订单号
	 */
	private String ordersNo;
	/**
	 * 商品Id
	 */
	private Integer productId;
	/**
	 * 商品全名称
	 */
	private String productFullName;
	/**
	 * 进货价
	 */
	private BigDecimal costPrice;
	/**
	 * 销售价
	 */
	private BigDecimal salesPrice;
	/**
	 * 市场价
	 */
	private BigDecimal marketPrice;
	/**
	 * 会员价格(N)
	 */
	private BigDecimal memberPrice;
	/**
	 * 数量
	 */
	private Integer count;
	/**
	 * 购买类型
	 */
	private Integer buyType;
	/**
	 * 商品编号
	 */
	private String productCode;
	/**
	 *商品图片LOGO
	 */
	private String logoImage;
	/**
	 *商品品牌
	 */
	private Integer brandId;
	/**
	 *店铺
	 */
	private Integer shopInfoId;
	/**
	 *品牌名称
	 */
	private String brandName;
	/**
	 *店铺名称
	 */
	private String shopName;
	/**
	 *运费
	 */
	private BigDecimal freightPrice;
	/**
	 * 用户id
	 */
	private Integer customerId;
	/**
	 * 预计发货日
	 */
	private String stockUpDate;
	/**
	 * SKU订货号
	 */
	private String sku;
	/**
	 * 商品条形码
	 */
	private String barCode;
	/**
	 * 赠送商城币总额
	 */
	private BigDecimal virtualCoinNumber;
	/**
	 * 赠送商城币时的比例
	 */
	private BigDecimal virtualCoinProportion;
	/**
	 * 使用金币抵扣数量
	 */
	private BigDecimal userCoin;
	/**
	 * 抵扣金额
	 */
	private BigDecimal changeAmount;
	/**
	 * 赠送佣金币
	 */
	private BigDecimal virtualCoin;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**折扣比例**/
	private BigDecimal discount;
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
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}
	public BigDecimal getMemberPrice() {
		return memberPrice;
	}
	public void setMemberPrice(BigDecimal memberPrice) {
		this.memberPrice = memberPrice;
	}
	public Integer getBuyType() {
		return buyType;
	}
	public void setBuyType(Integer buyType) {
		this.buyType = buyType;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public BigDecimal getSalesPrice() {
		return salesPrice;
	}
	public void setSalesPrice(BigDecimal salesPrice) {
		this.salesPrice = salesPrice;
	}
	public Integer getOrderDetailId() {
		return orderDetailId;
	}
	public void setOrderDetailId(Integer orderDetailId) {
		this.orderDetailId = orderDetailId;
	}
	public String getProductFullName() {
		return productFullName;
	}
	public void setProductFullName(String productFullName) {
		this.productFullName = productFullName;
	}
	public BigDecimal getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getLogoImage() {
		return logoImage;
	}
	public void setLogoImage(String logoImage) {
		this.logoImage = logoImage;
	}
	public Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	public Integer getShopInfoId() {
		return shopInfoId;
	}
	public void setShopInfoId(Integer shopInfoId) {
		this.shopInfoId = shopInfoId;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public BigDecimal getFreightPrice() {
		return freightPrice;
	}
	public void setFreightPrice(BigDecimal freightPrice) {
		this.freightPrice = freightPrice;
	}
	public String getStockUpDate() {
		return stockUpDate;
	}
	public void setStockUpDate(String stockUpDate) {
		this.stockUpDate = stockUpDate;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public BigDecimal getVirtualCoinNumber() {
		return virtualCoinNumber;
	}
	public void setVirtualCoinNumber(BigDecimal virtualCoinNumber) {
		this.virtualCoinNumber = virtualCoinNumber;
	}
	public BigDecimal getVirtualCoinProportion() {
		return virtualCoinProportion;
	}
	public void setVirtualCoinProportion(BigDecimal virtualCoinProportion) {
		this.virtualCoinProportion = virtualCoinProportion;
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
	public BigDecimal getVirtualCoin() {
		return virtualCoin;
	}
	public void setVirtualCoin(BigDecimal virtualCoin) {
		this.virtualCoin = virtualCoin;
	}
	public BigDecimal getDiscount() {
		return discount;
	}
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
