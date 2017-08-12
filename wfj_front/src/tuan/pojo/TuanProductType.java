package tuan.pojo;

import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * 团购商品分类实体类
 * @author 
 *
 */
public class TuanProductType extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 85976351945939995L;
	/**
	 * 商品分类ID
	 */
	private Integer tuanProductTypeId;
	/**
	 * 父ID
	 */
	private Integer parentId;
	/**
	 * 排序编号
	 */
	private Integer sortCode;
	/**
	 * 名称
	 */
	private String sortName;
	/**
	 * 是否为叶子节点
	 */
	private String loadType;
	
	public Integer getTuanProductTypeId() {
		return tuanProductTypeId;
	}
	public void setTuanProductTypeId(Integer tuanProductTypeId) {
		this.tuanProductTypeId = tuanProductTypeId;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getSortCode() {
		return sortCode;
	}
	public void setSortCode(Integer sortCode) {
		this.sortCode = sortCode;
	}
	public String getSortName() {
		return sortName;
	}
	public void setSortName(String sortName) {
		this.sortName = sortName;
	}
	public String getLoadType() {
		return loadType;
	}
	public void setLoadType(String loadType) {
		this.loadType = loadType;
	}
}
