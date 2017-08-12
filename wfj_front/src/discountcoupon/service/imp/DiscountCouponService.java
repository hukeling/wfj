package discountcoupon.service.imp;
import util.service.BaseService;
import discountcoupon.dao.IDiscountCouponDao;
import discountcoupon.pojo.DiscountCoupon;
import discountcoupon.service.IDiscountCouponService;
/**
 * DiscountCouponService - 优惠券Service层接口实现类
 */
public class DiscountCouponService extends BaseService<DiscountCoupon> implements IDiscountCouponService {
	@SuppressWarnings("unused")
	private IDiscountCouponDao discountCouponDao;
	public void setDiscountCouponDao(IDiscountCouponDao discountCouponDao) {
		this.baseDao = this.discountCouponDao = discountCouponDao;
	}
}
