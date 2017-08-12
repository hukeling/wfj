package shop.tags.pojo;
import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * 适合行业标签
 * @author mqr
 */
public class ShopTradeTag extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -5046028684406655109L;
	
	/**适合行业标签ID**/
	private Integer ttId;
	
	/**标签名**/
	private String tageName;
	
	/**使用状态*0、废弃 1、正常使用*/
	private Integer useState;
	
	//setter getter
	public Integer getTtId() {
		return ttId;
	}
	public void setTtId(Integer ttId) {
		this.ttId = ttId;
	}
	public String getTageName() {
		return tageName;
	}
	public void setTageName(String tageName) {
		this.tageName = tageName;
	}
	public Integer getUseState() {
		return useState;
	}
	public void setUseState(Integer useState) {
		this.useState = useState;
	}
}
