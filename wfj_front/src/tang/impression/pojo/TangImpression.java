package tang.impression.pojo;
/**
 * 印象王府井实体类
 */
import java.io.Serializable;

import util.pojo.BaseEntity;

public class TangImpression extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 5724033992073356877L;
	/**
	 * 印象ID
	 */
	private Integer impressionId;
	/**
	 * 简介文
	 */	 	 
	private String jianjie;
	/**
	 * 简介图文
	 */	
	private String jianjieImage;
	/**
	 * 简介图
	 */	
	private String jianjieImage2;
	/**
	 * 源自文
	 */	
	private String yuanzi;
	/**
	 * 源自图文
	 */	
	private String yuanziImage;
	/**
	 * 源自图
	 */	
	private String yuanziImage2;
	/**
	 * 特点文
	 */	
	private String tedian;
	/**
	 * 特点图文
	 */	
	private String tedianImage;
	/**
	 * 特点图
	 */	
	private String tedianImage2;
	/**
	 * 规划文
	 */	
	private String guihua;
	/**
	 * 规划图文
	 */	
	private String guihuaImage;
	/**
	 * 规划图
	 */	
	private String guihuaImage2;
	/**
	 * 是否显示
	 * @return
	 */
	private Integer isShow;
	
	
	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public Integer getImpressionId() {
		return impressionId;
	}

	public void setImpressionId(Integer impressionId) {
		this.impressionId = impressionId;
	}

	public String getJianjie() {
		return jianjie;
	}

	public void setJianjie(String jianjie) {
		this.jianjie = jianjie;
	}

	public String getJianjieImage() {
		return jianjieImage;
	}

	public void setJianjieImage(String jianjieImage) {
		this.jianjieImage = jianjieImage;
	}

	public String getJianjieImage2() {
		return jianjieImage2;
	}

	public void setJianjieImage2(String jianjieImage2) {
		this.jianjieImage2 = jianjieImage2;
	}

	public String getYuanzi() {
		return yuanzi;
	}

	public void setYuanzi(String yuanzi) {
		this.yuanzi = yuanzi;
	}

	public String getYuanziImage() {
		return yuanziImage;
	}

	public void setYuanziImage(String yuanziImage) {
		this.yuanziImage = yuanziImage;
	}

	public String getYuanziImage2() {
		return yuanziImage2;
	}

	public void setYuanziImage2(String yuanziImage2) {
		this.yuanziImage2 = yuanziImage2;
	}

	public String getTedian() {
		return tedian;
	}

	public void setTedian(String tedian) {
		this.tedian = tedian;
	}

	public String getTedianImage() {
		return tedianImage;
	}

	public void setTedianImage(String tedianImage) {
		this.tedianImage = tedianImage;
	}

	public String getTedianImage2() {
		return tedianImage2;
	}

	public void setTedianImage2(String tedianImage2) {
		this.tedianImage2 = tedianImage2;
	}

	public String getGuihua() {
		return guihua;
	}

	public void setGuihua(String guihua) {
		this.guihua = guihua;
	}

	public String getGuihuaImage() {
		return guihuaImage;
	}

	public void setGuihuaImage(String guihuaImage) {
		this.guihuaImage = guihuaImage;
	}

	public String getGuihuaImage2() {
		return guihuaImage2;
	}

	public void setGuihuaImage2(String guihuaImage2) {
		this.guihuaImage2 = guihuaImage2;
	}
	
	
	
}
