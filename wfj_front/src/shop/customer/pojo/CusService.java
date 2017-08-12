package shop.customer.pojo;
import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * 客服表entity
 * @author wy
 *
 */
public class CusService extends BaseEntity implements Serializable{
	private static final long serialVersionUID = -7785578611387865715L;
	/**客服ID（当前表主键）**/
	private Integer customerServiceId;
	/**真实姓名**/
	private String trueName;
	/**昵称**/
	private String nikeName;
	/**qq**/
	private String qq;
	/**使用状态0：不显示（废弃） 1：显示（正常使用）**/
	private Integer useState;
	/**照片Url**/
	private String photoUrl;
	/**手机号**/
	private String mobile;
	/**电话号**/
	private String phone;
	/**工作号**/
	private String workNumber;
	public Integer getCustomerServiceId() {
		return customerServiceId;
	}
	public void setCustomerServiceId(Integer customerServiceId) {
		this.customerServiceId = customerServiceId;
	}
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public String getNikeName() {
		return nikeName;
	}
	public void setNikeName(String nikeName) {
		this.nikeName = nikeName;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public Integer getUseState() {
		return useState;
	}
	public void setUseState(Integer useState) {
		this.useState = useState;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getWorkNumber() {
		return workNumber;
	}
	public void setWorkNumber(String workNumber) {
		this.workNumber = workNumber;
	}
}
