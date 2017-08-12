package promotion.pojo;
import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * 商品折扣实体类
 * @author ss
 *
 */
public class SalesPromotionDiscount extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 3083290209198964836L;
	/**
	 * 商品折扣ID
	 */
	private Integer disproductId;
	/**
	 * 促销活动ID
	 */
	private Integer promotionId;
	/**
	 * 促销活动编号
	 */
	private String promotionIdNumber;
	/**
	 * 折扣率
	 */
	private Double discount;
	public Integer getDisproductId() {
		return disproductId;
	}
	public void setDisproductId(Integer disproductId) {
		this.disproductId = disproductId;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public Integer getPromotionId() {
		return promotionId;
	}
	public void setPromotionId(Integer promotionId) {
		this.promotionId = promotionId;
	}
	public String getPromotionIdNumber() {
		return promotionIdNumber;
	}
	public void setPromotionIdNumber(String promotionIdNumber) {
		this.promotionIdNumber = promotionIdNumber;
	}
}
