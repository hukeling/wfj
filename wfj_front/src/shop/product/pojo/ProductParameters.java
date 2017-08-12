package shop.product.pojo;
import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * Parameter - 商品详细参数类
 * @author 孟琦瑞
 */
public class ProductParameters extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 商品参数ID
	 */
	private Integer productParametersId;
	/**
	 * 商品分类ID
	 */
	private Integer productTypeId;
	/**
	 * 商品参数名称
	 */
	private String name;
	/**
	 * 商品参数详细内容
	 */
	private  String info;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * setter getter 
	 * @return
	 */
	public Integer getProductParametersId() {
		return productParametersId;
	}
	public void setProductParametersId(Integer productParametersId) {
		this.productParametersId = productParametersId;
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
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
}
