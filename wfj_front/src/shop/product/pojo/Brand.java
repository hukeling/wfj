package shop.product.pojo;
import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * 商品品牌实体类
 * @author ss
 *
 */
public class Brand extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 6496258787066782322L;
	/**
	 * 品牌ID
	 */
	private Integer brandId;
	/**
	 * 品牌名称
	 */
	private String brandName;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 简介
	 */
	private String synopsis;
	/**
	 * 品牌大图片
	 */
	private String brandBigImageUrl;
	/**
	 * 品牌图片
	 */
	private String brandImageUrl;
	/**
	 * 排序号
	 */
	private Integer sortCode;
	/**
	 * 是否显示
	 * 0：不显示
	 * 1：显示
	 */
	private Integer isShow;
	/**
	 * 是否推荐
	 * 0：不推荐
	 * 1：推荐
	 */
	private Integer isRecommend;
	/**
	 * 是否在首页显示
	 * 0：不显示
	 * 1：显示
	 */
	private Integer isHomePage;
	public Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getBrandImageUrl() {
		return brandImageUrl;
	}
	public void setBrandImageUrl(String brandImageUrl) {
		this.brandImageUrl = brandImageUrl;
	}
	public Integer getSortCode() {
		return sortCode;
	}
	public void setSortCode(Integer sortCode) {
		this.sortCode = sortCode;
	}
	public Integer getIsRecommend() {
		return isRecommend;
	}
	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
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
	public Integer getIsShow() {
		return isShow;
	}
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	public String getBrandBigImageUrl() {
		return brandBigImageUrl;
	}
	public void setBrandBigImageUrl(String brandBigImageUrl) {
		this.brandBigImageUrl = brandBigImageUrl;
	}
	public Integer getIsHomePage() {
		return isHomePage;
	}
	public void setIsHomePage(Integer isHomePage) {
		this.isHomePage = isHomePage;
	}
}
