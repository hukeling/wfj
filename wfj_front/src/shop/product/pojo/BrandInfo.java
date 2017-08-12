package shop.product.pojo;

import java.io.Serializable;

import util.pojo.BaseEntity;

public class BrandInfo extends BaseEntity implements Serializable{

	/**
	 * 品牌详情ID
	 */
	private Integer brandInfoId;
	/**
	 * 品牌ID
	 */
	private Integer brandId;
	/**
	 * 品牌故事
	 */
	private String brandStory;
	/**
	 * 品牌所属国家
	 */
	private String brandCountry;
	
	public Integer getBrandInfoId() {
		return brandInfoId;
	}
	public void setBrandInfoId(Integer brandInfoId) {
		this.brandInfoId = brandInfoId;
	}
	public Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	public String getBrandStory() {
		return brandStory;
	}
	public void setBrandStory(String brandStory) {
		this.brandStory = brandStory;
	}
	public String getBrandCountry() {
		return brandCountry;
	}
	public void setBrandCountry(String brandCountry) {
		this.brandCountry = brandCountry;
	}
	@Override
	public String toString() {
		return "BrandInfo [brandInfoId=" + brandInfoId + ", brandId=" + brandId
				+ ", brandStory=" + brandStory + ", brandCountry="
				+ brandCountry + "]";
	}
	
}
