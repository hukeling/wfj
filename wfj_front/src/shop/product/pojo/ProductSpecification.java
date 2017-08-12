package shop.product.pojo;
import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * ProductSpecification - 商品规格中间表实体类
 */
public class ProductSpecification extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * ID
	 */
	private Integer prodcutSpecificationId;
	/**
	 * 商品ID
	 */
	private Integer productId;
	/**
	 * 商品规格ID
	 */
	private Integer specificationId;
	public Integer getProdcutSpecificationId() {
		return prodcutSpecificationId;
	}
	public void setProdcutSpecificationId(Integer prodcutSpecificationId) {
		this.prodcutSpecificationId = prodcutSpecificationId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getSpecificationId() {
		return specificationId;
	}
	public void setSpecificationId(Integer specificationId) {
		this.specificationId = specificationId;
	}
}
