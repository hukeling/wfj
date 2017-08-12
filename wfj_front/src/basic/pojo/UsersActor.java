package basic.pojo;
import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * 管理园员色实体类
 * @author ss
 *
 */
public class UsersActor extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -6347133817958631714L;
	private Integer userActorId;//管理员角色ID
	private Integer usersId;//管理员ID
	private Integer actorId;//角色ID
	private String actorName;//角色名称
	public Integer getUserActorId() {
		return userActorId;
	}
	public void setUserActorId(Integer userActorId) {
		this.userActorId = userActorId;
	}
	public Integer getUsersId() {
		return usersId;
	}
	public void setUsersId(Integer usersId) {
		this.usersId = usersId;
	}
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
