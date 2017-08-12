package tang.tangstore.pojo;
/**
 * 营业时间类
 * @author hkl
 *
 */
public class LandMark {
	/**
	 * 地标名称
	 */
	private String locationName;
	/**
	 * 地标与当前的距离
	 */
	private String distance;
	
	/**
	 * 地标经度
	 */
	private Double longitude;
	/**
	 * 地标纬度
	 */
	private Double latitude;
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
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
	public LandMark(String locationName, String distance, Double longitude,
			Double latitude) {
		super();
		this.locationName = locationName;
		this.distance = distance;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	public LandMark() {
		super();
	}

}
