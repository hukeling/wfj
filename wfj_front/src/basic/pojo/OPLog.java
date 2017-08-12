package basic.pojo;
import java.io.Serializable;
import java.util.Date;

import util.pojo.BaseEntity;
/**
 * 操作日志实体类
 * @author 刘青松
 *
 */
public class OPLog extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 6476309157630798208L;
	//管理员ID
	private Integer opId;
	//访问者IP
	private String ip;
	//真实姓名
	private String userTrueName;
	//操作简介
	private String opDesc;
	//操作参数
	private String opParams;
	//操作日期
	private Date opDate;
	public Integer getOpId() {
		return opId;
	}
	public void setOpId(Integer opId) {
		this.opId = opId;
	}
	public String getOpDesc() {
		return opDesc;
	}
	public void setOpDesc(String opDesc) {
		this.opDesc = opDesc;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getUserTrueName() {
		return userTrueName;
	}
	public void setUserTrueName(String userTrueName) {
		this.userTrueName = userTrueName;
	}
	public String getOpParams() {
		return opParams;
	}
	public void setOpParams(String opParams) {
		this.opParams = opParams;
	}
	public Date getOpDate() {
		return opDate;
	}
	public void setOpDate(Date opDate) {
		this.opDate = opDate;
	}
}
