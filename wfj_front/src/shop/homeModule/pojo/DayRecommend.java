package shop.homeModule.pojo;
import util.pojo.BaseEntity;
/**
 * 前台每日推荐实体类
 * @author 张攀攀
 * 2014-01-03
 *
 */
public class DayRecommend extends BaseEntity{
	private static final long serialVersionUID = -7219260303748323366L;
	/**
	 * 前台每日推荐ID
	 */
	private Integer id;
	/**
	 * 商品ID
	 */
	private Integer productId;
	/**
	* 商品排序
	*/
	private Integer sort;
	/**
	 * 是否显示
	 */
	private Integer isShow;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getIsShow() {
		return isShow;
	}
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
}
