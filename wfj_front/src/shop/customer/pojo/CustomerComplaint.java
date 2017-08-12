package shop.customer.pojo;
import java.io.Serializable;
import java.util.Date;

import util.pojo.BaseEntity;
/**
 * CustomerComplaint - 客户投诉实体类
 */
public class CustomerComplaint extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 客户等级ID
	 */
	private Integer customerComplaintId;
	/**
	 * 客户ID
	 */
	private Integer customerId;
	/**
	 * 投诉类型
	 * 1：产品相关
	 * 2：价格相关
	 * 3：服务相关
	 * 4：物流相关
	 * 5：售后相关
	 * 6：财务相关
	 * 7：活动相关
	 * 8：网站相关
	 * 9：其它方面
	 */
	private Integer complaintType;
	/**
	 * 订单号
	 */
	private String complaintOrdersNo;
	/**
	 * 投诉内容
	 */
	private String complaintContext;
	/**
	 * 上传图片
	 */
	private String complaintImageUrl;
	/**
	 * 状态
	 * 1：审核中
	 * 2：处理中
	 * 3：已关闭
	 */
	private Integer state;
	/**
	 * 创建时间
	 */
	private Date createTime;
	public Integer getCustomerComplaintId() {
		return customerComplaintId;
	}
	public void setCustomerComplaintId(Integer customerComplaintId) {
		this.customerComplaintId = customerComplaintId;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Integer getComplaintType() {
		return complaintType;
	}
	public void setComplaintType(Integer complaintType) {
		this.complaintType = complaintType;
	}
	public String getComplaintOrdersNo() {
		return complaintOrdersNo;
	}
	public void setComplaintOrdersNo(String complaintOrdersNo) {
		this.complaintOrdersNo = complaintOrdersNo;
	}
	public String getComplaintContext() {
		return complaintContext;
	}
	public void setComplaintContext(String complaintContext) {
		this.complaintContext = complaintContext;
	}
	public String getComplaintImageUrl() {
		return complaintImageUrl;
	}
	public void setComplaintImageUrl(String complaintImageUrl) {
		this.complaintImageUrl = complaintImageUrl;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
