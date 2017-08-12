package shop.customer.service.imp;
import shop.customer.dao.IEvaluateGoodsDao;
import shop.customer.pojo.EvaluateGoods;
import shop.customer.service.IEvaluateGoodsService;
import util.service.BaseService;
/**
 * 
 * @author
 * 
 */
public class EvaluateGoodsService extends BaseService<EvaluateGoods> implements
		IEvaluateGoodsService {
	private IEvaluateGoodsDao evaluateGoodsDao;
	/**
	 * 修改会员评价的内容和状态
	 * 
	 * @author 刘钦楠
	 */
	public EvaluateGoods updateContentAndEvaluateState(String id,
			String content, String evaluateState) {
		EvaluateGoods e = evaluateGoodsDao.getObjectById(id);
		if (e != null) {
			e.setContent(content);
			e.setEvaluateState(Integer.valueOf(evaluateState));
		}
		return e;
	}
	public void setEvaluateGoodsDao(IEvaluateGoodsDao evaluateGoodsDao) {
		this.baseDao = this.evaluateGoodsDao = evaluateGoodsDao;
	}
}