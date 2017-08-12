package shop.customer.pojo;
import java.io.Serializable;
import java.util.Date;

import util.pojo.BaseEntity;
/**
 * 会员子账号
 * @author 王诗宇
 *
 */
public class Sonaccount extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 2196603766323548376L;
	private Integer sonAccountId;//会员子帐号ID
	private Integer customerId;//所属会员ID
	private String loginName;//登录帐号
	private String password;//登录密码
	private String nickname;//昵称
	private String phone;//手机号
	private String email;//邮箱
	private String photoUrl;//头像地址
	private String registerIp;//注册IP
	private Date registerDate;//注册日期
	private Integer lockedState;//冻结状态
	private Date lockedDate;//帐号冻结日期
	private Integer type;//子帐号类型
	public Integer getSonAccountId() {
		return sonAccountId;
	}
	public void setSonAccountId(Integer sonAccountId) {
		this.sonAccountId = sonAccountId;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
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
}
