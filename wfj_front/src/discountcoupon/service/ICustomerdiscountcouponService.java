package discountcoupon.service;

import util.service.IBaseService;
import discountcoupon.pojo.Customerdiscountcoupon;
/**
 * CustomerdiscountcouponService - 用户优惠券户表Service层接口
 */
public interface ICustomerdiscountcouponService extends
IBaseService<Customerdiscountcoupon> {
	public void updatestatus();
}
