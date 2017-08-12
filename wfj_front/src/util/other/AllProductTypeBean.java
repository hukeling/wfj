package util.other;
import java.util.List;
/**
 * ProductTypeBean：商品分类实体bean的封装，用于所有分类展示
 * @author 张攀攀
 *
 */
public class AllProductTypeBean {
	//父分类ID
	private Integer productTypeId;
	//父分类名称
	private String productTypeName;
	//子类集合（直接是本身对象集合）
	private List<AllProductTypeBean> productTypeList;
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
	public List<AllProductTypeBean> getProductTypeList() {
		return productTypeList;
	}
	public void setProductTypeList(List<AllProductTypeBean> productTypeList) {
		this.productTypeList = productTypeList;
	}
}
