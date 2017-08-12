package shop.customer.pojo;
import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * 用户收藏实体类
 * @author ss
 *
 */
public class CustomerCollectShop extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 店铺收藏ID
	 */
	private Integer customerCollectShopId;
	/**
	 * 用户ID
	 */
	private Integer customerId;
	/**
	 * 店铺ID
	 */
	private Integer shopInfoId;
	/**
	 * 店铺名称
	 */
	private String shopName;
	public Integer getCustomerCollectShopId() {
		return customerCollectShopId;
	}
	public void setCustomerCollectShopId(Integer customerCollectShopId) {
		this.customerCollectShopId = customerCollectShopId;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Integer getShopInfoId() {
		return shopInfoId;
	}
	public void setShopInfoId(Integer shopInfoId) {
		this.shopInfoId = shopInfoId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
}