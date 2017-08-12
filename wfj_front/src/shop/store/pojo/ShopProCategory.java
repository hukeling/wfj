package shop.store.pojo;
import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * ShopProCategory - 店铺内部商品分类实体类
 */
public class ShopProCategory extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 店铺商品分类ID
	 */
	private Integer shopProCategoryId;
	/**
	 * 分类名称
	 */
	private String shopProCategoryName;
	/**
	 * 父ID
	 */
	private Integer parentId;
	/**
	 * 排序号
	 */
	private Integer sortCode;
	/**
	 * 是否显示
	 */
	private Integer isShow;
	/**
	 * 加载类型
	 */
	private String loadType;
	/**
	 * 所属级别
	 */
	private Integer level;
	/**
	 * 图片
	 */
	private String categoryImage;
	/**
	 * 描述
	 */
	private String  categoryDescription;
	/**
	 * 店铺Id
	 */
	private Integer shopInfoId;
	public Integer getShopProCategoryId() {
		return shopProCategoryId;
	}
	public void setShopProCategoryId(Integer shopProCategoryId) {
		this.shopProCategoryId = shopProCategoryId;
	}
	public String getShopProCategoryName() {
		return shopProCategoryName;
	}
	public void setShopProCategoryName(String shopProCategoryName) {
		this.shopProCategoryName = shopProCategoryName;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
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
	public String getLoadType() {
		return loadType;
	}
	public void setLoadType(String loadType) {
		this.loadType = loadType;
	}
	public Integer getShopInfoId() {
		return shopInfoId;
	}
	public void setShopInfoId(Integer shopInfoId) {
		this.shopInfoId = shopInfoId;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getCategoryImage() {
		return categoryImage;
	}
	public void setCategoryImage(String categoryImage) {
		this.categoryImage = categoryImage;
	}
	public String getCategoryDescription() {
		return categoryDescription;
	}
	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}
}
