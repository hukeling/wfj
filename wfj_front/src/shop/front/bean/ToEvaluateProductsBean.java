package shop.front.bean;
import java.util.Date;
/**
 * ToEvaluateProductsBean：待评价商品实体bean封装，用于用户中心的待评价商品模块。
 * @author 张攀攀
 *
 */
public class ToEvaluateProductsBean {
	//商品ID
	private Integer productId;
	//商品名称
	private String productName;
	//商品小图片路径
	private String imageUrl;
	//购买时间
	private Date buyTime;
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
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Date getBuyTime() {
		return buyTime;
	}
	public void setBuyTime(Date buyTime) {
		this.buyTime = buyTime;
	}
}
