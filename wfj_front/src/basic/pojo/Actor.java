package basic.pojo;
import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * 角色实体类
 * @author ss
 *
 */
public class Actor extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 5787981031195767272L;
	private Integer actorId;//角色ID
	private String actorName;//角色名称
	public Integer getActorId() {
		return actorId;
	}
	public void setActorId(Integer actorId) {
		this.actorId = actorId;
	}
	public String getActorName() {
		return actorName;
	}
	public void setActorName(String actorName) {
		this.actorName = actorName;
	}
}
