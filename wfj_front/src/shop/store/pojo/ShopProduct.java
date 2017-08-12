package shop.store.pojo;
import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * ShopProduct - 店铺商品类
 * @author 孟琦瑞
 */
public class ShopProduct extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 店铺商品ID
	 */
	private Integer shopProductId;
	/**
	 * 商品ID
	 */
	private Integer productId;
	/**
	 * 店铺ID
	 */
	private Integer shopInfoId;
	/**
	 * 店铺价
	 */
	private double shopPrice;
	/**
	 * 店铺商品分类ID
	 */
	private Integer shopProCategoryId;
	/**
	 * setter getter
	 * @return
	 */
	public Integer getShopProductId() {
		return shopProductId;
	}
	public void setShopProductId(Integer shopProductId) {
		this.shopProductId = shopProductId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getShopInfoId() {
		return shopInfoId;
	}
	public void setShopInfoId(Integer shopInfoId) {
		this.shopInfoId = shopInfoId;
	}
	public double getShopPrice() {
		return shopPrice;
	}
	public void setShopPrice(double shopPrice) {
		this.shopPrice = shopPrice;
	}
	public Integer getShopProCategoryId() {
		return shopProCategoryId;
	}
	public void setShopProCategoryId(Integer shopProCategoryId) {
		this.shopProCategoryId = shopProCategoryId;
	}
}
