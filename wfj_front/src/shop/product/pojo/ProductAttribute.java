package shop.product.pojo;
import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * 商品扩展属性 - 类描述信息
 */
public class ProductAttribute extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 商品属性ID
	 */
	private Integer productAttrId;
	/**
	 * 商品分类ID
	 */
	private Integer productTypeId;
	/**
	 * 商品属性名称
	 */
	private String name;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 是否列表显示  0：不显示，1：显示
	 */
	private Integer isListShow;
	public Integer getProductAttrId() {
		return productAttrId;
	}
	public void setProductAttrId(Integer productAttrId) {
		this.productAttrId = productAttrId;
	}
	public Integer getProductTypeId() {
		return productTypeId;
	}
	public void setProductTypeId(Integer productTypeId) {
		this.productTypeId = productTypeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getIsListShow() {
		return isListShow;
	}
	public void setIsListShow(Integer isListShow) {
		this.isListShow = isListShow;
	}
}
