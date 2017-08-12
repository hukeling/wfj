package tang.tangstore.pojo;

import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * 店铺交通信息
 * @author hkl
 *
 */
public class TangTraffic extends BaseEntity implements Serializable {
 
	private static final long serialVersionUID = -8694930166923957299L;
	/**
	 * 交通信息Id
	 */
	private Integer trafficId;
	/**
	 * 店铺Id
	 */
	private Integer storeId;
	/**
	 * 名字 
	 */
	private String locationName;
	/**
	 * 地标地址
	 */
	private String address;
	/**
	 * 地标经度
	 */
	private Double longitude;
	/**
	 * 地标纬度
	 */
	private Double latitude;
	/**
	 * 公交乘车方式
	 */
	private String busWay;
	/**
	 * 地铁乘车方式
	 */
	private String subwayWay;
	/**
	 * 交通提示
	 */
	private String trafficTips;
	public Integer getTrafficId() {
		return trafficId;
	}
	public void setTrafficId(Integer trafficId) {
		this.trafficId = trafficId;
	}
	public Integer getStoreId() {
		return storeId;
	}
	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public String getBusWay() {
		return busWay;
	}
	public void setBusWay(String busWay) {
		this.busWay = busWay;
	}
	public String getSubwayWay() {
		return subwayWay;
	}
	public void setSubwayWay(String subwayWay) {
		this.subwayWay = subwayWay;
	}
	public String getTrafficTips() {
		return trafficTips;
	}
	public void setTrafficTips(String trafficTips) {
		this.trafficTips = trafficTips;
	}

}
