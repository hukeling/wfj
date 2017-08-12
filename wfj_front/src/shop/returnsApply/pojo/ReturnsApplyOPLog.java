package shop.returnsApply.pojo;
import java.io.Serializable;
import java.util.Date;

import util.pojo.BaseEntity;
/**
 * 退换申请操作日志实体类
 * @author ss
 *
 */
public class ReturnsApplyOPLog extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 5705391737678734686L;
	/**
	 * 退换申请操作日志ID
	 */
	private Integer returnsApplyOPLogId;
	/**
	 * 退换申请ID
	 */
	private Integer applyId;
	/**
	 * 店铺ID
	 */
	private Integer shopInfoId;
	/**
	 * 退换申请单号
	 */
	private String returnsApplyNo;
	/**
	 * 操作人登录名
	 */
	private String operatorLoginName;
	/**
	 * 操作人姓名
	 */
	private String operatorName;
	/**
	 * 处理信息
	 */
	private String comments;
	/**
	 * 操作时间
	 */
	private Date operatorTime;
	public Integer getReturnsApplyOPLogId() {
		return returnsApplyOPLogId;
	}
	public void setReturnsApplyOPLogId(Integer returnsApplyOPLogId) {
		this.returnsApplyOPLogId = returnsApplyOPLogId;
	}
	public Integer getApplyId() {
		return applyId;
	}
	public void setApplyId(Integer applyId) {
		this.applyId = applyId;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Date getOperatorTime() {
		return operatorTime;
	}
	public void setOperatorTime(Date operatorTime) {
		this.operatorTime = operatorTime;
	}
	public String getOperatorLoginName() {
		return operatorLoginName;
	}
	public void setOperatorLoginName(String operatorLoginName) {
		this.operatorLoginName = operatorLoginName;
	}
	public Integer getShopInfoId() {
		return shopInfoId;
	}
	public void setShopInfoId(Integer shopInfoId) {
		this.shopInfoId = shopInfoId;
	}
	public String getReturnsApplyNo() {
		return returnsApplyNo;
	}
	public void setReturnsApplyNo(String returnsApplyNo) {
		this.returnsApplyNo = returnsApplyNo;
	}
}
