package shop.shops.pojo;
import java.io.Serializable;
import java.util.Date;

import util.pojo.BaseEntity;
/**
 * 商城bean(线下实际商城)
 * @author 郑月龙
 *
 */
public class Shops extends BaseEntity implements Serializable{
	private static final long serialVersionUID = -3499562836901347771L;
	/**商城ID*/
	private Integer shopsId;
	/**商城编号*/
	private String shopsCode;
	/**商城名称*/
	private String shopsName;
	/**州省地区*/
	private String regionLocation;
	/**城市*/
	private String city;
	/**商城所属的地区名称（不是全名称）*/
	private String address;
	/**商城备注*/
	private String note;
	/**创建时间*/
	private Date createTime;
	/**修改时间*/
	private Date modifyTime;
	public Integer getShopsId() {
		return shopsId;
	}
	public void setShopsId(Integer shopsId) {
		this.shopsId = shopsId;
	}
	public String getShopsCode() {
		return shopsCode;
	}
	public void setShopsCode(String shopsCode) {
		this.shopsCode = shopsCode;
	}
	public String getShopsName() {
		return shopsName;
	}
	public void setShopsName(String shopsName) {
		this.shopsName = shopsName;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getRegionLocation() {
		return regionLocation;
	}
	public void setRegionLocation(String regionLocation) {
		this.regionLocation = regionLocation;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
