package shop.store.action;
import shop.store.service.ICustomerHaveCouponService;
import util.action.BaseAction;
/**
 * CustomerHaveCouponAction - 会员和优惠券Action
 */
@SuppressWarnings("serial")
public class CustomerHaveCouponAction extends BaseAction {
	private ICustomerHaveCouponService customerHaveCouponService;
	public ICustomerHaveCouponService getCustomerHaveCouponService() {
		return customerHaveCouponService;
	}
	public void setCustomerHaveCouponService(
			ICustomerHaveCouponService customerHaveCouponService) {
		this.customerHaveCouponService = customerHaveCouponService;
	}
}
