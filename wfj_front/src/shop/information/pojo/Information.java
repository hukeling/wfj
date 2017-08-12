package shop.information.pojo;
import util.pojo.BaseEntity;
/**
 * InfoMation	资讯信息
 * @author mqr
 */
public class Information extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 4464844090251282893L;
	// 文章id
	private Integer articleId;
	// 分类id
	private Integer categoryId;
	// 用户id
	private Integer customerId;
	// 店铺id
	private Integer shopInfoId;
	// 文章类型
	private String articleType;
	// 文章标题
	private String title;
	// 摘要
	private String brief;
	// 文章内容
	private String content;
	// 主题图片Url
	private String imgUrl;
	// 主题图片真实名称
	private String imgTrueName;
	// 外部访问地址
	private String outUrl;
	//SEOTITLE
	private String seoTitle;
	// seo关键字
	private String keywords;
	// 点击数
	private Integer clickCount;
	// 排序
	private Integer sortCode;
	// 是否推荐
	private Integer isEssence;
	// 审核状态
	private Integer isPass;
	// 是否显示
	private Integer isShow;
	// 创建时间
	private String createTime;
	// 修改时间
	private String updateTime;
	// 发布人
	private String publishUser;
	// 修改人
	private String modifyUser;
	//setter getter
	public Integer getArticleId() {
		return articleId;
	}
	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Integer getShopInfoId() {
		return shopInfoId;
	}
	public void setShopInfoId(Integer shopInfoId) {
		this.shopInfoId = shopInfoId;
	}
	public String getArticleType() {
		return articleType;
	}
	public void setArticleType(String articleType) {
		this.articleType = articleType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getImgTrueName() {
		return imgTrueName;
	}
	public void setImgTrueName(String imgTrueName) {
		this.imgTrueName = imgTrueName;
	}
	public String getOutUrl() {
		return outUrl;
	}
	public void setOutUrl(String outUrl) {
		this.outUrl = outUrl;
	}
	public String getSeoTitle() {
		return seoTitle;
	}
	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public Integer getClickCount() {
		return clickCount;
	}
	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}
	public Integer getSortCode() {
		return sortCode;
	}
	public void setSortCode(Integer sortCode) {
		this.sortCode = sortCode;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getPublishUser() {
		return publishUser;
	}
	public void setPublishUser(String publishUser) {
		this.publishUser = publishUser;
	}
	public String getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	public Integer getIsEssence() {
		return isEssence;
	}
	public void setIsEssence(Integer isEssence) {
		this.isEssence = isEssence;
	}
	public Integer getIsPass() {
		return isPass;
	}
	public void setIsPass(Integer isPass) {
		this.isPass = isPass;
	}
	public Integer getIsShow() {
		return isShow;
	}
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
}