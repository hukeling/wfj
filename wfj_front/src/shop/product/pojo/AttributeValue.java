package shop.product.pojo;
import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * 商品扩展属性值 - 类描述信息
 */
public class AttributeValue extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 商品属性值ID
	 */
	private Integer attrValueId;
	/**
	 * 商品属性ID
	 */
	private Integer productAttrId;
	/**
	 * 属性值名称
	 */
	private String attrValueName;
	/**
	 * 排序
	 */
	private Integer sort;
	
	public Integer getAttrValueId() {
		return attrValueId;
	}
	public void setAttrValueId(Integer attrValueId) {
		this.attrValueId = attrValueId;
	}
	public Integer getProductAttrId() {
		return productAttrId;
	}
	public void setProductAttrId(Integer productAttrId) {
		this.productAttrId = productAttrId;
	}
	public String getAttrValueName() {
		return attrValueName;
	}
	public void setAttrValueName(String attrValueName) {
		this.attrValueName = attrValueName;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
