package shop.customer.pojo;
import java.io.Serializable;
import java.util.Date;

import util.pojo.BaseEntity;
/**
 * 会员信息entity
 * @author mqm
 *
 */
public class Customer extends BaseEntity implements Serializable{
	private static final long serialVersionUID = -6718838800112233445L;
	/**客户id**/
	private Integer customerId;
	/**登录账号**/
	private String loginName;
	/**登录密码**/
	private String password;
	/**昵称**/
	private String nickName;
	/**手机号**/
	private String phone;
	/**邮箱**/
	private String email;
	/**头像地址**/
	private String photoUrl;
	/**注册ip**/
	private String registerIp;
	/**注册日期**/
	private Date registerDate;
	/**冻结状态**/
	private Integer lockedState;
	/**账号冻结日期**/
	private Date lockedDate;
	/**会员类型**/
	private Integer type;
	/**分享类型**/
	private Integer shareType;
	/**是否可以采购**/
	private Integer isCanBuy;
	//setter getter
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public String getRegisterIp() {
		return registerIp;
	}
	public void setRegisterIp(String registerIp) {
		this.registerIp = registerIp;
	}
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	public Integer getLockedState() {
		return lockedState;
	}
	public void setLockedState(Integer lockedState) {
		this.lockedState = lockedState;
	}
	public Date getLockedDate() {
		return lockedDate;
	}
	public void setLockedDate(Date lockedDate) {
		this.lockedDate = lockedDate;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getIsCanBuy() {
		return isCanBuy;
	}
	public void setIsCanBuy(Integer isCanBuy) {
		this.isCanBuy = isCanBuy;
	}
	public Integer getShareType() {
		return shareType;
	}
	public void setShareType(Integer shareType) {
		this.shareType = shareType;
	}
}
