package shop.store.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import util.pojo.BaseEntity;
/**
 * 店铺会员申请实体类
 * @author jxw
 * 2014-10-10
 */
@SuppressWarnings("serial")
public class MemberShip extends BaseEntity implements Serializable{
	/**店铺会员ID**/
	private Integer memberShipId;
	/**客户ID**/
	private Integer customerId;
	/**登录名称**/
	private String loginName;
	/**店铺ID**/
	private Integer shopInfoId;
	/**店铺名称**/
	private String shopName;
	/**折扣比例**/
	private BigDecimal discount;
	/**申请时间**/
	private Date createTime;
	/**审核时间**/
	private Date auditTime;
	/**审核状态**/
	private Integer state;//1:待审核;2:通过;3:拒绝
	
	public Integer getMemberShipId() {
		return memberShipId;
	}
	public void setMemberShipId(Integer memberShipId) {
		this.memberShipId = memberShipId;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public Integer getShopInfoId() {
		return shopInfoId;
	}
	public void setShopInfoId(Integer shopInfoId) {
		this.shopInfoId = shopInfoId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public BigDecimal getDiscount() {
		return discount;
	}
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
}
