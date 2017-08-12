package tang.tangstore.pojo;

import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * 店铺图片
 * @author hkl
 *
 */
public class StoreImg extends BaseEntity implements Serializable {
 
 
	private static final long serialVersionUID = 6958625498276312534L;
	/**
	 * 店铺图片Id
	 */
	private Integer storeImgId;
	/**
	 * 店铺Id
	 */
	private Integer storeId;
	/**
	 * 图1 
	 */
	private String img1;
	/**
	 * 图2 
	 */
	private String img2;
	/**
	 * 图3 
	 */
	private String img3;
	/**
	 * 图4
	 */
	private String img4;
	/**
	 * 图5
	 */
	private String img5;
	public Integer getStoreImgId() {
		return storeImgId;
	}
	public void setStoreImgId(Integer storeImgId) {
		this.storeImgId = storeImgId;
	}
	public Integer getStoreId() {
		return storeId;
	}
	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
	public String getImg1() {
		return img1;
	}
	public void setImg1(String img1) {
		this.img1 = img1;
	}
	public String getImg2() {
		return img2;
	}
	public void setImg2(String img2) {
		this.img2 = img2;
	}
	public String getImg3() {
		return img3;
	}
	public void setImg3(String img3) {
		this.img3 = img3;
	}
	public String getImg4() {
		return img4;
	}
	public void setImg4(String img4) {
		this.img4 = img4;
	}
	public String getImg5() {
		return img5;
	}
	public void setImg5(String img5) {
		this.img5 = img5;
	}
	 
	
}
