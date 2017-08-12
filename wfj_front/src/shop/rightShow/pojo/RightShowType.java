package shop.rightShow.pojo;
import util.pojo.BaseEntity;
/**
 * 首页右面展示分类bean
 * @author 张攀攀
 *
 */
@SuppressWarnings("serial")
public class RightShowType extends BaseEntity{
	/**
	 * 首页右面展示模块分类ID
	 */
	private Integer rightShowTypeId;
	/**
	 * 首页右面模块分类名称
	 */
	private String typeName;
	/**
	 * 父ID
	 */
	private Integer parentId;
	/**
	 * 展示类别(商品(1)、品牌(2)、品种(3先不加))
	 */
	private Integer showType;
	public Integer getRightShowTypeId() {
		return rightShowTypeId;
	}
	public void setRightShowTypeId(Integer rightShowTypeId) {
		this.rightShowTypeId = rightShowTypeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Integer getShowType() {
		return showType;
	}
	public void setShowType(Integer showType) {
		this.showType = showType;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
}
