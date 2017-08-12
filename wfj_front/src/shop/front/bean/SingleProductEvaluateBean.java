package shop.front.bean;
import java.io.Serializable;
import java.util.Date;
/**
 * 单个商品评价实体封装
 * @author 张攀攀
 *
 */
public class SingleProductEvaluateBean implements Serializable{
	private static final long serialVersionUID = 3994426440551610097L;
	/**
	 * 商品ID
	 */
	private Integer productId;
	/**
	 * 商品名称
	 */
	private String productName;
	/**
	 * 商品图片
	 */
	private String logoImage;
	/**
	 * 某个用户是否对此商品进行了评价（状态）
	 * “yes”代表已经评价，“no”代表未评价
	 */
	private String evaluateState;
	/**
	 * 评价的内容
	 */
	private String evaluateContent;
	/**
	 * 评价的时间
	 */
	private Date evaluateTime;
	/**
	 * 回复状态
	 */
	private Integer backState;
	/**
	 * 回复内容
	 */
	private String backContent;
	/**
	 * 回复时间
	 */
	private Date backTime;
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getLogoImage() {
		return logoImage;
	}
	public void setLogoImage(String logoImage) {
		this.logoImage = logoImage;
	}
	public String getEvaluateState() {
		return evaluateState;
	}
	public void setEvaluateState(String evaluateState) {
		this.evaluateState = evaluateState;
	}
	public String getEvaluateContent() {
		return evaluateContent;
	}
	public void setEvaluateContent(String evaluateContent) {
		this.evaluateContent = evaluateContent;
	}
	public Date getEvaluateTime() {
		return evaluateTime;
	}
	public void setEvaluateTime(Date evaluateTime) {
		this.evaluateTime = evaluateTime;
	}
	public Integer getBackState() {
		return backState;
	}
	public void setBackState(Integer backState) {
		this.backState = backState;
	}
	public String getBackContent() {
		return backContent;
	}
	public void setBackContent(String backContent) {
		this.backContent = backContent;
	}
	public Date getBackTime() {
		return backTime;
	}
	public void setBackTime(Date backTime) {
		this.backTime = backTime;
	}
}