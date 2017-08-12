package shop.store.service.imp;

import shop.store.dao.IMemberShipDao;
import shop.store.pojo.MemberShip;
import shop.store.service.IMemberShipService;
import util.service.BaseService;
/**
 * 店铺会员Service接口实现类
 * @author jxw
 * 2014-10-10
 */
public class MemberShipService extends BaseService<MemberShip> implements IMemberShipService{
	@SuppressWarnings("unused")
	private IMemberShipDao memberShipDao;

	public void setMemberShipDao(IMemberShipDao memberShipDao) {
		this.baseDao = this.memberShipDao = memberShipDao;
	}
	
}
