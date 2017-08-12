package shop.front.shoppingOnLine.pojo;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import util.pojo.BaseEntity;
/**
 * ShoppingCart - 购物车实体类
 */
public class ShoppingCart extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 购物车ID
	 */
	private Integer shopCartId;
	/**
	 * 客户ID
	 */
	private Integer customerId;
	/**
	 * 产品ID
	 */
	private Integer productId;
	/**
	 * 店铺ID
	 */
	private Integer shopInfoId;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 数量	
	 */
	private Integer quantity;  
	/**
	 * 预计发货日
	 */
	private String stockUpDate;	
	/**
	 * sku订货号	
	 */
	private String sku;
	/**
	 * 折扣比例	
	 */
	private BigDecimal discount;
	
	public Integer getShopCartId() {
		return shopCartId;
	}
	public void setShopCartId(Integer shopCartId) {
		this.shopCartId = shopCartId;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getShopInfoId() {
		return shopInfoId;
	}
	public void setShopInfoId(Integer shopInfoId) {
		this.shopInfoId = shopInfoId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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
	public BigDecimal getDiscount() {
		return discount;
	}
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
}
