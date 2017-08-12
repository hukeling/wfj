package shop.front.bean;
import shop.product.pojo.ProductInfo;
/**
 * RelatedProductBean:商品详情页的相关商品封装的实体bean
 * @author 张攀攀
 *
 */
public class RelatedProductBean {
	//商品对象
	private ProductInfo productInfo;
	//此商品的浏览品论次数
	private Integer count;
	public ProductInfo getProductInfo() {
		return productInfo;
	}
	public void setProductInfo(ProductInfo productInfo) {
		this.productInfo = productInfo;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
}
