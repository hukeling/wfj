package shop.information.pojo;
import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * 店铺内部资讯分类
 * @author mqr
 *
 */
public class InformationCategory extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1693235566968142980L;
	/**
	 * ID
	 */
	private Integer categoryId;
	/**
	 * 用户ID
	 */
	private Integer customerId;
	/**
	 * 店铺ID
	 */
	private Integer shopInfoId;
	/**
	 * 分类名称
	 */
	private String shopProCategoryName;
	/**
	 * 排序
	 */
	private Integer sortCode;
	/**
	 * 父ID
	 */
	private Integer parentId;
	/**
	 * 是否显示
	 */
	private Integer isShow;
	/**
	 * 分类图片
	 */
	private String categoryImage;
	/**
	 * 分类描述
	 */
	private String categoryDescription;
	/**
	 * 加载类型
	 */
	private String loadType;
	/**
	 * 分类级别
	 */
	private Integer level;
	//setter getter
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Integer getShopInfoId() {
		return shopInfoId;
	}
	public void setShopInfoId(Integer shopInfoId) {
		this.shopInfoId = shopInfoId;
	}
	public String getShopProCategoryName() {
		return shopProCategoryName;
	}
	public void setShopProCategoryName(String shopProCategoryName) {
		this.shopProCategoryName = shopProCategoryName;
	}
	public Integer getSortCode() {
		return sortCode;
	}
	public void setSortCode(Integer sortCode) {
		this.sortCode = sortCode;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getIsShow() {
		return isShow;
	}
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
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
	public String getLoadType() {
		return loadType;
	}
	public void setLoadType(String loadType) {
		this.loadType = loadType;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
}
