package shop.product.pojo;
/**
 * 继承自Brand；多了品牌故事和品牌所在国家
 * @author hkl
 *
 */
public class Brand2 extends Brand {
	private String brandStory;
	
	private String brandCountry;

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
	

}
