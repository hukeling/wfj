package tang.impression.pojo;

import util.pojo.BaseEntity;

public class TangHome extends BaseEntity {

	private static final long serialVersionUID = -740026856688060216L;

	private Integer tangHomeModuleId;
	
	private String modulName;
	
	private String modulEngName;
	
	private String image;
	
	private Integer isShow;

	public Integer getTangHomeModuleId() {
		return tangHomeModuleId;
	}

	public void setTangHomeModuleId(Integer tangHomeModuleId) {
		this.tangHomeModuleId = tangHomeModuleId;
	}

	public String getModulName() {
		return modulName;
	}

	public void setModulName(String modulName) {
		this.modulName = modulName;
	}

	public String getModulEngName() {
		return modulEngName;
	}

	public void setModulEngName(String modulEngName) {
		this.modulEngName = modulEngName;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	
	
	
	
}
