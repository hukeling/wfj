package shop.customer.pojo;
import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * 安全认证实体类
 * @author ss
 *
 */
public class SafetyCertificate extends BaseEntity implements Serializable{
	private static final long serialVersionUID = -1113481086287211747L;
	/**
	 * 安全认证ID
	 */
	private Integer safetyCertificateId;
	/**
	 * 用户ID
	 */
	private Integer customerId;
	/**
	 * 密保问题
	 */
	private String securityQuestion;
	/**
	 * 邮箱验证
	 */
	private String checkingEmail;
	/**
	 * 手机验证
	 */
	private String checkingPhone;
	/**
	 * 第三方支付账户
	 */
	private String otherAccount;
	/**
	 * 合作者身份(PID)
	 */
	private String cooperatorPID;
	/**
	 * 密钥(KEY)
	 */
	private String secretkey;
	public Integer getSafetyCertificateId() {
		return safetyCertificateId;
	}
	public void setSafetyCertificateId(Integer safetyCertificateId) {
		this.safetyCertificateId = safetyCertificateId;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getSecurityQuestion() {
		return securityQuestion;
	}
	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}
	public String getCheckingEmail() {
		return checkingEmail;
	}
	public void setCheckingEmail(String checkingEmail) {
		this.checkingEmail = checkingEmail;
	}
	public String getCheckingPhone() {
		return checkingPhone;
	}
	public void setCheckingPhone(String checkingPhone) {
		this.checkingPhone = checkingPhone;
	}
	public String getOtherAccount() {
		return otherAccount;
	}
	public void setOtherAccount(String otherAccount) {
		this.otherAccount = otherAccount;
	}
	public String getCooperatorPID() {
		return cooperatorPID;
	}
	public void setCooperatorPID(String cooperatorPID) {
		this.cooperatorPID = cooperatorPID;
	}
	public String getSecretkey() {
		return secretkey;
	}
	public void setSecretkey(String secretkey) {
		this.secretkey = secretkey;
	}
}
