package tuan.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.compass.annotations.Searchable;

import util.pojo.BaseEntity;
/**
 * 团购商品实体类
 * @author 
 *
 */
@Searchable
public class TuanProduct extends BaseEntity implements Serializable{
	private static final long serialVersionUID = -5347076884988138715L;
	/**
	 * 团购ID
	 */
	private Integer tuanId;
	/**
	 * 商品ID
	 */
	private Integer productId;
	/**
	 * 店铺ID
	 */
	private Integer shopInfoId;
	/**
	 * 团购商品分类ID
	 */
	private Integer tuanProductTypeId;
	/**
	 * 团购主图图片
	 */
	private String tuanImageUrl;
	/**
	 * 团购标题
	 */
	private String title;
	/**
	 * 团购价格
	 */
	private BigDecimal price;
	/**
	 * 团购简介
	 */
	private String introduction;
	/**
	 * 商品团购周期
	 */
	private Integer tuanPeriod;
	/**
	 * 申请时间
	 */
	private Date createTime;
	/**
	 * 开启时间
	 */
	private Date beginTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 开团人数
	 */
	private Integer openGroupCount;
	/**
	 * 状态,0:未启用，1：启用，2:关闭，3:结束
	 */
	private Integer state;
	/**
	 * 已购人数
	 */
	private Integer bought;
	/***
	 * 商品二维码
	 */
	private String qrCode;

	public Integer getTuanId() {
		return tuanId;
	}
	public void setTuanId(Integer tuanId) {
		this.tuanId = tuanId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getShopInfoId() {
		return shopInfoId;
	}
	public void setShopInfoId(Integer shopInfoId) {
		this.shopInfoId = shopInfoId;
	}
	public Integer getTuanProductTypeId() {
		return tuanProductTypeId;
	}
	public void setTuanProductTypeId(Integer tuanProductTypeId) {
		this.tuanProductTypeId = tuanProductTypeId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer getOpenGroupCount() {
		return openGroupCount;
	}
	public void setOpenGroupCount(Integer openGroupCount) {
		this.openGroupCount = openGroupCount;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getBought() {
		return bought;
	}
	public void setBought(Integer bought) {
		this.bought = bought;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getTuanImageUrl() {
		return tuanImageUrl;
	}
	public void setTuanImageUrl(String tuanImageUrl) {
		this.tuanImageUrl = tuanImageUrl;
	}
	public Integer getTuanPeriod() {
		return tuanPeriod;
	}
	public void setTuanPeriod(Integer tuanPeriod) {
		this.tuanPeriod = tuanPeriod;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getQrCode() {
		return qrCode;
	}
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
}
