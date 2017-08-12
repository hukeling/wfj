package shop.front.bean;
import java.io.Serializable;

import shop.product.pojo.ProductInfo;
/**
 * 购物车商品信息封装类
 * @author 张攀攀
 *
 */
public class GoodsInfo implements Serializable{
	private static final long serialVersionUID = 3994426440551610097L;
	/**
	 * 商品数量
	 */
	private Integer goodsNumber;
	/**
	 * 商品信息
	 */
	private ProductInfo productInfo;
	/**
	 * 商品总金额
	 */
	private Double totalMoney;
	/**
	 * 商品的来源类型（1.商城，2.团购，3.零元购）
	 */
	private String comeFromType;
	public Integer getGoodsNumber() {
		return goodsNumber;
	}
	public void setGoodsNumber(Integer goodsNumber) {
		this.goodsNumber = goodsNumber;
	}
	public ProductInfo getProductInfo() {
		return productInfo;
	}
	public void setProductInfo(ProductInfo productInfo) {
		this.productInfo = productInfo;
	}
	public Double getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}
	public String getComeFromType() {
		return comeFromType;
	}
	public void setComeFromType(String comeFromType) {
		this.comeFromType = comeFromType;
	}
}