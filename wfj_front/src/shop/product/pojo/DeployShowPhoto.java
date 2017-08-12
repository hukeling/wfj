package shop.product.pojo;
import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * DeployShowPhoto - 商品图片展示配置
 */
public class DeployShowPhoto extends BaseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**主建id**/
	private Integer deployShowPhotoId;
	/**值**/
	private String value;
	/**名称**/
	private String name;
	/**类型**/
	private String type;
	/**类型名称**/
	private String typeName;
	public Integer getDeployShowPhotoId() {
		return deployShowPhotoId;
	}
	public void setDeployShowPhotoId(Integer deployShowPhotoId) {
		this.deployShowPhotoId = deployShowPhotoId;
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
