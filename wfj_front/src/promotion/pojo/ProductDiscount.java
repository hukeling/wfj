package promotion.pojo;
import java.io.Serializable;
/**
 * 为了满足业务需求封装的商品名称和折扣价的实体类
 * @author ss
 *
 */
public class ProductDiscount implements Serializable{
	private static final long serialVersionUID = -7795621309190054969L;
	/**
	 * 商品折扣ID
	 */
	private Integer productDiscountId;
	/**
	 * 商品ID
	 */
	private Integer productId;
	/**
	 * 商品名称
	 */
	private String productName;
	/**
	 * 商品折扣价
	 */
	private Double discount;
	public Integer getProductDiscountId() {
		return productDiscountId;
	}
	public void setProductDiscountId(Integer productDiscountId) {
		this.productDiscountId = productDiscountId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
}
