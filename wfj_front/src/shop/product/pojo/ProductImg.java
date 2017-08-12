package shop.product.pojo;
import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * ProductImg - 商品图片类
 * @author 孟琦瑞
 */
@SuppressWarnings("serial")
public class ProductImg extends BaseEntity implements Serializable {
	/**
	 * 主键id
	 */
	private Integer productImageId;
	/**
	 * 商品实体ID
	 */
	private Integer productId;
	/**
	 * 原始图片URL
	 */
	private String source;
	/**
	 * 处理后大的URL
	 */
	private String large;
	/**
	 * 处理后中等的URL
	 */
	private String medium;
	/**
	 * 处理后缩略图URL
	 */
	private String thumbnail;
	/**
	 * 图片排序
	 */
	private Integer orders;
	/**
	 * 图片标题
	 */
	private String title;
	public Integer getProductImageId() {
		return productImageId;
	}
	public void setProductImageId(Integer productImageId) {
		this.productImageId = productImageId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getLarge() {
		return large;
	}
	public void setLarge(String large) {
		this.large = large;
	}
	public String getMedium() {
		return medium;
	}
	public void setMedium(String medium) {
		this.medium = medium;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public Integer getOrders() {
		return orders;
	}
	public void setOrders(Integer orders) {
		this.orders = orders;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
