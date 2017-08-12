package shop.common.pojo;
import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * DistrictInfo - 地区信息实体类
 */
public class DistrictInfo extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 地区信息ID
	 */
	private Integer districtInfoId;
	/**
	 * 地区名称
	 */
	private String districtName;
	/**
	 * 排序
	 */
	private Integer sortCode;
	/**
	 * 地区信息父ID
	 */
	private Integer parentId;
	/**
	 * 加载类型
	 */
	private String loadType;
	public Integer getDistrictInfoId() {
		return districtInfoId;
	}
	public void setDistrictInfoId(Integer districtInfoId) {
		this.districtInfoId = districtInfoId;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public Integer getSortCode() {
		return sortCode;
	}
	public void setSortCode(Integer sortCode) {
		this.sortCode = sortCode;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getLoadType() {
		return loadType;
	}
	public void setLoadType(String loadType) {
		this.loadType = loadType;
	}
}
