package shop.front.store.pojo;
import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * ShopProCateClass - 店铺内部商品分类和商品关系
 */
public class ShopProCateClass extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 店铺内部商品分类ID
	 */
	private Integer shopProCategoryId;
	/**
	 * 商品ID
	 */
	private Integer productId;
	public Integer getShopProCategoryId() {
		return shopProCategoryId;
	}
	public void setShopProCategoryId(Integer shopProCategoryId) {
		this.shopProCategoryId = shopProCategoryId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
}
