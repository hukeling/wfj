package shop.store.pojo;
import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * ShopBrand - 店铺和品牌关联表
 */
public class ShopBrand extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**	
	 * 店铺和品牌ID
	 */
	private Integer shopBrandId;
	/**
	 * 店铺ID
	 */
	private Integer shopInfoId;
	/**
	 * 品牌ID
	 */
	private Integer brandId;
	public Integer getShopBrandId() {
		return shopBrandId;
	}
	public void setShopBrandId(Integer shopBrandId) {
		this.shopBrandId = shopBrandId;
	}
	public Integer getShopInfoId() {
		return shopInfoId;
	}
	public void setShopInfoId(Integer shopInfoId) {
		this.shopInfoId = shopInfoId;
	}
	public Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
}
