package shop.customer.pojo;
import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * 用户收藏实体类
 * @author ss
 *
 */
public class CustomerCollectProduct extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 3048950500190000203L;
	/**
	 * 用户收藏ID
	 */
	private Integer customerCollectProductId;
	/**
	 * 用户ID
	 */
	private Integer customerId;
	/**
	 * 商品ID
	 */
	private Integer productId;
	/**
	 * 商品名称
	 */
	private String productName;
	public Integer getCustomerCollectProductId() {
		return customerCollectProductId;
	}
	public void setCustomerCollectProductId(Integer customerCollectProductId) {
		this.customerCollectProductId = customerCollectProductId;
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
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
}
