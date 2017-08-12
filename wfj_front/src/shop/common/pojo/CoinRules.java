package shop.common.pojo;
import java.io.Serializable;
import java.util.Date;

import util.pojo.BaseEntity;
/**
 * 虚拟金币赠送规则
 * @author mf
 *
 */
public class CoinRules extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1693235566968142980L;
	/**
	 * 虚拟金币赠送规则ID
	 */
	private Integer coinRulesId;
	/**
	 * 值
	 */
	private String value;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 类型名称
	 */
	private String typeName;
	/**
	 * 操作人ID
	 */
	private Integer userId;
	/**
	 * 操作人名称
	 */
	private String userName;
	/**
	 * 操作人ID
	 */
	private Date createTime;
	/**
	 * 操作人ID
	 */
	private Date updateTime;
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Integer getCoinRulesId() {
		return coinRulesId;
	}
	public void setCoinRulesId(Integer coinRulesId) {
		this.coinRulesId = coinRulesId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
