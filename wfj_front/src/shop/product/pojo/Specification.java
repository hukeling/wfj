package shop.product.pojo;
import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * Specification - 商品规格实体类
 */
public class Specification extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 商品规格ID
	 */
	private Integer specificationId;
	/**
	 * 商品分类ID
	 */
	private Integer productTypeId;
	/**
	 * 商品规格名称
	 */
	private String name;
	/**
	 * 商品规格备注
	 */
	private String notes;
	/**
	 * 商品规格类型
	 * 1、文本
	   2、图片
	 */
	private Integer type;
	/**
	 * 排序
	 */
	private Integer sort;
	public Integer getSpecificationId() {
		return specificationId;
	}
	public void setSpecificationId(Integer specificationId) {
		this.specificationId = specificationId;
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
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
}	
