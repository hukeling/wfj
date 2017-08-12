package shop.store.service.imp;
import shop.store.dao.ICustomerHaveCouponDao;
import shop.store.pojo.CustomerHaveCoupon;
import shop.store.service.ICustomerHaveCouponService;
import util.service.BaseService;
/**
 * DiscountCouponCustomerService - 优惠券和客户表Service层接口实现类
 */
public class CustomerHaveCouponService extends BaseService<CustomerHaveCoupon> implements ICustomerHaveCouponService {
	@SuppressWarnings("unused")
	private ICustomerHaveCouponDao customerHaveCouponDao;
	public void setCustomerHaveCouponDao(ICustomerHaveCouponDao customerHaveCouponDao) {
		this.baseDao = this.customerHaveCouponDao = customerHaveCouponDao;
	}
}
