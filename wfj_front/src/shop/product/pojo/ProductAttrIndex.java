package shop.product.pojo;
import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * 商品扩展属性值 - 类描述信息
 */
public class ProductAttrIndex extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 6317442983038841006L;
	private Integer attributeIndexId;
	private Integer productAttrId;
	private Integer attrValueId;
	private Integer productId;
	private Integer productTypeId;
	public Integer getAttributeIndexId() {
		return attributeIndexId;
	}
	public void setAttributeIndexId(Integer attributeIndexId) {
		this.attributeIndexId = attributeIndexId;
	}
	public Integer getProductAttrId() {
		return productAttrId;
	}
	public void setProductAttrId(Integer productAttrId) {
		this.productAttrId = productAttrId;
	}
	public Integer getAttrValueId() {
		return attrValueId;
	}
	public void setAttrValueId(Integer attrValueId) {
		this.attrValueId = attrValueId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getProductTypeId() {
		return productTypeId;
	}
	public void setProductTypeId(Integer productTypeId) {
		this.productTypeId = productTypeId;
	}
	
}
