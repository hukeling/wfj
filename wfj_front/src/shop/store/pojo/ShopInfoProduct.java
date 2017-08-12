package shop.store.pojo;
import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * ShopInfoProduct - 为了满足业务需求封装的店铺商品信息实体类
 * @author 孟琦瑞
 */
public class ShopInfoProduct extends BaseEntity implements Serializable  {
	private static final long serialVersionUID = 1L;
	/**
	 * 店铺商品信息ID
	 */
	private Integer shopInfoProductId;
	/**
	 * 商品ID
	 */
	private Integer productId;
	/**
	 * 商品名称
	 */
	private String productName;
	/**
	 * 店铺价钱
	 */
	private double shopPrice;
	/**
	 * 店铺分类ID
	 */
	private double shopProCategoryId;
	/**
	 * setter getter
	 * @return
	 */
	public Integer getShopInfoProductId() {
		return shopInfoProductId;
	}
	public void setShopInfoProductId(Integer shopInfoProductId) {
		this.shopInfoProductId = shopInfoProductId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public double getShopPrice() {
		return shopPrice;
	}
	public void setShopPrice(double shopPrice) {
		this.shopPrice = shopPrice;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public double getShopProCategoryId() {
		return shopProCategoryId;
	}
	public void setShopProCategoryId(double shopProCategoryId) {
		this.shopProCategoryId = shopProCategoryId;
	}
}
