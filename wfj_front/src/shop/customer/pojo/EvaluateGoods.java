package shop.customer.pojo;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import util.pojo.BaseEntity;
public class EvaluateGoods extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer evaluateId;  //评价ID
	private Integer ordersId;   //订单ID
	private Integer orderDetailId; //订单详情ID
	private Integer productId;  //商品ID
	private String ordersNo;  //订单编号	
	private String productFullName;  //商品全名称
	private BigDecimal salesPrice;  //商品价格(销售价)
	private Integer level;  //评价等级
	private String content;   //评价内容
	private Integer isAnonymous;  //是否匿名评价
	private Date createTime;   //发表评价时间
	private Integer shopInfoId;  //店铺ID
	private String shopName; //店铺名称
	private Integer appraiserId; //评价人ID
	private String appraiserName;  //评价人用户名
	private Integer acceptAppraiserId;  //接受评价人ID
	private String acceptAppraiserName;  //接受评价人用户名
	private Integer evaluateState;  //评价信息状态
	private String remark; //评价处理备注
	private String explain; //解释内容
	private Integer bothState; //互评状态
	private Date showTime;   //评价展示时间
	private Integer evaluateUserType;  //评价用户类型
	/**商品logo**/
	private String logoImg;
	public Integer getEvaluateId() {
		return evaluateId;
	}
	public void setEvaluateId(Integer evaluateId) {
		this.evaluateId = evaluateId;
	}
	public Integer getOrdersId() {
		return ordersId;
	}
	public void setOrdersId(Integer ordersId) {
		this.ordersId = ordersId;
	}
	public Integer getOrderDetailId() {
		return orderDetailId;
	}
	public void setOrderDetailId(Integer orderDetailId) {
		this.orderDetailId = orderDetailId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getOrdersNo() {
		return ordersNo;
	}
	public void setOrdersNo(String ordersNo) {
		this.ordersNo = ordersNo;
	}
	public String getProductFullName() {
		return productFullName;
	}
	public void setProductFullName(String productFullName) {
		this.productFullName = productFullName;
	}
	public BigDecimal getSalesPrice() {
		return salesPrice;
	}
	public void setSalesPrice(BigDecimal salesPrice) {
		this.salesPrice = salesPrice;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getIsAnonymous() {
		return isAnonymous;
	}
	public void setIsAnonymous(Integer isAnonymous) {
		this.isAnonymous = isAnonymous;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getShopInfoId() {
		return shopInfoId;
	}
	public void setShopInfoId(Integer shopInfoId) {
		this.shopInfoId = shopInfoId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public Integer getAppraiserId() {
		return appraiserId;
	}
	public void setAppraiserId(Integer appraiserId) {
		this.appraiserId = appraiserId;
	}
	public String getAppraiserName() {
		return appraiserName;
	}
	public void setAppraiserName(String appraiserName) {
		this.appraiserName = appraiserName;
	}
	public Integer getAcceptAppraiserId() {
		return acceptAppraiserId;
	}
	public void setAcceptAppraiserId(Integer acceptAppraiserId) {
		this.acceptAppraiserId = acceptAppraiserId;
	}
	public String getAcceptAppraiserName() {
		return acceptAppraiserName;
	}
	public void setAcceptAppraiserName(String acceptAppraiserName) {
		this.acceptAppraiserName = acceptAppraiserName;
	}
	public Integer getEvaluateState() {
		return evaluateState;
	}
	public void setEvaluateState(Integer evaluateState) {
		this.evaluateState = evaluateState;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getExplain() {
		return explain;
	}
	public void setExplain(String explain) {
		this.explain = explain;
	}
	public Integer getBothState() {
		return bothState;
	}
	public void setBothState(Integer bothState) {
		this.bothState = bothState;
	}
	public Date getShowTime() {
		return showTime;
	}
	public void setShowTime(Date showTime) {
		this.showTime = showTime;
	}
	public Integer getEvaluateUserType() {
		return evaluateUserType;
	}
	public void setEvaluateUserType(Integer evaluateUserType) {
		this.evaluateUserType = evaluateUserType;
	}
	public String getAnonymityAppraiserName(){
		String name = this.getAppraiserName();
		return name.substring(0,1) +"****"+ name.substring(name.length()-1);
	}
	public String getLogoImg() {
		return logoImg;
	}
	public void setLogoImg(String logoImg) {
		this.logoImg = logoImg;
	}
}
