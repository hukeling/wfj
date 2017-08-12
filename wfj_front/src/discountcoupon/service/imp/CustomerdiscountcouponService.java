package discountcoupon.service.imp;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import util.service.BaseService;
import discountcoupon.dao.ICustomerdiscountcouponDao;
import discountcoupon.pojo.Customerdiscountcoupon;
import discountcoupon.service.ICustomerdiscountcouponService;
/**
 * CustomerdiscountcouponService - 用户优惠券Service层接口实现类
 */
public class CustomerdiscountcouponService extends BaseService<Customerdiscountcoupon> implements ICustomerdiscountcouponService {
	private ICustomerdiscountcouponDao customerdiscountcouponDao;

	public void setCustomerdiscountcouponDao(
			ICustomerdiscountcouponDao customerdiscountcouponDao) {
		this.baseDao = this.customerdiscountcouponDao = customerdiscountcouponDao;
	}
	//修改启用时间
		public void updatestatus(){
			Calendar rightNow = Calendar.getInstance(); 
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//格式大小写有区别
			String sysDatetime = fmt.format(rightNow.getTime());
			String updateopenSql ="update shop_customer_discountcoupon set status = 1 where beginTime<= '"+sysDatetime+"' and expirationTime > '"+sysDatetime+"'";
			customerdiscountcouponDao.updateObject(updateopenSql);
			String updatecloseSql ="update shop_customer_discountcoupon set status = 2 where expirationTime <= '"+sysDatetime+"'";
			customerdiscountcouponDao.updateObject(updatecloseSql);
		}
}
