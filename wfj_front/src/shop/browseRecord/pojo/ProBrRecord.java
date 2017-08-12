package shop.browseRecord.pojo;
import java.util.Date;

import util.pojo.BaseEntity;
/**
 * 前台商品浏览记录实体类
 * @author 张攀攀
 *
 */
@SuppressWarnings("serial")
public class ProBrRecord extends BaseEntity{
	/**
	 * 商品浏览记录Id
	 */
	private Integer proBrRecordId;
	/**
	 * 客户ID
	 */
	private Integer customerId;
	/**
	 * 商品ID
	 */
	private Integer productId;
	/**
	 * 最后浏览时间
	 */
	private Date lastBrTime;
	public Integer getProBrRecordId() {
		return proBrRecordId;
	}
	public void setProBrRecordId(Integer proBrRecordId) {
		this.proBrRecordId = proBrRecordId;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Date getLastBrTime() {
		return lastBrTime;
	}
	public void setLastBrTime(Date lastBrTime) {
		this.lastBrTime = lastBrTime;
	}
}
