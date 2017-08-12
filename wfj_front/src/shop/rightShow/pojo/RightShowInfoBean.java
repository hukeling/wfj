package shop.rightShow.pojo;
/**
 * 首页右面展示信息bean
 * @author 张攀攀
 *
 */
public class RightShowInfoBean{
	/**
	 * 展示类别名称
	 */
	private String name;
	/**
	 * 首页右面展示模块信息ID
	 */
	private String rightShowInfoId;
	/**
	 * 首页右面模块分类Id
	 */
	private Integer rightShowTypeId;
	/**
	 * 展示类别ID
	 */
	private String showThingId;
	/**
	 * 标志符号
	 */
	private String flagNo;
	/**
	 * 展示排序
	 */
	private String sort;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRightShowInfoId() {
		return rightShowInfoId;
	}
	public void setRightShowInfoId(String rightShowInfoId) {
		this.rightShowInfoId = rightShowInfoId;
	}
	public Integer getRightShowTypeId() {
		return rightShowTypeId;
	}
	public void setRightShowTypeId(Integer rightShowTypeId) {
		this.rightShowTypeId = rightShowTypeId;
	}
	public String getShowThingId() {
		return showThingId;
	}
	public void setShowThingId(String showThingId) {
		this.showThingId = showThingId;
	}
	public String getFlagNo() {
		return flagNo;
	}
	public void setFlagNo(String flagNo) {
		this.flagNo = flagNo;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
}
