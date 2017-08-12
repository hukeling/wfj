package shop.common.pojo;
import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * 商城SEO实体类
 * @author ss
 *
 */
public class ShopSeo extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -2301159595281694657L;
	/**
	 * 商城SEOID
	 */
	private Integer shopSeoId;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 关键字
	 */
	private String keywords;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 类型来源
	 */
	private String categorySource;
	/**
	 * 来源ID
	 */
	private Integer sourceId;
	public Integer getShopSeoId() {
		return shopSeoId;
	}
	public void setShopSeoId(Integer shopSeoId) {
		this.shopSeoId = shopSeoId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCategorySource() {
		return categorySource;
	}
	public void setCategorySource(String categorySource) {
		this.categorySource = categorySource;
	}
	public Integer getSourceId() {
		return sourceId;
	}
	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}
}
