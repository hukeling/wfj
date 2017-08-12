package shop.shops.pojo;
import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * 商城与店铺关系bean
 * @author 郑月龙
 *
 */
public class ShopsShopinfo extends BaseEntity implements Serializable{
	private static final long serialVersionUID = -7216542924483635768L;
	/**商城与店铺关系ID*/
	private Integer shopsShopinfoId;
	/**商城ID*/
	private Integer shopsId;
	/**店铺ID*/
	private Integer shopInfoId;
	/**店铺分类ID*/
	private Integer shopCategoryId;
	/**州省地区*/
	private String regionLocation;
	/**城市*/
	private String city;
	public Integer getShopsShopinfoId() {
		return shopsShopinfoId;
	}
	public void setShopsShopinfoId(Integer shopsShopinfoId) {
		this.shopsShopinfoId = shopsShopinfoId;
	}
	public Integer getShopsId() {
		return shopsId;
	}
	public void setShopsId(Integer shopsId) {
		this.shopsId = shopsId;
	}
	public Integer getShopInfoId() {
		return shopInfoId;
	}
	public void setShopInfoId(Integer shopInfoId) {
		this.shopInfoId = shopInfoId;
	}
	public Integer getShopCategoryId() {
		return shopCategoryId;
	}
	public void setShopCategoryId(Integer shopCategoryId) {
		this.shopCategoryId = shopCategoryId;
	}
	public String getRegionLocation() {
		return regionLocation;
	}
	public void setRegionLocation(String regionLocation) {
		this.regionLocation = regionLocation;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
}
