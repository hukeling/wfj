package gou.experience.pojo;

import java.io.Serializable;
import java.util.List;

import util.pojo.BaseEntity;
/**
 * 体验实体
 * @author hkl
 *
 */
public class Experience extends BaseEntity implements Serializable {
 
	private static final long serialVersionUID = 3794377554336479774L;
	/**
	 * 体验Id
	 */
	private Integer experienceId;
	/**
	 * 体验类型
	 */
	private String experienceType;
	/**
	 * 体验名称
	 */
	private String expName;
	/**
	 * 简介标题 
	 */
	private String synopsisTitle;
	/**
	 * 简介文字内容
	 */
	private String synopsisContent;
	/**
	 * 简介图片
	 */
	private String synopsisImg;
	/**
	 * 列表图
	 */
	private String listTabImg;
	/**
	 * 轮播图1
	 */
	private String lbtImg1;
	/**
	 * 轮播图2
	 */
	private String lbtImg2;
	/**
	 * 轮播图3
	 */
	private String lbtImg3;
	
	/**
	 * 温馨提示
	 */
	private String tip;
	/**
	 * 是否显示
	 */
	private Integer isShow;
	/**
	 * 详情list
	 */
	private List<ExperienceInfo> expInfoList;
	
	
	public List<ExperienceInfo> getExpInfoList() {
		return expInfoList;
	}
	public void setExpInfoList(List<ExperienceInfo> expInfoList) {
		this.expInfoList = expInfoList;
	}
	public Integer getExperienceId() {
		return experienceId;
	}
	public String getListTabImg() {
		return listTabImg;
	}
	public void setListTabImg(String listTabImg) {
		this.listTabImg = listTabImg;
	}
	public String getLbtImg1() {
		return lbtImg1;
	}
	public void setLbtImg1(String lbtImg1) {
		this.lbtImg1 = lbtImg1;
	}
	public String getLbtImg2() {
		return lbtImg2;
	}
	public void setLbtImg2(String lbtImg2) {
		this.lbtImg2 = lbtImg2;
	}
	public String getLbtImg3() {
		return lbtImg3;
	}
	public void setLbtImg3(String lbtImg3) {
		this.lbtImg3 = lbtImg3;
	}
	public Integer getIsShow() {
		return isShow;
	}
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	public void setExperienceId(Integer experienceId) {
		this.experienceId = experienceId;
	}
	public String getExperienceType() {
		return experienceType;
	}
	public void setExperienceType(String experienceType) {
		this.experienceType = experienceType;
	}
	public String getExpName() {
		return expName;
	}
	public void setExpName(String expName) {
		this.expName = expName;
	}
	public String getSynopsisTitle() {
		return synopsisTitle;
	}
	public void setSynopsisTitle(String synopsisTitle) {
		this.synopsisTitle = synopsisTitle;
	}
	public String getSynopsisContent() {
		return synopsisContent;
	}
	public void setSynopsisContent(String synopsisContent) {
		this.synopsisContent = synopsisContent;
	}
	public String getSynopsisImg() {
		return synopsisImg;
	}
	public void setSynopsisImg(String synopsisImg) {
		this.synopsisImg = synopsisImg;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	
	
}
