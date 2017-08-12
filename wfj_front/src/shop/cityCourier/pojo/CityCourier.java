package shop.cityCourier.pojo;
import java.io.Serializable;
import java.util.Date;

import util.pojo.BaseEntity;
/**
 * 同城快递员实体类
 * @author wy
 *	20141204
 */
public class CityCourier extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 5858059272779467865L;
	private Integer cityCourierId;
	//姓名(账号)
	private String cityCourierName;
	//责任区域
	private String responsibleAreas;
	//联系电话
	private String phone;
	//联系地址
	private String address;
	//身份证号
	private String cardIdNo;
	//入职时间
	private Date entryTime;
	//介绍人
	private String introducer;
	public Integer getCityCourierId() {
		return cityCourierId;
	}
	public void setCityCourierId(Integer cityCourierId) {
		this.cityCourierId = cityCourierId;
	}
	public String getCityCourierName() {
		return cityCourierName;
	}
	public void setCityCourierName(String cityCourierName) {
		this.cityCourierName = cityCourierName;
	}
	public String getResponsibleAreas() {
		return responsibleAreas;
	}
	public void setResponsibleAreas(String responsibleAreas) {
		this.responsibleAreas = responsibleAreas;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCardIdNo() {
		return cardIdNo;
	}
	public void setCardIdNo(String cardIdNo) {
		this.cardIdNo = cardIdNo;
	}
	public Date getEntryTime() {
		return entryTime;
	}
	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}
	public String getIntroducer() {
		return introducer;
	}
	public void setIntroducer(String introducer) {
		this.introducer = introducer;
	}
	
}
