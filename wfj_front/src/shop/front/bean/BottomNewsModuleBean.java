package shop.front.bean;
import java.util.List;
/**
 * SHOPJSP 底部新闻信息实体bean封装
 */
public class BottomNewsModuleBean {
	/**
	 * 信息分类ID
	 */
	private Integer newsTypeId;
	/**
	 * 信息分类名称
	 */
	private String newsTypeName;
	/**
	 * 信息内容列表
	 */
	private List<?> newsContentList;
	public String getNewsTypeName() {
		return newsTypeName;
	}
	public void setNewsTypeName(String newsTypeName) {
		this.newsTypeName = newsTypeName;
	}
	public List<?> getNewsContentList() {
		return newsContentList;
	}
	public void setNewsContentList(List<?> newsContentList) {
		this.newsContentList = newsContentList;
	}
	public Integer getNewsTypeId() {
		return newsTypeId;
	}
	public void setNewsTypeId(Integer newsTypeId) {
		this.newsTypeId = newsTypeId;
	}
}
