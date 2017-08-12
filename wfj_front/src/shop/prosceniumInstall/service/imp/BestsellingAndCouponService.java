package shop.prosceniumInstall.service.imp;
import shop.prosceniumInstall.dao.IBestsellingAndCouponDao;
import shop.prosceniumInstall.pojo.BestsellingAndCoupon;
import shop.prosceniumInstall.service.IBestsellingAndCouponService;
import util.service.BaseService;
/**
 * BestsellingAndCouponService - 前台热销和优惠券Service接口实现类
 */
public class BestsellingAndCouponService extends BaseService  <BestsellingAndCoupon> implements IBestsellingAndCouponService{
	@SuppressWarnings("unused")
	private IBestsellingAndCouponDao bestsellingAndCouponDao;
	public void setBestsellingAndCouponDao(
			IBestsellingAndCouponDao bestsellingAndCouponDao) {
		this.baseDao = this.bestsellingAndCouponDao = bestsellingAndCouponDao;
	}
}
