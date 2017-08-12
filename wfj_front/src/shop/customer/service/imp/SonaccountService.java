package shop.customer.service.imp;
import shop.customer.dao.ISonaccountDao;
import shop.customer.pojo.Sonaccount;
import shop.customer.service.ISonaccountService;
import util.service.BaseService;
/**
 * 会员子帐号service接口实现
 * @author 王诗宇
 *
 */
public class SonaccountService extends BaseService<Sonaccount> implements ISonaccountService {
	@SuppressWarnings("unused")
	private ISonaccountDao sonaccountDao;//会员子帐号dao
	public void setSonaccountDao(ISonaccountDao sonaccountDao) {
		this.baseDao=this.sonaccountDao = sonaccountDao;
	}
}
