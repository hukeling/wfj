package shop.returnsApply.pojo;
import java.io.Serializable;
import java.util.Date;

import util.pojo.BaseEntity;
/**
 * 退换申请实体类
 * @author ss
 *
 */
public class ReturnsApply extends BaseEntity implements Serializable{
	private static final long serialVersionUID = -836086083537996763L;
	/**
	 * 退换申请ID
	 */
	private Integer returnsApplyId;
	/**
	 * 退货申请单号
	 */
	private String returnsApplyNo;
	/**
	 * 用户ID
	 */
	private Integer customerId;
	/**
	 * 订单ID
	 */
	private Integer ordersId;
	/**
	 * 店铺ID
	 */
	private Integer shopInfoId;
	/**
	 * 订单号
	 */
	private String ordersNo;
	/**
	 * 商品ID
	 */
	private Integer productId;
	/**
	 * 商品名称
	 */
	private String productName;
	/**
	 * 期望处理方式
	 */
	private String disposeMode;
	/**
	 * 多图片上传
	 */
	private String uploadImage;
	/**
	 * 问题描述
	 */
	private String problemDescription;
	/**
	 * 描述商品是：件，带，盒等等
	 */
	private String productAccessories;
	/**
	 * 收获地址
	 */
	private String shippingAddress;
	/**
	 * 联系人
	 */
	private String linkman;
	/**
	 * 手机号码
	 */
	private String mobilePhone;
	/**
	 * 申请时间
	 */
	private Date applyTime;
	/**
	 * 修改人
	 */
	private String updateMember;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 提交数量
	 */
	private Integer count;
	/**
	 * 审核状态
	 */
	private Integer applyState;
	/**
	 * 退换货完成状态
	 */
	private Integer returnsState;
	/**
	 * 物流公司名称
	 */
	private String deliveryCorpName;
	/**
	 * 物流公司查询网址
	 */
	private String deliveryUrl;
	/**
	 * 物流运单号
	 */
	private String deliverySn;
	/**
	 * 换货物流信息
	 */
	private String message;
	public Integer getShopInfoId() {
		return shopInfoId;
	}
	public void setShopInfoId(Integer shopInfoId) {
		this.shopInfoId = shopInfoId;
	}
	public Integer getReturnsApplyId() {
		return returnsApplyId;
	}
	public void setReturnsApplyId(Integer returnsApplyId) {
		this.returnsApplyId = returnsApplyId;
	}
	public String getUploadImage() {
		return uploadImage;
	}
	public void setUploadImage(String uploadImage) {
		this.uploadImage = uploadImage;
	}
	public String getOrdersNo() {
		return ordersNo;
	}
	public void setOrdersNo(String ordersNo) {
		this.ordersNo = ordersNo;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public Integer getApplyState() {
		return applyState;
	}
	public void setApplyState(Integer applyState) {
		this.applyState = applyState;
	}
	public Integer getReturnsState() {
		return returnsState;
	}
	public void setReturnsState(Integer returnsState) {
		this.returnsState = returnsState;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getDisposeMode() {
		return disposeMode;
	}
	public void setDisposeMode(String disposeMode) {
		this.disposeMode = disposeMode;
	}
	public String getProblemDescription() {
		return problemDescription;
	}
	public void setProblemDescription(String problemDescription) {
		this.problemDescription = problemDescription;
	}
	public String getProductAccessories() {
		return productAccessories;
	}
	public void setProductAccessories(String productAccessories) {
		this.productAccessories = productAccessories;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getUpdateMember() {
		return updateMember;
	}
	public void setUpdateMember(String updateMember) {
		this.updateMember = updateMember;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Integer getOrdersId() {
		return ordersId;
	}
	public void setOrdersId(Integer ordersId) {
		this.ordersId = ordersId;
	}
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getReturnsApplyNo() {
		return returnsApplyNo;
	}
	public void setReturnsApplyNo(String returnsApplyNo) {
		this.returnsApplyNo = returnsApplyNo;
	}
	public String getDeliveryCorpName() {
		return deliveryCorpName;
	}
	public void setDeliveryCorpName(String deliveryCorpName) {
		this.deliveryCorpName = deliveryCorpName;
	}
	public String getDeliveryUrl() {
		return deliveryUrl;
	}
	public void setDeliveryUrl(String deliveryUrl) {
		this.deliveryUrl = deliveryUrl;
	}
	public String getDeliverySn() {
		return deliverySn;
	}
	public void setDeliverySn(String deliverySn) {
		this.deliverySn = deliverySn;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
