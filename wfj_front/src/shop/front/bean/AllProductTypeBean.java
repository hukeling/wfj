package shop.front.bean;
import java.util.List;
import java.util.Map;
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
	/**商品品牌集合**/
	private List<Map<String,Object>> brandList ;
	/**分类等级**/
	private Integer level;
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
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public List<Map<String, Object>> getBrandList() {
		return brandList;
	}
	public void setBrandList(List<Map<String, Object>> brandList) {
		this.brandList = brandList;
	}
}
