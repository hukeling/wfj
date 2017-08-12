package shop.front.bean;
import java.util.List;

import shop.product.pojo.ProductType;
/**
 * ProductTypeBean：商品分类实体bean的封装，用于商品分类信息二级页
 * @author 张攀攀
 *
 */
public class ProductTypeBean {
	//父分类ID
	private Integer productTypeId;
	//父分类名称
	private String productTypeName;
	//子类集合（直接是对象集合）
	private List<ProductType> productTypeList;
	public Integer getProductTypeId() {
		return productTypeId;
	}
	public void setProductTypeId(Integer productTypeId) {
		this.productTypeId = productTypeId;
	}
	public String getProductTypeName() {
		return productTypeName;
	}
	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}
	public List<ProductType> getProductTypeList() {
		return productTypeList;
	}
	public void setProductTypeList(List<ProductType> productTypeList) {
		this.productTypeList = productTypeList;
	}
}
