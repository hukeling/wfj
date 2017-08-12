package tang.impression.pojo;

import java.util.Date;

import util.pojo.BaseEntity;

public class TangLbt extends BaseEntity {

	private static final long serialVersionUID = 967061839038187890L;

	private Integer broadcastingId;
	
	private String imageUrl;
	
	private String title;
	
	private String synopsis;
	
	private String interlinkage;
	
	private Integer sortCode;
	
	private Integer showLocation;
	
	private Integer isShow;
	
	private Date createTime;
	
	private String publishUser;
	
	private Date updateTime;
	
	private String modifyUser;

	public Integer getBroadcastingId() {
		return broadcastingId;
	}

	public void setBroadcastingId(Integer broadcastingId) {
		this.broadcastingId = broadcastingId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public String getInterlinkage() {
		return interlinkage;
	}

	public void setInterlinkage(String interlinkage) {
		this.interlinkage = interlinkage;
	}

	public Integer getSortCode() {
		return sortCode;
	}

	public void setSortCode(Integer sortCode) {
		this.sortCode = sortCode;
	}

	public Integer getShowLocation() {
		return showLocation;
	}

	public void setShowLocation(Integer showLocation) {
		this.showLocation = showLocation;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getPublishUser() {
		return publishUser;
	}

	public void setPublishUser(String publishUser) {
		this.publishUser = publishUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	
	
	
}
