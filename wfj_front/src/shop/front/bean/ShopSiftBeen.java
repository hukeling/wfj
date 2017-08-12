package shop.front.bean;
import java.util.List;

import promotion.pojo.Promotion;
import shop.product.pojo.ProductInfo;
/**
 * ShopSiftBeen -- 商城精选实体been的封装
 * @author Administrotar
 *
 */
public class ShopSiftBeen {
	/**商品分类ID**/
	private String typeName;
	/**主促销专题**/
	private Promotion mainPromotion;
	/**次促销专题**/
	private Promotion secPromotion;
	/**商品集合**/
	private List<ProductInfo> productInfoList;
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Promotion getMainPromotion() {
		return mainPromotion;
	}
	public void setMainPromotion(Promotion mainPromotion) {
		this.mainPromotion = mainPromotion;
	}
	public Promotion getSecPromotion() {
		return secPromotion;
	}
	public void setSecPromotion(Promotion secPromotion) {
		this.secPromotion = secPromotion;
	}
	public List<ProductInfo> getProductInfoList() {
		return productInfoList;
	}
	public void setProductInfoList(List<ProductInfo> productInfoList) {
		this.productInfoList = productInfoList;
	}	
}
