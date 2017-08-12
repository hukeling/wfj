package shop.store.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.compass.annotations.Searchable;

import shop.product.pojo.ProductInfo;
import util.pojo.BaseEntity;

/**
 * ShopInfo - 店铺信息实体类
 * 
 * @Author §j*fm§
 */
@Searchable(root = false)
public class ShopInfo extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -1097202271434870301L;
	/**
	 * 店铺ID
	 */
	private Integer shopInfoId;
	/**
	 * 会员ID
	 */
	private Integer customerId;
	/**
	 * 会员名称
	 */
	private String customerName;
	/**
	 * 店铺名称
	 */
	private String shopName;
	/**
	 * 店铺分类ID
	 */
	private Integer shopCategoryId;
	/**
	 * 经营类型
	 */
	private Integer businessType;
	/**
	 * 州省地区
	 */
	private String regionLocation;
	/**
	 * 城市
	 */
	private String city;
	/**
	 * 国家
	 */
	private String areaCounty;
	/**
	 * 详细地址(街道)
	 */
	private String address;
	/**
	 * 邮政编码
	 */
	private String postCode;
	/**
	 * 主要销售产品
	 */
	private String mainProduct;
	/**
	 * 公司注册年份
	 */
	private String companyRegistered;
	/**
	 * 公司法人
	 */
	private String legalOwner;
	/**
	 * 公司认证
	 */
	private String companyCertification;
	/**
	 * 电子邮箱
	 */
	private String email;
	/**
	 * 联系电话
	 */
	private String phone;
	/**
	 * 营业时间开始
	 */
	private String businessHoursStart;
	/**
	 * 营业时间结束
	 */
	private String businessHoursEnd;
	/**
	 * 身份证
	 */
	private String IDCards;
	/**
	 * 身份证照片
	 */
	private String IDCardsImage;
	/**
	 * 公司证件URL
	 */
	private String companyDocuments;
	/**
	 * 税务登记证URL
	 */
	private String taxRegistrationDocuments;
	/**
	 * 营业执照URL
	 */
	private String businessLicense;
	/**
	 * 售卖品牌名称
	 */
	private String marketBrand;
	/**
	 * 售卖品牌图片
	 */
	private String marketBrandUrl;
	/**
	 * 申请时间
	 */
	private Date applyTime;
	/**
	 * 店铺是否通过:1、待审核,2、不通过,3、通过
	 */
	private Integer isPass;
	/**
	 * 店铺通过时间
	 */
	private Date passTime;
	/**
	 * 店铺审核人
	 */
	private String verifier;
	/**
	 * 是否关闭0：不关闭1：关闭
	 */
	private Integer isClose;
	/**
	 * 店铺LOGO
	 */
	private String logoUrl;
	/**
	 * 店铺条幅BANNER
	 */
	private String bannerUrl;
	/**
	 * 店铺标签TAG
	 */
	private String tag;
	/**
	 * 店铺简介
	 */
	private String synopsis;
	/**
	 * 店铺描述
	 */
	private String description;
	/**
	 * 公司名称
	 */
	private String companyName;
	/**
	 * 模版类型
	 */
	private Integer templateSet;
	/**
	 * 店铺二维码
	 */
	private String qrCode;
	/**
	 * 店铺是否推荐
	 */
	private Integer isRecommend;
	/**
	 * 发票企业名称
	 */
	private String companyNameForInvoice;
	/**
	 * 纳税人识别号
	 */
	private String taxpayerNumber;
	/**
	 * 发票地址
	 */
	private String addressForInvoice;
	/**
	 * 发票电话
	 */
	private String phoneForInvoice;
	/**
	 * 开户行
	 */
	private String openingBank;
	/**
	 * 账号
	 */
	private String bankAccountNumber;
	/**
	 * 发票审核状态
	 */
	private Integer invoiceCheckStatus;
	/**
	 * 企业审核状态 1.待审核2.审核通过3.审核不通过
	 */
	private Integer shopInfoCheckSatus;
	/**
	 * 电话显示状态
	 */
	private Integer phoneShowStatus;
	/**
	 * 发票类型 1：不开发票 2:普通发票 3：增值税发票
	 */
	private Integer invoiceType;
	/**
	 * 发票内容
	 */
	private String invoiceInfo;
	/**
	 * 是否是VIP
	 */
	private Integer isVip;
	/**
	 * 最小订单金额
	 */
	private BigDecimal minAmount;
	/**
	 * 邮费
	 */
	private BigDecimal postage;

	/**
	 * 配置店铺与商品一对多关联关系
	 */
	private Set<ProductInfo> productInfoList;

	private List<Map> productInfoMap;

	/**
	 * 佣金抽成
	 */
	private String commission;

	public List<Map> getProductInfoMap() {
		return productInfoMap;
	}

	public void setProductInfoMap(List<Map> productInfoMap) {
		this.productInfoMap = productInfoMap;
	}

	public Integer getShopInfoId() {
		return shopInfoId;
	}

	public void setShopInfoId(Integer shopInfoId) {
		this.shopInfoId = shopInfoId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Integer getShopCategoryId() {
		return shopCategoryId;
	}

	public void setShopCategoryId(Integer shopCategoryId) {
		this.shopCategoryId = shopCategoryId;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public String getRegionLocation() {
		return regionLocation;
	}

	public void setRegionLocation(String regionLocation) {
		this.regionLocation = regionLocation;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAreaCounty() {
		return areaCounty;
	}

	public void setAreaCounty(String areaCounty) {
		this.areaCounty = areaCounty;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getMainProduct() {
		return mainProduct;
	}

	public void setMainProduct(String mainProduct) {
		this.mainProduct = mainProduct;
	}

	public String getCompanyRegistered() {
		return companyRegistered;
	}

	public void setCompanyRegistered(String companyRegistered) {
		this.companyRegistered = companyRegistered;
	}

	public String getLegalOwner() {
		return legalOwner;
	}

	public void setLegalOwner(String legalOwner) {
		this.legalOwner = legalOwner;
	}

	public String getCompanyCertification() {
		return companyCertification;
	}

	public void setCompanyCertification(String companyCertification) {
		this.companyCertification = companyCertification;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBusinessHoursStart() {
		return businessHoursStart;
	}

	public void setBusinessHoursStart(String businessHoursStart) {
		this.businessHoursStart = businessHoursStart;
	}

	public String getBusinessHoursEnd() {
		return businessHoursEnd;
	}

	public void setBusinessHoursEnd(String businessHoursEnd) {
		this.businessHoursEnd = businessHoursEnd;
	}

	public String getIDCards() {
		return IDCards;
	}

	public void setIDCards(String iDCards) {
		IDCards = iDCards;
	}

	public String getIDCardsImage() {
		return IDCardsImage;
	}

	public void setIDCardsImage(String iDCardsImage) {
		IDCardsImage = iDCardsImage;
	}

	public String getCompanyDocuments() {
		return companyDocuments;
	}

	public void setCompanyDocuments(String companyDocuments) {
		this.companyDocuments = companyDocuments;
	}

	public String getTaxRegistrationDocuments() {
		return taxRegistrationDocuments;
	}

	public void setTaxRegistrationDocuments(String taxRegistrationDocuments) {
		this.taxRegistrationDocuments = taxRegistrationDocuments;
	}

	public String getBusinessLicense() {
		return businessLicense;
	}

	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}

	public String getMarketBrand() {
		return marketBrand;
	}

	public void setMarketBrand(String marketBrand) {
		this.marketBrand = marketBrand;
	}

	public String getMarketBrandUrl() {
		return marketBrandUrl;
	}

	public void setMarketBrandUrl(String marketBrandUrl) {
		this.marketBrandUrl = marketBrandUrl;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Integer getIsPass() {
		return isPass;
	}

	public void setIsPass(Integer isPass) {
		this.isPass = isPass;
	}

	public Date getPassTime() {
		return passTime;
	}

	public void setPassTime(Date passTime) {
		this.passTime = passTime;
	}

	public String getVerifier() {
		return verifier;
	}

	public void setVerifier(String verifier) {
		this.verifier = verifier;
	}

	public Integer getIsClose() {
		return isClose;
	}

	public void setIsClose(Integer isClose) {
		this.isClose = isClose;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getBannerUrl() {
		return bannerUrl;
	}

	public void setBannerUrl(String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getTemplateSet() {
		return templateSet;
	}

	public void setTemplateSet(Integer templateSet) {
		this.templateSet = templateSet;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public Integer getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
	}

	public String getTaxpayerNumber() {
		return taxpayerNumber;
	}

	public void setTaxpayerNumber(String taxpayerNumber) {
		this.taxpayerNumber = taxpayerNumber;
	}

	public String getAddressForInvoice() {
		return addressForInvoice;
	}

	public void setAddressForInvoice(String addressForInvoice) {
		this.addressForInvoice = addressForInvoice;
	}

	public String getPhoneForInvoice() {
		return phoneForInvoice;
	}

	public void setPhoneForInvoice(String phoneForInvoice) {
		this.phoneForInvoice = phoneForInvoice;
	}

	public String getOpeningBank() {
		return openingBank;
	}

	public void setOpeningBank(String openingBank) {
		this.openingBank = openingBank;
	}

	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	public Integer getInvoiceCheckStatus() {
		return invoiceCheckStatus;
	}

	public void setInvoiceCheckStatus(Integer invoiceCheckStatus) {
		this.invoiceCheckStatus = invoiceCheckStatus;
	}

	public Integer getShopInfoCheckSatus() {
		return shopInfoCheckSatus;
	}

	public void setShopInfoCheckSatus(Integer shopInfoCheckSatus) {
		this.shopInfoCheckSatus = shopInfoCheckSatus;
	}

	public String getCompanyNameForInvoice() {
		return companyNameForInvoice;
	}

	public void setCompanyNameForInvoice(String companyNameForInvoice) {
		this.companyNameForInvoice = companyNameForInvoice;
	}

	public Integer getPhoneShowStatus() {
		return phoneShowStatus;
	}

	public void setPhoneShowStatus(Integer phoneShowStatus) {
		this.phoneShowStatus = phoneShowStatus;
	}

	public Integer getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(Integer invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getInvoiceInfo() {
		return invoiceInfo;
	}

	public void setInvoiceInfo(String invoiceInfo) {
		this.invoiceInfo = invoiceInfo;
	}

	public Set<ProductInfo> getProductInfoList() {
		return productInfoList;
	}

	public void setProductInfoList(Set<ProductInfo> productInfoList) {
		this.productInfoList = productInfoList;
	}

	public Integer getIsVip() {
		return isVip;
	}

	public void setIsVip(Integer isVip) {
		this.isVip = isVip;
	}

	public BigDecimal getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(BigDecimal minAmount) {
		this.minAmount = minAmount;
	}

	public BigDecimal getPostage() {
		return postage;
	}

	public void setPostage(BigDecimal postage) {
		this.postage = postage;
	}

	public String getCommission() {
		return commission;
	}

	public void setCommission(String commission) {
		this.commission = commission;
	}
	
}
