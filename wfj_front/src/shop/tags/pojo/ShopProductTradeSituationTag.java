package shop.tags.pojo;
import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * 商品和适合行业标签及使用场合标签关联表
 * @author mqr
 */
public class ShopProductTradeSituationTag extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -5046028684406655109L;
	
	/**商品适合行业使用场合标签ID**/
	private Integer ptstId;
	
	/**商品实体ID**/
	private Integer productId;
	
	/**适合行业标签ID**/
	private Integer ttId;
	
	/**使用场合标签ID**/
	private Integer stId;

	//setter getter
	public Integer getPtstId() {
		return ptstId;
	}
	public void setPtstId(Integer ptstId) {
		this.ptstId = ptstId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
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
