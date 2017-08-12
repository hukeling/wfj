package shop.homeIndex.pojo;
import util.pojo.BaseEntity;
/**
 * 首页中间分类
 * @author mqr
 * 2014-10-15
 *
 */
public class ShopHomeMiddleCategory extends BaseEntity{
	private static final long serialVersionUID = 1L;
	/**
	 * 分类ID
	 */
	private Integer categoryId;
	/**
	 * 分类名称
	 */
	private String categoryName;
	/**
	 * 分类描述
	 */
	private String categoryDescription;
	/**
	 * 分类图片
	 */
	private String categoryImage;
	/**
	 * 父分类ID
	 */
	private Integer parentId;
	/**
	 * 是否为叶子
	 * 0为叶子  1非叶子
	 */
	private Integer isLeaf;
	/**
	 * 分类级别
	 */
	private Integer level;
	/**
	 * 排序
	 */
	private Integer sortCode;
	/**
	 * SEO关键字
	 */
	private String keywords;
	/**
	 * 是否显示
	 */
	private Integer isShow;
	/**
	 * 连接
	 * @return
	 */
	private String link;
	//setter getter
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCategoryDescription() {
		return categoryDescription;
	}
	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}
	public String getCategoryImage() {
		return categoryImage;
	}
	public void setCategoryImage(String categoryImage) {
		this.categoryImage = categoryImage;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getSortCode() {
		return sortCode;
	}
	public void setSortCode(Integer sortCode) {
		this.sortCode = sortCode;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public Integer getIsShow() {
		return isShow;
	}
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
}
