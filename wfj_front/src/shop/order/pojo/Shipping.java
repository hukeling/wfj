package shop.order.pojo;
import java.io.Serializable;
import java.util.Date;

import util.pojo.BaseEntity;
/**
 * 发货详情实体类
 * @author ss
 *
 */
public class Shipping extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 8068395172009003503L;
	/**
	 * 发货ID
	 */
	private Integer shippingId;
	/**
	 * 订单ID
	 */
	private Integer ordersId;
	/**
	 * 发货编号
	 */
	private String shippingSn;
	/**
	 * 物流公司名称
	 */
	private String deliveryCorpName;
	/**
	 * 物流编号
	 */
	private String deliverySn;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 物流公司查询网址
	 */
	private String deliveryUrl;
	/**
	 * 物流公司代码
	 */
	private String code;
	/**
	 * 物流动态
	 */
	private Integer state;
	/**
	 * 快递信息
	 */
	private String expressInfo;
	public String getDeliveryUrl() {
		return deliveryUrl;
	}
	public void setDeliveryUrl(String deliveryUrl) {
		this.deliveryUrl = deliveryUrl;
	}
	public Integer getShippingId() {
		return shippingId;
	}
	public void setShippingId(Integer shippingId) {
		this.shippingId = shippingId;
	}
	public Integer getOrdersId() {
		return ordersId;
	}
	public void setOrdersId(Integer ordersId) {
		this.ordersId = ordersId;
	}
	public String getShippingSn() {
		return shippingSn;
	}
	public void setShippingSn(String shippingSn) {
		this.shippingSn = shippingSn;
	}
	public String getDeliveryCorpName() {
		return deliveryCorpName;
	}
	public void setDeliveryCorpName(String deliveryCorpName) {
		this.deliveryCorpName = deliveryCorpName;
	}
	public String getDeliverySn() {
		return deliverySn;
	}
	public void setDeliverySn(String deliverySn) {
		this.deliverySn = deliverySn;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getExpressInfo() {
		return expressInfo;
	}
	public void setExpressInfo(String expressInfo) {
		this.expressInfo = expressInfo;
	}
}
