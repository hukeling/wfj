package cms.article.pojo;
import util.pojo.BaseEntity;
/**
 * CmsArticle：文章表
 */
public class CmsArticle extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 4464844090251282893L;
	// 文章id
	private Integer articleId;
	// 分类id
	private Integer categoryId;
	// 文章类型
	private String articleType;
	// 文章标题
	private String title;
	// 摘要
	private String brief;
	// 文章内容
	private String content;
	//作者
	private String author;
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
	// 是否删除
	private Short isDeal;
	// 设置精华
	private Short isEssence;
	// 审核状态
	private Short isPass;
	// 是否开放评价
	private Short isOpenDiscuss;
	// 是否显示
	private Short isShow;
	// 创建时间
	private String createTime;
	// 修改时间
	private String updateTime;
	// 发布人
	private String publishUser;
	// 修改人
	private String modifyUser;
	//用户id
	private Integer fpId;
	private String fpName;
	private Integer spId;
	private String spName;
	//积分
	private Integer integral;
	//顶计数
	private Integer topCount;
	//踩计数
	private Integer treadCount;
	public Integer getArticleId() {
		return articleId;
	}
	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
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
	public Short getIsDeal() {
		return isDeal;
	}
	public void setIsDeal(Short isDeal) {
		this.isDeal = isDeal;
	}
	public Short getIsEssence() {
		return isEssence;
	}
	public void setIsEssence(Short isEssence) {
		this.isEssence = isEssence;
	}
	public Short getIsPass() {
		return isPass;
	}
	public void setIsPass(Short isPass) {
		this.isPass = isPass;
	}
	public Short getIsOpenDiscuss() {
		return isOpenDiscuss;
	}
	public void setIsOpenDiscuss(Short isOpenDiscuss) {
		this.isOpenDiscuss = isOpenDiscuss;
	}
	public Short getIsShow() {
		return isShow;
	}
	public void setIsShow(Short isShow) {
		this.isShow = isShow;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getSeoTitle() {
		return seoTitle;
	}
	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
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
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Integer getIntegral() {
		return integral;
	}
	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	public Integer getFpId() {
		return fpId;
	}
	public void setFpId(Integer fpId) {
		this.fpId = fpId;
	}
	public String getFpName() {
		return fpName;
	}
	public void setFpName(String fpName) {
		this.fpName = fpName;
	}
	public Integer getSpId() {
		return spId;
	}
	public void setSpId(Integer spId) {
		this.spId = spId;
	}
	public String getSpName() {
		return spName;
	}
	public void setSpName(String spName) {
		this.spName = spName;
	}
	public Integer getTopCount() {
		return topCount;
	}
	public void setTopCount(Integer topCount) {
		this.topCount = topCount;
	}
	public Integer getTreadCount() {
		return treadCount;
	}
	public void setTreadCount(Integer treadCount) {
		this.treadCount = treadCount;
	}
}