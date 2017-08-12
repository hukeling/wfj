package shop.navigation.pojo;
import util.pojo.BaseEntity;
/**
 * 前台导航条实体类
 * @author 张攀攀
 *
 */
public class NavigationBars extends BaseEntity{
	private static final long serialVersionUID = -7219260303748323366L;
	/**
	 * 导航条ID
	 */
	private Integer navigationBarsId;
	/**
	 * 父ID
	 */
	private Integer parentId;
	/**
	 * 模块名称
	 */
	private String modName;
	/**
	 * 排序位置
	 */
	private Integer sortNo;
	public Integer getNavigationBarsId() {
		return navigationBarsId;
	}
	public void setNavigationBarsId(Integer navigationBarsId) {
		this.navigationBarsId = navigationBarsId;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getModName() {
		return modName;
	}
	public void setModName(String modName) {
		this.modName = modName;
	}
	public Integer getSortNo() {
		return sortNo;
	}
	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}
}
