package tang.tangstore.pojo;
/**
 * 营业时间类
 * @author hkl
 *
 */
public class BusinessHour {
	
	private String synopsis;//营业时间说明
	
	private String openTime;//开门时间
	
	private String closeTime;//关门时间


	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}
	
}
