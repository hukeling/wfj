package tang.tangstore.pojo;

import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * 店铺服务
 * @author hkl
 *
 */
public class TangService extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -8343531183423411246L;
	/**
	 * 服务Id
	 */
	private Integer serviceId;
	/**
	 * 服务名称
	 */
	private String serviceName;
	/**
	 * 服务小图标
	 */
	private String serviceImageUrl;
	/**
	 * 店铺类别Id
	 */
	private Integer shopCategoryId;
	/**
	 * 服务类型
	 */
	private String serviceType;
	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceImageUrl() {
		return serviceImageUrl;
	}

	public void setServiceImageUrl(String serviceImageUrl) {
		this.serviceImageUrl = serviceImageUrl;
	}

	public Integer getShopCategoryId() {
		return shopCategoryId;
	}

	public void setShopCategoryId(Integer shopCategoryId) {
		this.shopCategoryId = shopCategoryId;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	
	
	
	
	

}
