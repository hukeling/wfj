package tang.tangProduct.pojo;
/**
 * 唐智府商品实体类
 */
import java.io.Serializable;

import util.pojo.BaseEntity;

public class TangProduct extends BaseEntity implements Serializable {
 
	private static final long serialVersionUID = 6547638694173362491L;
	/**
	 * 商品Id
	 */
	private Integer tangProductId;
	/**
	 * 商品名
	 */
	private String  productName;
	/**
	 * 售价
	 */
	private Double salesPrice;
	/**
	 * 商品列表用图片
	 */
	private String productImg;
	/**
	 * 是否显示
	 */
	private Integer isShow;
	/**
	 * 是否推荐
	 */
	private Integer isRecommend;
	/**
	 * 商品类别Id
	 */
	private Integer productTypeId;
	/**
	 * 店铺Id
	 */
	private Integer storeId;

	public Integer getTangProductId() {
		return tangProductId;
	}

	public void setTangProductId(Integer tangProductId) {
		this.tangProductId = tangProductId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(Double salesPrice) {
		this.salesPrice = salesPrice;
	}

	public String getProductImg() {
		return productImg;
	}

	public void setProductImg(String productImg) {
		this.productImg = productImg;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public Integer getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
	}

	public Integer getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(Integer productTypeId) {
		this.productTypeId = productTypeId;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
	
	

}
