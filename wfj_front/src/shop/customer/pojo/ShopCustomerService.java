package shop.customer.pojo;
import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * 会员—客服关系表entity
 * @author wy
 *
 */
public class ShopCustomerService extends BaseEntity implements Serializable{
	private static final long serialVersionUID = -6195126549799933241L;
	/**会员—客服关系ID（当前表主键）**/
	private Integer ccsId;
	/**店铺管理者ID**/
	private Integer customerId;
	/**客服信息表ID**/
	private Integer customerServiceId;
	public Integer getCcsId() {
		return ccsId;
	}
	public void setCcsId(Integer ccsId) {
		this.ccsId = ccsId;
	}
	public Integer getCustomerServiceId() {
		return customerServiceId;
	}
	public void setCustomerServiceId(Integer customerServiceId) {
		this.customerServiceId = customerServiceId;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	
}
