package shop.rightShow.pojo;
import util.pojo.BaseEntity;
/**
 * 首页右面展示信息bean
 * @author 张攀攀
 *
 */
@SuppressWarnings("serial")
public class RightShowInfo extends BaseEntity{
	/**
	 * 首页右面展示模块信息ID
	 */
	private Integer rightShowInfoId;
	/**
	 * 首页右面模块分类Id
	 */
	private Integer rightShowTypeId;
	/**
	 * 展示类别ID
	 */
	private Integer showThingId;
	/**
	 * 标志符号
	 */
	private Integer flagNo;
	/**
	 * 展示排序
	 */
	private Integer sort;
	public Integer getRightShowInfoId() {
		return rightShowInfoId;
	}
	public void setRightShowInfoId(Integer rightShowInfoId) {
		this.rightShowInfoId = rightShowInfoId;
	}
	public Integer getRightShowTypeId() {
		return rightShowTypeId;
	}
	public void setRightShowTypeId(Integer rightShowTypeId) {
		this.rightShowTypeId = rightShowTypeId;
	}
	public Integer getShowThingId() {
		return showThingId;
	}
	public void setShowThingId(Integer showThingId) {
		this.showThingId = showThingId;
	}
	public Integer getFlagNo() {
		return flagNo;
	}
	public void setFlagNo(Integer flagNo) {
		this.flagNo = flagNo;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
}
