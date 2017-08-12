package tang.oldBrand.pojo;

import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * 老字号与店铺关联表实体
 * @author hkl
 *
 */
public class OldBrandTangStore extends BaseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1752874038895173019L;
	/**
	 * 老字号ID
	 */
	private Integer oldBrandId;
	/**
	 * 唐智府店铺ID
	 */
	private Integer storeId;
	
	public Integer getOldBrandId() {
		return oldBrandId;
	}
	public void setOldBrandId(Integer oldBrandId) {
		this.oldBrandId = oldBrandId;
	}
	public Integer getStoreId() {
		return storeId;
	}
	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
	 
	
	
	
	
}
