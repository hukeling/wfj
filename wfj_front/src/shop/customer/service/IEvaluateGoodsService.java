package shop.customer.service;
import shop.customer.pojo.EvaluateGoods;
import util.service.IBaseService;
/**
 * 
 * @author 
 *
 */
public interface IEvaluateGoodsService  extends IBaseService <EvaluateGoods> {
	/**
	 * 修改评价内容和评价状态
	 * @param id
	 * @param content
	 * @param evaluateState
	 * @return
	 */
	public EvaluateGoods updateContentAndEvaluateState(String id,String content,String evaluateState);
}
