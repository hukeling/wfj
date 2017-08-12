package phone.pojo;

import java.math.BigDecimal;

public class CollectProductInfo {
	/**
	 * 商品ID
	 */
	private Integer productId;
	
	private String LogoUrl;
	/**
	 * 商品名称
	 */
	private String productName;
	/**
	 * 销售价
	 */
	private BigDecimal salesPrice;
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getLogoUrl() {
		return LogoUrl;
	}
	public void setLogoUrl(String logoUrl) {
		LogoUrl = logoUrl;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public BigDecimal getSalesPrice() {
		return salesPrice;
	}
	public void setSalesPrice(BigDecimal salesPrice) {
		this.salesPrice = salesPrice;
	}
	
	
}
