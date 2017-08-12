package tang.tangstore.pojo;

import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * 店铺服务设施等信息
 * @author hkl
 *
 */
public class StoreService extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 307665840633628016L;
	/**
	 * Id
	 */
	private Integer storeServiceId;
	/**
	 * 店铺Id
	 */
	private Integer storeId;
	/**
	 * 服务Id
	 */
	private Integer serviceId;

	
	public Integer getStoreServiceId() {
		return storeServiceId;
	}

	public void setStoreServiceId(Integer storeServiceId) {
		this.storeServiceId = storeServiceId;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	
}
