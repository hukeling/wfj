package tang.oldBrand.pojo;
import java.io.Serializable;
import java.util.Date;

import util.pojo.BaseEntity;
/**
 * 老字号实体类
 *
 */
public class OldBrand extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 6496258787066782322L;
	/**
	 * 老字号ID
	 */
	private Integer oldBrandId;
	/**
	 * 老字号名称
	 */
	private String oldBrandName;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 简介
	 */
	private String synopsis;
	/**
	 *老字号联系电话
	 */
	private String oldBrandPhone;
	/**
	 *老字号所在地（省市）id
	 */
	private Integer areaId;
	/**
	 *老字号详细地址
	 */
	private String oldBrandAddress;
	/**
	 * 老字号大图片
	 */
	private String brandBigImageUrl;
	/**
	 * 老字号（logo）
	 */
	private String brandImageUrl;
	/**
	 * 排序号
	 */
	private Integer sortCode;
	/**
	 * 是否显示
	 * 0：不显示
	 * 1：显示
	 */
	private Integer isShow;
	/**
	 * 是否推荐
	 * 0：不推荐
	 * 1：推荐
	 */
	private Integer isRecommend;
	/**
	 * 是否在首页显示
	 * 0：不显示
	 * 1：显示
	 */
	private Integer isHomePage;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 创建人
	 */
	private String createUsr;
	/**
	 * 更新人
	 */
	private String updateUsr;
	
	public Integer getOldBrandId() {
		return oldBrandId;
	}
	public void setOldBrandId(Integer oldBrandId) {
		this.oldBrandId = oldBrandId;
	}
	public String getOldBrandName() {
		return oldBrandName;
	}
	public void setOldBrandName(String oldBrandName) {
		this.oldBrandName = oldBrandName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSynopsis() {
		return synopsis;
	}
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	public String getOldBrandPhone() {
		return oldBrandPhone;
	}
	public void setOldBrandPhone(String oldBrandPhone) {
		this.oldBrandPhone = oldBrandPhone;
	}
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public String getOldBrandAddress() {
		return oldBrandAddress;
	}
	public void setOldBrandAddress(String oldBrandAddress) {
		this.oldBrandAddress = oldBrandAddress;
	}
	public String getBrandBigImageUrl() {
		return brandBigImageUrl;
	}
	public void setBrandBigImageUrl(String brandBigImageUrl) {
		this.brandBigImageUrl = brandBigImageUrl;
	}
	public String getBrandImageUrl() {
		return brandImageUrl;
	}
	public void setBrandImageUrl(String brandImageUrl) {
		this.brandImageUrl = brandImageUrl;
	}
	public Integer getSortCode() {
		return sortCode;
	}
	public void setSortCode(Integer sortCode) {
		this.sortCode = sortCode;
	}
	public Integer getIsShow() {
		return isShow;
	}
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	public Integer getIsRecommend() {
		return isRecommend;
	}
	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
	}
	public Integer getIsHomePage() {
		return isHomePage;
	}
	public void setIsHomePage(Integer isHomePage) {
		this.isHomePage = isHomePage;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getCreateUsr() {
		return createUsr;
	}
	public void setCreateUsr(String createUsr) {
		this.createUsr = createUsr;
	}
	public String getUpdateUsr() {
		return updateUsr;
	}
	public void setUpdateUsr(String updateUsr) {
		this.updateUsr = updateUsr;
	}
	 
	
}
