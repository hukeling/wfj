package basic.pojo;
import java.io.Serializable;

import util.pojo.BaseEntity;
public class Functions extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 359607423087649133L;
	private Integer fid;
	private Integer purviewId;
	private String funName;
	private String funValue;
	public Integer getFid() {
		return fid;
	}
	public void setFid(Integer fid) {
		this.fid = fid;
	}
	public Integer getPurviewId() {
		return purviewId;
	}
	public void setPurviewId(Integer purviewId) {
		this.purviewId = purviewId;
	}
	public String getFunName() {
		return funName;
	}
	public void setFunName(String funName) {
		this.funName = funName;
	}
	public String getFunValue() {
		return funValue;
	}
	public void setFunValue(String funValue) {
		this.funValue = funValue;
	}
}
