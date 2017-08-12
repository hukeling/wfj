package shop.product.pojo;
/**
 * ParamGroupInfo - 详细参数组
 */
public class ParamGroupInfo {
	/**
	 * 参数组id
	 */
	private Integer pgiId;
	/***
	 * 参数组名称
	 */
	private String name;
	/**
	 * 参数组排序
	 */
	private Integer order;
	/**
	 * 参数组中的参数
	 */
	private String[] value;
	/***
	 * 当前参数
	 */
	private Integer pgInfoId;
	public Integer getPgInfoId() {
		return pgInfoId;
	}
	public void setPgInfoId(Integer pgInfoId) {
		this.pgInfoId = pgInfoId;
	}
	public Integer getPgiId() {
		return pgiId;
	}
	public void setPgiId(Integer pgiId) {
		this.pgiId = pgiId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public String[] getValue() {
		return value;
	}
	public void setValue(String[] value) {
		this.value = value;
	}
}
