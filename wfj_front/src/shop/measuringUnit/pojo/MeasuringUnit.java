package shop.measuringUnit.pojo;
import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * 计量单位实体类
 * @author wangya
 *
 */
public class MeasuringUnit extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1205173770888146468L;
	/**
	 * 计量单位ID
	 */
	private Integer measuringUnitId;
	/**
	 * 计量单位名称
	 */
	private String name;
	/**
	 * 是否显示
	 * 0：不启用
	 * 1：启用
	 */
	private Integer useState;
	
	public Integer getMeasuringUnitId() {
		return measuringUnitId;
	}
	public void setMeasuringUnitId(Integer measuringUnitId) {
		this.measuringUnitId = measuringUnitId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getUseState() {
		return useState;
	}
	public void setUseState(Integer useState) {
		this.useState = useState;
	}
	
}
