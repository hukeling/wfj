package shop.prosceniumInstall.pojo;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import shop.product.pojo.ProductType;
import util.pojo.BaseEntity;
/**
 * ProsceniumCategory - 前台中间部分分类实体类
 */
public class ProsceniumCategory extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 前台中间部分分类ID
	 */
	private Integer prosceniumCategoryId;
	/**
	 * 商品一级分类ID
	 */
	private Integer productTypeId;
	/**
	 * 商品二级分类ID
	 */
	private Integer secondProductTypeId;
	/**
	 * 一级分类名称
	 */
	private String productTypeName;
	/**
	 * 二级分类名称
	 */
	private String secondProductTypeName;
	/**
	 * 图片
	 */
	private String prosceniumCategoryUrl;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 简介
	 */
	private String synopsis;
	/**
	 * 链接
	 */
	private String interlinkage;
	/**
	 * 排序
	 */
	private Integer sortCode;
	/**
	 * 是否显示
	 */
	private Integer isShow;
	private List<ProsceniumCategory> secondprosceniumCategoryList;
	private List<Map> brandList;
	private List<ProductType> productTypeList;
	public Integer getProsceniumCategoryId() {
		return prosceniumCategoryId;
	}
	public void setProsceniumCategoryId(Integer prosceniumCategoryId) {
		this.prosceniumCategoryId = prosceniumCategoryId;
	}
	public Integer getProductTypeId() {
		return productTypeId;
	}
	public void setProductTypeId(Integer productTypeId) {
		this.productTypeId = productTypeId;
	}
	public Integer getSecondProductTypeId() {
		return secondProductTypeId;
	}
	public void setSecondProductTypeId(Integer secondProductTypeId) {
		this.secondProductTypeId = secondProductTypeId;
	}
	public String getProsceniumCategoryUrl() {
		return prosceniumCategoryUrl;
	}
	public void setProsceniumCategoryUrl(String prosceniumCategoryUrl) {
		this.prosceniumCategoryUrl = prosceniumCategoryUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSynopsis() {
		return synopsis;
	}
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	public String getInterlinkage() {
		return interlinkage;
	}
	public void setInterlinkage(String interlinkage) {
		this.interlinkage = interlinkage;
	}
	public Integer getSortCode() {
		return sortCode;
	}
	public void setSortCode(Integer sortCode) {
		this.sortCode = sortCode;
	}
	public Integer getIsShow() {
		return isShow;
	}
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	public String getProductTypeName() {
		return productTypeName;
	}
	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}
	public String getSecondProductTypeName() {
		return secondProductTypeName;
	}
	public void setSecondProductTypeName(String secondProductTypeName) {
		this.secondProductTypeName = secondProductTypeName;
	}
	public List<ProsceniumCategory> getSecondprosceniumCategoryList() {
		return secondprosceniumCategoryList;
	}
	public void setSecondprosceniumCategoryList(
			List<ProsceniumCategory> secondprosceniumCategoryList) {
		this.secondprosceniumCategoryList = secondprosceniumCategoryList;
	}
	public List<Map> getBrandList() {
		return brandList;
	}
	public void setBrandList(List<Map> brandList) {
		this.brandList = brandList;
	}
	public List<ProductType> getProductTypeList() {
		return productTypeList;
	}
	public void setProductTypeList(List<ProductType> productTypeList) {
		this.productTypeList = productTypeList;
	}
}
