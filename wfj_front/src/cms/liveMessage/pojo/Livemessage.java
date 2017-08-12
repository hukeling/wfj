package cms.liveMessage.pojo;
import util.pojo.BaseEntity;
/**
 * CmsLivemessage：留言表
 */
public class Livemessage  extends BaseEntity  implements java.io.Serializable {
	private static final long serialVersionUID = -7495496625961770016L;
	//留言id
	private Integer messageId;
	//用户id
	private Integer usersId;
	//用户名
	private String userName;
	//用户真实名称
	private String trueName;
	//电话
	private String phone;
	//邮箱
	private String email;
	//内容
	private String content;
	public Integer getMessageId() {
		return messageId;
	}
	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}