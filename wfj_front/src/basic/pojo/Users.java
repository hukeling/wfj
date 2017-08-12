package basic.pojo;
import java.io.Serializable;
import java.util.Date;

import util.pojo.BaseEntity;
/**
 * 后台管理员实体类
 * @author ss
 *
 */
public class Users extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -6740161499400841511L;
	private Integer usersId;//管理员ID
	private String password;//密码
	private String userName;//管理员名称	
	private String trueName;//真是姓名
	private String email;//邮件
	private String phone;//电话
	private String comments;//备注
	private Date registerDate;//注册日期
	private Integer lockState;//是否冻结
	public Integer getUsersId() {
		return usersId;
	}
	public void setUsersId(Integer usersId) {
		this.usersId = usersId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	public Integer getLockState() {
		return lockState;
	}
	public void setLockState(Integer lockState) {
		this.lockState = lockState;
	}
}
