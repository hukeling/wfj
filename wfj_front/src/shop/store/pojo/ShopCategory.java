package shop.store.pojo;
import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * ShopCategory - 商品分类实体类
 */
public class ShopCategory extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 店铺分类ID
	 */
	private Integer shopCategoryId;
	/**
	 * 分类名称
	 */
	private String shopCategoryName;
	/**
	 * 父ID
	 */
	private Integer parentId;
	/**
	 * 排序号
	 */
	private Integer sortCode;
	/**
	 * 加载类型
	 * 1：叶子：显示删除
	   2：非叶子：不显示删除
	   3:有文章：不显示删除
	 */
	private String loadType;
	/**
	 * 所属级别
	 */
	private Integer level;
	public Integer getShopCategoryId() {
		return shopCategoryId;
	}
	public void setShopCategoryId(Integer shopCategoryId) {
		this.shopCategoryId = shopCategoryId;
	}
	public String getShopCategoryName() {
		return shopCategoryName;
	}
	public void setShopCategoryName(String shopCategoryName) {
		this.shopCategoryName = shopCategoryName;
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
