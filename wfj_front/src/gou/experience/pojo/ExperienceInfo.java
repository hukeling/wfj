package gou.experience.pojo;

import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * 体验详情实体
 * @author hkl
 *
 */
public class ExperienceInfo extends BaseEntity implements Serializable {
 
 
	private static final long serialVersionUID = 8813698896893937773L;
	/**
	 * 体验详情Id
	 */
	private Integer experienceInfoId;
	/**
	 * 体验Id
	 */
	private Integer experienceId;
	/**
	 * 排序
	 */
	private Integer sortcode;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 简介
	 */
	private String content;
	/**
	 * 图片
	 */
	private String expImg;
	/**
	 * 是否显示
	 */
	private Integer isShow;
	
	
	public Integer getIsShow() {
		return isShow;
	}
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	public Integer getExperienceInfoId() {
		return experienceInfoId;
	}
	public void setExperienceInfoId(Integer experienceInfoId) {
		this.experienceInfoId = experienceInfoId;
	}
	public Integer getExperienceId() {
		return experienceId;
	}
	public void setExperienceId(Integer experienceId) {
		this.experienceId = experienceId;
	}
	public Integer getSortcode() {
		return sortcode;
	}
	public void setSortcode(Integer sortcode) {
		this.sortcode = sortcode;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getExpImg() {
		return expImg;
	}
	public void setExpImg(String expImg) {
		this.expImg = expImg;
	}
	
}
