package tang.tangstore.pojo;

import java.io.Serializable;
import java.util.List;

import tang.tangProduct.pojo.TangProduct;
import util.pojo.BaseEntity;
/**
 * 唐智府部分的店铺
 * @author hkl
 *
 */
public class TangStore extends BaseEntity implements Serializable {
	 
	private static final long serialVersionUID = 5198683439335818081L;
	
	/**
	 * 店铺ID
	 */
	private Integer storeId;
	
	/**
	 * 店铺分类Id
	 */
	private Integer shopCategoryId;
	/**
	 *店铺名称
	 */
	private String storeName;
	/**
	 * 店铺简介
	 */
	private String synopsis;
	/**
	 * 联系电话
	 */
	private String phone;
	/**
	 * 店铺所在城市
	 */
	private String city;
	/**
	 * 详细地址
	 */
	private String address;
	/**
	 * 经度
	 */
	private Double longitude;
	/**
	 * 纬度
	 */
	private Double latitude;
	/**
	 * 店铺图片
	 */
	private String logoUrl;
	/**
	 * 店铺大图片
	 */
	private String bannerUrl;
	/**
	 * 营业时间开始
	 */
	private String businessHoursStart;
	/**
	 * 营业时间结束
	 */
	private String businessHoursEnd;
	/**
	 * 营业时间集串
	 */
	private String businessHourStr;
	/**
	 * 店铺提示
	 */
	private String storeTip;
	
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
	 * 备注
	 */
	private String note;
	/**
	 * 店铺级别（比如景区评级；酒店评级等）
	 */
	private String storeLevel;
	/**
	 * 附近地标
	 */
	private String landMark;
	/**
	 * 店铺特色设施，特色服务等（如WiFi，停车等；)
	 */
	private List<StoreService> storeServiceList;
	
	private List<TangService> serviceList;
	/**
	 * 交通信息
	 */
	private TangTraffic tangTraffic;
	/**
	 * 营业时间集
	 * @return
	 */
//	private List<BusinessHour> businessHourList;
	
	/**
	 * 店铺和我之间的距离
	 * @return
	 */
	private Integer distance;
	/**
	 * 距离的单位
	 */
	private String distanceUnit;
	/**
	 * 店铺的商品信息
	 * @return
	 */
	private List<TangProduct> proList;
	/**
	 * 店铺更多图片集
	 * @return
	 */
	private List<StoreImg> storeImgList;
	
	
	
	public List<TangService> getServiceList() {
		return serviceList;
	}
	public void setServiceList(List<TangService> serviceList) {
		this.serviceList = serviceList;
	}
	public List<StoreImg> getStoreImgList() {
		return storeImgList;
	}
	public void setStoreImgList(List<StoreImg> storeImgList) {
		this.storeImgList = storeImgList;
	}
	public List<TangProduct> getProList() {
		return proList;
	}
	public void setProList(List<TangProduct> proList) {
		this.proList = proList;
	}
	public String getDistanceUnit() {
		return distanceUnit;
	}
	public void setDistanceUnit(String distanceUnit) {
		this.distanceUnit = distanceUnit;
	}
	public Integer getDistance() {
		return distance;
	}
	public void setDistance(Integer distance) {
		this.distance = distance;
	}
	public String getBusinessHourStr() {
		return businessHourStr;
	}
	public void setBusinessHourStr(String businessHourStr) {
		this.businessHourStr = businessHourStr;
	}
	/*public List<BusinessHour> getBusinessHourList() {
		return businessHourList;
	}
	public void setBusinessHourList(List<BusinessHour> businessHourList) {
		this.businessHourList = businessHourList;
	}*/
	public Integer getStoreId() {
		return storeId;
	}
	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
	public Integer getShopCategoryId() {
		return shopCategoryId;
	}
	public void setShopCategoryId(Integer shopCategoryId) {
		this.shopCategoryId = shopCategoryId;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getSynopsis() {
		return synopsis;
	}
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public String getStoreTip() {
		return storeTip;
	}
	public void setStoreTip(String storeTip) {
		this.storeTip = storeTip;
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
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public List<StoreService> getStoreServiceList() {
		return storeServiceList;
	}
	public void setStoreServiceList(List<StoreService> storeServiceList) {
		this.storeServiceList = storeServiceList;
	}
	 
	public TangTraffic getTangTraffic() {
		return tangTraffic;
	}
	public void setTangTraffic(TangTraffic tangTraffic) {
		this.tangTraffic = tangTraffic;
	}
	public String getStoreLevel() {
		return storeLevel;
	}
	public void setStoreLevel(String storeLevel) {
		this.storeLevel = storeLevel;
	}
	public String getLandMark() {
		return landMark;
	}
	public void setLandMark(String landMark) {
		this.landMark = landMark;
	}
	
	
	

}
