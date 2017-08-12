package shop.front.bean;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 * 商品评价信息封装
 * @author 张攀攀
 *
 */
public class EvaluateProductBean implements Serializable{
	private static final long serialVersionUID = 3994426440551610097L;
	/**
	 * 订单号
	 */
	private String ordersNo;
	/**
	 * 下单时间
	 */
	private Date createTime;
	/**
	 * 订单下的商品集合(包括了商品的评价状态)
	 */
	private List<SingleProductEvaluateBean> productEvaluateBeans;
	public String getOrdersNo() {
		return ordersNo;
	}
	public void setOrdersNo(String ordersNo) {
		this.ordersNo = ordersNo;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public List<SingleProductEvaluateBean> getProductEvaluateBeans() {
		return productEvaluateBeans;
	}
	public void setProductEvaluateBeans(
			List<SingleProductEvaluateBean> productEvaluateBeans) {
		this.productEvaluateBeans = productEvaluateBeans;
	}
}