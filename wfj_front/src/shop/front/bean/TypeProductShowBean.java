package shop.front.bean;
import java.util.List;

import shop.product.pojo.ProductInfo;
/**
 * TypeProductShowBean：此实体bean用于通过首页商品分类点击进入到的页面（子分类商品信息页），用于模块的展示（二级页面）
 * @author 张攀攀
 *
 */
public class TypeProductShowBean {
	//商品分类ID
	private Integer productTypeId;
	//商品分类名称
	private String productTypeName;
	//商品列表集合
	private List<ProductInfo> productInfoList;
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
	public List<ProductInfo> getProductInfoList() {
		return productInfoList;
	}
	public void setProductInfoList(List<ProductInfo> productInfoList) {
		this.productInfoList = productInfoList;
	}
}
