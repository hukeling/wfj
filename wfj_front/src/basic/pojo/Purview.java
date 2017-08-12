package basic.pojo;
import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * 权限实体类
 * @author ss
 *
 */
public class Purview extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -56813026990564922L;
	private Integer purviewId;//权限ID
	private String purviewName;//权限名称
	private Integer parentId;//父ID
	private String url;//路径
	/**排序**/
	private Integer sortCode;
	/**是否为叶子节点0是，1不是**/
	private Integer isLeaf;
	public Integer getPurviewId() {
		return purviewId;
	}
	public void setPurviewId(Integer purviewId) {
		this.purviewId = purviewId;
	}
	public String getPurviewName() {
		return purviewName;
	}
	public void setPurviewName(String purviewName) {
		this.purviewName = purviewName;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getSortCode() {
		return sortCode;
	}
	public void setSortCode(Integer sortCode) {
		this.sortCode = sortCode;
	}
	public Integer getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}
}
