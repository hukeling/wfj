package basic.pojo;
import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * 数据字典实体类
 * @author ss
 *
 */
public class KeyBook extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1693235566968142980L;
	/**
	 * 数据字典ID
	 */
	private Integer keyBookId;
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
	public Integer getKeyBookId() {
		return keyBookId;
	}
	public void setKeyBookId(Integer keyBookId) {
		this.keyBookId = keyBookId;
	}
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
}
