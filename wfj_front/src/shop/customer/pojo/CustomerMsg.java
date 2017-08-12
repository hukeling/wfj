package shop.customer.pojo;
import java.io.Serializable;
import java.util.Date;

import util.pojo.BaseEntity;
/**
 * 用户留言实体类
 * @author ss
 *
 */
public class CustomerMsg extends BaseEntity implements Serializable{
	private static final long serialVersionUID = -5337751726550961752L;
	/**
	 * 用户留言
	 */
	private Integer customerMsgId;
	/**
	 * 用户ID
	 */
	private Integer customerId;
	/**
	 * 留言主题
	 */
	private String title;
	/**
	 * 留言内容
	 */
	private String msg;
	/**
	 * 留言类型
	 */
	private Integer type;
	/**
	 * 留言状态
	 */
	private Integer state;
	/**
	 * 回复
	 */
	private String backMsg;
	/**
	 * 留言时间
	 */
	private Date createTime;
	public Integer getCustomerMsgId() {
		return customerMsgId;
	}
	public void setCustomerMsgId(Integer customerMsgId) {
		this.customerMsgId = customerMsgId;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getBackMsg() {
		return backMsg;
	}
	public void setBackMsg(String backMsg) {
		this.backMsg = backMsg;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
