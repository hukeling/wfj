package basic.pojo;
import java.io.Serializable;

import util.pojo.BaseEntity;
public class ActorPurview extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 4468463205240392308L;
	private Integer actorPurviewId;//角色权限ID
	private Integer actorId;//角色ID
	private Integer purviewId;//权限ID
	/**
	 * 功能权限关系
	 */
	private String functions;
	public Integer getActorPurviewId() {
		return actorPurviewId;
	}
	public void setActorPurviewId(Integer actorPurviewId) {
		this.actorPurviewId = actorPurviewId;
	}
	public Integer getActorId() {
		return actorId;
	}
	public void setActorId(Integer actorId) {
		this.actorId = actorId;
	}
	public Integer getPurviewId() {
		return purviewId;
	}
	public void setPurviewId(Integer purviewId) {
		this.purviewId = purviewId;
	}
	public String getFunctions() {
		return functions;
	}
	public void setFunctions(String functions) {
		this.functions = functions;
	}
}
