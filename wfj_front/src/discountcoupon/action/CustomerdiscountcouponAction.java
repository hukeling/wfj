package discountcoupon.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import shop.customer.pojo.Customer;
import util.action.BaseAction;
import util.other.Utils;
import discountcoupon.pojo.Customerdiscountcoupon;
import discountcoupon.pojo.DiscountCoupon;
import discountcoupon.service.ICustomerdiscountcouponService;
import discountcoupon.service.IDiscountCouponService;
/**
 * CustomerdiscountcouponAction - 会员优惠券Action
 */
@SuppressWarnings({"serial","rawtypes"})
public class CustomerdiscountcouponAction extends BaseAction{
	private ICustomerdiscountcouponService customerdiscountcouponService;
	private IDiscountCouponService discountCouponService;//优惠券Service
	private DiscountCoupon discountCoupon;//优惠券对象
	private Customerdiscountcoupon customerdiscountcoupon;
	private String discountCouponID;
	private List<Map> ordersListMap ;//优惠券集合
	private String level;
	
	public void addCustomerdiscountcoupon() throws ParseException, IOException{
		Customer cus= (Customer) session.getAttribute("customer");
		Date createTime = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String formatTime = sdf.format(createTime);
		Date createTimes = sdf.parse(formatTime);
		int di = Integer.parseInt(discountCouponID);
		discountCoupon = (DiscountCoupon) discountCouponService.getObjectById(discountCouponID);
		String bj;//标记。标注是否已领取优惠券
		Customerdiscountcoupon bc = new Customerdiscountcoupon();
		bc = (Customerdiscountcoupon) customerdiscountcouponService.getObjectByParams(" where o.customerId = "+cus.getCustomerId()+" and discountCouponID = "+discountCouponID);
		if(Utils.objectIsNotEmpty(bc)){
			bj = "ylq";
		}else if(Utils.objectIsEmpty(discountCoupon.getSurplus())){
			bj = "nulll";
		}else{
			Customerdiscountcoupon nc = new Customerdiscountcoupon();
			nc.setDiscountCouponID(di);
			nc.setCustomerId(cus.getCustomerId());
			nc.setDiscountCouponCode(discountCoupon.getDiscountCouponCode());
			nc.setDiscountCouponName(discountCoupon.getDiscountCouponName());
			nc.setDiscountCouponAmount(discountCoupon.getDiscountCouponAmount());
			nc.setDiscountCouponLowAmount(discountCoupon.getDiscountCouponLowAmount());
			nc.setBeginTime(discountCoupon.getBeginTime());
			nc.setExpirationTime(discountCoupon.getExpirationTime());
			nc.setUseStatus(0);
			nc.setStatus(1);
			nc.setCreateTime(createTime);
			nc.setUpdateTime(createTimes);
			nc.setDiscountImage(discountCoupon.getDiscountImage());
			customerdiscountcoupon = (Customerdiscountcoupon) customerdiscountcouponService.saveOrUpdateObject(nc);
			if(customerdiscountcoupon.getCustomerDiscountCouponID()!=null){
				int g = discountCoupon.getSurplus();
				discountCoupon.setSurplus(g-1);
				discountCoupon = (DiscountCoupon) discountCouponService.saveOrUpdateObject(discountCoupon);
				bj="true";
			}else{
				bj="false";
			}
			}
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", bj);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	
	public String gotoMyAccountCustoCouponList() throws IOException{
		Customer customer =(Customer) session.getAttribute("customer");
		if(customer!=null){
			String hql="select o.customerDiscountCouponID as customerDiscountCouponID,"
					+ " o.discountCouponID as discountCouponID,"
					+ " o.customerId as customerId,"
					+ " o.discountCouponCode as discountCouponCode,"
					+ " o.discountCouponName as discountCouponName,"
					+ " o.discountCouponAmount as discountCouponAmount,"
					+ " o.discountCouponLowAmount as discountCouponLowAmount,"
					+ " o.beginTime as beginTime,"
					+ " o.expirationTime as expirationTime,"
					+ " o.useStatus as useStatus,"
					+ " o.status as status,"
					+ " a.useStatus as auseStatus,"
					+ " o.createTime as createTime,"
					+ " o.updateTime as updateTime,"
					+ " o.discountImage as discountImage from Customerdiscountcoupon o , DiscountCoupon a"
					+ " where o.discountCouponID=a.discountCouponID and o.customerId =" +customer.getCustomerId();
			String params=" where 1=1 and o.customerId =" +customer.getCustomerId();
			if(Utils.stringIsNotEmpty(level)){
				hql+=" and o.useStatus="+level;
				params+=" and o.useStatus="+level;
			}
			
			int totalRecordCount = customerdiscountcouponService.getCount(params);
			pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
			ordersListMap = customerdiscountcouponService.findListMapPage(hql+" order by o.createTime desc", pageHelper);
			  SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
			   for(Map<String,Object> m:ordersListMap){//格式化创建时间
			    if(m.get("beginTime")!=null){
			     Date date = (Date) m.get("beginTime");
			     String format = fm.format(date);
			     m.put("beginTime", format);
			    }
			    if(m.get("expirationTime")!=null){
			    	Date date = (Date) m.get("expirationTime");
			    	String format = fm.format(date);
			    	m.put("expirationTime", format);
			    }
			    
			   }
		}
		return SUCCESS;
		
	}
	
	public void setCustomerdiscountcouponService(
			ICustomerdiscountcouponService customerdiscountcouponService) {
		this.customerdiscountcouponService = customerdiscountcouponService;
	}

	public void setDiscountCouponService(
			IDiscountCouponService discountCouponService) {
		this.discountCouponService = discountCouponService;
	}

	public DiscountCoupon getDiscountCoupon() {
		return discountCoupon;
	}

	public void setDiscountCoupon(DiscountCoupon discountCoupon) {
		this.discountCoupon = discountCoupon;
	}

	public String getDiscountCouponID() {
		return discountCouponID;
	}

	public void setDiscountCouponID(String discountCouponID) {
		this.discountCouponID = discountCouponID;
	}

	public Customerdiscountcoupon getCustomerdiscountcoupon() {
		return customerdiscountcoupon;
	}

	public void setCustomerdiscountcoupon(
			Customerdiscountcoupon customerdiscountcoupon) {
		this.customerdiscountcoupon = customerdiscountcoupon;
	}

	public List<Map> getOrdersListMap() {
		return ordersListMap;
	}

	public void setOrdersListMap(List<Map> ordersListMap) {
		this.ordersListMap = ordersListMap;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
}
