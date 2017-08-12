package shop.product.pojo;
import java.io.Serializable;
import java.util.Date;

import util.pojo.BaseEntity;
/**
 * 积分兑换实体类
 * @author ss
 *
 */
public class IntegralexChange extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 288258517688348204L;
	/**
	 * 积分兑换ID
	 */
	private Integer integralId;
	/**
	 * 商品实体ID
	 */
	private Integer productId;
	/**
	 * 积分兑换
	 */
	private Integer pointExchange;
	/**
	 * 兑换人数上限
	 */
	private Integer maxUsers;
	/**
	 * 可兑换商品数量
	 */
	private Integer maxProducts;
	/**
	 * 允许兑换的会员等级
	 */
	private Integer chooseVIP;
	/**
	 * 开启时间
	 */
	private Date beginTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 状态
	 */
	private Integer state;
	public Integer getIntegralId() {
		return integralId;
	}
	public void setIntegralId(Integer integralId) {
		this.integralId = integralId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getPointExchange() {
		return pointExchange;
	}
	public void setPointExchange(Integer pointExchange) {
		this.pointExchange = pointExchange;
	}
	public Integer getMaxUsers() {
		return maxUsers;
	}
	public void setMaxUsers(Integer maxUsers) {
		this.maxUsers = maxUsers;
	}
	public Integer getMaxProducts() {
		return maxProducts;
	}
	public void setMaxProducts(Integer maxProducts) {
		this.maxProducts = maxProducts;
	}
	public Integer getChooseVIP() {
		return chooseVIP;
	}
	public void setChooseVIP(Integer chooseVIP) {
		this.chooseVIP = chooseVIP;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
}
