package shop.front.store.service.imp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import shop.cityCourier.pojo.CityCourier;
import shop.customer.pojo.Customer;
import shop.customer.pojo.Sonaccount;
import shop.front.store.service.IShopOrdersService;
import shop.order.dao.IOrdersDao;
import shop.order.dao.IOrdersOPLogDao;
import shop.order.dao.IShippingDao;
import shop.order.pojo.Orders;
import shop.order.pojo.OrdersOPLog;
import shop.order.pojo.Shipping;
import shop.store.pojo.ShopInfo;
import util.other.JSONFormatDate;
import util.other.SerialNumberUtil;
import util.service.BaseService;
/**
 * ShopOrdersService - 类描述信息
 */
public class ShopOrdersService extends BaseService<Orders> implements IShopOrdersService {
	/**订单DAo**/
	private IOrdersDao ordersDao;
	/**物流信息DAO**/
	private IShippingDao shippingDao;
	/**订单日志**/
    private IOrdersOPLogDao ordersOPLogDao;
	/**
	 * 更改订单已发货状态(走物流公司)
	 * @return
	 */
	@SuppressWarnings("static-access")
	public Orders saveOrUpdateChangeShipments(Orders orders ,Shipping ship,Customer customer,ShopInfo shopInfo,Sonaccount son){
		//修改订单
		Orders order = new Orders();
		order = ordersDao.get(" where o.ordersId="+orders.getOrdersId()+" and o.shopInfoId="+shopInfo.getShopInfoId());
		order.setSendType(1);
		order.setOrdersState(4);
		order.setUpdateTime(new Date());
		ordersDao.saveOrUpdateObject(order);
		//插入日志
		OrdersOPLog ordersOPLog = new OrdersOPLog();
		ordersOPLog.setOrdersId(order.getOrdersId());
		ordersOPLog.setOrdersNo(order.getOrdersNo());
		ordersOPLog.setOperatorTime(new Date());
		ordersOPLog.setOrdersOperateState(4);
		ordersOPLog.setOoperatorId(customer.getCustomerId());//操作人id
		ordersOPLog.setOoperatorSource("3");//1、后台用户；2、后台系统；3、前台顾客；
		/*if(son!=null){
			ordersOPLog.setOperatorName(shopInfo.getCompanyName()+" : "+son.getNickname());//操作人name
		}else{
			ordersOPLog.setOperatorName(shopInfo.getCompanyName());//操作人name
		}*/
		ordersOPLog.setOperatorName(shopInfo.getCompanyName());//操作人name
		ordersOPLogDao.saveOrUpdateObject(ordersOPLog);//保存订单日志
		//物流信息
		SerialNumberUtil serialNumberUtil = new SerialNumberUtil();
		Shipping shipping = new Shipping();
		shipping = ship;
		shipping.setShippingSn(serialNumberUtil.ShippingSnNumber());
		shipping.setOrdersId(orders.getOrdersId());
		shipping.setCreateDate(new Date());
		shippingDao.saveOrUpdateObject(shipping);
		return order;
	}
	/**
	 * 更改订单已发货状态(走同城快递)
	 * @return
	 */
	@SuppressWarnings("static-access")
	public Orders saveOrUpdateChangeShipments(Orders orders ,CityCourier cityCourier,Customer customer,ShopInfo shopInfo,Sonaccount son){
		//修改订单
		Orders order = new Orders();
		order = ordersDao.get(" where o.ordersId="+orders.getOrdersId()+" and o.shopInfoId="+shopInfo.getShopInfoId());
		order.setSendType(2);
		order.setOrdersState(4);
		order.setUpdateTime(new Date());
		ordersDao.saveOrUpdateObject(order);
		//插入日志
		OrdersOPLog ordersOPLog = new OrdersOPLog();
		ordersOPLog.setOrdersId(order.getOrdersId());
		ordersOPLog.setOrdersNo(order.getOrdersNo());
		ordersOPLog.setOperatorTime(new Date());
		ordersOPLog.setOrdersOperateState(4);
		ordersOPLog.setOoperatorId(customer.getCustomerId());//操作人id
		ordersOPLog.setOoperatorSource("3");//1、后台用户；2、后台系统；3、前台顾客；
		/*if(son!=null){
			ordersOPLog.setOperatorName(shopInfo.getCompanyName()+" : "+son.getNickname());//操作人name
		}else{
			ordersOPLog.setOperatorName(shopInfo.getCompanyName());//操作人name
		}*/
		ordersOPLog.setOperatorName(shopInfo.getCompanyName());//操作人name
		ordersOPLogDao.saveOrUpdateObject(ordersOPLog);//保存订单日志
		//物流信息
		SerialNumberUtil serialNumberUtil = new SerialNumberUtil();
		Shipping shipping = new Shipping();
		shipping.setShippingSn(serialNumberUtil.ShippingSnNumber());
		shipping.setCode("tongcheng");
		shipping.setDeliverySn(cityCourier.getCityCourierId()+"");
		shipping.setOrdersId(orders.getOrdersId());
		shipping.setCreateDate(new Date());
		Map<String, Object> jsonMap = new HashMap<String, Object>();// 定义map
		jsonMap.put("cityCourierId", cityCourier.getCityCourierId());
		jsonMap.put("cityCourierName", cityCourier.getCityCourierName());
		jsonMap.put("responsibleAreas", cityCourier.getResponsibleAreas());
		jsonMap.put("phone", cityCourier.getPhone());
		jsonMap.put("address", cityCourier.getAddress());
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd"));
		JSONObject jo = JSONObject.fromObject(jsonMap, jsonConfig);
		shipping.setExpressInfo(jo.toString());
		shippingDao.saveOrUpdateObject(shipping);
		return order;
	}
	/**
	 * 更改订单正在配送状态
	 * @return
	 */
	public Orders saveOrUpdateChanggeOrdersState(Orders orders ,Customer customer,ShopInfo shopInfo,Sonaccount son){
		//修改订单
		Orders order = new Orders();
		order = ordersDao.get(" where o.ordersId="+orders.getOrdersId()+" and o.shopInfoId="+shopInfo.getShopInfoId());
		order.setOrdersState(3);
		order.setUpdateTime(new Date());
		ordersDao.saveOrUpdateObject(order);
		//插入日志
		OrdersOPLog ordersOPLog = new OrdersOPLog();
		ordersOPLog.setOrdersId(order.getOrdersId());
		ordersOPLog.setOrdersNo(order.getOrdersNo());
		ordersOPLog.setOperatorTime(new Date());
		ordersOPLog.setOrdersOperateState(3);
		ordersOPLog.setOoperatorId(customer.getCustomerId());//操作人id
		ordersOPLog.setOoperatorSource("3");//1、后台用户；2、后台系统；3、前台顾客；
		/*if(son!=null){
			ordersOPLog.setOperatorName(shopInfo.getCompanyName()+" : "+son.getNickname());//操作人name
		}else{
			ordersOPLog.setOperatorName(shopInfo.getCompanyName());//操作人name
		}*/
		ordersOPLog.setOperatorName(shopInfo.getCompanyName());//操作人name
		ordersOPLogDao.saveOrUpdateObject(ordersOPLog);//保存订单日志
		return order;
	}
	public void setOrdersDao(IOrdersDao ordersDao) {
		this.baseDao=this.ordersDao = ordersDao;
	}
	public void setShippingDao(IShippingDao shippingDao) {
		this.shippingDao = shippingDao;
	}
	public void setOrdersOPLogDao(IOrdersOPLogDao ordersOPLogDao) {
		this.ordersOPLogDao = ordersOPLogDao;
	}
}
