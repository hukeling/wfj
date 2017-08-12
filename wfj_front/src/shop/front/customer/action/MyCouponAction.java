package shop.front.customer.action;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import shop.customer.pojo.Customer;
import shop.store.service.ICustomerHaveCouponService;
import shop.store.service.IShopBrandService;
import util.action.BaseAction;
/** 
 * MyCouponAction - 我的优惠券Action类
 * @author 孟琦瑞
 */
public class MyCouponAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	/**用户优惠券service**/
	private ICustomerHaveCouponService CustomerHaveCouponService;
	/**店铺品牌关联表service**/
	@SuppressWarnings("unused")
	private IShopBrandService shopBrandService;
	/**我的优惠券List**/
	@SuppressWarnings("unchecked")
	private List<Map>  myCouponList=new ArrayList<Map>();
	/**当前页**/
	private String pageIndex="1";
	/**每页显示个数**/
	private Integer pageSize=9;
	/**查询的时间**/
	private String dateTime;
	/** 当前时间去掉时分秒用于页面时间比对 **/
	private Date nowDate;
	//跳转
	public String gotoMyCouponPage() throws ParseException{
		//获取当前时间并发送到前台进行比对
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		nowDate = sdf.parse(dateformat.format(new Date()));
		//查询我的优惠券
		Customer customer=(Customer) session.getAttribute("customer");
		if(customer != null){
			@SuppressWarnings("unused")
			String selectFlag = request.getParameter("selectFlag");
			String hql="SELECT a.haveCouponId as haveCouponId ,a.discountCouponAmount as discountCouponAmount,a.discountCouponID as discountCouponID,a.discountCouponLowAmount as discountCouponLowAmount,a.expirationTime as expirationTime ,b.shopName as shopName,a.discountImage as discountImage ,b.shopInfoId as shopInfoId,a.state as state FROM CustomerHaveCoupon a , ShopInfo b where  a.shopInfoId=b.shopInfoId ";
			String countHql="SELECT COUNT(*) FROM CustomerHaveCoupon a , ShopInfo b where  a.shopInfoId=b.shopInfoId and a.state=1 and UNIX_TIMESTAMP(a.expirationTime)<=UNIX_TIMESTAMP('"+dateTime+"') and a.customerId = "+customer.getCustomerId();
			if(StringUtils.isNotEmpty(dateTime)){
				hql+="and UNIX_TIMESTAMP(a.expirationTime)<=UNIX_TIMESTAMP('"+dateTime+"') and  a.customerId = "+customer.getCustomerId();
				countHql+="and UNIX_TIMESTAMP(a.expirationTime)<=UNIX_TIMESTAMP('"+dateTime+"') and  a.customerId = "+customer.getCustomerId();
			}else{
				hql+=" and  a.customerId = "+customer.getCustomerId();
				countHql+=" and  a.customerId = "+customer.getCustomerId();
			}
			int totalRecordCount=CustomerHaveCouponService.getMultilistCount(countHql);
			pageHelper.setPageInfo(pageSize, totalRecordCount,currentPage);
			myCouponList = CustomerHaveCouponService.findListMapPage(hql, pageHelper);
		}
		return SUCCESS;
	}
	/**
	 * setter getter
	 */
	public String getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public void setCustomerHaveCouponService(
			ICustomerHaveCouponService customerHaveCouponService) {
		CustomerHaveCouponService = customerHaveCouponService;
	}
	@SuppressWarnings("unchecked")
	public List<Map> getMyCouponList() {
		return myCouponList;
	}
	@SuppressWarnings("unchecked")
	public void setMyCouponList(List<Map> myCouponList) {
		this.myCouponList = myCouponList;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public void setShopBrandService(IShopBrandService shopBrandService) {
		this.shopBrandService = shopBrandService;
	}
	public Date getNowDate() {
		return nowDate;
	}
	public void setNowDate(Date nowDate) {
		this.nowDate = nowDate;
	}
}
