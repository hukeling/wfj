package shop.tags.pojo;
import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * 适合行业标签和使用场合标签关联表
 * @author mqr
 */
public class ShopTradeSituationTag extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -5046028684406655109L;
	
	/**适合行业使用场合标签ID**/
	private Integer tstId;
	
	/**适合行业标签ID**/
	private Integer ttId;
	
	/**使用场合标签ID**/
	private Integer stId;

	//setter getter
	public Integer getTstId() {
		return tstId;
	}
	public void setTstId(Integer tstId) {
		this.tstId = tstId;
	}
	public Integer getTtId() {
		return ttId;
	}
	public void setTtId(Integer ttId) {
		this.ttId = ttId;
	}
	public Integer getStId() {
		return stId;
	}
	public void setStId(Integer stId) {
		this.stId = stId;
	}
}
