package shop.product.pojo;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.compass.annotations.Index;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableProperty;
import org.compass.annotations.Store;

import util.pojo.BaseEntity;
/**
 * 商品实体类
 * @author ss
 *
 */
@SuppressWarnings("serial")
@Searchable
public class ProductInfo extends BaseEntity implements Serializable{
	private static long serialVersionUID = -4296800758519683653L;
	/**
	 * 商品ID
	 */
	private Integer productId;
	/**
	 * 品牌ID
	 */
	private Integer brandId;
	/**
	 * 商品分类ID
	 */
	private Integer productTypeId;
	/**
	 * 一级分类ID
	 */
	private Integer categoryLevel1;
	/**
	 * 二级分类ID
	 */
	private Integer categoryLevel2;
	/**
	 * 三级分类ID
	 */
	private Integer categoryLevel3;
	/**
	 * 四级分类ID
	 */
	private Integer categoryLevel4;
	/**
	 * 店铺ID
	 */
	private Integer shopInfoId;
	/**
	 * 商品名称
	 */
	private String productName;
	/**
	 * 商品全名称
	 */
	private String productFullName;
	/**
	 * 市场价格
	 */
	private BigDecimal marketPrice;
	/**
	 * 销售价
	 */
	private BigDecimal salesPrice;
	/**
	 * 销售价备份
	 */
	private BigDecimal salesPriceBack;
	/**
	 * 进货价
	 */
	private BigDecimal costPrice;
	/**
	 * 会员价格(N)
	 */
	private BigDecimal memberPrice;
	/**
	 * 商品重量
	 */
	private Double weight;
	/**
	 * 重量单位
	 */
	private String weightUnit;
	/**
	 * 计量单位名称
	 */
	private String measuringUnitName;
	/**
	 * 包装规格
	 */
	private String packingSpecifications;
	/**
	 * 商品规格描述
	 */
	private String specification;
	/**
	 * 制造商型号
	 */
	private String manufacturerModel;
	/**
	 * 商品描述
	 */
	private String describle;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 更新时间
	 */
	private Date updateDate;
	/**
	 * 上架时间
	 */
	private Date putSaleDate;
	/**
	 * 是否上架
	 */
	private Integer isPutSale;
	/**
	 * 商品编号
	 */
	private String productCode;
	/**
	 * 商品功能简介
	 */
	private String functionDesc;
	/**
	 * 库存数
	 */
	private Integer storeNumber;
	/**
	 * 是否收取运费
	 * 1、不收取邮费，由店铺承担邮费。
	 * 2、收取邮费，由买家承担邮费。
	 */
	private Integer isChargeFreight;
	/**
	 * 运费价格
	 */
	private BigDecimal freightPrice;
	/**
	 * 商品属性值
	 */
	private String productAttributeValue;
	/**
	 * 商品参数值
	 */
	private String productParametersValue;
	/**
	 * 商品规格归类组
	 */
	private Integer goods;
	/**
	 * 商品数据同步标注
	 * 0：平台
	 * 1：店铺
	 */
	private Integer productRemark;
	/**
	 * 审核状态
	 */
	private Integer isPass;
	/**
	 * 商品备注
	 */
	private String note;
	/**
	 * 商品搜索标签TAG
	 */
	private String productTag;
	/**
	 * SEO标题
	 */
	private String seoTitle;
	/**
	 * SEO关键字
	 */
	private String seoKeyWord;
	/**
	 * SEO描述
	 */
	private String seoDescription;
	/**
	 * 点击量(N)
	 */
	private Integer totalHits;
	/**
	 * 商品销售量
	 */
	private Integer totalSales;
	/**
	 * 是否为推荐商品
	 */
	private Integer isRecommend;
	/**
	 * 是否为新品商品
	 */
	private Integer isNew;
	/**
	 * 是否为热销商品
	 */
	private Integer isHot;
	/**
	 * 是否为顶置商品
	 */
	private Integer isTop;
	/**商品图片**/
	private String logoImg;
	/**商品属性**/
	private String productAttribute;
	/**
	 * 是否显示
	 * 0：不显示
	 * 1：显示
	 */
	private Integer isShow;
	/**
	 * 买商品送天海币值
	 */
	private BigDecimal virtualCoinNumber;
	/**
	 * 赠送商城币
	 */
	private BigDecimal giveAwayCoinNumber;
	/**
	 * 商品条形码
	 */
	private String barCode;
	/***
	 * 商品二维码
	 */
	private String qrCode;
	/**
	 * 预计发货日
	 */
	private Integer stockUpDate;
	/**
	 * 商品省级发货地
	 */
	private Integer deliveryAddressProvince;
	/**
	 * 商品地市级发货地
	 */
	private Integer deliveryAddressCities;
	/**
	 * SKU订货号
	 */
	private String sku;
	/**
	 * compass做关联查询品牌名称
	 */
	private String brandName;
	/**
	 * 虚拟商品种类个数goodsCount
	 */
	private Integer goodsCount;
	/**
	 * 店铺实体
	 */
	/*private ShopInfo shopInfo;*/
	/**
	 * 店铺名称
	 */
	private String shopName;
	/**
	 * 店铺是否通过
	 */
	private Integer tisPass;
	/**
	 * 店铺是否关闭
	 */
	private Integer tisClose;
	
	/********************************************END**********************************************/
	/**
	 * 商品ID
	 */
	@SearchableId
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	/**
	 * 品牌ID
	 */
	@SearchableProperty(index = Index.NOT_ANALYZED, store = Store.YES)//做存储
	public Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	/**
	 * 商品分类ID
	 */
	@SearchableProperty(index = Index.NOT_ANALYZED, store = Store.YES)//做存储
	public Integer getProductTypeId() {
		return productTypeId;
	}
	public void setProductTypeId(Integer productTypeId) {
		this.productTypeId = productTypeId;
	}
	/**
	 * 商品名称
	 */
	@SearchableProperty(index=Index.ANALYZED,store=Store.YES)//做存储也要分词
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * 商品全名称
	 */
	@SearchableProperty(index=Index.ANALYZED,store=Store.YES)//做存储
	public String getProductFullName() {
		return productFullName;
	}
	public void setProductFullName(String productFullName) {
		this.productFullName = productFullName;
	}
	/**
	 * 市场价格
	 */
	@SearchableProperty(index = Index.NOT_ANALYZED, store = Store.YES)//做存储
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}
	/**
	 * 销售价
	 */
	@SearchableProperty(store = Store.YES)//做存储
	public BigDecimal getSalesPrice() {
		return salesPrice;
	}
	public void setSalesPrice(BigDecimal salesPrice) {
		this.salesPrice = salesPrice;
	}
	public BigDecimal getSalesPriceBack() {
		return salesPriceBack;
	}
	public void setSalesPriceBack(BigDecimal salesPriceBack) {
		this.salesPriceBack = salesPriceBack;
	}
	/**
	 * 进货价
	 */
	@SearchableProperty(index = Index.NOT_ANALYZED, store = Store.YES)//做存储
	public BigDecimal getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}
	/**
	 * 会员价格(N)
	 */
	@SearchableProperty(index = Index.NOT_ANALYZED, store = Store.YES)//做存储
	public BigDecimal getMemberPrice() {
		return memberPrice;
	}
	public void setMemberPrice(BigDecimal memberPrice) {
		this.memberPrice = memberPrice;
	}
	/**
	 * 商品重量
	 */
	@SearchableProperty(index = Index.NOT_ANALYZED, store = Store.YES)//做存储
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	/**
	 * 重量单位
	 */
	@SearchableProperty(index = Index.NOT_ANALYZED, store = Store.YES)//做存储
	public String getWeightUnit() {
		return weightUnit;
	}
	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}
	/**
	 * 商品规格描述
	 */
	@SearchableProperty(store=Store.YES)//做存储
	public String getSpecification() {
		return specification;
	}
	public void setSpecification(String specification) {
		this.specification = specification;
	}
	/**
	 * 商品描述
	 */
	@SearchableProperty(store=Store.YES)//做存储
	public String getDescrible() {
		return describle;
	}
	public void setDescrible(String describle) {
		this.describle = describle;
	}
	/**
	 * 创建时间
	 */
	@SearchableProperty(store=Store.YES)//做存储
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * 更新时间
	 */
	@SearchableProperty(store=Store.YES)//做存储
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * 上架时间
	 */
	@SearchableProperty(store=Store.YES)//做存储
	public Date getPutSaleDate() {
		return putSaleDate;
	}
	public void setPutSaleDate(Date putSaleDate) {
		this.putSaleDate = putSaleDate;
	}
	/**
	 * 是否上架
	 */
	@SearchableProperty(store=Store.YES)//做存储
	public Integer getIsPutSale() {
		return isPutSale;
	}
	public void setIsPutSale(Integer isPutSale) {
		this.isPutSale = isPutSale;
	}
	/**
	 * 商品编号
	 */
	@SearchableProperty(store=Store.YES)//做存储
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	/**
	 * 商品功能简介
	 */
	@SearchableProperty(index = Index.NOT_ANALYZED, store = Store.YES)//做存储
	public String getFunctionDesc() {
		return functionDesc;
	}
	public void setFunctionDesc(String functionDesc) {
		this.functionDesc = functionDesc;
	}
	/**
	 * 库存数
	 */
	@SearchableProperty(store=Store.YES)//做存储
	public Integer getStoreNumber() {
		return storeNumber;
	}
	public void setStoreNumber(Integer storeNumber) {
		this.storeNumber = storeNumber;
	}
	/**
	 * 是否收取运费
	 * 1、不收取邮费，由店铺承担邮费。
	 * 2、收取邮费，由买家承担邮费。
	 */
	@SearchableProperty(store=Store.YES)//做存储
	public Integer getIsChargeFreight() {
		return isChargeFreight;
	}
	public void setIsChargeFreight(Integer isChargeFreight) {
		this.isChargeFreight = isChargeFreight;
	}
	/**
	 * 运费价格
	 */
	@SearchableProperty(index = Index.NOT_ANALYZED, store = Store.YES)//做存储
	public BigDecimal getFreightPrice() {
		return freightPrice;
	}
	public void setFreightPrice(BigDecimal freightPrice) {
		this.freightPrice = freightPrice;
	}
	/**
	 * 商品属性值
	 */
	@SearchableProperty(store=Store.YES)//做存储
	public String getProductAttributeValue() {
		return productAttributeValue;
	}
	public void setProductAttributeValue(String productAttributeValue) {
		this.productAttributeValue = productAttributeValue;
	}
	/**
	 * 商品参数值
	 */
	@SearchableProperty(index = Index.NOT_ANALYZED, store = Store.YES)//做存储
	public String getProductParametersValue() {
		return productParametersValue;
	}
	public void setProductParametersValue(String productParametersValue) {
		this.productParametersValue = productParametersValue;
	}
	/**
	 * 商品规格归类组
	 */
	@SearchableProperty(index = Index.NOT_ANALYZED, store = Store.YES)//做存储
	public Integer getGoods() {
		return goods;
	}
	public void setGoods(Integer goods) {
		this.goods = goods;
	}
	/**
	 * 商品数据同步标注
	 * 0：平台
	 * 1：店铺
	 */
	@SearchableProperty(index = Index.NOT_ANALYZED, store = Store.YES)//做存储
	public Integer getProductRemark() {
		return productRemark;
	}
	public void setProductRemark(Integer productRemark) {
		this.productRemark = productRemark;
	}
	/**
	 * 审核状态
	 */
	@SearchableProperty(store=Store.YES)//做存储
	public Integer getIsPass() {
		return isPass;
	}
	public void setIsPass(Integer isPass) {
		this.isPass = isPass;
	}
	/**
	 * 商品备注
	 */
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * 商品搜索标签TAG
	 */
	@SearchableProperty(store=Store.YES)//做存储
	public String getProductTag() {
		return productTag;
	}
	public void setProductTag(String productTag) {
		this.productTag = productTag;
	}
	/**
	 * SEO标题
	 */
	@SearchableProperty(store=Store.YES)//做存储
	public String getSeoTitle() {
		return seoTitle;
	}
	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}
	/**
	 * SEO关键字
	 */
	@SearchableProperty(store=Store.YES)//做存储
	public String getSeoKeyWord() {
		return seoKeyWord;
	}
	public void setSeoKeyWord(String seoKeyWord) {
		this.seoKeyWord = seoKeyWord;
	}
	/**
	 * SEO描述
	 */
	@SearchableProperty(store=Store.YES)//做存储
	public String getSeoDescription() {
		return seoDescription;
	}
	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}
	/**
	 * 点击量(N)
	 */
	@SearchableProperty(store=Store.YES)//做存储
	public Integer getTotalHits() {
		return totalHits;
	}
	public void setTotalHits(Integer totalHits) {
		this.totalHits = totalHits;
	}
	/**
	 * 商品销售量
	 */
	@SearchableProperty(store=Store.YES)//做存储
	public Integer getTotalSales() {
		return totalSales;
	}
	public void setTotalSales(Integer totalSales) {
		this.totalSales = totalSales;
	}
	/**
	 * 是否为推荐商品
	 */
	@SearchableProperty(store=Store.YES)//做存储
	public Integer getIsRecommend() {
		return isRecommend;
	}
	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
	}
	/**
	 * 是否为新品商品
	 */
	@SearchableProperty(store=Store.YES)//做存储
	public Integer getIsNew() {
		return isNew;
	}
	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
	}
	/**
	 * 是否为热销商品
	 */
	@SearchableProperty(store=Store.YES)//做存储
	public Integer getIsHot() {
		return isHot;
	}
	public void setIsHot(Integer isHot) {
		this.isHot = isHot;
	}
	/**
	 * 是否为顶置商品
	 */
	@SearchableProperty(store=Store.YES)//做存储
	public Integer getIsTop() {
		return isTop;
	}
	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}
	/**
	 * 店铺ID
	 */
	@SearchableProperty(index = Index.NOT_ANALYZED, store = Store.YES)//做存储
	public Integer getShopInfoId() {
		return shopInfoId;
	}
	public void setShopInfoId(Integer shopInfoId) {
		this.shopInfoId = shopInfoId;
	}
	/**商品图片**/
	@SearchableProperty(store=Store.YES)//做存储
	public String getLogoImg() {
		return logoImg;
	}
	public void setLogoImg(String logoImg) {
		this.logoImg = logoImg;
	}
	/**商品属性**/
	@SearchableProperty(store=Store.YES)//做存储
	public String getProductAttribute() {
		return productAttribute;
	}
	public void setProductAttribute(String productAttribute) {
		this.productAttribute = productAttribute;
	}
	/**
	 * 是否显示
	 * 0：不显示
	 * 1：显示
	 */
	@SearchableProperty(store=Store.YES)//做存储
	public Integer getIsShow() {
		return isShow;
	}
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	/**
	 *买商品送天海币值 
	 */
	public String getBarCode() {
		return barCode;
	}
	/**
	 * 预计出售日
	 */
	public Integer getStockUpDate() {
		return stockUpDate;
	}
	public void setStockUpDate(Integer stockUpDate) {
		this.stockUpDate = stockUpDate;
	}
	public BigDecimal getVirtualCoinNumber() {
		return virtualCoinNumber;
	}
	public void setVirtualCoinNumber(BigDecimal virtualCoinNumber) {
		this.virtualCoinNumber = virtualCoinNumber;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public String getQrCode() {
		return qrCode;
	}
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
	public Integer getDeliveryAddressProvince() {
		return deliveryAddressProvince;
	}
	public void setDeliveryAddressProvince(Integer deliveryAddressProvince) {
		this.deliveryAddressProvince = deliveryAddressProvince;
	}
	public Integer getDeliveryAddressCities() {
		return deliveryAddressCities;
	}
	public void setDeliveryAddressCities(Integer deliveryAddressCities) {
		this.deliveryAddressCities = deliveryAddressCities;
	}
	@SearchableProperty(index = Index.NOT_ANALYZED, store = Store.YES)//做存储
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	@SearchableProperty(index = Index.NOT_ANALYZED, store = Store.YES)//做存储
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getMeasuringUnitName() {
		return measuringUnitName;
	}
	public void setMeasuringUnitName(String measuringUnitName) {
		this.measuringUnitName = measuringUnitName;
	}
	public String getPackingSpecifications() {
		return packingSpecifications;
	}
	public void setPackingSpecifications(String packingSpecifications) {
		this.packingSpecifications = packingSpecifications;
	}
	public String getManufacturerModel() {
		return manufacturerModel;
	}
	public void setManufacturerModel(String manufacturerModel) {
		this.manufacturerModel = manufacturerModel;
	}
	public Integer getGoodsCount() {
		return goodsCount;
	}
	public void setGoodsCount(Integer goodsCount) {
		this.goodsCount = goodsCount;
	}
	/*@SearchableComponent
	public ShopInfo getShopInfo() {
		return shopInfo;
	}
	public void setShopInfo(ShopInfo shopInfo) {
		this.shopInfo = shopInfo;
	}*/
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public static void setSerialVersionUID(long serialVersionUID) {
		ProductInfo.serialVersionUID = serialVersionUID;
	}
	@SearchableProperty(index = Index.NOT_ANALYZED, store = Store.YES)//做存储
	public Integer getCategoryLevel1() {
		return categoryLevel1;
	}
	public void setCategoryLevel1(Integer categoryLevel1) {
		this.categoryLevel1 = categoryLevel1;
	}
	public Integer getCategoryLevel2() {
		return categoryLevel2;
	}
	public void setCategoryLevel2(Integer categoryLevel2) {
		this.categoryLevel2 = categoryLevel2;
	}
	public Integer getCategoryLevel3() {
		return categoryLevel3;
	}
	public void setCategoryLevel3(Integer categoryLevel3) {
		this.categoryLevel3 = categoryLevel3;
	}
	public Integer getCategoryLevel4() {
		return categoryLevel4;
	}
	public void setCategoryLevel4(Integer categoryLevel4) {
		this.categoryLevel4 = categoryLevel4;
	}
	public BigDecimal getGiveAwayCoinNumber() {
		return giveAwayCoinNumber;
	}
	public void setGiveAwayCoinNumber(BigDecimal giveAwayCoinNumber) {
		this.giveAwayCoinNumber = giveAwayCoinNumber;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	@SearchableProperty(index = Index.NOT_ANALYZED, store = Store.YES)//做存储
	public Integer getTisPass() {
		return tisPass;
	}
	public void setTisPass(Integer tisPass) {
		this.tisPass = tisPass;
	}
	@SearchableProperty(index = Index.NOT_ANALYZED, store = Store.YES)//做存储
	public Integer getTisClose() {
		return tisClose;
	}
	public void setTisClose(Integer tisClose) {
		this.tisClose = tisClose;
	}
	public ProductInfo(Integer productId, Integer brandId,
			Integer productTypeId, Integer categoryLevel1,
			Integer categoryLevel2, Integer categoryLevel3,
			Integer categoryLevel4, Integer shopInfoId, String productName,
			String productFullName, BigDecimal marketPrice,
			BigDecimal salesPrice, BigDecimal salesPriceBack,
			BigDecimal costPrice, BigDecimal memberPrice, Double weight,
			String weightUnit, String measuringUnitName,
			String packingSpecifications, String specification,
			String manufacturerModel, String describle, Date createDate,
			Date updateDate, Date putSaleDate, Integer isPutSale,
			String productCode, String functionDesc, Integer storeNumber,
			Integer isChargeFreight, BigDecimal freightPrice,
			String productAttributeValue, String productParametersValue,
			Integer goods, Integer productRemark, Integer isPass, String note,
			String productTag, String seoTitle, String seoKeyWord,
			String seoDescription, Integer totalHits, Integer totalSales,
			Integer isRecommend, Integer isNew, Integer isHot, Integer isTop,
			String logoImg, String productAttribute, Integer isShow,
			BigDecimal virtualCoinNumber, BigDecimal giveAwayCoinNumber,
			String barCode, String qrCode, Integer stockUpDate,
			Integer deliveryAddressProvince, Integer deliveryAddressCities,
			String sku ) {
		super();
		this.productId = productId;
		this.brandId = brandId;
		this.productTypeId = productTypeId;
		this.categoryLevel1 = categoryLevel1;
		this.categoryLevel2 = categoryLevel2;
		this.categoryLevel3 = categoryLevel3;
		this.categoryLevel4 = categoryLevel4;
		this.shopInfoId = shopInfoId;
		this.productName = productName;
		this.productFullName = productFullName;
		this.marketPrice = marketPrice;
		this.salesPrice = salesPrice;
		this.salesPriceBack = salesPriceBack;
		this.costPrice = costPrice;
		this.memberPrice = memberPrice;
		this.weight = weight;
		this.weightUnit = weightUnit;
		this.measuringUnitName = measuringUnitName;
		this.packingSpecifications = packingSpecifications;
		this.specification = specification;
		this.manufacturerModel = manufacturerModel;
		this.describle = describle;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.putSaleDate = putSaleDate;
		this.isPutSale = isPutSale;
		this.productCode = productCode;
		this.functionDesc = functionDesc;
		this.storeNumber = storeNumber;
		this.isChargeFreight = isChargeFreight;
		this.freightPrice = freightPrice;
		this.productAttributeValue = productAttributeValue;
		this.productParametersValue = productParametersValue;
		this.goods = goods;
		this.productRemark = productRemark;
		this.isPass = isPass;
		this.note = note;
		this.productTag = productTag;
		this.seoTitle = seoTitle;
		this.seoKeyWord = seoKeyWord;
		this.seoDescription = seoDescription;
		this.totalHits = totalHits;
		this.totalSales = totalSales;
		this.isRecommend = isRecommend;
		this.isNew = isNew;
		this.isHot = isHot;
		this.isTop = isTop;
		this.logoImg = logoImg;
		this.productAttribute = productAttribute;
		this.isShow = isShow;
		this.virtualCoinNumber = virtualCoinNumber;
		this.giveAwayCoinNumber = giveAwayCoinNumber;
		this.barCode = barCode;
		this.qrCode = qrCode;
		this.stockUpDate = stockUpDate;
		this.deliveryAddressProvince = deliveryAddressProvince;
		this.deliveryAddressCities = deliveryAddressCities;
		this.sku = sku;
		 
	}
	public ProductInfo() {
		super();
	}
	
	
}
